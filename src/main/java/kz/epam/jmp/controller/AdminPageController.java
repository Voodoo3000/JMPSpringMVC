package kz.epam.jmp.controller;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.sql.*;
import kz.epam.jmp.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminPageController {

    private static final Logger LOGGER = Logger.getLogger(AdminPageController.class);

    @Autowired
    private WaterDao waterDao;
    @Autowired
    private BottleSizeDao sizeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderContentDao contentDao;
    @Autowired
    private CustomerOrderDao orderDao;

    @RequestMapping(value = "admin_user_page", method = RequestMethod.GET)
    public ModelAndView openAdminUserPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin_users");

        List<User> userList = null;
        try {
            userList = userDao.getAll();
        } catch (DaoException e) {
            LOGGER.error("DaoException in OpenAdminPageCommand", e);
        }
        if (userList != null) model.addObject(Entity.ATTR_USER_LIST, userList);
        model.addObject(Entity.PARAM_ROLES, User.Role.values());
        model.addObject(Entity.PARAM_STATES, User.State.values());
        return model;
    }

    @RequestMapping(value = "admin_order_page", method = RequestMethod.GET)
    public ModelAndView openAdminOrderPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin_orders");
        List<CustomerOrder> orderList = null;
        List<OrderContent> contentList = null;
        try {
            orderList = orderDao.getAll();
            contentList = contentDao.getAll();
        } catch (DaoException e) {
            LOGGER.error("DaoException in OpenAdminPageCommand", e);
        }
        if (orderList != null) model.addObject(Entity.ATTR_ORDER_LIST, orderList);
        if (contentList != null) model.addObject(Entity.ATTR_CONTENT_LIST, contentList);
        model.addObject(Entity.PARAM_STATUSES, CustomerOrder.Status.values());
        return model;
    }

    @RequestMapping(value = "admin_water_page", method = RequestMethod.GET)
    public ModelAndView openAdminWaterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin_water");
        try {
        modelAndView.addObject("waterList", waterDao.getAll());
        } catch (DaoException e) {
            LOGGER.error("DaoException in SpringController", e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "admin_bottle_page", method = RequestMethod.GET)
    public ModelAndView openAdminBottlePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin_bottle_size");
        try {
            modelAndView.addObject("bottleSizes", sizeDao.getAll());
        } catch (DaoException e) {
            LOGGER.error("DaoException in SpringController", e);
        }
        return modelAndView;
    }
}
