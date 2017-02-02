package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.config.JspConfig;
import ua.onufreiv.hotel.controller.config.PathConfig;
import ua.onufreiv.hotel.entity.Bill;
import ua.onufreiv.hotel.service.BillService;
import ua.onufreiv.hotel.service.impl.BillServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.config.JspConfig.*;

/**
 * Command for creating new bill after book request processing
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/10/17.
 */
public class CommandCreateBill implements Command {
    private final BillService billService;
    private final JspConfig jspConfig;

    public CommandCreateBill() {
        billService = BillServiceImpl.getInstance();
        jspConfig = JspConfig.getInstance();
    }

    /**
     * Handles request to create new bill
     *
     * @param request  request with the required parameters and attributes
     * @param response response that will be formed as a result
     * @return path to the page, that shows bill info, or to the same page in case of any error
     * @see PathConfig
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int bookRequestId = Integer.parseInt(request.getParameter(jspConfig.get(BOOK_REQUEST_ID_NAME)));
        int roomId = Integer.parseInt(request.getParameter(jspConfig.get(ROOM_ID_NAME)));

        Bill bill = billService.createAndInsertBill(bookRequestId, roomId);
        if (bill == null) {
            request.setAttribute(jspConfig.get(CREATE_BILL_ERROR_NAME), true);
            return PathConfig.getInstance()
                    .getProperty(PathConfig.PROCESS_BOOK_REQUEST_BY_ID_COMMAND_PATH) + bookRequestId;
        }
        request.setAttribute(jspConfig.get(CREATE_BILL_SUCCESS_NAME), true);

        return PathConfig.getInstance().getProperty(PathConfig.SHOW_BILL_INFO_COMMAND_PATH);
    }
}
