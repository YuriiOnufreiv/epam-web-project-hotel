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
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomDao().findAllExcept(exceptRoomIds);
    }

    @Override
    public Room getById(int id) {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomDao().find(id);
    }

    @Override
    public Room getByRoomNumber(int number) {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomDao().findByRoomNum(number);
    }

    @Override
    public void addNewRoom(Room room) {
        DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getRoomDao().insert(room);
    }
}
