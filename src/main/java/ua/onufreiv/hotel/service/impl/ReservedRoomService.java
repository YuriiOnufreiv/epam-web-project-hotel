package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.entities.ReservedRoom;
import ua.onufreiv.hotel.service.IReservedRoomService;

import java.util.Date;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class ReservedRoomService implements IReservedRoomService {
    @Override
    public List<ReservedRoom> findReservedInDateRange(Date checkInDate, Date checkOutDate) {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getReservedRoomDao().findReservedInDateRange(checkInDate, checkOutDate);
    }
}
