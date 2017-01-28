package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        bookRequestService = BookRequestService.getInstance();
        roomTypeService = RoomTypeService.getInstance();
        names = ParamNamesConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession(false).getAttribute(names.get(USER_NAME));
        List<BookRequest> bookRequests = bookRequestService.findByUserId(user.getId());

        request.setAttribute(names.get(ID_ROOM_TYPE_MAP_NAME), roomTypeService.findAllAsMap());
        request.setAttribute(names.get(BOOK_REQUEST_LIST_NAME), bookRequests);

        return PathConfig.getInstance().getProperty(PathConfig.SHOW_ALL_REQUESTS_PAGE_PATH);
    }
}
