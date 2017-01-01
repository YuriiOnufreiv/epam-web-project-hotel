package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yurii on 12/29/16.
 */
public class CommandRedirect implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        if(request.getSession(false).getAttribute("user") != null) {
            page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
        } else {
            page = PathConfig.getInstance().getProperty(PathConfig.LOGIN_PAGE_PATH);
        }
        return page;
    }
}
