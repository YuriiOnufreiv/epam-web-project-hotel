package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.roomfinder.RoomChooser;

/**
 * Created by yurii on 1/5/17.
 */
public interface RoomService {
    boolean insertRoom(Room room);

    Room findById(int id);

    Room findByRoomNumber(int number);

    Room searchRoomForRequest(BookRequest bookRequest, RoomChooser roomFinder);
}
