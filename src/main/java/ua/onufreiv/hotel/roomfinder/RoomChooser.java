package ua.onufreiv.hotel.roomfinder;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;

import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public interface RoomChooser {
    Room chooseRoom(BookRequest requestToProcess, List<BookRequest> requests, List<Room> rooms, Map<Integer, String> types);
}
