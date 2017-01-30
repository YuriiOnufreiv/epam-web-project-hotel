package ua.onufreiv.hotel.roomfinder;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.roomfinder.comparator.RoomPriceComparator;
import ua.onufreiv.hotel.util.MapValueComparator;

import java.util.*;

/**
 * Created by yurii on 1/28/17.
 */
public class FavouriteTypeRoomChooser implements RoomChooser {

    @Override
    public Room chooseRoom(BookRequest requestToProcess, List<BookRequest> requests,
                           List<Room> rooms, Map<Integer, String> types) {
        Map<Integer, Integer> typesFrequencyMap = getRequestedTypesFrequencyMap(requests);
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

    private SortedSet<Map.Entry<Integer, Integer>> sortTypesMapByFrequency(Map<Integer, Integer> typesFrequencyMap) {
        SortedSet<Map.Entry<Integer, Integer>> sortedSet = new TreeSet<>(new MapValueComparator<>());
        sortedSet.addAll(typesFrequencyMap.entrySet());

        return sortedSet;
    }

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
}
