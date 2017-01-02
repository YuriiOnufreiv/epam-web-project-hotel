package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.commands.ICommand;
import ua.onufreiv.hotel.controller.manager.MessageConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entities.Form;
import ua.onufreiv.hotel.entities.User;
import ua.onufreiv.hotel.service.IReservationService;
import ua.onufreiv.hotel.service.impl.AuthService;
import ua.onufreiv.hotel.service.impl.ReservationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IReservationService reservationService = new ReservationService();

        Integer totalPersons = Integer.valueOf(request.getParameter(PARAM_NAME_TOTAL_PERSONS));
        String roomType = request.getParameter(PARAM_NAME_ROOM_TYPE);
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
                request.setAttribute(PARAM_NAME_TOTAL_PERSONS, totalPersons);
                return PathConfig.getInstance().getProperty(PathConfig.RESERVATION_PAGE_PATH);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Form form = new Form(0, user.getId(), totalPersons, 1, checkInDate, checkOutDate, false);
        reservationService.makeNewReservation(form);

        request.setAttribute("successfulReserve", "true");
        return PathConfig.getInstance().getProperty(PathConfig.RESERVATION_PAGE_PATH);
    }
}
