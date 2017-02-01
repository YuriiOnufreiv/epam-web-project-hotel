package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command that handles wrong commands
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/27/16.
 */
public class CommandEmpty implements Command {

    /**
     * Handles wrong commands
     *
     * @param request  request with the required parameters and attributes
     * @param response response that will be formed as a result
     * @return path to the main page
     * @see PathConfig
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
    }
}
