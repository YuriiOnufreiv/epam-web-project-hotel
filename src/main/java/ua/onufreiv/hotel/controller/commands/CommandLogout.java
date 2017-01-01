package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.MessageConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yurii on 12/27/16.
 */
public class CommandLogout implements ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
            page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
        } else {
            request.setAttribute("errorMessage", MessageConfig.getInstance().getProperty(MessageConfig.LOGIN_ERROR_MESSAGE));
            page = PathConfig.getInstance().getProperty(PathConfig.ERROR_PAGE_PATH);
        }
        return page;
    }
}
