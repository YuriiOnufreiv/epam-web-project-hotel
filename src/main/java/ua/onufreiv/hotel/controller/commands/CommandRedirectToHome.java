package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.JspConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.UserRoleService;
import ua.onufreiv.hotel.service.impl.UserRoleServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.manager.JspConfig.USER_NAME;

/**
 * Created by yurii on 12/29/16.
 */
public class CommandRedirectToHome implements Command {
    private final UserRoleService userRoleServiceImpl;
    private final JspConfig jspConfig;

    public CommandRedirectToHome() {
        userRoleServiceImpl = UserRoleServiceImpl.getInstance();
        jspConfig = JspConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        User user = (User) request.getSession(false).getAttribute(jspConfig.get(USER_NAME));

        if (user != null && userRoleServiceImpl.userIsAdmin(user)) {
            page = PathConfig.getInstance().getProperty(PathConfig.SHOW_ADMIN_DASHBOARD_COMMAND_PATH);
        } else {
            page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
        }

        return page;
    }
}
