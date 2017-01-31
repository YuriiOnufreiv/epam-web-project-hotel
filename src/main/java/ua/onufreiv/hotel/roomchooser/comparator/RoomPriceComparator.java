package ua.onufreiv.hotel.roomchooser.comparator;

import ua.onufreiv.hotel.entity.Room;

import java.util.Comparator;

/**
 * Comparator implementation that compares two {@link Room} object
 * by it's prices.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/27/17.
 */
public class RoomPriceComparator implements Comparator<Room> {

    /**
     * Compares two rooms by it's prices
     *
     * @param room1 first room to compare
     * @param room2 second room to compare
     * @return 1 if the price of {@code room1} is bigger,
     * -1 if the price of {@code room2} is bigger,
     * 0 if they are equals
     */
    @Override
    public int compare(Room room1, Room room2) {
        return room1.getPrice().compareTo(room2.getPrice());
    }
}
