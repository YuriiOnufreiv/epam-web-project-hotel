package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.Bill;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.ReservedRoom;
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
        ReservedRoom reservedRoom = new ReservedRoom(null, bill.getRoomId(), bookRequest.getCheckIn(), bookRequest.getCheckOut());
        bookRequest.setProcessed(true);

        ConnectionManager.startTransaction();
        if (daoFactory.getReservedRoomDao().insert(reservedRoom) < 0
                || daoFactory.getBillDao().insert(bill) < 0
                || !daoFactory.getBookRequestDao().update(bookRequest)) {
            ConnectionManager.rollback();
            bookRequest.setProcessed(false);
            return false;
        }
        ConnectionManager.commit();

        return true;
    }

    @Override
    public Bill getByBookRequestId(int id) {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        return daoFactory.getBillDao().findByBookRequestId(id);
    }
}
