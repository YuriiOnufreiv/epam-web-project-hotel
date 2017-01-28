package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.ReservedRoom;

import java.util.Date;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public interface IReservedRoomDao extends IDao<ReservedRoom> {
    List<ReservedRoom> findInDateRange(Date checkInDate, Date checkOutDate);

    boolean roomIsReserved(int roomId, Date checkInDate, Date checkOutDate);

    boolean deleteExpired();
}
