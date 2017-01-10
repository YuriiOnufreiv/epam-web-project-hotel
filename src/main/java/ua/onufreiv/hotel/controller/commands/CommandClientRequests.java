package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entities.BookRequest;
import ua.onufreiv.hotel.entities.User;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by yurii on 1/2/17.
 */
public class CommandClientRequests implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        List<BookRequest> bookRequests = new BookRequestService().getRequestById(user.getId());
        if(session.getAttribute("idTypeTitlesMap") == null) {
            session.setAttribute("idTypeTitlesMap", new RoomTypeService().getIdTypeTitleMap());
        }
        request.setAttribute("bookRequests", bookRequests);
        return PathConfig.getInstance().getProperty(PathConfig.SHOW_ALL_REQUESTS_PAGE_PATH);
    }
}
