package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.JspConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.RoomType;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.manager.JspConfig.*;

/**
 * Created by yurii on 1/14/17.
 */
public class CommandAddNewRoomType implements Command {
    private final RoomTypeService roomTypeService;
    private final JspConfig names;

    public CommandAddNewRoomType() {
        roomTypeService = RoomTypeService.getInstance();
        names = JspConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter(names.get(ROOM_TYPE_NAME));

        RoomType roomType = new RoomType(null, type);
        if (roomTypeService.typeExists(type)) {
            request.setAttribute(names.get(INVALID_ROOM_TYPE_ERROR_NAME), true);
            request.setAttribute(names.get(ROOM_TYPE_NAME), type);
        } else if (!roomTypeService.insertRoomType(roomType)) {
            request.setAttribute(names.get(ADD_ROOM_TYPE_SUCCESS_NAME), true);
            request.getSession(false).setAttribute(names.get(ID_ROOM_TYPE_MAP_NAME), roomTypeService.findAllAsMap());
        } else {
            request.setAttribute(names.get(ADD_ROOM_TYPE_ERROR_NAME), true);
        }

        return PathConfig.getInstance().getProperty(PathConfig.ADD_NEW_ROOM_TYPE_PAGE_PATH);
    }
}
