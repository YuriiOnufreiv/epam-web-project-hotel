package ua.onufreiv.hotel.controller.commands;

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

/**
 * Created by yurii on 1/1/17.
 */
public class CommandMakeNewBooking implements Command {
    private static final String TOTAL_PERSONS_NAME = "total_persons";
    private static final String ROOM_TYPE_NAME = "room_type";
    private static final String CHECK_IN_DATE_NAME = "check_in_date";
    private static final String CHECK_OUT_DATE_NAME = "check_out_date";
    private static final String USER_NAME = "user";
    private static final String SUCCESSFUL_RESERVE_NAME = "successfulReserve";
    private static final String INVALID_DATES_ERROR_NAME = "invalidDatesError";

    private final IBookRequestService reservationService;

    public CommandMakeNewBooking() {
        reservationService = new BookRequestService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer totalPersons = Integer.valueOf(request.getParameter(TOTAL_PERSONS_NAME));
        Integer roomType = Integer.valueOf(request.getParameter(ROOM_TYPE_NAME));
        String checkInDateString = request.getParameter(CHECK_IN_DATE_NAME);
        String checkOutDateString = request.getParameter(CHECK_OUT_DATE_NAME);

        User user = (User) request.getSession(false).getAttribute(USER_NAME);

        Date checkInDate = null, checkOutDate = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            checkInDate = df.parse(checkInDateString);
            checkOutDate = df.parse(checkOutDateString);
            if (checkInDate.before(new Date()) || checkInDate.after(checkOutDate)) {
                request.setAttribute(INVALID_DATES_ERROR_NAME, "true");
                request.setAttribute(ROOM_TYPE_NAME, roomType.toString());
                request.setAttribute(TOTAL_PERSONS_NAME, totalPersons);
                return PathConfig.getInstance().getProperty(PathConfig.NEW_BOOK_REQUEST_PAGE_PATH);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BookRequest form = new BookRequest(0, new Date(), user.getId(), totalPersons, roomType, checkInDate, checkOutDate, false);
        reservationService.makeNewRequest(form);

        request.setAttribute(SUCCESSFUL_RESERVE_NAME, "true");
        return PathConfig.getInstance().getProperty(PathConfig.NEW_BOOK_REQUEST_PAGE_PATH);
    }
}
