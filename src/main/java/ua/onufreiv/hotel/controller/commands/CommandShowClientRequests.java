package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by yurii on 1/2/17.
 */
public class CommandShowClientRequests implements Command {
    private static final String USER_NAME = "user";
    private static final String ID_TYPE_TITLES_MAP_NAME = "idTypeTitlesMap";
    private static final String BOOK_REQUESTS_NAME = "bookRequests";

    private final BookRequestService bookRequestService;

    public CommandShowClientRequests() {
        bookRequestService = new BookRequestService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(USER_NAME);
        List<BookRequest> bookRequests = bookRequestService.getRequestsByUserId(user.getId());

        if (session.getAttribute(ID_TYPE_TITLES_MAP_NAME) == null) {
            session.setAttribute(ID_TYPE_TITLES_MAP_NAME, new RoomTypeService().getIdTypeTitleMap());
        }

        request.setAttribute(BOOK_REQUESTS_NAME, bookRequests);
        return PathConfig.getInstance().getProperty(PathConfig.SHOW_ALL_REQUESTS_PAGE_PATH);
    }
}
