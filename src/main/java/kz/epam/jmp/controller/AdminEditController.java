package kz.epam.jmp.controller;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.sql.*;
import kz.epam.jmp.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminEditController {
    private static final Logger LOGGER = Logger.getLogger(AdminEditController.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerOrderDao orderDao;
    @Autowired
    private WaterDao waterDao;
    @Autowired
    private BottleSizeDao sizeDao;

    @RequestMapping(value = "change_order_status", method = RequestMethod.POST)
    public String changeOrderStatus(@ModelAttribute CustomerOrder orderModel) {
        CustomerOrder order;
        User user;
        try {
            order = orderDao.getById(orderModel.getId());
            user = userDao.getById(order.getCustomerId());
            if (orderModel.getStatus() == CustomerOrder.Status.CREATING
                    || orderModel.getStatus() == CustomerOrder.Status.PREPARATION) {
                LOGGER.info("Assigning of " + orderModel.getStatus() + " status is prohibited");
            } else if (orderModel.getStatus() == CustomerOrder.Status.DELIVERED
                    && order.getStatus() == CustomerOrder.Status.CREATING
                    || orderModel.getStatus() == CustomerOrder.Status.CANCELLED
                    && order.getStatus() == CustomerOrder.Status.CREATING) {
                LOGGER.info("Assigning of " + orderModel.getStatus() + " status is prohibited it is in creation stage");
            } else if (orderModel.getStatus() == CustomerOrder.Status.DELIVERED
                    && order.getStatus() == CustomerOrder.Status.CANCELLED
                    || orderModel.getStatus() == CustomerOrder.Status.CANCELLED
                    && order.getStatus() == CustomerOrder.Status.DELIVERED) {
                LOGGER.info("Assigning of " + orderModel.getStatus() + " status is prohibited " + " it is already has " + order.getStatus() + " status");
            } else if (order.getStatus() == orderModel.getStatus()) {
                LOGGER.info("Status of order " + order + " already is " + orderModel.getStatus());
            } else if (orderModel.getStatus() == CustomerOrder.Status.CANCELLED
                    && order.getStatus() == CustomerOrder.Status.PREPARATION) {
                order.setStatus(orderModel.getStatus());
                user.setWallet(user.getWallet() + order.getAmount());
                userDao.update(user);
                LOGGER.info("Money was refunded to the customer because of order cancellation");
                orderDao.update(order);
                LOGGER.info("Status of order " + order + " was changed to " + orderModel.getStatus() + " by administrator");
            } else if (orderModel.getStatus() == CustomerOrder.Status.DELIVERED
                    && order.getStatus() == CustomerOrder.Status.PREPARATION) {
                order.setStatus(orderModel.getStatus());
                LOGGER.info("Status of order " + order + " was changed to " + orderModel.getStatus() + " by administrator");
                orderDao.update(order);
            }
        } catch (DaoException e) {
            LOGGER.error("DaoException in ChangeOrderCommand", e);
        }
        return "redirect:admin_order_page";
    }

    @RequestMapping(value = "change_user", method = RequestMethod.POST)
    public String changeUser(@ModelAttribute User userModel) {
        User user;
        try {
            user = userDao.getById(userModel.getId());
            user.setFirstName(userModel.getFirstName());
            user.setLastName(userModel.getLastName());

            user.setPassword(userModel.getPassword());
            user.setId(userModel.getId());
            user.setRole(userModel.getRole());
            user.setWallet(userModel.getWallet());
            user.setState(userModel.getState());
            userDao.update(user);
            LOGGER.info("User " + user + " was changed by administrator");
        } catch (DaoException e) {
            LOGGER.error("DaoException in ChangeUserCommand", e);
        }
        return "redirect:admin_user_page";
    }

    @RequestMapping(value = "change_bottle", method = RequestMethod.POST)
    public String changeBottle(@ModelAttribute BottleSize bottleModel) {
        BottleSize bottle;
        try {
            bottle = sizeDao.getById(bottleModel.getId());
            bottle.setSize(bottleModel.getSize());
            bottle.setId(bottleModel.getId());
            sizeDao.update(bottle);
            LOGGER.info("Size of bottle " + bottle + " was changed " + "to " + bottleModel.getSize() + " by administrator");
        } catch (DaoException e) {
            LOGGER.error("DaoException in ChangeBottleSizeCommand", e);
        }
        return "redirect:admin_bottle_page";
    }

    @RequestMapping(value = "create_size", method = RequestMethod.POST)
    public String createBottle(@ModelAttribute BottleSize sizeModel) {
        BottleSize bottleSize = new BottleSize();
        bottleSize.setSize(sizeModel.getSize());
        try {
            sizeDao.add(bottleSize);
            LOGGER.info("Size of bottle " + sizeModel.getSize() + " was created by administrator");
        } catch (DaoException e) {
            LOGGER.error("DaoException in CreateBottleSizeCommand", e);
        }
        return "redirect:admin_bottle_page";
    }

    @RequestMapping(value = "change_water", method = RequestMethod.POST)
    public String changeWater(@ModelAttribute Water waterModel) {
        Water water;
        try {
            water = waterDao.getById(waterModel.getId());
            water.setType(waterModel.getType());
            water.setPricePerLiter(waterModel.getPricePerLiter());
            water.setId(waterModel.getId());
            waterDao.update(water);
            LOGGER.info("Water " + water + " was changed by administrator");
        } catch (DaoException e) {
            LOGGER.error("DaoException in ChangeWaterCommand", e);
        }
        return "redirect:admin_water_page";
    }

    @RequestMapping(value = "create_water", method = RequestMethod.POST)
    public String createWater(@ModelAttribute Water waterForm) {
        Water water = new Water();
        water.setType(waterForm.getType());
        water.setPricePerLiter(waterForm.getPricePerLiter());
        try {
            waterDao.add(water);
            LOGGER.info("Water was created by administrator" + water.getType() + " price per liter " + water.getPricePerLiter() + " kzt");
        } catch (DaoException e) {
            LOGGER.error("DaoException in CreateWaterCommand", e);
        }
        return "redirect:admin_water_page";
    }
}
