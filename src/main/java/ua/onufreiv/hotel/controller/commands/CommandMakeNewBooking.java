package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.IBookRequestService;
import ua.onufreiv.hotel.service.impl.BookRequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.*;

/**
 * Created by yurii on 1/1/17.
 */
public class CommandMakeNewBooking implements Command {
    private final IBookRequestService reservationService;
    private final ParamNamesConfig names;

    public CommandMakeNewBooking() {
        names = ParamNamesConfig.getInstance();
        reservationService = new BookRequestService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer totalPersons = Integer.valueOf(request.getParameter(names.get(BOOK_REQUEST_PERSONS_NAME)));
        Integer roomTypeId = Integer.valueOf(request.getParameter(names.get(ROOM_TYPE_NAME)));
        String checkInDateString = request.getParameter(names.get(CHECK_IN_DATE_NAME));
        String checkOutDateString = request.getParameter(names.get(CHECK_OUT_DATE_NAME));

        User user = (User) request.getSession(false).getAttribute(names.get(USER_NAME));

        Date checkInDate = null, checkOutDate = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            checkInDate = df.parse(checkInDateString);
            checkOutDate = df.parse(checkOutDateString);
            if (checkInDate.before(new Date()) || checkInDate.after(checkOutDate)) {
                request.setAttribute(names.get(INVALID_BOOK_REQUEST_DATES_ERROR_NAME), "true");
                request.setAttribute(names.get(ROOM_TYPE_NAME), roomTypeId.toString());
                request.setAttribute(names.get(BOOK_REQUEST_PERSONS_NAME), totalPersons);
                return PathConfig.getInstance().getProperty(PathConfig.NEW_BOOK_REQUEST_PAGE_PATH);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BookRequest form = new BookRequest(0, new Date(), user.getId(), totalPersons, roomTypeId, checkInDate, checkOutDate, false);
        if (reservationService.makeNewRequest(form)) {
            request.setAttribute(names.get(RESERVE_SUCCESS_NAME), "true");
        } else {
            request.setAttribute(names.get(RESERVE_ERROR_NAME), "true");
        }

        return PathConfig.getInstance().getProperty(PathConfig.NEW_BOOK_REQUEST_PAGE_PATH);
    }
}
