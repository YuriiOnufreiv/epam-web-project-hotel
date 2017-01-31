package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.entity.ReservedRoom;
import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.*;
import ua.onufreiv.hotel.roomchooser.AnyTypeRoomChooser;
import ua.onufreiv.hotel.roomchooser.ExactTypeRoomChooser;
import ua.onufreiv.hotel.roomchooser.FavouriteTypeRoomChooser;
import ua.onufreiv.hotel.roomchooser.RoomChooser;
import ua.onufreiv.hotel.service.RoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public class RoomServiceImpl implements RoomService {
    private static RoomServiceImpl instance;

    private final RoomTypeDao roomTypeDao;
    private final RoomDao roomDao;
    private final ReservedRoomDao reservedRoomDao;
    private final UserDao userDao;
    private final BookRequestDao bookRequestDaoDao;

    private RoomServiceImpl() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        roomTypeDao = daoFactory.getRoomTypeDao();
        roomDao = daoFactory.getRoomDao();
        reservedRoomDao = daoFactory.getReservedRoomDao();
        userDao = daoFactory.getUserDao();
        bookRequestDaoDao = daoFactory.getBookRequestDao();
    }

    public synchronized static RoomServiceImpl getInstance() {
        if (instance == null) {
            instance = new RoomServiceImpl();
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
    public Room searchRoomForRequest(BookRequest bookRequest, RoomChooser roomChooser) {
        List<ReservedRoom> reservedInDateRange = reservedRoomDao.findInDateRange(bookRequest.getCheckIn(), bookRequest.getCheckOut());
        List<Integer> roomIds = new ArrayList<>(reservedInDateRange.size());
        for (ReservedRoom reservedRoom : reservedInDateRange) {
            roomIds.add(reservedRoom.getId());
        }

        List<Room> possibleRooms = roomDao.findAllExcept(roomIds);
        Map<Integer, String> idTypeMap = roomTypeDao.findAllAsMap();
        List<BookRequest> allUserRequests = bookRequestDaoDao.findByUserId(bookRequest.getUserId());

        return roomChooser.chooseRoom(bookRequest, allUserRequests, possibleRooms, idTypeMap);
    }

    @Override
    public Room searchExactRoom(BookRequest bookRequest) {
        return searchRoomForRequest(bookRequest, new ExactTypeRoomChooser());
    }

    @Override
    public Room searchFavouriteTypeRoom(BookRequest bookRequest) {
        return searchRoomForRequest(bookRequest, new FavouriteTypeRoomChooser());
    }

    @Override
    public Room searchAnyTypeRoom(BookRequest bookRequest) {
        return searchRoomForRequest(bookRequest, new AnyTypeRoomChooser());
    }
}
