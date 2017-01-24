package ua.onufreiv.hotel.controller;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.controller.commands.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yurii on 12/27/16.
 */
public class Controller extends HttpServlet {
    private final static Logger logger = Logger.getLogger(Controller.class);

    private ControllerHelper controllerHelper;

    public Controller() {
        controllerHelper = ControllerHelper.getInstance();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = controllerHelper.getCommand(request);
        String page = command.execute(request, response);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("=== Processing POST request ===");
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("=== Processing GET request ===");
        processRequest(request, response);
    }
}