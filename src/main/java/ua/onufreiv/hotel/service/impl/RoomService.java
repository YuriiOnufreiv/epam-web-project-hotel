package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.jdbc.ConnectionManager;
import ua.onufreiv.hotel.service.IRoomService;

import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class RoomService implements IRoomService {
    @Override
    public List<Room> findAllExcept(List<Integer> exceptRoomIds) {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getRoomDao().findAllExcept(exceptRoomIds);
    }

    @Override
    public Room getById(int id) {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getRoomDao().find(id);
    }

    @Override
    public Room getByRoomNumber(int number) {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getRoomDao().findByRoomNum(number);
    }

    @Override
    public void addNewRoom(Room room) {
        DaoFactory.getDAOFactory(ConnectionManager.databaseType).getRoomDao().insert(room);
    }
}
