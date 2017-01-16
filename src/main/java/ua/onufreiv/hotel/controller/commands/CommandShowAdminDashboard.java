package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public class CommandShowAdminDashboard implements ua.onufreiv.hotel.controller.commands.ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IRoomTypeService roomTypeService = new RoomTypeService();
        List<BookRequest> notProcessedRequests = new BookRequestService().getNotProcessedRequests();
        request.setAttribute("newBookRequests", notProcessedRequests);
        request.setAttribute("newBookRequestsCount", notProcessedRequests.size());
        Map<Integer, String> idTypeTitleMap = roomTypeService.getIdTypeTitleMap();
        request.getSession(false).setAttribute("idTypeTitlesMap", idTypeTitleMap);
        return PathConfig.getInstance().getProperty(PathConfig.ADMIN_DASHBOARD_PAGE_PATH);
    }
}