package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entities.BookRequest;
import ua.onufreiv.hotel.service.impl.BookRequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class CommandShowAdminDashboard implements ua.onufreiv.hotel.controller.commands.ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BookRequest> notProcessedRequests = new BookRequestService().getNotProcessedRequests();
        request.setAttribute("newBookRequests", notProcessedRequests);
        request.setAttribute("newBookRequestsCount", notProcessedRequests.size());
//        String checkIn = "2017-01-02";
//        String checkOut = "2017-01-11";
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date checkInDate = formatter.parse(checkIn);
//            Date checkOutDate = formatter.parse(checkOut);
//            List<ReservedRoom> reservedInDateRange = new ReservedRoomService().findReservedInDateRange(checkInDate, checkOutDate);
//            List<Integer> roomIds = new ArrayList<>(reservedInDateRange.size());
//            for (ReservedRoom reservedRoom : reservedInDateRange) {
//                roomIds.add(reservedRoom.getId());
//            }
//            List<Room> possibleRooms = new RoomService().findAllExcept(roomIds);
//
//
//            BookRequest bookRequest = new BookRequest();
//            bookRequest.setRoomTypeId(1);
//            bookRequest.setCheckIn(null);
//            bookRequest.setCheckOut(null);
//            bookRequest.setPersons(4);
//            List<Room> exact = new ExactRoomFinder().getMostSuitableRooms(bookRequest, possibleRooms, new RoomTypeService().getIdTypeMap());
//            List<Room> cheaper = new CheaperRoomFinder().getMostSuitableRooms(bookRequest, possibleRooms, new RoomTypeService().getIdTypeMap());
//            List<Room> expensive = new ExpensiveRoomFinder().getMostSuitableRooms(bookRequest, possibleRooms, new RoomTypeService().getIdTypeMap());
//
//
//            request.setAttribute("possibleRooms", possibleRooms);
//            HttpSession session = request.getSession(false);
//            session.setAttribute("idTypeTitlesMap", new RoomTypeService().getIdTypeTitleMap());
//            System.out.println();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        return PathConfig.getInstance().getProperty(PathConfig.ADMIN_DASHBOARD_PAGE_PATH);
    }
}