package ua.onufreiv.hotel.dao;

import ua.onufreiv.hotel.entities.RoomType;

import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public interface IRoomTypeDao extends IDao<RoomType> {
    List<String> getAllRoomTypes();
    Map<Integer, String> getIdTypeTitleMap();
    Map<Integer, RoomType> getIdTypeMap();
}