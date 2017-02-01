package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.RoomType;

import java.util.List;
import java.util.Map;

/**
 * DAO for {@link RoomType} entity; contains additional methods.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/4/17.
 */
public interface RoomTypeDao extends Dao<RoomType> {
    /**
     * @return list of room types string
     */
    List<String> findAllRoomTypesString();

    /**
     * Map of entities having id as a key and room type string as value
     *
     * @return map of room types
     */
    Map<Integer, String> findAllAsMap();
}