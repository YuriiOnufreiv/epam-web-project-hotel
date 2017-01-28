package ua.onufreiv.hotel.controller;

import ua.onufreiv.hotel.controller.commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yurii on 12/27/16.
 */
public class ControllerHelper {
    private static ControllerHelper helperInstance = null;

    private Map<String, Command> commands;

    private ControllerHelper() {
        commands = new HashMap<>();

        // common commands
        commands.put("login", new CommandLogin());
        commands.put("logout", new CommandLogout());
        commands.put("register", new CommandRegister());
        commands.put("redirect", new CommandRedirectToHome());
        commands.put("forward", new CommandForwardToPage());

        // client specific commands
        commands.put("makeNewBooking", new CommandMakeNewBooking());
        commands.put("showClientRequests", new CommandShowClientRequests());
        commands.put("showBillInfo", new CommandShowBillInfo());

        // admin specific commands
        commands.put("showAdminDashboard", new CommandShowAdminDashboard());
        commands.put("processBookRequest", new CommandProcessBookRequest());
        commands.put("createBill", new CommandCreateBill());
        commands.put("addNewRoomType", new CommandAddNewRoomType());
        commands.put("addNewRoom", new CommandAddNewRoom());
    }

    public static ControllerHelper getInstance() {
        if (helperInstance == null) {
            helperInstance = new ControllerHelper();
        }
        return helperInstance;
    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        Command command = commands.get(action);

        if (command == null) {
            return (request.getSession(false) != null) ?
                    new CommandRedirectToHome() : new CommandEmpty();
        }

        return command;
    }


}
