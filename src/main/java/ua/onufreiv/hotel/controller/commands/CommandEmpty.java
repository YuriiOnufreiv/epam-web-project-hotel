package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yurii on 12/27/16.
 */
public class CommandEmpty implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathConfig.getInstance().getProperty(PathConfig.LOGIN_PAGE_PATH);
        return page;
    }
}
