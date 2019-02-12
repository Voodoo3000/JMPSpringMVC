package kz.epam.jmp.controller;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.sql.*;
import kz.epam.jmp.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class OrderController {
    private static final Logger LOGGER = Logger.getLogger(OrderController.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerOrderDao orderDao;
    @Autowired
    private OrderContentDao contentDao;
    @Autowired
    private CustomerAddressDao addressDao;

    @RequestMapping(value = "cancel_order", method = RequestMethod.POST)
    public String cancelOrder(@RequestParam int id, HttpServletRequest request) {
        User user = null;
        CustomerOrder order;
        try {
            order = orderDao.getById(id);
            user = userDao.getById(order.getCustomerId());
            user.setWallet(user.getWallet() + order.getAmount());
            userDao.update(user);
            LOGGER.info("Money was refunded to the customer because of order cancellation");
            order.setStatus(CustomerOrder.Status.CANCELLED);
            orderDao.update(order);
            LOGGER.info("Order was cancelled by customer");
        } catch (DaoException e) {
            LOGGER.error("DaoException in CancelOrderCommand", e);
        }
        request.getSession().setAttribute(Entity.ATTR_USER, user);
        return "redirect:open_user_cabinet";
    }

    @RequestMapping(value = "remove_content", method = RequestMethod.POST)
    public String removeContent(@RequestParam int contentPositionId) {
        OrderContent content = new OrderContent();
        content.setId(contentPositionId);
        try {
            contentDao.remove(content);
            LOGGER.info("Content was removed");
        } catch (DaoException e) {
            LOGGER.error("DaoException in RemoveContentCommand", e);
        }
        return "redirect:" + Entity.OPEN_CUSTOMER_CART;
    }

    @RequestMapping(value = "get_order", method = RequestMethod.POST)
    public ModelAndView getOrder(@ModelAttribute CustomerAddress addressModel, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();

        double charge;
        CustomerOrder order;
        CustomerAddress address;
        Date orderDate = new Date();

        User user = (User) request.getSession().getAttribute(Entity.ATTR_USER);
        try {
            order = orderDao.getCreatingOrderByUserId(user.getId());
            if (order.getAmount() <= user.getWallet()) {
                order.setOrderDate(orderDate);
                order.setStatus(CustomerOrder.Status.PREPARATION);
                orderDao.update(order);
                LOGGER.info("Customer ordered water delivering");
                charge = user.getWallet() - order.getAmount();
                user.setWallet(charge);
                userDao.update(user);
                LOGGER.info("Order price was charged from customer wallet");
                address = addressDao.getByCustomerId(user.getId());
                address.setCity(addressModel.getCity());
                address.setStreet(addressModel.getStreet());
                address.setHouseNumber(addressModel.getHouseNumber());
                address.setApartmentNumber(addressModel.getApartmentNumber());
                address.setPhoneNumber(addressModel.getPhoneNumber());
                addressDao.update(address);
                LOGGER.info("Customer address was filled");
                model.setViewName(Entity.COMPLETED_ORDER);
            } else {
                model.setViewName(Entity.CUSTOMER_CART_PAGE);
            }
        } catch (DaoException e) {
            LOGGER.error("DaoException in GetOrderCommand", e);
        }
        return model;
    }
}
