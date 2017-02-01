package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.ReservedRoom;

import java.util.Date;
import java.util.List;

/**
 * DAO for {@link ReservedRoom} entity; contains additional methods.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/5/17.
 */
public interface ReservedRoomDao extends Dao<ReservedRoom> {
    /**
     * Finds list of rooms that are reserved in the specified date range
     *
     * @param startDate starting date
     * @param endDate   ending date
     * @return list of reserved rooms
     */
    List<ReservedRoom> findInDateRange(Date startDate, Date endDate);

    /**
     * Checks if the room is reserved on the specified date range by it's id
     *
     * @param roomId    id of room
     * @param startDate starting date
     * @param endDate   ending date
     * @return true if room is reserved, false otherwise
     */
    boolean roomIsReserved(int roomId, Date startDate, Date endDate);

    /**
     * Method removes all rooms reservation of which is expired
     *
     * @return true if at least one room reservation was expired, false otherwise
     */
    boolean deleteExpired();
}
