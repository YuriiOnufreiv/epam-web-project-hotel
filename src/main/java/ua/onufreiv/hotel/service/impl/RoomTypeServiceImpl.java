package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.RoomType;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.RoomTypeDao;
import ua.onufreiv.hotel.service.RoomTypeService;

import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public class RoomTypeServiceImpl implements RoomTypeService {
    private static RoomTypeServiceImpl instance;

    private final RoomTypeDao roomTypeDao;

    private RoomTypeServiceImpl() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        roomTypeDao = daoFactory.getRoomTypeDao();
    }

    public synchronized static RoomTypeServiceImpl getInstance() {
        if (instance == null) {
            instance = new RoomTypeServiceImpl();
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
