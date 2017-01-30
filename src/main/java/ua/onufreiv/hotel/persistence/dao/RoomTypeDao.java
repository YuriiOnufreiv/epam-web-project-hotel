package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.RoomType;

import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public interface RoomTypeDao extends Dao<RoomType> {
    List<String> findAllRoomTypesString();

    Map<Integer, String> findAllAsMap();
}