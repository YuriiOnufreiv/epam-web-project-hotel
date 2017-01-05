package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.entities.Room;
import ua.onufreiv.hotel.service.IRoomService;

import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class RoomService implements IRoomService {
    @Override
    public List<Room> findAllExcept(List<Integer> exceptRoomIds) {
        return  DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomDao().findAllExcept(exceptRoomIds);
    }
}
