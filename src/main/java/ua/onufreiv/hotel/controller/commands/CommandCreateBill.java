package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.Bill;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.RoomType;
import ua.onufreiv.hotel.service.IBillService;
import ua.onufreiv.hotel.service.IBookRequestService;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.BillService;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by yurii on 1/10/17.
 */
public class CommandCreateBill implements Command {
    private static final String REQUEST_ID_NAME = "requestId";
    private static final String ROOM_ID_NAME = "roomId";
    private static final String ROOM_TYPE_ID_NAME = "roomTypeId";
    private static final String CREATE_BILL_ERROR_NAME = "createBillError";
    private static final String BOOK_REQUEST_ID_NAME = "bookRequestId";
    private static final String REQUEST_CREATION_DATE_NAME = "requestCreationDate";
    private static final String PERSONS_NAME = "persons";
    private static final String TYPE_NAME = "type";
    private static final String CHECK_IN_NAME = "checkIn";
    private static final String CHECK_OUT_NAME = "checkOut";
    private static final String TOTAL_PRICE_NAME = "totalPrice";

    private static final String PROCESS_BOOK_REQUEST_PATH = "/hotel?command=processBookRequest&id=";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        IBillService billService = new BillService();
        IBookRequestService bookRequestService = new BookRequestService();
        IRoomTypeService roomTypeService = new RoomTypeService();

        int bookRequestId = Integer.parseInt(request.getParameter(REQUEST_ID_NAME));
        int roomId = Integer.parseInt(request.getParameter(ROOM_ID_NAME));
        int roomTypeId = Integer.parseInt(request.getParameter(ROOM_TYPE_ID_NAME));

        BookRequest bookRequest = bookRequestService.getById(bookRequestId);
        long diff = bookRequest.getCheckOut().getTime() - bookRequest.getCheckIn().getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        RoomType roomType = roomTypeService.find(roomTypeId);
        long price = days * roomType.getPrice();

        Bill bill = new Bill();
        bill.setCreationDate(new Date());
        bill.setBookRequestId(bookRequestId);
        bill.setRoomId(roomId);
        bill.setTotalPrice(price);

        if(!billService.createNewBill(bill)) {
            request.setAttribute(CREATE_BILL_ERROR_NAME, "true");
            return PROCESS_BOOK_REQUEST_PATH + bookRequestId;
        }

        request.setAttribute(BOOK_REQUEST_ID_NAME, bookRequestId);
        request.setAttribute(REQUEST_CREATION_DATE_NAME, bookRequest.getCreationDate());
        request.setAttribute(PERSONS_NAME, bookRequest.getPersons());
        request.setAttribute(TYPE_NAME, roomType.getType());
        request.setAttribute(CHECK_IN_NAME, bookRequest.getCheckIn());
        request.setAttribute(CHECK_OUT_NAME, bookRequest.getCheckOut());
        request.setAttribute(TOTAL_PRICE_NAME, price);

        return PathConfig.getInstance().getProperty(PathConfig.ADMIN_BILL_INFO_PAGE_PATH);
    }
}
