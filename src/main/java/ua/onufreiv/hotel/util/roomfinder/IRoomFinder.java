package ua.onufreiv.hotel.util.roomfinder;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.entity.RoomType;

import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public interface IRoomFinder {
    List<Room> getMostSuitableRooms(BookRequest bookRequest, List<Room> rooms, Map<Integer, RoomType> types);
}
