package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.entities.RoomType;
import ua.onufreiv.hotel.service.IRoomTypeService;

import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public class RoomTypeService implements IRoomTypeService {
    @Override
    public boolean addNewRoomType(RoomType roomType) {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomTypeDao().insert(roomType) > 0;
    }

    @Override
    public boolean containsType(String type) {
        List<String> allRoomTypes = DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomTypeDao().getAllRoomTypes();
        for(String existingType : allRoomTypes) {
            if(existingType.trim().equalsIgnoreCase(type.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public RoomType find(int id) {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomTypeDao().find(id);
    }

    @Override
    public Map<Integer, String> getIdTypeTitleMap() {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomTypeDao().getIdTypeTitleMap();
    }

    @Override
    public Map<Integer, RoomType> getIdTypeMap() {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomTypeDao().getIdTypeMap();
    }
}
