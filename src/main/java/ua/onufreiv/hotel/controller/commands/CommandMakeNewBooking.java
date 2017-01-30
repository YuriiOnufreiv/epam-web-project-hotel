package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.JspConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.BookRequestService;
import ua.onufreiv.hotel.service.impl.BookRequestServiceImpl;
import ua.onufreiv.hotel.util.DateParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static ua.onufreiv.hotel.controller.manager.JspConfig.*;

/**
 * Created by yurii on 1/1/17.
 */
public class CommandMakeNewBooking implements Command {
    private final BookRequestService reservationService;
    private final JspConfig jspConfig;

    public CommandMakeNewBooking() {
        jspConfig = JspConfig.getInstance();
        reservationService = BookRequestServiceImpl.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer totalPersons = Integer.valueOf(request.getParameter(jspConfig.get(BOOK_REQUEST_PERSONS_NAME)));
        Integer roomTypeId = Integer.valueOf(request.getParameter(jspConfig.get(ROOM_TYPE_NAME)));
        String checkInDateString = request.getParameter(jspConfig.get(CHECK_IN_DATE_NAME));
        String checkOutDateString = request.getParameter(jspConfig.get(CHECK_OUT_DATE_NAME));

        String dateFormatString = jspConfig.get(DATE_PICKER_DATE_FORMAT);
        Date checkInDate = DateParser.parse(checkInDateString, dateFormatString);
        Date checkOutDate = DateParser.parse(checkOutDateString, dateFormatString);

        if (checkInDate.before(new Date()) || checkInDate.after(checkOutDate)) {
            request.setAttribute(jspConfig.get(INVALID_BOOK_REQUEST_DATES_ERROR_NAME), true);
            request.setAttribute(jspConfig.get(ROOM_TYPE_NAME), roomTypeId.toString());
            request.setAttribute(jspConfig.get(BOOK_REQUEST_PERSONS_NAME), totalPersons);
            return PathConfig.getInstance().getProperty(PathConfig.NEW_BOOK_REQUEST_PAGE_PATH);
        }

        User user = (User) request.getSession(false).getAttribute(jspConfig.get(USER_NAME));
        BookRequest form = new BookRequest(0, new Date(), user.getId(), totalPersons, roomTypeId, checkInDate, checkOutDate, false);
        if (reservationService.insertBookRequest(form)) {
            request.setAttribute(jspConfig.get(CREATE_BOOK_REQUEST_SUCCESS_NAME), true);
        } else {
            request.setAttribute(jspConfig.get(CREATE_BOOK_REQUEST_ERROR_NAME), true);
        }

        return PathConfig.getInstance().getProperty(PathConfig.NEW_BOOK_REQUEST_PAGE_PATH);
    }
}
