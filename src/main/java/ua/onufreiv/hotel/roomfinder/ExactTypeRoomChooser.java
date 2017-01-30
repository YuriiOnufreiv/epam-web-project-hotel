package ua.onufreiv.hotel.roomfinder;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.roomfinder.comparator.RoomPriceComparator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/7/17.
 */
public class ExactTypeRoomChooser implements RoomChooser {

    @Override
    public Room chooseRoom(BookRequest requestToProcess, List<BookRequest> prevRequests,
                           List<Room> rooms, Map<Integer, String> types) {
        int neededTypeId = requestToProcess.getRoomTypeId();
        int neededPersonsAmount = requestToProcess.getPersons();
        Comparator<Room> roomComparator = new RoomPriceComparator();

        Room exactRoom = null;
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
