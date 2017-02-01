package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.Bill;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.ReservedRoom;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.*;
import ua.onufreiv.hotel.service.BillService;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by yurii on 1/10/17.
 */
public class BillServiceImpl implements BillService {
    private static BillServiceImpl instance;

    private final BookRequestDao bookRequestDao;
    private final RoomDao roomDao;
    private final ReservedRoomDao reservedRoomDao;
    private final BillDao billDao;

    private BillServiceImpl() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        bookRequestDao = daoFactory.getBookRequestDao();
        roomDao = daoFactory.getRoomDao();
        reservedRoomDao = daoFactory.getReservedRoomDao();
        billDao = daoFactory.getBillDao();
    }

    public static synchronized BillServiceImpl getInstance() {
        if (instance == null) {
            instance = new BillServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean insertBill(Bill bill) {
        BookRequest bookRequest = bookRequestDao.find(bill.getBookRequestId());
        boolean roomIsReserved = reservedRoomDao.roomIsReserved(bill.getRoomId(),
                bookRequest.getCheckIn(), bookRequest.getCheckOut());

        if (bookRequest.getProcessed() || roomIsReserved) {
            return false;
        }
        ReservedRoom reservedRoom = new ReservedRoom(null, bill.getRoomId(), bookRequest.getCheckIn(), bookRequest.getCheckOut());
        bookRequest.setProcessed(true);

        ConnectionManager.startTransaction();
        if (reservedRoomDao.insert(reservedRoom) < 0
                || billDao.insert(bill) < 0
                || !bookRequestDao.update(bookRequest)) {
            ConnectionManager.rollback();
            bookRequest.setProcessed(false);
            return false;
        }
        ConnectionManager.commit();
        return true;
    }

    @Override
    public Bill findByBookRequestId(int id) {
        return billDao.findByBookRequestId(id);
    }

    @Override
    public Bill createAndInsertBill(int bookRequestId, int roomId) {
        BookRequest bookRequest = bookRequestDao.find(bookRequestId);
        Room room = roomDao.find(roomId);

        long diff = bookRequest.getCheckOut().getTime() - bookRequest.getCheckIn().getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        long price = days * room.getPrice();

        Bill bill = new Bill();
        bill.setCreationDate(new Date());
        bill.setBookRequestId(bookRequest.getId());
        bill.setRoomId(room.getId());
        bill.setTotalPrice(price);

        return insertBill(bill) ? bill : null;
    }
}
