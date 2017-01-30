package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.JspConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.BookRequestService;
import ua.onufreiv.hotel.service.RoomTypeService;
import ua.onufreiv.hotel.service.impl.BookRequestServiceImpl;
import ua.onufreiv.hotel.service.impl.RoomTypeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static ua.onufreiv.hotel.controller.manager.JspConfig.*;

/**
 * Created by yurii on 1/2/17.
 */
public class CommandShowClientRequests implements Command {
    private final BookRequestService bookRequestServiceImpl;
    private final RoomTypeService roomTypeServiceImpl;
    private final JspConfig jspConfig;

    public CommandShowClientRequests() {
        bookRequestServiceImpl = BookRequestServiceImpl.getInstance();
        roomTypeServiceImpl = RoomTypeServiceImpl.getInstance();
        jspConfig = JspConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession(false).getAttribute(jspConfig.get(USER_NAME));
        List<BookRequest> bookRequests = bookRequestServiceImpl.findByUserId(user.getId());

        request.setAttribute(jspConfig.get(ID_ROOM_TYPE_MAP_NAME), roomTypeServiceImpl.findAllAsMap());
        request.setAttribute(jspConfig.get(BOOK_REQUEST_LIST_NAME), bookRequests);

        return PathConfig.getInstance().getProperty(PathConfig.SHOW_ALL_REQUESTS_PAGE_PATH);
    }
}
