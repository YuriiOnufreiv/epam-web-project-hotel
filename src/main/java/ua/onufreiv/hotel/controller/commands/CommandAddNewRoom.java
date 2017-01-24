package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.service.IRoomService;
import ua.onufreiv.hotel.service.impl.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yurii on 1/14/17.
 */
public class CommandAddNewRoom implements Command {
    private static final String ROOM_TYPE_NAME = "room_type";
    private static final String NUMBER_NAME = "number";
    private static final String INVALID_ROOM_NUMBER_ERROR_NAME = "invalidRoomNumberError";
    private static final String ADD_ROOM_SUCCESS_NAME = "addRoomSuccess";

    private final RoomService roomService;

    public CommandAddNewRoom() {
        roomService = new RoomService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        IRoomService roomService = this.roomService;

        int roomTypeId = Integer.parseInt(request.getParameter(ROOM_TYPE_NAME));
        int number = Integer.parseInt(request.getParameter(NUMBER_NAME));

        Room room = new Room(null, roomTypeId, number);
        if (roomService.getByRoomNumber(number) == null) {
            roomService.addNewRoom(room);
            request.setAttribute(ADD_ROOM_SUCCESS_NAME, true);
        } else {
            request.setAttribute(INVALID_ROOM_NUMBER_ERROR_NAME, true);
            request.setAttribute(NUMBER_NAME, number);
            request.setAttribute(ROOM_TYPE_NAME, roomTypeId);
        }

        return PathConfig.getInstance().getProperty(PathConfig.ADD_NEW_ROOM_PAGE_PATH);
    }
}
