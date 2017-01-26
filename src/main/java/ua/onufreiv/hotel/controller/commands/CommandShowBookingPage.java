package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.ID_ROOM_TYPE_TITLE_MAP_NAME;

/**
 * Created by yurii on 1/4/17.
 */
public class CommandShowBookingPage implements Command {
    private final IRoomTypeService roomTypeService;
    private final ParamNamesConfig names;

    public CommandShowBookingPage() {
        roomTypeService = new RoomTypeService();
        names = ParamNamesConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Map<Integer, String> idTypeTitleMap = roomTypeService.getIdTypeTitleMap();
        request.getSession(false).setAttribute(names.get(ID_ROOM_TYPE_TITLE_MAP_NAME), idTypeTitleMap);

        return PathConfig.getInstance().getProperty(PathConfig.NEW_BOOK_REQUEST_PAGE_PATH);
    }
}