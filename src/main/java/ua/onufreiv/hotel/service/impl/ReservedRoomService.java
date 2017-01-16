package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.ReservedRoom;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.service.IReservedRoomService;

import java.util.Date;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class ReservedRoomService implements IReservedRoomService {
    @Override
    public List<ReservedRoom> findReservedInDateRange(Date checkInDate, Date checkOutDate) {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getReservedRoomDao().findReservedInDateRange(checkInDate, checkOutDate);
    }
}
