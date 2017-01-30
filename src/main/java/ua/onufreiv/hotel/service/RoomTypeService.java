package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.RoomType;

import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public interface RoomTypeService {
    boolean insertRoomType(RoomType roomType);

    RoomType findById(int id);

    Map<Integer, String> findAllAsMap();

    boolean typeExists(String type);
}