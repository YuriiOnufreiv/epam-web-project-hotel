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
public class CommandReservation implements ICommand {
    private static final String PARAM_NAME_TOTAL_PERSONS = "total_persons";
    private static final String PARAM_NAME_ROOM_TYPE = "room_type";
    private static final String PARAM_NAME_CHECK_IN_DATE = "check_in_date";
    private static final String PARAM_NAME_CHECK_OUT_DATE = "check_out_date";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        IBookRequestService reservationService = new BookRequestService();

        Integer totalPersons = Integer.valueOf(request.getParameter(PARAM_NAME_TOTAL_PERSONS));
        Integer roomType = Integer.valueOf(request.getParameter(PARAM_NAME_ROOM_TYPE));
        String checkInDateString = request.getParameter(PARAM_NAME_CHECK_IN_DATE);
        String checkOutDateString = request.getParameter(PARAM_NAME_CHECK_OUT_DATE);
        User user = (User) request.getSession(false).getAttribute("user");

        Date checkInDate = null, checkOutDate = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            checkInDate = df.parse(checkInDateString);
            checkOutDate = df.parse(checkOutDateString);
            if(checkInDate.compareTo(checkOutDate) > 0) {
                request.setAttribute("invalidDates", "true");
                request.setAttribute(PARAM_NAME_ROOM_TYPE, roomType.toString());
                request.setAttribute(PARAM_NAME_TOTAL_PERSONS, totalPersons);
                return PathConfig.getInstance().getProperty(PathConfig.NEW_BOOK_REQUEST_PAGE_PATH);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BookRequest form = new BookRequest(0, new Date(), user.getId(), totalPersons, roomType, checkInDate, checkOutDate, false);
        reservationService.makeNewRequest(form);

        request.setAttribute("successfulReserve", "true");
        return PathConfig.getInstance().getProperty(PathConfig.NEW_BOOK_REQUEST_PAGE_PATH);
    }
}
