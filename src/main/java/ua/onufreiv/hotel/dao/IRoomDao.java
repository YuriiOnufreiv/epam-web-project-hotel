package ua.onufreiv.hotel.dao;

import ua.onufreiv.hotel.entities.Room;

import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public interface IRoomDao extends IDao<Room> {
    List<Room> findAllExcept(List<Integer> exceptRoomIds);

    Room findByRoomNum(int number);
}
