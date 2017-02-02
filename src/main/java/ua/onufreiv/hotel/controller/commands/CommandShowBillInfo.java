package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.config.JspConfig;
import ua.onufreiv.hotel.controller.config.PathConfig;
import ua.onufreiv.hotel.entity.*;
import ua.onufreiv.hotel.service.*;
import ua.onufreiv.hotel.service.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.config.JspConfig.*;

/**
 * Command for showing bill info

 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/14/17.
 */
public class CommandShowBillInfo implements Command {
    private final BookRequestService bookRequestService;
    private final BillService billServiceImpl;
    private final RoomService roomServiceImpl;
    private final RoomTypeService roomTypeService;
    private final UserRoleService userRoleService;
    private final JspConfig jspConfig;

    public CommandShowBillInfo() {
        bookRequestService = BookRequestServiceImpl.getInstance();
        billServiceImpl = BillServiceImpl.getInstance();
        roomServiceImpl = RoomServiceImpl.getInstance();
        roomTypeService = RoomTypeServiceImpl.getInstance();
        userRoleService = UserRoleServiceImpl.getInstance();
        jspConfig = JspConfig.getInstance();
    }

    /**
     * Handles request to show bill info based on the user's role saved in the
     * session object
     *
     * @param request  request with the required parameters and attributes
     * @param response response that will be formed as a result
     * @return path to the appropriate bill info page
     * @see PathConfig
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int bookRequestId = Integer.parseInt(request.getParameter(jspConfig.get(BOOK_REQUEST_ID_NAME)));
        String page;

        User user = (User) (request.getSession()).getAttribute(jspConfig.get(USER_NAME));
        Bill bill = billServiceImpl.findByBookRequestId(bookRequestId);
        BookRequest bookRequest = bookRequestService.findById(bookRequestId);
        Room room = roomServiceImpl.findById(bill.getRoomId());
        RoomType roomType = roomTypeService.findById(room.getRoomTypeId());
        StringBuilder roomStringBuilder = new StringBuilder();
        roomStringBuilder.append("#")
                .append(room.getNumber())
                .append(" (")
                .append(roomType.getType())
                .append(")");

        request.setAttribute(jspConfig.get(BILL_TOTAL_PRICE_NAME), bill.getTotalPrice());
        request.setAttribute(jspConfig.get(ROOM_TYPE_NAME), roomStringBuilder.toString());
        request.setAttribute(jspConfig.get(CHECK_IN_DATE_NAME), bookRequest.getCheckIn());
        request.setAttribute(jspConfig.get(CHECK_OUT_DATE_NAME), bookRequest.getCheckOut());
        request.setAttribute(jspConfig.get(ID_ROOM_TYPE_MAP_NAME), roomTypeService.findAllAsMap());

        if (userRoleService.userIsAdmin(user)) {
            request.setAttribute(jspConfig.get(BOOK_REQUEST_ID_NAME), bookRequestId);
            request.setAttribute(jspConfig.get(BOOK_REQUEST_CREATION_DATE_NAME), bookRequest.getCreationDate());
            request.setAttribute(jspConfig.get(BOOK_REQUEST_PERSONS_NAME), bookRequest.getPersons());

            page = PathConfig.getInstance().getProperty(PathConfig.ADMIN_BILL_INFO_PAGE_PATH);
        } else {
            request.setAttribute(jspConfig.get(USER_NAME), user);
            request.setAttribute(jspConfig.get(ROOM_NUMBER_NAME), room.getNumber());
            request.setAttribute(jspConfig.get(ROOM_PRICE_NAME), room.getPrice());
            request.setAttribute(jspConfig.get(BILL_CREATION_DATE_NAME), bill.getCreationDate());

            page = PathConfig.getInstance().getProperty(PathConfig.CLIENT_BILL_INFO_PAGE_PATH);
        }

        return page;
    }
}
