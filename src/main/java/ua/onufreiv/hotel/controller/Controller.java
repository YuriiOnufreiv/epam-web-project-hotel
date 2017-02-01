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
 * Servlet that will respond to client requests.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/27/16.
 */
public class Controller extends HttpServlet {
    private final static Logger logger = Logger.getLogger(Controller.class);

    /**
     * This object contains list of possible commands
     */
    private ControllerHelper controllerHelper;

    public Controller() {
        controllerHelper = ControllerHelper.getInstance();
    }

    /**
     * Processes the request by finding appropriate command, executes it and
     * forwards the user to the resulting page
     *
     * @param request  request to process
     * @param response response to the user according to the information in {@code request}
     * @throws ServletException if the target resource throws this exception
     * @throws IOException      if the target resource throws this exception
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = controllerHelper.getCommand(request);
        String page = command.execute(request, response);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    /**
     * Processes the POST request with hte help of {@code processRequest} method
     *
     * @param request  request to process
     * @param response response to the user according to the information in {@code request}
     * @throws ServletException if the target resource throws this exception
     * @throws IOException      if the target resource throws this exception
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("=== Processing POST request ===");
        processRequest(request, response);
    }

    /**
     * Processes the GET request with hte help of {@code processRequest} method
     *
     * @param request  request to process
     * @param response response to the user according to the information in {@code request}
     * @throws ServletException if the target resource throws this exception
     * @throws IOException      if the target resource throws this exception
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("=== Processing GET request ===");
        processRequest(request, response);
    }
}