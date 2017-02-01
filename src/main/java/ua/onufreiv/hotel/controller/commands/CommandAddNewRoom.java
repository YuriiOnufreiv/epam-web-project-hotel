package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.JspConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.service.impl.RoomServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.manager.JspConfig.*;

/**
 * Command for adding new room
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/14/17.
 */
public class CommandAddNewRoom implements Command {
    private final RoomServiceImpl roomServiceImpl;
    private final JspConfig names;

    public CommandAddNewRoom() {
        roomServiceImpl = RoomServiceImpl.getInstance();
        names = JspConfig.getInstance();
    }

    /**
     * Handles request to add new room
     *
     * @param request  request with the required parameters and attributes
     * @param response response that will be formed as a result
     * @return path to the same page
     * @see PathConfig
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int number = Integer.parseInt(request.getParameter(names.get(ROOM_NUMBER_NAME)));
        int price = Integer.parseInt(request.getParameter(names.get(ROOM_PRICE_NAME)));
        int persons = Integer.parseInt(request.getParameter(names.get(ROOM_PERSONS_TOTAL_NAME)));
        int roomTypeId = Integer.parseInt(request.getParameter(names.get(ROOM_TYPE_NAME)));
        String description = request.getParameter(names.get(ROOM_DESCRIPTION_NAME));

        Room room = new Room(null, roomTypeId, number, description, price, persons);
        if (roomServiceImpl.findByRoomNumber(number) != null) {
            request.setAttribute(names.get(INVALID_ROOM_NUMBER_ERROR_NAME), true);
            request.setAttribute(names.get(ROOM_NUMBER_NAME), number);
            request.setAttribute(names.get(ROOM_PRICE_NAME), price);
            request.setAttribute(names.get(ROOM_PERSONS_TOTAL_NAME), persons);
            request.setAttribute(names.get(ROOM_TYPE_NAME), roomTypeId);
            request.setAttribute(names.get(ROOM_DESCRIPTION_NAME), description);
        } else if (roomServiceImpl.insertRoom(room)) {
            request.setAttribute(names.get(ADD_ROOM_SUCCESS_NAME), true);
        } else {
            request.setAttribute(names.get(ADD_ROOM_ERROR_NAME), true);
        }

        return PathConfig.getInstance().getProperty(PathConfig.ADD_NEW_ROOM_PAGE_PATH);
    }
}
