package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.config.JspConfig;
import ua.onufreiv.hotel.controller.config.PathConfig;
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

import static ua.onufreiv.hotel.controller.config.JspConfig.*;

/**
 * Command for showing admin's dashboard with all required date
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/5/17.
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

    /**
     * Handles request to show admin's dashboard by loading required data and
     * setting it as an attributes
     *
     * @param request  request with the required parameters and attributes
     * @param response response that will be formed as a result
     * @return path to the admin's dashboard page
     * @see PathConfig
     */
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