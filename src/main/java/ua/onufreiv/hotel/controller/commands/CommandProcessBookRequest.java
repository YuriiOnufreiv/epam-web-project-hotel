package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
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

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.*;

/**
 * Created by yurii on 1/10/17.
 */
public class CommandProcessBookRequest implements Command {
//    private static final String USER_NAME = "user";
//    private static final String ID_NAME = "id";
//    private static final String BOOK_REQUEST_NAME = "bookRequest";
//    private static final String EXACT_ROOM_NAME = "exactRoom";
//    private static final String CHEAPER_ROOM_NAME = "cheaperRoom";
//    private static final String EXPENSIVE_ROOM_NAME = "expensiveRoom";
//    private static final String ID_TYPE_MAP_NAME = "idTypeMap";

    private final IBookRequestService bookRequestService;
    private final IUserService userService;
    private final ReservedRoomService reservedRoomService;
    private final RoomService roomService;
    private final RoomTypeService roomTypeService;
    private final ParamNamesConfig names;

    public CommandProcessBookRequest() {
        bookRequestService = new BookRequestService();
        userService = new UserService();
        reservedRoomService = new ReservedRoomService();
        roomService = new RoomService();
        roomTypeService = new RoomTypeService();
        names = ParamNamesConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter(names.get(BOOK_REQUEST_ID_NAME)));
        BookRequest bookRequest = bookRequestService.getById(id);
        User user = userService.getById(bookRequest.getUserId());

        request.setAttribute(names.get(USER_NAME), user);
        request.setAttribute(names.get(BOOK_REQUEST_NAME), bookRequest);

        List<ReservedRoom> reservedInDateRange = reservedRoomService.findReservedInDateRange(bookRequest.getCheckIn(), bookRequest.getCheckOut());
        List<Integer> roomIds = new ArrayList<>(reservedInDateRange.size());
        for (ReservedRoom reservedRoom : reservedInDateRange) {
            roomIds.add(reservedRoom.getId());
        }
        List<Room> possibleRooms = roomService.findAllExcept(roomIds);
        Map<Integer, RoomType> idTypeMap = roomTypeService.getIdTypeMap();
        List<Room> exact = new ExactRoomFinder().getMostSuitableRooms(bookRequest, possibleRooms, idTypeMap);
        List<Room> cheaper = new CheaperRoomFinder().getMostSuitableRooms(bookRequest, possibleRooms, idTypeMap);
        List<Room> expensive = new ExpensiveRoomFinder().getMostSuitableRooms(bookRequest, possibleRooms, idTypeMap);

        HttpSession session = request.getSession(false);
        request.setAttribute(names.get(EXACT_ROOM_NAME), exact.size() > 0 ? exact.get(0) : null);
        request.setAttribute(names.get(CHEAPER_ROOM_NAME), cheaper.size() > 0 ? cheaper.get(0) : null);
        request.setAttribute(names.get(EXPENSIVE_ROOM_NAME), expensive.size() > 0 ? expensive.get(0) : null);
        session.setAttribute(names.get(ID_ROOM_TYPE_MAP_NAME), idTypeMap);

        return PathConfig.getInstance().getProperty(PathConfig.PROCESS_REQUEST_PAGE_PATH);
    }
}