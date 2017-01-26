package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.service.IRoomService;
import ua.onufreiv.hotel.service.impl.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.*;

/**
 * Created by yurii on 1/14/17.
 */
public class CommandAddNewRoom implements Command {
    private final RoomService roomService;
    private final ParamNamesConfig names;

    public CommandAddNewRoom() {
        roomService = new RoomService();
        names = ParamNamesConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        IRoomService roomService = this.roomService;

        int roomTypeId = Integer.parseInt(request.getParameter(names.get(ROOM_TYPE_NAME)));
        int number = Integer.parseInt(request.getParameter(names.get(ROOM_NUMBER_NAME)));

        Room room = new Room(null, roomTypeId, number);
        if (roomService.getByRoomNumber(number) == null) {
            roomService.addNewRoom(room);
            request.setAttribute(names.get(ADD_ROOM_SUCCESS_NAME), true);
        } else {
            request.setAttribute(names.get(INVALID_ROOM_NUMBER_ERROR_NAME), true);
            request.setAttribute(names.get(ROOM_NUMBER_NAME), number);
            request.setAttribute(names.get(ROOM_TYPE_NAME), roomTypeId);
        }

        return PathConfig.getInstance().getProperty(PathConfig.ADD_NEW_ROOM_PAGE_PATH);
    }
}
