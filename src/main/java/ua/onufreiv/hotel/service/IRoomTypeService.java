package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entities.RoomType;

import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public interface IRoomTypeService {
    int addNewRoomType(RoomType roomType);
    boolean containsType(String type);
    RoomType find(int id);
    Map<Integer, String> getIdTypeTitleMap();
    Map<Integer, RoomType> getIdTypeMap();
}