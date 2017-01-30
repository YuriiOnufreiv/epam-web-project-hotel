package ua.onufreiv.hotel.roomfinder;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.roomfinder.comparator.RoomPriceComparator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/28/17.
 */
public class AnyTypeRoomChooser implements RoomChooser {

    @Override
    public Room chooseRoom(BookRequest bookRequest, List<BookRequest> requests, List<Room> rooms, Map<Integer, String> types) {
        int neededPersonsAmount = bookRequest.getPersons();
        Comparator<Room> roomComparator = new RoomPriceComparator();

        Room exactRoom = null;
        for (Room room : rooms) {
            if (room.getMaxPerson() >= neededPersonsAmount
                    && (exactRoom == null || roomComparator.compare(room, exactRoom) < 0)) {
                exactRoom = room;
            }
        }

        return exactRoom;
    }
}
