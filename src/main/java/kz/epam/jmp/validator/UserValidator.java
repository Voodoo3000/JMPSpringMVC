package kz.epam.jmp.validator;

import kz.epam.jmp.entity.Entity;
import kz.epam.jmp.entity.User;
import kz.epam.jmp.util.LocaleUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UserValidator {

    private UserValidator() {
    }

    public static boolean validateLoginEmail(String loginEmail) {
        return Pattern.compile(Entity.EMAIL_REGEX).matcher(loginEmail).matches();
    }

    public static boolean validatePassword(String password) {
        return Pattern.compile(Entity.PASSWORD_REGEX).matcher(password).matches();
    }

    public static boolean validateNames(String names) {
        return Pattern.compile(Entity.NAMES_REGEX).matcher(names).matches();
    }
}

