package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.JspConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.AuthService;
import ua.onufreiv.hotel.service.impl.AuthServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static ua.onufreiv.hotel.controller.manager.JspConfig.*;

/**
 * Created by yurii on 12/27/16.
 */
public class CommandLogin implements Command {
    private final AuthService authServiceImpl;
    private final JspConfig jspConfig;

    public CommandLogin() {
        jspConfig = JspConfig.getInstance();
        authServiceImpl = AuthServiceImpl.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(jspConfig.get(USER_EMAIL_NAME));
        String pass = request.getParameter(jspConfig.get(USER_PASSWORD_NAME));

        User user = authServiceImpl.authenticate(login, pass);
        HttpSession session = request.getSession(false);

        String page;
        if (user != null && session != null) {
            session.setAttribute(jspConfig.get(USER_NAME), user);
            page = PathConfig.getInstance().getProperty(PathConfig.REDIRECT_TO_HOME_COMMAND_PATH);
        } else {
            request.setAttribute(jspConfig.get(INVALID_LOGIN_ERROR_NAME), true);
            page = PathConfig.getInstance().getProperty(PathConfig.LOGIN_PAGE_PATH);
        }

        return page;
    }
}