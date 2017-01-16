package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.impl.UserRoleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yurii on 12/29/16.
 */
public class CommandRedirectToHome implements ICommand {
    private static final String ADMIN_DASHBOARD_PATH = "/hotel?command=showAdminDashboard";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        User user = (User) request.getSession(false).getAttribute("user");
        if (user != null) {
            UserRoleService userRoleService = new UserRoleService();

            if (userRoleService.isAdmin(user)) {
                page = ADMIN_DASHBOARD_PATH;
            } else {
                page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
            }

        } else {
            page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
        }
        return page;
    }
}
