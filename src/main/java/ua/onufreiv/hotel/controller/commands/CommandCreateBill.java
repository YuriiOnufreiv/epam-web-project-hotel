package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entities.Bill;
import ua.onufreiv.hotel.entities.BookRequest;
import ua.onufreiv.hotel.entities.RoomType;
import ua.onufreiv.hotel.service.IBillService;
import ua.onufreiv.hotel.service.IBookRequestService;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.BillService;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by yurii on 1/10/17.
 */
public class CommandCreateBill implements ICommand {
    @Override
        public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        bill.setBookRequestId(bookRequestId);
        bill.setRoomId(roomId);
        bill.setTotalPrice(price); ///////////////// hardcoded

        if(!billService.createNewBill(bill)) {
            request.setAttribute("createBillError", "true");
            return "/hotel?command=processBookRequest&id=" + bookRequestId;
        }

        request.setAttribute("bookRequestId", bookRequestId);
        request.setAttribute("persons", bookRequest.getPersons());
        request.setAttribute("type", roomType.getType());
        request.setAttribute("checkIn", bookRequest.getCheckIn());
        request.setAttribute("checkOut", bookRequest.getCheckOut());
        request.setAttribute("totalPrice", price);

        return PathConfig.getInstance().getProperty(PathConfig.BILL_INFO_PAGE_PATH);
    }
}
