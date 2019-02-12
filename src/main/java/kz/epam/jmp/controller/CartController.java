package kz.epam.jmp.controller;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.sql.*;
import kz.epam.jmp.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CartController {

    private static final Logger LOGGER = Logger.getLogger(CartController.class);

    @Autowired
    private CustomerOrderDao orderDao;
    @Autowired
    private WaterDao waterDao;
    @Autowired
    private BottleSizeDao sizeDao;
    @Autowired
    private OrderContentDao contentDao;
    @Autowired
    private CustomerAddressDao addressDao;

    @RequestMapping(value = "add_to_cart", method = RequestMethod.GET)
    public String addToCart(@RequestParam String type, @RequestParam double size,
                            @RequestParam int count, HttpServletRequest request) {

        Water water;
        BottleSize bottle;
        CustomerOrder order;
        OrderContent content = new OrderContent();

        User user = (User) request.getSession().getAttribute(Entity.ATTR_USER);
        try {
            water = waterDao.getByType(type);
            bottle = sizeDao.getBySize(size);
            order = orderDao.getCreatingOrderByUserId(user.getId());
            if (order == null) {
                order = new CustomerOrder();
                order.setCustomerId(user.getId());
                order.setStatus(CustomerOrder.Status.CREATING);
                orderDao.add(order);
                LOGGER.info("Order was created");
            }
            content.setWater(water);
            content.setBottleSize(bottle);
            content.setQuantity(count);
            content.setCustomerOrderId(orderDao.getByUserId(user.getId()));
            contentDao.add(content);
            LOGGER.info("Content was created");
        } catch (DaoException e) {
            LOGGER.error("DaoException in AddToCartCommand", e);
        }
        return "redirect:" + Entity.MAIN_PAGE;
    }

    @RequestMapping(value = "open_customer_cart", method = RequestMethod.GET)
    public ModelAndView openCustomerCart(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.setViewName(Entity.CUSTOMER_CART_PAGE);

        double totalAmount = Entity.ZERO;
        CustomerOrder order;
        CustomerAddress address;

        User user = (User) request.getSession().getAttribute(Entity.ATTR_USER);
        try {
            order = orderDao.getCreatingOrderByUserId(user.getId());
            LOGGER.info("Getting current order");
            totalAmount = getCustomerOrderContent(model, totalAmount, order, orderDao, contentDao);
            address = addressDao.getByCustomerId(user.getId());
            addAndUpdateAddress(model, address, addressDao, user);
        } catch (DaoException e) {
            LOGGER.error("DaoException in OpenCartCommand", e);
        }
        model.addObject(Entity.PARAM_TOTAL_AMOUNT, totalAmount);

        return model;
    }

    private double getCustomerOrderContent(ModelAndView model, double totalAmount, CustomerOrder order, CustomerOrderDao orderDao, OrderContentDao contentDao) throws DaoException {
        if (order != null) {
            List<OrderContent> contentReal;
            contentReal = contentDao.getAllByCustomerOrderId(order.getId());
            model.addObject(Entity.ATTR_CONTENT_LIST, contentReal);
            for (OrderContent content : contentReal) {
                totalAmount = totalAmount + (content.getWater().getPricePerLiter() * content.getBottleSize().getSize() * content.getQuantity());
            }
            order.setAmount(totalAmount);
            orderDao.update(order);
            LOGGER.info("Order price was calculated");
        }
        return totalAmount;
    }

    private void addAndUpdateAddress(ModelAndView model, CustomerAddress address, CustomerAddressDao addressDao, User user) throws DaoException {
        if (address != null) {
            model.addObject(Entity.ATTR_ADDRESS, address);
        } else {
            address = new CustomerAddress();
            address.setCustomerId(user.getId());
            addressDao.add(address);
            LOGGER.info("Address was created");
        }
    }
}
