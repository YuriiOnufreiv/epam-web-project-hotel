package ua.onufreiv.hotel.util.roomfinder;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.util.roomfinder.comparator.RoomPriceComparator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/7/17.
 */
public class ExactRoomChooser implements IRoomChooser {

    @Override
    public Room chooseRoom(BookRequest bookRequest, List<Room> rooms, Map<Integer, String> types) {
        int neededTypeId = bookRequest.getRoomTypeId();
        int neededPersonsAmount = bookRequest.getPersons();

        Room exactRoom = null;

        Comparator<Room> roomComparator = new RoomPriceComparator();
        for (Room room : rooms) {
            if (room.getRoomTypeId().equals(neededTypeId)
                    && room.getMaxPerson() >= neededPersonsAmount
                    && (exactRoom == null || roomComparator.compare(room, exactRoom) < 0)) {
                exactRoom = room;
            }
        }

        return exactRoom;
    }
}
