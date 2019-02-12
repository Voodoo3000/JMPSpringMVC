package kz.epam.jmp.controller;

import kz.epam.jmp.dao.DaoException;
import kz.epam.jmp.dao.sql.UserDao;
import kz.epam.jmp.entity.CustomerOrder;
import kz.epam.jmp.entity.Entity;
import kz.epam.jmp.entity.User;
import kz.epam.jmp.util.LocaleUtil;
import kz.epam.jmp.validator.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "sign_up", method = RequestMethod.POST)
    public String signUp(@ModelAttribute User userModel, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = LocaleUtil.getSessionLocale(request);
        ResourceBundle RB = ResourceBundle.getBundle(Entity.RB_NAME, locale);
        String busyLoginErrMsg = RB.getString(Entity.ERROR_BUSY_LOGIN);
        String passMisErrMsg = RB.getString(Entity.ERROR_PASS_MISMATCH);
        String invalidLoginEmailErrMsg = RB.getString(Entity.ERROR_INVALID_LOGIN_EMAIL);
        String undesirablePassErrMsg = RB.getString(Entity.ERROR_UNDESIRABLE_PASSWORD);
        String invalidFirstNameErrMsg = RB.getString(Entity.ERROR_INVALID_FIRST_NAME);
        String invalidLastNameErrMsg = RB.getString(Entity.ERROR_INVALID_LAST_NAME);

        String newRePassword = request.getParameter(Entity.PARAM_RE_NEW_PASSWORD);

        boolean loginEmailValidResult;
        boolean passwordValidResult;
        boolean firstNameValidResult;
        boolean lastNameValidResult;
        loginEmailValidResult = UserValidator.validateLoginEmail(userModel.getLoginEmail());
        passwordValidResult = UserValidator.validatePassword(userModel.getPassword());
        firstNameValidResult = UserValidator.validateNames(userModel.getFirstName());
        lastNameValidResult = UserValidator.validateNames(userModel.getLastName());
        User user;

        try {
            if (!loginEmailValidResult) {
                LOGGER.info("Invalid login(email address)");
                session.setAttribute(Entity.ERROR, invalidLoginEmailErrMsg);
            } else if (!passwordValidResult) {
                LOGGER.info("Invalid password");
                session.setAttribute(Entity.ERROR, undesirablePassErrMsg);
            } else if (!firstNameValidResult) {
                LOGGER.info(Entity.FIRST_NAME_MSG);
                session.setAttribute(Entity.ERROR, invalidFirstNameErrMsg);
            } else if (!lastNameValidResult) {
                LOGGER.info(Entity.LAST_NAME_MSG);
                session.setAttribute(Entity.ERROR, invalidLastNameErrMsg);
            } else if (userDao.getByLogin(userModel.getLoginEmail()) != null) {
                LOGGER.info("Entered login is busy");
                session.setAttribute(Entity.ERROR, busyLoginErrMsg);
            } else if (!userModel.getPassword().equals(newRePassword)) {
                LOGGER.info(Entity.PASS_MSG);
                session.setAttribute(Entity.ERROR, passMisErrMsg);
            } else {
                user = createUser(userModel.getLoginEmail(), userModel.getPassword(), userModel.getFirstName(), userModel.getLastName(), userDao);
                LOGGER.info("Newly registered user is: " + user.toString());
                session.setAttribute(Entity.ATTR_USER, user);
            }
        } catch (DaoException e) {
            LOGGER.error("DaoException in SignUpCommand", e);
        }
        return "redirect:" + Entity.MAIN_PAGE;
    }

    private User createUser(String loginEmail, String password, String firstName, String lastName, UserDao userDao) throws DaoException {
        User user;
        user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLoginEmail(loginEmail);
        user.setPassword(password);
        user.setRole(User.Role.CLIENT);
        user.setWallet(Entity.ZERO);
        user.setState(User.State.ENABLED);
        userDao.add(user);
        return userDao.getByLogin(loginEmail);
    }

    @RequestMapping(value = "sign_in", method = RequestMethod.POST)
    public String signIn(@ModelAttribute User userModel, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = LocaleUtil.getSessionLocale(request);
        ResourceBundle RB = ResourceBundle.getBundle(Entity.RB_NAME, locale);
        String loginPassErrMsg = RB.getString(Entity.ERROR_LOGIN_PASS);
        String bannedUserErrMsg = RB.getString(Entity.ERROR_USER_BAN);
        User user = null;
        String result;
        try {
            userDao = new UserDao();
            user = userDao.getByLogin(userModel.getLoginEmail());
        } catch (DaoException e) {
            LOGGER.error("DaoException in SignInCommand", e);
        }
        if (user == null || !user.getLoginEmail().equals(userModel.getLoginEmail()) || !user.getPassword().equals(userModel.getPassword())) {
            LOGGER.info("Wrong login or password");
            session.setAttribute(Entity.ERROR, loginPassErrMsg);
            result = Entity.MAIN_PAGE;
        } else if (user.getState() == User.State.DISABLED) {
            LOGGER.info("Requested user was banned");
            session.setAttribute(Entity.ERROR, bannedUserErrMsg);
            result = Entity.MAIN_PAGE;
        } else if (user.getRole() == User.Role.ADMIN) {
            session.setAttribute(Entity.ATTR_USER, user);
            session.removeAttribute(Entity.ERROR);
            LOGGER.info("Administrator has logged in");
            result = Entity.ADMIN_PAGE;
        } else {
            session.setAttribute(Entity.ATTR_USER, user);
            session.removeAttribute(Entity.ERROR);
            LOGGER.info("User " + user.getLoginEmail() + " has logged in");
            result = Entity.MAIN_PAGE;
        }
        return "redirect:" + result;
    }

    @RequestMapping(value = "sign_out", method = RequestMethod.GET)
    public String signOut(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(Entity.ATTR_USER);
        if (user == null) return "redirect:" + Entity.MAIN_PAGE;

        request.getSession().removeAttribute(Entity.ATTR_CONTENT_LIST);
        request.getSession().removeAttribute(Entity.ATTR_USER);
        request.getSession().removeAttribute(Entity.ATTR_ORDER);
        LOGGER.info("User signed out, " + user.getLoginEmail() + " user session is closed");

        return "redirect:" + Entity.MAIN_PAGE;
    }

    @RequestMapping(value = "edit_user", method = RequestMethod.POST)
    public String editUser(@ModelAttribute User userModel, @RequestParam String newPassword,
                           @RequestParam String newRePassword,  HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = LocaleUtil.getSessionLocale(request);
        ResourceBundle RB = ResourceBundle.getBundle(Entity.RB_NAME, locale);
        String passErrMsg = RB.getString(Entity.ERROR_PASS);
        String passMisErrMsg = RB.getString(Entity.ERROR_PASS_MISMATCH);
        String invalidFirstNameErrMsg = RB.getString(Entity.ERROR_INVALID_FIRST_NAME);
        String invalidLastNameErrMsg = RB.getString(Entity.ERROR_INVALID_LAST_NAME);

        User user;

        boolean firstNameValidResult;
        boolean lastNameValidResult;
        firstNameValidResult = UserValidator.validateNames(userModel.getFirstName());
        lastNameValidResult = UserValidator.validateNames(userModel.getLastName());
        try {
            user = userDao.getByLogin(userModel.getLoginEmail());
            if (!firstNameValidResult) {
                LOGGER.info(Entity.FIRST_NAME_MSG);
                session.setAttribute(Entity.ERROR, invalidFirstNameErrMsg);
            } else if (!lastNameValidResult) {
                LOGGER.info(Entity.LAST_NAME_MSG);
                session.setAttribute(Entity.ERROR, invalidLastNameErrMsg);
            } else if (!userModel.getPassword().equals(user.getPassword())) {
                session.setAttribute(Entity.ERROR, passErrMsg);
                LOGGER.info("Wrong password");
            } else if (!newPassword.equals(newRePassword)) {
                session.setAttribute(Entity.ERROR, passMisErrMsg);
                LOGGER.info(Entity.PASS_MSG);
            } else {
                updateUser(user, userModel.getFirstName(), userModel.getLastName(), newPassword, userDao, request);
            }
        } catch (DaoException e) {
            LOGGER.error("DaoException in EditUserCommand", e);
        }
        return "redirect:open_user_cabinet";
    }

    private void updateUser(User user, String firstName, String lastName, String newPassword, UserDao userDao, HttpServletRequest request) throws DaoException {
        boolean passwordValidResult;
        passwordValidResult = UserValidator.validatePassword(newPassword);
        HttpSession session = request.getSession();
        Locale locale = LocaleUtil.getSessionLocale(request);
        ResourceBundle RB = ResourceBundle.getBundle(Entity.RB_NAME, locale);
        String undesirable_pass_err_msg = RB.getString(Entity.ERROR_UNDESIRABLE_PASSWORD);
        if (!newPassword.isEmpty()) {
            if (passwordValidResult){
                user.setPassword(newPassword);
            }
            else {
                LOGGER.info("Invalid login(email address)");
                session.setAttribute(Entity.ERROR, undesirable_pass_err_msg);
            }
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userDao.update(user);
        LOGGER.info("User " + user.getLoginEmail() + " was changed by himself");
        request.getSession().setAttribute(Entity.ATTR_USER, user);
    }
}
