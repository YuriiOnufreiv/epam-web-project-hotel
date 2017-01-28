package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.RoomType;

import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public interface IRoomTypeDao extends IDao<RoomType> {
    List<String> getAllRoomTypes();

    Map<Integer, String> getAllInMap();
}