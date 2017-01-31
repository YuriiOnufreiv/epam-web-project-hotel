package ua.onufreiv.hotel.roomchooser;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.roomchooser.comparator.RoomPriceComparator;
import ua.onufreiv.hotel.util.MapValueComparator;

import java.util.*;

/**
 * This class is part of strategy pattern; implements the algorithm of
 * finding suitable room by taking into consideration user's favourite types.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/28/17.
 */
public class FavouriteTypeRoomChooser implements RoomChooser {

    /**
     * Chooses the cheapest room that has enough total persons amount,
     * with taking into consideration the favourite room types by analyzing
     * the {@code prevRequests} list.
     *
     * @param requestToProcess book request that should be processed
     * @param prevRequests     previous requests of the same user
     * @param rooms            list of possible rooms
     * @param types            map of types of rooms
     * @return the cheapest room of one of the favourite types
     */
    @Override
    public Room chooseRoom(BookRequest requestToProcess, List<BookRequest> prevRequests,
                           List<Room> rooms, Map<Integer, String> types) {
        Map<Integer, Integer> typesFrequencyMap = getRequestedTypesFrequencyMap(prevRequests);
        SortedSet<Map.Entry<Integer, Integer>> favouriteTypesSet = sortTypesMapByFrequency(typesFrequencyMap);

        int neededPersonsAmount = requestToProcess.getPersons();
        Comparator<Room> roomComparator = new RoomPriceComparator();
        Room alternativeRoom = null;

        Iterator<Map.Entry<Integer, Integer>> iterator = favouriteTypesSet.iterator();
        while (iterator.hasNext() && alternativeRoom == null) {
            int typeId = iterator.next().getKey();
            for (Room room : rooms) {
                if (room.getRoomTypeId().equals(typeId)
                        && room.getMaxPerson() >= neededPersonsAmount
                        && (alternativeRoom == null || roomComparator.compare(room, alternativeRoom) < 0)) {
                    alternativeRoom = room;
                }
            }
        }

        return alternativeRoom;
    }

    /**
     * Counts how many times particular room type was requested and forms the {@link Map}
     * instance as the result
     *
     * @param requests list of requests
     * @return map containing room type as key, and amount of requests with such type as value
     */
    private Map<Integer, Integer> getRequestedTypesFrequencyMap(List<BookRequest> requests) {
        Map<Integer, Integer> typesMap = new HashMap<>();

        for (BookRequest prevRequest : requests) {
            int roomTypeId = prevRequest.getRoomTypeId();

            if (typesMap.containsKey(roomTypeId)) {
                typesMap.put(roomTypeId, typesMap.get(roomTypeId) + 1);
            } else {
                typesMap.put(roomTypeId, 1);
            }
        }

        return typesMap;
    }

    /**
     * Sorts the {@code typesFrequencyMap} by it's value
     *
     * @param typesFrequencyMap map to sort
     * @return sorted set of {@link Map.Entry}'s
     */
    private SortedSet<Map.Entry<Integer, Integer>> sortTypesMapByFrequency(Map<Integer, Integer> typesFrequencyMap) {
        SortedSet<Map.Entry<Integer, Integer>> sortedSet = new TreeSet<>(new MapValueComparator<>());
        sortedSet.addAll(typesFrequencyMap.entrySet());

        return sortedSet;
    }
}
