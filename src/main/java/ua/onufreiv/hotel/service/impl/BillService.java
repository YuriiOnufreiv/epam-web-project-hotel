package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.entities.Bill;
import ua.onufreiv.hotel.entities.BookRequest;
import ua.onufreiv.hotel.jdbc.ConnectionManager;
import ua.onufreiv.hotel.service.IBillService;

/**
 * Created by yurii on 1/10/17.
 */
public class BillService implements IBillService {
    @Override
    public boolean createNewBill(Bill bill) {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        daoFactory.getBillDao().insert(bill);
        BookRequest bookRequest = daoFactory.getBookRequestDao().find(bill.getBookRequestId());
        if(bookRequest.getProcessed()) {
            return false;
        }
        bookRequest.setProcessed(true);
        daoFactory.getBookRequestDao().update(bookRequest);
        return true;
    }

    @Override
    public Bill getByBookRequestId(int id) {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        return daoFactory.getBillDao().findByBookRequestId(id);
    }
}
