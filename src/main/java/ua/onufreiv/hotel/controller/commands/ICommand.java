package ua.onufreiv.hotel.controller.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yurii on 12/27/16.
 */
public interface ICommand {
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
