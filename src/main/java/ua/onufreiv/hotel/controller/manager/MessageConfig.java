package ua.onufreiv.hotel.controller.manager;

import java.util.ResourceBundle;

/**
 * Created by yurii on 12/27/16.
 */
public class MessageConfig {
    public static final String LOGIN_ERROR_MESSAGE = "LOGIN_ERROR_MESSAGE";
    public static final String SERVLET_EXCEPTION_ERROR_MESSAGE = "SERVLET_EXCEPTION_ERROR_MESSAGE";
    public static final String IO_EXCEPTION_ERROR_MESSAGE = "IO_EXCEPTION_ERROR_MESSAGE";
    private static final String BUNDLE_NAME = "messages";

    private static MessageConfig instance;
    private ResourceBundle resourceBundle;

    public static MessageConfig getInstance() {
        if (instance == null) {
            instance = new MessageConfig();
            instance.resourceBundle =
                    ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String)resourceBundle.getObject(key);
    }
}
