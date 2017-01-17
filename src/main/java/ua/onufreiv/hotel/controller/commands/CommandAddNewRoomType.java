package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.RoomType;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by yurii on 1/14/17.
 */
public class CommandAddNewRoomType implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        IRoomTypeService roomTypeService = new RoomTypeService();

        String type = request.getParameter("type");
        int price = Integer.parseInt(request.getParameter("price"));
        int persons = Integer.parseInt(request.getParameter("persons"));
        String description = request.getParameter("description");

        RoomType roomType = new RoomType(null, type, description, price, persons);
        if (roomTypeService.containsType(type)) {
            request.setAttribute("invalidRoomTypeError", true);
            request.setAttribute("type", type);
            request.setAttribute("price", price);
            request.setAttribute("persons", persons);
            request.setAttribute("description", description);
        } else {
            int id = roomTypeService.addNewRoomType(roomType);
            request.setAttribute("addRoomTypeSuccess", true);
            ((Map<Integer, String>) request.getSession(false).getAttribute("idTypeTitlesMap")).put(id, type);
        }

        return PathConfig.getInstance().getProperty(PathConfig.ADD_NEW_ROOM_TYPE_PAGE_PATH);
    }
}
