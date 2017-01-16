package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.service.IRoomService;
import ua.onufreiv.hotel.service.impl.RoomService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yurii on 1/14/17.
 */
public class CommandAddNewRoom implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IRoomService roomService = new RoomService();

        int roomTypeId = Integer.parseInt(request.getParameter("room_type"));
        int number = Integer.parseInt(request.getParameter("number"));

        Room room = new Room(null, roomTypeId, number);
        if (roomService.getByRoomNumber(number) == null) {
            roomService.addNewRoom(room);
            request.setAttribute("addRoomSuccess", true);
        } else {
            request.setAttribute("invalidRoomNumberError", true);
            request.setAttribute("number", number);
            request.setAttribute("room_type", roomTypeId);
        }

        return PathConfig.getInstance().getProperty(PathConfig.ADD_NEW_ROOM_PAGE_PATH);
    }
}
