package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.ReservedRoomDao;
import ua.onufreiv.hotel.service.ReservedRoomService;

/**
 * Created by yurii on 1/5/17.
 */
public class ReservedRoomServiceImpl implements ReservedRoomService {
    private static ReservedRoomServiceImpl instance;

    private final ReservedRoomDao reservedRoomDao;

    private ReservedRoomServiceImpl() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        reservedRoomDao = daoFactory.getReservedRoomDao();
    }

    public static synchronized ReservedRoomServiceImpl getInstance() {
        if (instance == null) {
            instance = new ReservedRoomServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean deleteExpired() {
        return reservedRoomDao.deleteExpired();
    }
}
