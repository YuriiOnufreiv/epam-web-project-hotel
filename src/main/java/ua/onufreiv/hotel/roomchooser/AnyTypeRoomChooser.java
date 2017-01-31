package ua.onufreiv.hotel.roomchooser;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.roomchooser.comparator.RoomPriceComparator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * This class is part of strategy pattern; implements the algorithm
 * of finding suitable room with no regarding to it's type.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/28/17.
 */
public class AnyTypeRoomChooser implements RoomChooser {

    /**
     * Chooses the cheapest room that has enough total persons amount,
     * without taking into consideration the type, specified in the
     * {@code requestToProcess}.
     *
     * @param requestToProcess book request that should be processed
     * @param prevRequests     previous requests of the same user
     * @param rooms            list of possible rooms
     * @param types            map of types of rooms
     * @return the cheapest room that has enough total persons amount
     */
    @Override
    public Room chooseRoom(BookRequest requestToProcess, List<BookRequest> prevRequests, List<Room> rooms,
                           Map<Integer, String> types) {
        int neededPersonsAmount = requestToProcess.getPersons();
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
