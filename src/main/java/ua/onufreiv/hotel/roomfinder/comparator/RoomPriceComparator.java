package ua.onufreiv.hotel.roomfinder.comparator;

import ua.onufreiv.hotel.entity.Room;

import java.util.Comparator;

/**
 * Created by yurii on 1/27/17.
 */
public class RoomPriceComparator implements Comparator<Room> {
    @Override
    public int compare(Room room1, Room room2) {
        return room1.getPrice().compareTo(room2.getPrice());
    }
}
