package ua.onufreiv.hotel.controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command interface; it's realizations will process the concrete requested
 * actions from the user.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/27/16.
 */
public interface Command {
    /**
     * This method reads a command from the request and processes it.
     * The result will be given as a page to forward to
     *
     * @param request  request with the required parameters and attributes
     * @param response response that will be formed as a result
     * @return path to the page to forward
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}