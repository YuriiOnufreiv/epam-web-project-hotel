package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entities.*;
import ua.onufreiv.hotel.service.IBillService;
import ua.onufreiv.hotel.service.IBookRequestService;
import ua.onufreiv.hotel.service.IRoomService;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.BillService;
import ua.onufreiv.hotel.service.impl.BookRequestService;
import ua.onufreiv.hotel.service.impl.RoomService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yurii on 1/14/17.
 */
public class CommandShowBillInfo implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IBookRequestService bookRequestService = new BookRequestService();
        IBillService billService = new BillService();
        IRoomService roomService = new RoomService();
        IRoomTypeService roomTypeService = new RoomTypeService();

        int bookRequestId = Integer.parseInt(request.getParameter("bookRequestId"));

        User user = (User) (request.getSession()).getAttribute("user");
        Bill bill = billService.getByBookRequestId(bookRequestId);
        BookRequest bookRequest = bookRequestService.getById(bookRequestId);
        Room room = roomService.getById(bill.getRoomId());
        RoomType roomType = roomTypeService.find(room.getRoomTypeId());

        request.setAttribute("user", user);
        request.setAttribute("totalPrice", bill.getTotalPrice());
        request.setAttribute("roomNo", room.getNumber());
        request.setAttribute("roomType", roomType.getType());
        request.setAttribute("roomPrice", roomType.getPrice());
        request.setAttribute("checkIn", bookRequest.getCheckIn());
        request.setAttribute("checkOut", bookRequest.getCheckOut());
        request.setAttribute("billCreationDate", bill.getCreationDate());
        return PathConfig.getInstance().getProperty(PathConfig.CLIENT_BILL_INFO_PAGE_PATH);
    }
}
