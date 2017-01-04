package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.service.IRoomTypeService;

import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public class RoomTypeService implements IRoomTypeService {
    @Override
    public Map<Integer, String> getIdTypeTitleMap() {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomTypeDAO().getIdTypeTitleMap();
    }
}