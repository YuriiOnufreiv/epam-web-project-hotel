package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.util.roomfinder.IRoomChooser;

/**
 * Created by yurii on 1/5/17.
 */
public interface IRoomService {
    boolean insertRoom(Room room);

    Room findById(int id);

    Room findByRoomNumber(int number);

    Room searchRoomForRequest(BookRequest bookRequest, IRoomChooser roomFinder);
}
