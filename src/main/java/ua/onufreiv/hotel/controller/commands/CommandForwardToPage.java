package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.service.IRoomTypeService;
import ua.onufreiv.hotel.service.impl.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.ID_ROOM_TYPE_MAP_NAME;

/**
 * Created by yurii on 1/26/17.
 */
public class CommandForwardToPage implements Command {
    private final IRoomTypeService roomTypeService;
    private final ParamNamesConfig names;

    public CommandForwardToPage() {
        roomTypeService = RoomTypeService.getInstance();
        names = ParamNamesConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String pageType = request.getParameter("page");
        boolean mapRequired = Boolean.parseBoolean(request.getParameter("mapRequired"));
        Map<Integer, String> idTypeTitleMap = roomTypeService.findAllAsMap();

        if (mapRequired) {
            request.setAttribute(names.get(ID_ROOM_TYPE_MAP_NAME), idTypeTitleMap);
        }

        return getPagePath(pageType);
    }

    private String getPagePath(String pageType) {
        String page;

        switch (pageType) {
            case "notSignedIn":
                page = PathConfig.getInstance().getProperty(PathConfig.NOT_SIGNED_IN_ERROR_PAGE_PATH);
                break;
            case "login":
                page = PathConfig.getInstance().getProperty(PathConfig.LOGIN_PAGE_PATH);
                break;
            case "clientMain":
                page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
                break;
            case "createAccount":
                page = PathConfig.getInstance().getProperty(PathConfig.CREATE_ACCOUNT_PAGE_PATH);
                break;
            case "addNewRoom":
                page = PathConfig.getInstance().getProperty(PathConfig.ADD_NEW_ROOM_PAGE_PATH);
                break;
            case "addNewRoomType":
                page = PathConfig.getInstance().getProperty(PathConfig.ADD_NEW_ROOM_TYPE_PAGE_PATH);
                break;
            case "newBookRequest":
                page = PathConfig.getInstance().getProperty(PathConfig.NEW_BOOK_REQUEST_PAGE_PATH);
                break;
            default:
                page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
                break;
        }

        return page;
    }
}
