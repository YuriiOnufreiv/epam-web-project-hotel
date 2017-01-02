package ua.onufreiv.hotel.controller.manager;

import java.util.ResourceBundle;

/**
 * Created by yurii on 12/27/16.
 */
public class PathConfig {
    public static final String DATABASE_DRIVER_NAME = "DATABASE_DRIVER_NAME";
    public static final String DATABASE_URL = "DATABASE_URL";
    public static final String ERROR_PAGE_PATH = "ERROR_PAGE_PATH";
    public static final String LOGIN_PAGE_PATH = "LOGIN_PAGE_PATH";
    public static final String MAIN_PAGE_PATH = "MAIN_PAGE_PATH";
    public static final String REGISTER_PAGE_PATH = "REGISTER_PAGE_PATH";
    public static final String RESERVATION_PAGE_PATH = "RESERVATION_PAGE_PATH";
    private static final String BUNDLE_NAME = "paths";

    private static PathConfig instance;

    private ResourceBundle resourceBundle;

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
