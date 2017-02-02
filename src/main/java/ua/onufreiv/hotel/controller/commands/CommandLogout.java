package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.config.PathConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command for performing log out.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/27/16.
 */
public class CommandLogout implements Command {

    /**
     * Performs logout by invalidating session
     *
     * @param request  request with the required parameters and attributes
     * @param response response that will be formed as a result
     * @return path to the main page in case of successful logout
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String page;

        if (session != null) {
            session.invalidate();
            page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
        } else {
            page = PathConfig.getInstance().getProperty(PathConfig.NOT_SIGNED_IN_ERROR_PAGE_PATH);
        }

        return page;
    }
}
