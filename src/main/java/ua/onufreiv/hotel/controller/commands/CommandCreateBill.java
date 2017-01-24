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
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        IBillService billService = new BillService();
        IBookRequestService bookRequestService = new BookRequestService();
        IRoomTypeService roomTypeService = new RoomTypeService();

        int bookRequestId = Integer.parseInt(request.getParameter("requestId"));
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));

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
            request.setAttribute("createBillError", "true");
            return "/hotel?command=processBookRequest&id=" + bookRequestId;
        }

        request.setAttribute("bookRequestId", bookRequestId);
        request.setAttribute("requestCreationDate", bookRequest.getCreationDate());
        request.setAttribute("persons", bookRequest.getPersons());
        request.setAttribute("type", roomType.getType());
        request.setAttribute("checkIn", bookRequest.getCheckIn());
        request.setAttribute("checkOut", bookRequest.getCheckOut());
        request.setAttribute("totalPrice", price);

        return PathConfig.getInstance().getProperty(PathConfig.ADMIN_BILL_INFO_PAGE_PATH);
    }
}
