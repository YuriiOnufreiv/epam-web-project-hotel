package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.*;
import ua.onufreiv.hotel.service.IBookRequestService;
import ua.onufreiv.hotel.service.IUserService;
import ua.onufreiv.hotel.service.impl.*;
import ua.onufreiv.hotel.util.roomfinder.CheaperRoomFinder;
import ua.onufreiv.hotel.util.roomfinder.ExactRoomFinder;
import ua.onufreiv.hotel.util.roomfinder.ExpensiveRoomFinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/10/17.
 */
public class CommandProcessBookRequest implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        IBookRequestService bookRequestService = new BookRequestService();
        IUserService userService = new UserService();
        int id = Integer.parseInt(request.getParameter("id"));
        BookRequest bookRequest = bookRequestService.getById(id);
        User user = userService.getById(bookRequest.getUserId());
        request.setAttribute("user", user);
        request.setAttribute("bookRequest", bookRequest);

        List<ReservedRoom> reservedInDateRange = new ReservedRoomService().findReservedInDateRange(bookRequest.getCheckIn(), bookRequest.getCheckOut());
        List<Integer> roomIds = new ArrayList<>(reservedInDateRange.size());
        for (ReservedRoom reservedRoom : reservedInDateRange) {
            roomIds.add(reservedRoom.getId());
        }
        List<Room> possibleRooms = new RoomService().findAllExcept(roomIds);
        Map<Integer, RoomType> idTypeMap = new RoomTypeService().getIdTypeMap();
        List<Room> exact = new ExactRoomFinder().getMostSuitableRooms(bookRequest, possibleRooms, idTypeMap);
        List<Room> cheaper = new CheaperRoomFinder().getMostSuitableRooms(bookRequest, possibleRooms, idTypeMap);
        List<Room> expensive = new ExpensiveRoomFinder().getMostSuitableRooms(bookRequest, possibleRooms, idTypeMap);

        HttpSession session = request.getSession(false);
        request.setAttribute("exactRoom", exact.size() > 0 ? exact.get(0) : null);
        request.setAttribute("cheaperRoom", cheaper.size() > 0 ? cheaper.get(0) : null);
        request.setAttribute("expensiveRoom", expensive.size() > 0 ? expensive.get(0) : null);
        session.setAttribute("idTypeMap", idTypeMap);

        return PathConfig.getInstance().getProperty(PathConfig.PROCESS_REQUEST_PAGE_PATH);
    }
}