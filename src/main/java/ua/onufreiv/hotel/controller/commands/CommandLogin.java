package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.controller.manager.MessageConfig;
import ua.onufreiv.hotel.entities.User;
import ua.onufreiv.hotel.service.impl.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yurii on 12/27/16.
 */
public class CommandLogin implements ICommand {
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthService authService = new AuthService();
        String page;
        String login = request.getParameter(PARAM_NAME_EMAIL);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        User user = authService.authenticate(login, pass);
        HttpSession session = request.getSession(false);
        if (user != null && session != null) {
            session.setAttribute("user", user);
            page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
        } else {
            request.setAttribute("errorMessage", MessageConfig.getInstance().getProperty(MessageConfig.LOGIN_ERROR_MESSAGE));
            page = PathConfig.getInstance().getProperty(PathConfig.LOGIN_PAGE_PATH);
        }
        return page;
    }
}
