package ua.onufreiv.hotel.controller;

import ua.onufreiv.hotel.controller.commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Finds appropriate command to handle user request.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/27/16.
 */
public class ControllerHelper {
    private static ControllerHelper helperInstance = null;

    /**
     * Map that contains command name as a key, and command object as a value
     */
    private Map<String, Command> commands;

    /**
     * Initializes the {@code commands} map
     */
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

    /**
     * Realization of singleton pattern
     *
     * @return instance of {@code ControllerHelper}
     */
    public static synchronized ControllerHelper getInstance() {
        if (helperInstance == null) {
            helperInstance = new ControllerHelper();
        }
        return helperInstance;
    }

    /**
     * Finds appropriate command by extracting parameter 'command' from the request
     *
     * @param request request that should be processed
     * @return {@link Command} object that will process the specified action,
     * {@link CommandRedirectToHome} object if the command wasn't specified but user is signed in,
     * {@link CommandEmpty} object otherwise
     */
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