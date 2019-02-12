package kz.epam.jmp.entity;

import kz.epam.jmp.connection.ConnectionPool;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class Entity {

    public static final String PARAM_ID = "id";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_COUNT = "count";
    public static final String PARAM_PRICE_PER_LITER = "pricePerLiter";
    public static final String PARAM_STATUS = "status";
    public static final String PARAM_STATES = "states";
    public static final String PARAM_STATUSES = "statuses";
    public static final String PARAM_FIRST_NAME = "firstName";
    public static final String PARAM_LAST_NAME = "lastName";
    public static final String PARAM_ROLE = "role";
    public static final String PARAM_ROLES = "roles";
    public static final String PARAM_WALLET = "wallet";
    public static final String PARAM_STATE = "state";
    public static final String PARAM_LOGIN_EMAIL = "loginEmail";
    public static final String PARAM_CURRENT_PASSWORD = "password";
    public static final String PARAM_NEW_PASSWORD = "newPassword";
    public static final String PARAM_RE_NEW_PASSWORD = "newRePassword";
    public static final String PARAM_ADDRESS_CITY = "city";
    public static final String PARAM_ADDRESS_STREET = "street";
    public static final String PARAM_ADDRESS_HOUSE = "house";
    public static final String PARAM_ADDRESS_APARTMENT = "apartment";
    public static final String PARAM_ADDRESS_PHONE = "phone";
    public static final String PARAM_TOTAL_AMOUNT = "totalAmount";

    public static final String ATTR_USER = "user";
    public static final String ATTR_ORDER = "order";
    public static final String ATTR_BOTTLE_SIZES = "bottleSizes";
    public static final String ATTR_ORDER_LIST = "orderList";
    public static final String ATTR_USER_LIST = "userList";
    public static final String ATTR_WATER_LIST = "waterList";
    public static final String ATTR_CONTENT_LIST = "contentList";
    public static final String ATTR_ADDRESS = "address";

    public static final String PASS_MSG = "Entered password mismatching";
    public static final String FIRST_NAME_MSG = "Invalid first name";
    public static final String LAST_NAME_MSG = "Invalid last name";
    public static final String CUSTOMER_ORDER_DAO_EXCEPTION = "DaoException in CustomerOrder";

    public static final String ERROR = "errormsg";
    public static final String ERROR_PASS = "error.wrong_pass";
    public static final String ERROR_PASS_MISMATCH = "error.password_mismatching";
    public static final String ERROR_LOGIN_PASS = "error.wrong_user_or_pass";
    public static final String ERROR_USER_BAN = "error.banned_user";
    public static final String ERROR_BUSY_LOGIN = "error.busy_login";
    public static final String ERROR_INVALID_LOGIN_EMAIL = "error.invalid_loginEmail";
    public static final String ERROR_UNDESIRABLE_PASSWORD = "error.undesirable_password";
    public static final String ERROR_INVALID_FIRST_NAME = "error.invalid_first_name";
    public static final String ERROR_INVALID_LAST_NAME = "error.invalid_last_name";
    public static final String MESSAGE_ERROR404 = "error.404";
    public static final String MESSAGE_ERROR500 = "error.500";
    public static final String ERROR404 = "error404";
    public static final String ERROR500 = "error500";

    public static final String OPEN_CUSTOMER_CART = "open_customer_cart";
    public static final String ADMIN_PAGE = "admin_user_page";

    public static final String CUSTOMER_CABINET = "customer_cabinet";

    public static final String MAIN_PAGE = "main";
    public static final String ADMIN_ORDERS_PAGE = "admin_orders";
    public static final String ADMIN_WATER_PAGE = "admin_water";
    public static final String ADMIN_BOTTLE_SIZE_PAGE = "admin_bottle_size";
    public static final String ERROR_PAGE = "error_page";
    public static final String CUSTOMER_CART_PAGE = "customer_cart";
    public static final String COMPLETED_ORDER = "completed_order";

    public static final String EMAIL_REGEX = "[\\w\\u002E\\u005F]{0,20}@([a-zA-Z]+\\u002E){1,2}[a-zA-Z]{2,3}";
    public static final String PASSWORD_REGEX = "[\\w]{3,20}";
    public static final String NAMES_REGEX = "([A-Z]{1}[a-z]{0,19})|([\\u0410-\\u042F]{1}[\\u0430-\\u044F]{0,19})";

    public static final String REFERER = "Referer";

    public static final String LOCALE = "locale";
    public static final Locale DEFAULT_LOCALE = Locale.getDefault();
    public static final String RB_NAME = "i18n.message";
    public static final String UTF = "UTF-8";

    private static final ResourceBundle RB = ResourceBundle.getBundle("database");
    public static final String DRIVER = RB.getString("db.driver");
    public static final String URL = RB.getString("db.url");
    public static final String LOGIN = RB.getString("db.user");
    public static final String PASSWORD = RB.getString("db.password");
    public static final int POOL_SIZE = Integer.parseInt(RB.getString("db.pool_size"));
    public static final ConnectionPool INSTANCE = new ConnectionPool();

    public static final double ZERO = 0;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
