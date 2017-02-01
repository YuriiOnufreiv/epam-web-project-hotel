package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.Room;

import java.util.List;

/**
 * DAO for {@link Room} entity; contains additional methods.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/5/17.
 */
public interface RoomDao extends Dao<Room> {
    /**
     * Finds room entity by its number
     *
     * @param number number of room
     * @return room object in case of success search, {@code null} otherwise
     */
    Room findByRoomNum(int number);

    /**
     * Finds all rooms, whose id's isn't listed in the {@code exceptRoomIds} parameter
     *
     * @param exceptRoomIds list of id's that are not needed
     * @return list of rest rooms
     */
    List<Room> findAllExcept(List<Integer> exceptRoomIds);
}
