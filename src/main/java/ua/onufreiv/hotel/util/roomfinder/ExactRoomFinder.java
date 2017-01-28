package ua.onufreiv.hotel.util.roomfinder;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/7/17.
 */
public class ExactRoomFinder implements IRoomFinder {

    @Override
    public List<Room> getMostSuitableRooms(BookRequest bookRequest, List<Room> rooms, Map<Integer, String> types) {
        int neededTypeId = bookRequest.getRoomTypeId();
        int neededPersonsAmount = bookRequest.getPersons();

        List<Room> suitableRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (room.getRoomTypeId().equals(neededTypeId)
                    && room.getMaxPerson() >= neededPersonsAmount) {
                suitableRooms.add(room);
            }
        }

        return suitableRooms;
    }
}
