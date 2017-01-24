package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public class CommandShowBookingPage implements Command {
    private static final String ID_TYPE_TITLES_MAP_NAME = "idTypeTitlesMap";

    private final IRoomTypeService roomTypeService;

    public CommandShowBookingPage() {
        roomTypeService = new RoomTypeService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Map<Integer, String> idTypeTitleMap = roomTypeService.getIdTypeTitleMap();
        request.getSession(false).setAttribute(ID_TYPE_TITLES_MAP_NAME, idTypeTitleMap);

        return PathConfig.getInstance().getProperty(PathConfig.NEW_BOOK_REQUEST_PAGE_PATH);
    }
}