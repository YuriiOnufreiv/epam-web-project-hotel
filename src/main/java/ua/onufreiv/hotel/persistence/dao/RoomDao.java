package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.Room;

import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public interface RoomDao extends Dao<Room> {
    Room findByRoomNum(int number);

    List<Room> findAllExcept(List<Integer> exceptRoomIds);
}
