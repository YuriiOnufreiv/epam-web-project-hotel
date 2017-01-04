package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entities.BookRequest;
import ua.onufreiv.hotel.entities.User;
import ua.onufreiv.hotel.service.impl.ClientService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yurii on 1/2/17.
 */
public class CommandClientRequests implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession(false).getAttribute("user");
        List<BookRequest> bookRequests = new ClientService().getUserForm(user.getId());
        request.setAttribute("bookRequests", bookRequests);
        return PathConfig.getInstance().getProperty(PathConfig.SHOW_ALL_REQUESTS_PAGE_PATH);
    }
}