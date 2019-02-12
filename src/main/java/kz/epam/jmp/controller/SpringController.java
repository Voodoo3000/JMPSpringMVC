package kz.epam.jmp.controller;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.sql.*;
import kz.epam.jmp.entity.CustomerOrder;
import kz.epam.jmp.entity.Entity;
import kz.epam.jmp.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

@Controller
public class SpringController {
    private static final Logger LOGGER = Logger.getLogger(SpringController.class);

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

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public ModelAndView openMainPage(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        model.setViewName(Entity.MAIN_PAGE);
        try {
            session.setAttribute("waterList", waterDao.getAll());
            session.setAttribute("bottleSizes", sizeDao.getAll());
        } catch (DaoException e) {
            LOGGER.error("DaoException in SpringController", e);
        }
        return model;
    }

    @RequestMapping(value = "locale", method = RequestMethod.GET)
    public String changeLocale(HttpServletRequest request) {
        String language = request.getParameter(Entity.LOCALE);
        Locale locale = new Locale(language);
        LOGGER.info("Selected locale is :" + locale);
        request.getSession().setAttribute(Entity.LOCALE, locale);
        String referer = request.getHeader(Entity.REFERER);
        referer = referer.substring(referer.lastIndexOf("/") + 1, referer.length());
        return "redirect:" + referer;
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public String refreshPage(HttpServletRequest request) {
        if (request.getSession().getAttribute(Entity.ERROR) != null) {
            request.getSession().removeAttribute(Entity.ERROR);
        }
        String referer = request.getHeader(Entity.REFERER);
        referer = referer.substring(referer.lastIndexOf("/") + 1, referer.length());
        return "redirect:" + referer;
    }

    @RequestMapping(value = "open_user_cabinet", method = RequestMethod.GET)
    public ModelAndView openUserCabinet(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Entity.ATTR_USER);
        List<CustomerOrder> orderList = null;
        model.setViewName(Entity.CUSTOMER_CABINET);
        try {
            orderList = orderDao.getAllByClientId(user.getId());
        } catch (DaoException e) {
            LOGGER.error("DaoException in OpenCabinetCommand", e);
        }
        if(orderList != null) model.addObject(Entity.ATTR_ORDER_LIST, orderList);
        return model;
    }
}
