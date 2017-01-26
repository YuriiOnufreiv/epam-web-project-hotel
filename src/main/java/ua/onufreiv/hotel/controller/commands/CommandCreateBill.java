package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
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

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.*;

/**
 * Created by yurii on 1/10/17.
 */
public class CommandCreateBill implements Command {
//    private static final String REQUEST_ID_NAME = "requestId";
//    private static final String ROOM_ID_NAME = "roomId";
//    private static final String ROOM_TYPE_ID_NAME = "roomTypeId";
//    private static final String CREATE_BILL_ERROR_NAME = "createBillError";
//    private static final String BOOK_REQUEST_ID_NAME = "bookRequestId";
//    private static final String REQUEST_CREATION_DATE_NAME = "requestCreationDate";
//    private static final String PERSONS_NAME = "persons";
//    private static final String TYPE_NAME = "type";
//    private static final String CHECK_IN_NAME = "checkIn";
//    private static final String CHECK_OUT_NAME = "checkOut";
//    private static final String BILL_TOTAL_PRICE_NAME = "totalPrice";

    private static final String PROCESS_BOOK_REQUEST_PATH = "/hotel?command=processBookRequest&id=";
    private ParamNamesConfig names;
    private final IBillService billService;
    private final IBookRequestService bookRequestService;
    private final IRoomTypeService roomTypeService;

    public CommandCreateBill() {
        names = ParamNamesConfig.getInstance();
        billService = new BillService();
        bookRequestService = new BookRequestService();
        roomTypeService = new RoomTypeService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int bookRequestId = Integer.parseInt(request.getParameter(names.get(BOOK_REQUEST_ID_NAME)));
        int roomId = Integer.parseInt(request.getParameter(names.get(ROOM_ID_NAME)));
        int roomTypeId = Integer.parseInt(request.getParameter(names.get(ROOM_TYPE_ID_NAME)));

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
            request.setAttribute(names.get(CREATE_BILL_ERROR_NAME), "true");
            return PROCESS_BOOK_REQUEST_PATH + bookRequestId;
        }

        request.setAttribute(names.get(BOOK_REQUEST_ID_NAME), bookRequestId);
        request.setAttribute(names.get(BOOK_REQUEST_CREATION_DATE_NAME), bookRequest.getCreationDate());
        request.setAttribute(names.get(BOOK_REQUEST_PERSONS_NAME), bookRequest.getPersons());
        request.setAttribute(names.get(ROOM_TYPE_NAME), roomType.getType());
        request.setAttribute(names.get(CHECK_IN_DATE_NAME), bookRequest.getCheckIn());
        request.setAttribute(names.get(CHECK_OUT_DATE_NAME), bookRequest.getCheckOut());
        request.setAttribute(names.get(BILL_TOTAL_PRICE_NAME), price);

        return PathConfig.getInstance().getProperty(PathConfig.ADMIN_BILL_INFO_PAGE_PATH);
    }
}
