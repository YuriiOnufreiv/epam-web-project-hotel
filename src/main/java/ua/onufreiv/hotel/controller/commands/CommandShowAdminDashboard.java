package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.JspConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.service.BookRequestService;
import ua.onufreiv.hotel.service.ReservedRoomService;
import ua.onufreiv.hotel.service.RoomTypeService;
import ua.onufreiv.hotel.service.impl.BookRequestServiceImpl;
import ua.onufreiv.hotel.service.impl.ReservedRoomServiceImpl;
import ua.onufreiv.hotel.service.impl.RoomTypeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.onufreiv.hotel.controller.manager.JspConfig.*;

/**
 * Created by yurii on 1/5/17.
 */
public class CommandShowAdminDashboard implements Command {
    private final RoomTypeService roomTypeService;
    private final BookRequestService bookRequestServiceImpl;
    private final ReservedRoomService reservedRoomServiceImpl;
    private final JspConfig jspConfig;

    public CommandShowAdminDashboard() {
        roomTypeService = RoomTypeServiceImpl.getInstance();
        bookRequestServiceImpl = BookRequestServiceImpl.getInstance();
        reservedRoomServiceImpl = ReservedRoomServiceImpl.getInstance();
        jspConfig = JspConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<BookRequest> notProcessedRequests = bookRequestServiceImpl.findNotProcessed();
        Map<Integer, String> idTypeTitleMap = roomTypeService.findAllAsMap();
        boolean result = reservedRoomServiceImpl.deleteExpired();

        request.setAttribute(jspConfig.get(NEW_ROOMS_AVAILABLE_NAME), result);
        request.setAttribute(jspConfig.get(NOT_PROCESSED_BOOK_REQUEST_LIST_NAME), notProcessedRequests);
        request.setAttribute(jspConfig.get(ID_ROOM_TYPE_MAP_NAME), idTypeTitleMap);

        return PathConfig.getInstance().getProperty(PathConfig.ADMIN_DASHBOARD_PAGE_PATH);
    }
}