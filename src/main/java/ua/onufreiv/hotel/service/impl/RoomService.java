package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.ReservedRoom;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.*;
import ua.onufreiv.hotel.service.IRoomService;
import ua.onufreiv.hotel.util.roomfinder.IRoomChooser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public class RoomService implements IRoomService {
    private static RoomService instance;

    private final IRoomTypeDao roomTypeDao;
    private final IRoomDao roomDao;
    private final IReservedRoomDao reservedRoomDao;
    private final IUserDao userDao;

    private RoomService() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        roomTypeDao = daoFactory.getRoomTypeDao();
        roomDao = daoFactory.getRoomDao();
        reservedRoomDao = daoFactory.getReservedRoomDao();
        userDao = daoFactory.getUserDao();
    }

    public synchronized static RoomService getInstance() {
        if (instance == null) {
            instance = new RoomService();
        }
        return instance;
    }

    @Override
    public boolean insertRoom(Room room) {
        return roomDao.insert(room) >= 0;
    }

    @Override
    public Room findById(int id) {
        return roomDao.find(id);
    }

    @Override
    public Room findByRoomNumber(int number) {
        return roomDao.findByRoomNum(number);
    }

    @Override
    public Room searchRoomForRequest(BookRequest bookRequest, IRoomChooser roomFinder) {
        User user = userDao.find(bookRequest.getUserId());

        List<ReservedRoom> reservedInDateRange = reservedRoomDao.findInDateRange(bookRequest.getCheckIn(), bookRequest.getCheckOut());
        List<Integer> roomIds = new ArrayList<>(reservedInDateRange.size());
        for (ReservedRoom reservedRoom : reservedInDateRange) {
            roomIds.add(reservedRoom.getId());
        }

        List<Room> possibleRooms = roomDao.findAllExcept(roomIds);
        Map<Integer, String> idTypeMap = roomTypeDao.findAllAsMap();

        return roomFinder.chooseRoom(bookRequest, possibleRooms, idTypeMap);
    }
}
