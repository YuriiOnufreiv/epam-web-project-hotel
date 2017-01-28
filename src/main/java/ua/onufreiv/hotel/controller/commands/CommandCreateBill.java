package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.Bill;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.service.IBillService;
import ua.onufreiv.hotel.service.IBookRequestService;
import ua.onufreiv.hotel.service.IRoomService;
import ua.onufreiv.hotel.service.impl.BillService;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.*;

/**
 * Created by yurii on 1/10/17.
 */
public class CommandCreateBill implements Command {
    private final IBillService billService;
    private final IBookRequestService bookRequestService;
    private final IRoomService roomService;
    private final ParamNamesConfig names;

    public CommandCreateBill() {
        names = ParamNamesConfig.getInstance();
        billService = new BillService();
        bookRequestService = new BookRequestService();
        roomService = new RoomService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int bookRequestId = Integer.parseInt(request.getParameter(names.get(BOOK_REQUEST_ID_NAME)));
        int roomId = Integer.parseInt(request.getParameter(names.get(ROOM_ID_NAME)));

        BookRequest bookRequest = bookRequestService.getById(bookRequestId);
        long diff = bookRequest.getCheckOut().getTime() - bookRequest.getCheckIn().getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        Room room = roomService.getById(roomId);
        long price = days * room.getPrice();

        Bill bill = new Bill();
        bill.setCreationDate(new Date());
        bill.setBookRequestId(bookRequestId);
        bill.setRoomId(roomId);
        bill.setTotalPrice(price);

        if(!billService.createNewBill(bill)) {
            request.setAttribute(names.get(CREATE_BILL_ERROR_NAME), "true");
            return PathConfig.getInstance()
                    .getProperty(PathConfig.PROCESS_BOOK_REQUEST_BY_ID_COMMAND_PATH) + bookRequestId;
        }

        request.setAttribute(names.get(BOOK_REQUEST_ID_NAME), bookRequestId);
        request.setAttribute(names.get(BOOK_REQUEST_CREATION_DATE_NAME), bookRequest.getCreationDate());
        request.setAttribute(names.get(BOOK_REQUEST_PERSONS_NAME), bookRequest.getPersons());
        request.setAttribute(names.get(ROOM_TYPE_NAME), room.getNumber());
        request.setAttribute(names.get(CHECK_IN_DATE_NAME), bookRequest.getCheckIn());
        request.setAttribute(names.get(CHECK_OUT_DATE_NAME), bookRequest.getCheckOut());
        request.setAttribute(names.get(BILL_TOTAL_PRICE_NAME), price);

        return PathConfig.getInstance().getProperty(PathConfig.ADMIN_BILL_INFO_PAGE_PATH);
    }
}
