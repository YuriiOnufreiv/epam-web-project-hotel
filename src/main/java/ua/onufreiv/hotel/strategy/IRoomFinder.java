package ua.onufreiv.hotel.strategy;

import ua.onufreiv.hotel.entities.BookRequest;
import ua.onufreiv.hotel.entities.Room;
import ua.onufreiv.hotel.entities.RoomType;

import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public interface IRoomFinder {
    List<Room> getMostSuitableRooms(BookRequest bookRequest, List<Room> rooms, Map<Integer, RoomType> types);
}
