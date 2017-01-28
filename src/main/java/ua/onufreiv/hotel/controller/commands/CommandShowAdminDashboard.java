package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.ReservedRoomService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.*;

/**
 * Created by yurii on 1/5/17.
 */
public class CommandShowAdminDashboard implements Command {
    private final IRoomTypeService roomTypeService;
    private final BookRequestService bookRequestService;
    private final ReservedRoomService reservedRoomService;
    private final ParamNamesConfig names;

    public CommandShowAdminDashboard() {
        roomTypeService = RoomTypeService.getInstance();
        bookRequestService = BookRequestService.getInstance();
        reservedRoomService = ReservedRoomService.getInstance();
        names = ParamNamesConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<BookRequest> notProcessedRequests = bookRequestService.findNotProcessed();
        Map<Integer, String> idTypeTitleMap = roomTypeService.findAllAsMap();
        boolean result = reservedRoomService.deleteExpired();

        request.setAttribute(names.get(NEW_ROOMS_AVAILABLE_NAME), result);
        request.setAttribute(names.get(NOT_PROCESSED_BOOK_REQUEST_LIST_NAME), notProcessedRequests);
        request.setAttribute(names.get(ID_ROOM_TYPE_MAP_NAME), idTypeTitleMap);

        return PathConfig.getInstance().getProperty(PathConfig.ADMIN_DASHBOARD_PAGE_PATH);
    }
}