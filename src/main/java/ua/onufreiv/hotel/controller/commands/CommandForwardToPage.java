package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yurii on 1/26/17.
 */
public class CommandForwardToPage implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String pageType = request.getParameter("page");

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
            default:
                page = PathConfig.getInstance().getProperty(PathConfig.MAIN_PAGE_PATH);
                break;
        }

        return page;
    }
}
