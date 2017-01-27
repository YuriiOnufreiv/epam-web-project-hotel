package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.ReservedRoom;

import java.util.Date;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public interface IReservedRoomDao extends IDao<ReservedRoom> {
    List<ReservedRoom> findReservedInDateRange(Date checkInDate, Date checkOutDate);

    boolean roomIsReservedInDateRange(int roomId, Date checkInDate, Date checkOutDate);

    boolean removeExpiredDateReservedRooms(Date date);
}
