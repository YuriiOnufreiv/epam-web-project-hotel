package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.config.JspConfig;
import ua.onufreiv.hotel.controller.config.PathConfig;
import ua.onufreiv.hotel.service.RoomTypeService;
import ua.onufreiv.hotel.service.impl.RoomTypeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static ua.onufreiv.hotel.controller.config.JspConfig.ID_ROOM_TYPE_MAP_NAME;

/**
 * Command that returns path of the required page
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/26/17.
 */
public class CommandForwardToPage implements Command {
    private final RoomTypeService roomTypeService;
    private final JspConfig jspConfig;

    public CommandForwardToPage() {
        roomTypeService = RoomTypeServiceImpl.getInstance();
        jspConfig = JspConfig.getInstance();
    }

    /**
     * Handles request to forward to required page;
     * if request has {@code mapRequired} parameter and it's value is {@code true} then
     * it also set's map having room type id as key and room type string as value
     *
     * @param request  request with the required parameters and attributes
     * @param response response that will be formed as a result
     * @return path to the requested page
     * @see PathConfig
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String pageType = request.getParameter("page");
        boolean mapRequired = Boolean.parseBoolean(request.getParameter("mapRequired"));
        Map<Integer, String> idTypeTitleMap = roomTypeService.findAllAsMap();

        if (mapRequired) {
            request.setAttribute(jspConfig.get(ID_ROOM_TYPE_MAP_NAME), idTypeTitleMap);
        }

        return getPagePath(pageType);
    }

    /**
     * Defines needed page
     *
     * @param pageType name of the required page
     * @return path to the required page; main page's path if the required page wasn't found
     * @see
     */
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
