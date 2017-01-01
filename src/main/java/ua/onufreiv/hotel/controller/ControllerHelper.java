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

    private Map<String, ICommand> commands;

    private ControllerHelper() {
        commands = new HashMap<>();
        commands.put("login", new CommandLogin());
        commands.put("logout", new CommandLogout());
        commands.put("register", new CommandRegister());
    }

    public static ControllerHelper getInstance() {
        if(helperInstance == null) {
            helperInstance = new ControllerHelper();
        }
        return helperInstance;
    }

    public ICommand getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        ICommand command = commands.get(action);

        if(command == null) {
            return (request.getSession(false) != null)
                    ? new CommandRedirect() : new CommandEmpty();
        }

        return command;
    }


}