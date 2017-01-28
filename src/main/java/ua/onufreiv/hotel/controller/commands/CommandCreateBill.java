package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.Bill;
import ua.onufreiv.hotel.service.IBillService;
import ua.onufreiv.hotel.service.impl.BillService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.*;

/**
 * Created by yurii on 1/10/17.
 */
public class CommandCreateBill implements Command {
    private final IBillService billService;
    private final ParamNamesConfig names;

    public CommandCreateBill() {
        billService = BillService.getInstance();
        names = ParamNamesConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int bookRequestId = Integer.parseInt(request.getParameter(names.get(BOOK_REQUEST_ID_NAME)));
        int roomId = Integer.parseInt(request.getParameter(names.get(ROOM_ID_NAME)));

        Bill bill = billService.createAndInsertBill(bookRequestId, roomId);
        if (bill == null) {
            request.setAttribute(names.get(CREATE_BILL_ERROR_NAME), true);
            return PathConfig.getInstance()
                    .getProperty(PathConfig.PROCESS_BOOK_REQUEST_BY_ID_COMMAND_PATH) + bookRequestId;
        }
        request.setAttribute(names.get(CREATE_BILL_SUCCESS_NAME), true);

        return PathConfig.getInstance().getProperty(PathConfig.SHOW_BILL_INFO_COMMAND_PATH);
    }
}
