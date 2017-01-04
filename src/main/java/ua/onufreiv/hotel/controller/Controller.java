package ua.onufreiv.hotel.controller;

import ua.onufreiv.hotel.controller.commands.ICommand;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.controller.manager.MessageConfig;

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
    private ControllerHelper controllerHelper;

    public Controller() {
        controllerHelper = ControllerHelper.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        try {
            ICommand command = controllerHelper.getCommand(request);
            page = command.execute(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
            page = PathConfig.getInstance()
                    .getProperty(PathConfig.ERROR_PAGE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            page = PathConfig.getInstance()
                    .getProperty(PathConfig.ERROR_PAGE_PATH);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}