package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.Bill;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.service.IBillService;

/**
 * Created by yurii on 1/10/17.
 */
public class BillService implements IBillService {
    @Override
    public boolean createNewBill(Bill bill) {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        BookRequest bookRequest = daoFactory.getBookRequestDao().find(bill.getBookRequestId());
        boolean roomIsReserved = daoFactory.getReservedRoomDao()
                .roomIsReservedInDateRange(bill.getRoomId(), bookRequest.getCheckIn(), bookRequest.getCheckOut());
        if (bookRequest.getProcessed() || roomIsReserved) {
            return false;
        }
        ConnectionManager.startTransaction();
        daoFactory.getBillDao().insert(bill);
        bookRequest.setProcessed(true);
        daoFactory.getBookRequestDao().update(bookRequest);
        ConnectionManager.commit();
        return true;
    }

    @Override
    public Bill getByBookRequestId(int id) {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        return daoFactory.getBillDao().findByBookRequestId(id);
    }
}
