package ua.onufreiv.hotel.dao;

import ua.onufreiv.hotel.entities.RoomType;

import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public interface IRoomTypeDao extends IDao<RoomType> {
    Map<Integer, String> getIdTypeTitleMap();
}