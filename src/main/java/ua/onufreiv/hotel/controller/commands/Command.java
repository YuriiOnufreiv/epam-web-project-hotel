package ua.onufreiv.hotel.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yurii on 12/27/16.
 */
public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response);
}
