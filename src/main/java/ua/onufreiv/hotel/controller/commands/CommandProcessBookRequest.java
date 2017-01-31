package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.JspConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.BookRequestService;
import ua.onufreiv.hotel.service.RoomService;
import ua.onufreiv.hotel.service.RoomTypeService;
import ua.onufreiv.hotel.service.UserService;
import ua.onufreiv.hotel.service.impl.BookRequestServiceImpl;
import ua.onufreiv.hotel.service.impl.RoomServiceImpl;
import ua.onufreiv.hotel.service.impl.RoomTypeServiceImpl;
import ua.onufreiv.hotel.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.manager.JspConfig.*;

/**
 * Created by yurii on 1/10/17.
 */
public class CommandProcessBookRequest implements Command {
    private final RoomService roomServiceImpl;
    private final RoomTypeService roomTypeServiceImpl;
    private final BookRequestService bookRequestService;
    private final UserService userService;
    private final JspConfig jspConfig;

    public CommandProcessBookRequest() {
        bookRequestService = BookRequestServiceImpl.getInstance();
        userService = UserServiceImpl.getInstance();
        roomServiceImpl = RoomServiceImpl.getInstance();
        roomTypeServiceImpl = RoomTypeServiceImpl.getInstance();
        jspConfig = JspConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int bookRequestId = Integer.parseInt(request.getParameter(jspConfig.get(BOOK_REQUEST_ID_NAME)));

        BookRequest bookRequest = bookRequestService.findById(bookRequestId);
        User user = userService.findById(bookRequest.getUserId());
        Room exactTypeRoom = roomServiceImpl.searchExactRoom(bookRequest);
        Room favouriteTypeRoom = roomServiceImpl.searchFavouriteTypeRoom(bookRequest);
        Room anyTypeRoom = roomServiceImpl.searchAnyTypeRoom(bookRequest);

        request.setAttribute(jspConfig.get(USER_NAME), user);
        request.setAttribute(jspConfig.get(BOOK_REQUEST_NAME), bookRequest);
        request.setAttribute(jspConfig.get(EXACT_TYPE_ROOM_NAME), exactTypeRoom);
        request.setAttribute(jspConfig.get(FAVOURITE_TYPE_ROOM_NAME), favouriteTypeRoom);
        request.setAttribute(jspConfig.get(ANY_TYPE_ROOM_NAME), anyTypeRoom);
        request.setAttribute(jspConfig.get(ID_ROOM_TYPE_MAP_NAME), roomTypeServiceImpl.findAllAsMap());

        return PathConfig.getInstance().getProperty(PathConfig.PROCESS_REQUEST_PAGE_PATH);
    }
}