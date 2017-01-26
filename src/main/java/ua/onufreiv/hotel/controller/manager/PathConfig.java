package ua.onufreiv.hotel.controller.manager;

import java.util.ResourceBundle;

/**
 * Created by yurii on 12/27/16.
 */
public class PathConfig {
    private static final String BUNDLE_NAME = "paths";

    public static final String ERROR_PAGE_PATH = "ERROR_PAGE_PATH";
    public static final String MAIN_PAGE_PATH = "MAIN_PAGE_PATH";
    public static final String LOGIN_PAGE_PATH = "LOGIN_PAGE_PATH";
    public static final String CREATE_ACCOUNT_PAGE_PATH = "CREATE_ACCOUNT_PAGE_PATH";
    public static final String NEW_BOOK_REQUEST_PAGE_PATH = "NEW_BOOK_REQUEST_PAGE_PATH";
    public static final String SHOW_ALL_REQUESTS_PAGE_PATH = "SHOW_ALL_REQUESTS_PAGE_PATH";
    public static final String NOT_SIGNED_IN_ERROR_PAGE_PATH = "NOT_SIGNED_IN_ERROR_PAGE_PATH";
    public static final String ADMIN_DASHBOARD_PAGE_PATH = "ADMIN_DASHBOARD_PAGE_PATH";
    public static final String PROCESS_REQUEST_PAGE_PATH = "PROCESS_REQUEST_PAGE_PATH";
    public static final String ADMIN_BILL_INFO_PAGE_PATH = "ADMIN_BILL_INFO_PAGE_PATH";
    public static final String CLIENT_BILL_INFO_PAGE_PATH = "CLIENT_BILL_INFO_PAGE_PATH";
    public static final String ADD_NEW_ROOM_TYPE_PAGE_PATH = "ADD_NEW_ROOM_TYPE_PAGE_PATH";
    public static final String ADD_NEW_ROOM_PAGE_PATH = "ADD_NEW_ROOM_PAGE_PATH";

    public static final String FORWARD_TO_NOT_SIGNED_IN_COMMAND_PATH = "FORWARD_TO_NOT_SIGNED_IN_COMMAND_PATH";
    public static final String PROCESS_BOOK_REQUEST_BY_ID_COMMAND_PATH = "PROCESS_BOOK_REQUEST_BY_ID_COMMAND_PATH";
    public static final String REDIRECT_TO_HOME_COMMAND_PATH = "REDIRECT_TO_HOME_COMMAND_PATH";
    public static final String SHOW_ADMIN_DASHBOARD_COMMAND_PATH = "SHOW_ADMIN_DASHBOARD_COMMAND_PATH";

    private static PathConfig instance;

    private ResourceBundle resourceBundle;

    private PathConfig() {
    }

    public static PathConfig getInstance() {
        if (instance == null) {
            instance = new PathConfig();
            instance.resourceBundle =
                    ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }
}
