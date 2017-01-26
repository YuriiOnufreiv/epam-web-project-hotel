package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.RoomType;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.*;

/**
 * Created by yurii on 1/14/17.
 */
public class CommandAddNewRoomType implements Command {
    private final RoomTypeService roomTypeService;
    private final ParamNamesConfig names;

    public CommandAddNewRoomType() {
        roomTypeService = new RoomTypeService();
        names = ParamNamesConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        IRoomTypeService roomTypeService = this.roomTypeService;

        String type = request.getParameter(names.get(ROOM_TYPE_NAME));
        int price = Integer.parseInt(request.getParameter(names.get(ROOM_PRICE_NAME)));
        int persons = Integer.parseInt(request.getParameter(names.get(ROOM_PERSONS_TOTAL_NAME)));
        String description = request.getParameter(names.get(ROOM_DESCRIPTION_NAME));

        RoomType roomType = new RoomType(null, type, description, price, persons);
        if (roomTypeService.containsType(type)) {
            request.setAttribute(names.get(INVALID_ROOM_TYPE_ERROR_NAME), true);
            request.setAttribute(names.get(ROOM_TYPE_NAME), type);
            request.setAttribute(names.get(ROOM_PRICE_NAME), price);
            request.setAttribute(names.get(ROOM_PERSONS_TOTAL_NAME), persons);
            request.setAttribute(names.get(ROOM_DESCRIPTION_NAME), description);
        } else {
            int id = roomTypeService.addNewRoomType(roomType);
            request.setAttribute(names.get(ADD_ROOM_TYPE_SUCCESS_NAME), true);
//            ((Map<Integer, String>) request.getSession(false).getAttribute(ID_TYPE_TITLES_NAME)).put(id, type);
        }

        return PathConfig.getInstance().getProperty(PathConfig.ADD_NEW_ROOM_TYPE_PAGE_PATH);
    }
}
