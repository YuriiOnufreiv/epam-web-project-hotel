package ua.onufreiv.hotel.controller.config;

import java.util.ResourceBundle;

/**
 * Helper class for dealing with the 'paths.properties' bundle
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/27/16.
 */
public class PathConfig {
    private static final String BUNDLE_NAME = "paths";

    // common pages
    public static final String MAIN_PAGE_PATH = "main.page.path";
    public static final String LOGIN_PAGE_PATH = "login.page.path";
    public static final String CREATE_ACCOUNT_PAGE_PATH = "create.account.page.path";
    public static final String NOT_SIGNED_IN_ERROR_PAGE_PATH = "not.signed.in.error.page.path";

    // client related pages
    public static final String NEW_BOOK_REQUEST_PAGE_PATH = "new.book.request.page.path";
    public static final String SHOW_ALL_REQUESTS_PAGE_PATH = "show.all.requests.page.path";
    public static final String CLIENT_BILL_INFO_PAGE_PATH = "client.bill.info.page.path";

    // admin related pages
    public static final String ADMIN_DASHBOARD_PAGE_PATH = "admin.main.page.path";
    public static final String PROCESS_REQUEST_PAGE_PATH = "process.request.page.path";
    public static final String ADMIN_BILL_INFO_PAGE_PATH = "admin.bill.info.page.path";
    public static final String ADD_NEW_ROOM_TYPE_PAGE_PATH = "add.new.room.type.page.path";
    public static final String ADD_NEW_ROOM_PAGE_PATH = "add.new.room.page.path";

    // commands paths
    public static final String FORWARD_TO_NOT_SIGNED_IN_COMMAND_PATH = "forward.to.not.signed.in.command.path";
    public static final String PROCESS_BOOK_REQUEST_BY_ID_COMMAND_PATH = "process.book.request.by.id.command.path";
    public static final String REDIRECT_TO_HOME_COMMAND_PATH = "redirect.to.home.command.path";
    public static final String SHOW_ADMIN_DASHBOARD_COMMAND_PATH = "show.admin.dashboard.command.path";
    public static final String SHOW_BILL_INFO_COMMAND_PATH = "show.bill.info.command.path";

    private static PathConfig instance;

    private ResourceBundle resourceBundle;

    private PathConfig() {
    }

    public static synchronized PathConfig getInstance() {
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
