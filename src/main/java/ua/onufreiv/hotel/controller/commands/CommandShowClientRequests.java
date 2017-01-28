package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.*;

/**
 * Created by yurii on 1/2/17.
 */
public class CommandShowClientRequests implements Command {
    private final BookRequestService bookRequestService;
    private final RoomTypeService roomTypeService;
    private final ParamNamesConfig names;

    public CommandShowClientRequests() {
        names = ParamNamesConfig.getInstance();
        bookRequestService = new BookRequestService();
        roomTypeService = new RoomTypeService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(names.get(USER_NAME));
        List<BookRequest> bookRequests = bookRequestService.getRequestsByUserId(user.getId());

        if (session.getAttribute(names.get(ID_ROOM_TYPE_MAP_NAME)) == null) {
            session.setAttribute(names.get(ID_ROOM_TYPE_MAP_NAME), roomTypeService.getAllInMap());
        }

        request.setAttribute(names.get(BOOK_REQUEST_LIST_NAME), bookRequests);
        return PathConfig.getInstance().getProperty(PathConfig.SHOW_ALL_REQUESTS_PAGE_PATH);
    }
}
