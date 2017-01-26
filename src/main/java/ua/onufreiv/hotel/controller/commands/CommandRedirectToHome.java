package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.impl.UserRoleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.USER_NAME;

/**
 * Created by yurii on 12/29/16.
 */
public class CommandRedirectToHome implements Command {
    //    private static final String USER_NAME = "user";
    private static final String ADMIN_DASHBOARD_PATH = "/hotel?command=showAdminDashboard";

    private final ParamNamesConfig names;
    private UserRoleService userRoleService;

    public CommandRedirectToHome() {
        userRoleService = new UserRoleService();
        names = ParamNamesConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        User user = (User) request.getSession(false).getAttribute(names.get(USER_NAME));

        if (user != null && userRoleService.userIsAdmin(user)) {
            page = ADMIN_DASHBOARD_PATH;
        } else {
            page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
        }

        return page;
    }
}
