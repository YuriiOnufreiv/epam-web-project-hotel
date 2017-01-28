package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.IReservedRoomDao;
import ua.onufreiv.hotel.service.IReservedRoomService;

/**
 * Created by yurii on 1/5/17.
 */
public class ReservedRoomService implements IReservedRoomService {
    private static ReservedRoomService instance;

    private final IReservedRoomDao reservedRoomDao;

    private ReservedRoomService() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        reservedRoomDao = daoFactory.getReservedRoomDao();
    }

    public synchronized static ReservedRoomService getInstance() {
        if (instance == null) {
            instance = new ReservedRoomService();
        }
        return instance;
    }

    @Override
    public boolean deleteExpired() {
        return reservedRoomDao.deleteExpired();
    }
}
