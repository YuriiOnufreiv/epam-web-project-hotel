package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.entities.RoomType;
import ua.onufreiv.hotel.service.IRoomTypeService;

import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public class RoomTypeService implements IRoomTypeService {
    @Override
    public RoomType find(int id) {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomTypeDao().find(id);
    }

    @Override
    public Map<Integer, String> getIdTypeTitleMap() {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomTypeDao().getIdTypeTitleMap();
    }
}
