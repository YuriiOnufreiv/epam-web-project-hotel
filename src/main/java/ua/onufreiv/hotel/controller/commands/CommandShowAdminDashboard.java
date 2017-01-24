package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public class CommandShowAdminDashboard implements Command {
    private static final String NEW_BOOK_REQUESTS_NAME = "newBookRequests";
    private static final String NEW_BOOK_REQUESTS_COUNT_NAME = "newBookRequestsCount";
    private static final String ID_TYPE_TITLES_MAP_NAME = "idTypeTitlesMap";

    private final IRoomTypeService roomTypeService;
    private final BookRequestService bookRequestService;

    public CommandShowAdminDashboard() {
        roomTypeService = new RoomTypeService();
        bookRequestService = new BookRequestService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<BookRequest> notProcessedRequests = bookRequestService.getNotProcessedRequests();
        Map<Integer, String> idTypeTitleMap = roomTypeService.getIdTypeTitleMap();

        request.setAttribute(NEW_BOOK_REQUESTS_NAME, notProcessedRequests);
        request.setAttribute(NEW_BOOK_REQUESTS_COUNT_NAME, notProcessedRequests.size());
        request.getSession(false).setAttribute(ID_TYPE_TITLES_MAP_NAME, idTypeTitleMap);

        return PathConfig.getInstance().getProperty(PathConfig.ADMIN_DASHBOARD_PAGE_PATH);
    }
}