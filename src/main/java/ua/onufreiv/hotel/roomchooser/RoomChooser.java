package ua.onufreiv.hotel.roomchooser;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;

import java.util.List;
import java.util.Map;

/**
 * This interface is the basis for the realization of 'strategy' pattern.
 * Represents different algorithms for finding room according to particular
 * book request.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/5/17.
 */
public interface RoomChooser {
    /**
     * Finds room for a particular book request according to specific algorithm
     *
     * @param requestToProcess book request that should be processed
     * @param prevRequests     previous requests of the same user
     * @param rooms            list of possible rooms
     * @param types            map of types of rooms
     * @return room that was found out by specific algorithm
     */
    Room chooseRoom(BookRequest requestToProcess, List<BookRequest> prevRequests, List<Room> rooms,
                    Map<Integer, String> types);
}
