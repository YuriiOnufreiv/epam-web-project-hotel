package ua.onufreiv.hotel.util.roomfinder;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public class ExpensiveRoomFinder implements IRoomFinder {

    @Override
    public List<Room> getMostSuitableRooms(BookRequest bookRequest, List<Room> rooms, Map<Integer, String> types) {
//        int neededTypeId = bookRequest.getRoomTypeId();
//        int neededPersonsAmount = bookRequest.getPersons();
//
//        List<Room> suitableRooms = new ArrayList<>();
//        int priceOfSelectedType = types.get(neededTypeId).getPrice();
//
//        for (Room room : rooms) {
//            if (types.get(room.getRoomTypeId()).getPrice() > priceOfSelectedType
//                    && types.get(room.getRoomTypeId()).getMaxPerson() >= neededPersonsAmount) {
//                suitableRooms.add(room);
//            }
//        }
//
//        return suitableRooms;
        return new ArrayList<>();
    }
}
