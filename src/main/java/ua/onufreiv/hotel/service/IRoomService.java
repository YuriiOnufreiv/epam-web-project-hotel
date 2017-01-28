package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.Room;

import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public interface IRoomService {
    List<Room> findAllExcept(List<Integer> exceptRoomIds);

    Room getById(int id);

    Room getByRoomNumber(int number);

    boolean addNewRoom(Room room);
}
