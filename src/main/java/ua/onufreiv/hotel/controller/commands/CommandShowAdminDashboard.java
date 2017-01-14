package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entities.BookRequest;
import ua.onufreiv.hotel.service.impl.BookRequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class CommandShowAdminDashboard implements ua.onufreiv.hotel.controller.commands.ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BookRequest> notProcessedRequests = new BookRequestService().getNotProcessedRequests();
        request.setAttribute("newBookRequests", notProcessedRequests);
        request.setAttribute("newBookRequestsCount", notProcessedRequests.size());
        return PathConfig.getInstance().getProperty(PathConfig.ADMIN_DASHBOARD_PAGE_PATH);
    }
}