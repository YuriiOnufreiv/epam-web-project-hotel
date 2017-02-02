package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.config.JspConfig;
import ua.onufreiv.hotel.controller.config.PathConfig;
import ua.onufreiv.hotel.entity.RoomType;
import ua.onufreiv.hotel.service.RoomTypeService;
import ua.onufreiv.hotel.service.impl.RoomTypeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.config.JspConfig.*;

/**
 * Command for adding new room type
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/14/17.
 */
public class CommandAddNewRoomType implements Command {
    private final RoomTypeService roomTypeServiceImpl;
    private final JspConfig names;

    public CommandAddNewRoomType() {
        roomTypeServiceImpl = RoomTypeServiceImpl.getInstance();
        names = JspConfig.getInstance();
    }

    /**
     * Handles request to add new room type
     *
     * @param request  request with the required parameters and attributes
     * @param response response that will be formed as a result
     * @return path to the same page
     * @see PathConfig
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter(names.get(ROOM_TYPE_NAME));

        RoomType roomType = new RoomType(null, type);
        if (roomTypeServiceImpl.typeExists(type)) {
            request.setAttribute(names.get(INVALID_ROOM_TYPE_ERROR_NAME), true);
            request.setAttribute(names.get(ROOM_TYPE_NAME), type);
        } else if (roomTypeServiceImpl.insertRoomType(roomType)) {
            request.setAttribute(names.get(ADD_ROOM_TYPE_SUCCESS_NAME), true);
        } else {
            request.setAttribute(names.get(ADD_ROOM_TYPE_ERROR_NAME), true);
        }

        return PathConfig.getInstance().getProperty(PathConfig.ADD_NEW_ROOM_TYPE_PAGE_PATH);
    }
}
