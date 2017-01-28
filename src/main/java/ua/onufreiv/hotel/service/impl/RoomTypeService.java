package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.RoomType;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.IRoomTypeDao;
import ua.onufreiv.hotel.service.IRoomTypeService;

import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public class RoomTypeService implements IRoomTypeService {
    private static RoomTypeService instance;

    private final IRoomTypeDao roomTypeDao;

    private RoomTypeService() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        roomTypeDao = daoFactory.getRoomTypeDao();
    }

    public synchronized static RoomTypeService getInstance() {
        if (instance == null) {
            instance = new RoomTypeService();
        }
        return instance;
    }

    @Override
    public boolean insertRoomType(RoomType roomType) {
        return roomTypeDao.insert(roomType) >= 0;
    }

    @Override
    public RoomType findById(int id) {
        return roomTypeDao.find(id);
    }

    @Override
    public Map<Integer, String> findAllAsMap() {
        return roomTypeDao.findAllAsMap();
    }

    @Override
    public boolean typeExists(String type) {
        List<String> allRoomTypes = roomTypeDao.findAllRoomTypesString();
        for (String existingType : allRoomTypes) {
            if (existingType.trim().equalsIgnoreCase(type.trim())) {
                return true;
            }
        }
        return false;
    }
}
