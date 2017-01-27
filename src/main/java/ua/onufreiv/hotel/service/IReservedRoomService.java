package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.ReservedRoom;

import java.util.Date;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public interface IReservedRoomService {
    List<ReservedRoom> findReservedInDateRange(Date checkInDate, Date checkOutDate);

    boolean removeExpiredDateReservedRooms();
}
