package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.impl.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yurii on 12/27/16.
 */
public class CommandLogin implements Command {
    private static final String EMAIL_NAME = "email";
    private static final String PASSWORD_NAME = "password";
    private static final String USER_NAME = "user";
    private static final String ERROR_MESSAGE_NAME = "errorMessage";

    private static final String REDIRECT_TO_HOME_PATH = "/hotel?command=redirect";

    private AuthService authService;

    public CommandLogin() {
        authService = new AuthService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(EMAIL_NAME);
        String pass = request.getParameter(PASSWORD_NAME);

        User user = authService.authenticate(login, pass);
        HttpSession session = request.getSession(false);

        if (user != null && session != null) {
            session.setAttribute(USER_NAME, user);
        } else {
            request.setAttribute(ERROR_MESSAGE_NAME, true);
            return PathConfig.getInstance().getProperty(PathConfig.LOGIN_PAGE_PATH);
        }

        return REDIRECT_TO_HOME_PATH;
    }
}