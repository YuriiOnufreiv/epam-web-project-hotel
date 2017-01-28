package ua.onufreiv.hotel.util.roomfinder;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;

import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/28/17.
 */
public class AlternativeRoomFinder implements IRoomChooser {
    @Override
    public Room chooseRoom(BookRequest bookRequest, List<Room> rooms, Map<Integer, String> types) {
        return null;
    }
}
