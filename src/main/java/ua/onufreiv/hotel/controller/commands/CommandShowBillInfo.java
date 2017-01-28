package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.*;
import ua.onufreiv.hotel.service.IBookRequestService;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.IUserRoleService;
import ua.onufreiv.hotel.service.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.*;

/**
 * Created by yurii on 1/14/17.
 */
public class CommandShowBillInfo implements Command {
    private final ParamNamesConfig names;
    private final IBookRequestService bookRequestService;
    private final BillService billService;
    private final RoomService roomService;
    private final IRoomTypeService roomTypeService;
    private final IUserRoleService userRoleService;

    public CommandShowBillInfo() {
        bookRequestService = BookRequestService.getInstance();
        billService = BillService.getInstance();
        roomService = RoomService.getInstance();
        roomTypeService = RoomTypeService.getInstance();
        userRoleService = UserRoleService.getInstance();
        names = ParamNamesConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int bookRequestId = Integer.parseInt(request.getParameter(names.get(BOOK_REQUEST_ID_NAME)));
        String page;

        User user = (User) (request.getSession()).getAttribute(names.get(USER_NAME));
        Bill bill = billService.findByBookRequestId(bookRequestId);
        BookRequest bookRequest = bookRequestService.findById(bookRequestId);
        Room room = roomService.findById(bill.getRoomId());
        RoomType roomType = roomTypeService.findById(room.getRoomTypeId());
        StringBuilder roomStringBuilder = new StringBuilder();
        roomStringBuilder.append("#")
                .append(room.getNumber())
                .append(" (")
                .append(roomType.getType())
                .append(")");

        request.setAttribute(names.get(BILL_TOTAL_PRICE_NAME), bill.getTotalPrice());
        request.setAttribute(names.get(ROOM_TYPE_NAME), roomStringBuilder.toString());
        request.setAttribute(names.get(CHECK_IN_DATE_NAME), bookRequest.getCheckIn());
        request.setAttribute(names.get(CHECK_OUT_DATE_NAME), bookRequest.getCheckOut());
        request.setAttribute(names.get(ID_ROOM_TYPE_MAP_NAME), roomTypeService.findAllAsMap());

        if (userRoleService.userIsAdmin(user)) {
            request.setAttribute(names.get(BOOK_REQUEST_ID_NAME), bookRequestId);
            request.setAttribute(names.get(BOOK_REQUEST_CREATION_DATE_NAME), bookRequest.getCreationDate());
            request.setAttribute(names.get(BOOK_REQUEST_PERSONS_NAME), bookRequest.getPersons());

            page = PathConfig.getInstance().getProperty(PathConfig.ADMIN_BILL_INFO_PAGE_PATH);
        } else {
            request.setAttribute(names.get(USER_NAME), user);
            request.setAttribute(names.get(ROOM_NUMBER_NAME), room.getNumber());
            request.setAttribute(names.get(ROOM_PRICE_NAME), room.getPrice());
            request.setAttribute(names.get(BILL_CREATION_DATE_NAME), bill.getCreationDate());

            page = PathConfig.getInstance().getProperty(PathConfig.CLIENT_BILL_INFO_PAGE_PATH);
        }

        return page;
    }
}
