package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.*;
import ua.onufreiv.hotel.service.IBookRequestService;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.BillService;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yurii on 1/14/17.
 */
public class CommandShowBillInfo implements Command {
    private static final String USER_NAME = "user";
    private static final String BOOK_REQUEST_ID_NAME = "bookRequestId";
    private static final String TOTAL_PRICE_NAME = "totalPrice";
    private static final String ROOM_NO_NAME = "roomNo";
    private static final String ROOM_TYPE_NAME = "roomType";
    private static final String ROOM_PRICE_NAME = "roomPrice";
    private static final String CHECK_IN_NAME = "checkIn";
    private static final String CHECK_OUT_NAME = "checkOut";
    private static final String BILL_CREATION_DATE_NAME = "billCreationDate";

    private IBookRequestService bookRequestService;
    private BillService billService;
    private RoomService roomService;
    private IRoomTypeService roomTypeService;

    public CommandShowBillInfo() {
        bookRequestService = new BookRequestService();
        billService = new BillService();
        roomService = new RoomService();
        roomTypeService = new RoomTypeService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int bookRequestId = Integer.parseInt(request.getParameter(BOOK_REQUEST_ID_NAME));

        User user = (User) (request.getSession()).getAttribute(USER_NAME);
        Bill bill = billService.getByBookRequestId(bookRequestId);
        BookRequest bookRequest = bookRequestService.getById(bookRequestId);
        Room room = roomService.getById(bill.getRoomId());
        RoomType roomType = roomTypeService.find(room.getRoomTypeId());

        request.setAttribute(USER_NAME, user);
        request.setAttribute(TOTAL_PRICE_NAME, bill.getTotalPrice());
        request.setAttribute(ROOM_NO_NAME, room.getNumber());
        request.setAttribute(ROOM_TYPE_NAME, roomType.getType());
        request.setAttribute(ROOM_PRICE_NAME, roomType.getPrice());
        request.setAttribute(CHECK_IN_NAME, bookRequest.getCheckIn());
        request.setAttribute(CHECK_OUT_NAME, bookRequest.getCheckOut());
        request.setAttribute(BILL_CREATION_DATE_NAME, bill.getCreationDate());

        return PathConfig.getInstance().getProperty(PathConfig.CLIENT_BILL_INFO_PAGE_PATH);
    }
}
