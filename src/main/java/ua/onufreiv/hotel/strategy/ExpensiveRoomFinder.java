package ua.onufreiv.hotel.strategy;

import ua.onufreiv.hotel.entities.BookRequest;
import ua.onufreiv.hotel.entities.Room;
import ua.onufreiv.hotel.entities.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public class ExpensiveRoomFinder implements IRoomFinder {

    @Override
    public List<Room> getMostSuitableRooms(BookRequest bookRequest, List<Room> rooms, Map<Integer, RoomType> types) {
        int neededTypeId = bookRequest.getRoomTypeId();
        int neededPersonsAmount = bookRequest.getPersons();

        List<Room> suitableRooms = new ArrayList<>();
        int priceOfSelectedType = types.get(neededTypeId).getPrice();

        for (Room room : rooms) {
            if (types.get(room.getRoomTypeId()).getPrice() >= priceOfSelectedType
                    && types.get(room.getRoomTypeId()).getMaxPerson() >= neededPersonsAmount) {
                suitableRooms.add(room);
            }
        }

        return suitableRooms;
    }
}
