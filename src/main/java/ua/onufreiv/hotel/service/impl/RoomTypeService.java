package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.RoomType;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.service.IRoomTypeService;

import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public class RoomTypeService implements IRoomTypeService {
    @Override
    public boolean addNewRoomType(RoomType roomType) {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getRoomTypeDao().insert(roomType) >= 0;
    }

    @Override
    public boolean containsType(String type) {
        List<String> allRoomTypes = DaoFactory.getDAOFactory(ConnectionManager.databaseType).getRoomTypeDao().getAllRoomTypes();
        for(String existingType : allRoomTypes) {
            if(existingType.trim().equalsIgnoreCase(type.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public RoomType find(int id) {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getRoomTypeDao().find(id);
    }

    @Override
    public Map<Integer, String> getAllInMap() {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getRoomTypeDao().getAllInMap();
    }
}
