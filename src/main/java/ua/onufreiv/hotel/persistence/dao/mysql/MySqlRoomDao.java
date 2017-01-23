package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IRoomDao;
import ua.onufreiv.hotel.persistence.jdbc.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.jdbc.query.resultsetmapper.RoomMapper;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class MySqlRoomDao implements IRoomDao {
    private static final String TABLE_NAME = "room";
    private static final String COLUMN_ID_NAME = "idRoom";
    private static final String COLUMN_ROOM_TYPE_FK_NAME = "typeFK";
    private static final String COLUMN_NUMBER_NAME = "number";

    private static MySqlRoomDao instance;
    private QueryBuilder<Room> queryBuilder;

//    private static final String QUERY_INSERT = "INSERT INTO ROOM (typeFK, number) VALUES (?, ?)";
//    private static final String QUERY_SELECT_ALL = "SELECT * FROM ROOM";
//    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM ROOM WHERE idRoom = ?";
//    private static final String QUERY_SELECT_BY_ROOM_NUM = "SELECT * FROM ROOM WHERE number = ?";
//    private static final String QUERY_UPDATE = "UPDATE ROOM SET typeFK = ?, number = ? WHERE idRoom = ?";
//    private static final String QUERY_DELETE = "DELETE FROM ROOM WHERE idRoom = ?";

    private MySqlRoomDao() {
        queryBuilder = new QueryBuilder<>(TABLE_NAME);
    }

    public static synchronized MySqlRoomDao getInstance() {
        if (instance == null) {
            instance = new MySqlRoomDao();
        }
        return instance;
    }

    @Override
    public int insert(Room room) {
        Connection connection = ConnectionManager.getConnection();
        int id = queryBuilder.insert()
                .value(COLUMN_ROOM_TYPE_FK_NAME, room.getRoomTypeId())
                .value(COLUMN_NUMBER_NAME, room.getNumber())
                .execute(connection);
        ConnectionManager.closeConnection(connection);
        return id;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionManager.getConnection();
        boolean result = queryBuilder.delete()
                .where()
                .column(COLUMN_ID_NAME).isEqual(id)
                .executeUpdate(connection);
        ConnectionManager.closeConnection(connection);
        return result;
    }

    @Override
    public Room find(int id) {
        Connection connection = ConnectionManager.getConnection();
        Room room = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).isEqual(id)
                .executeQueryForObject(connection, new RoomMapper());
        ConnectionManager.closeConnection(connection);
        return room;
    }

    @Override
    public List<Room> findAll() {
        Connection connection = ConnectionManager.getConnection();
        List<Room> bookRequests = queryBuilder.select()
                .selectAll(connection, new RoomMapper());
        ConnectionManager.closeConnection(connection);
        return bookRequests;
    }

    @Override
    public boolean update(Room room) {
        Connection connection = ConnectionManager.getConnection();
        boolean update = queryBuilder.update()
                .set(COLUMN_ROOM_TYPE_FK_NAME, room.getRoomTypeId())
                .set(COLUMN_NUMBER_NAME, room.getNumber())
                .where()
                .column(COLUMN_ID_NAME).isEqual(room.getId())
                .executeUpdate(connection);
        ConnectionManager.closeConnection(connection);
        return update;
    }

    @Override
    public List<Room> findAllExcept(List<Integer> exceptRoomIds) {
        List<Room> validRooms = new ArrayList<>();
        List<Room> allRooms = findAll();
        for (Room room : allRooms) {
            if (!exceptRoomIds.contains(room.getId())) {
                validRooms.add(room);
            }
        }
        return validRooms;
    }

    @Override
    public Room findByRoomNum(int number) {
        Connection connection = ConnectionManager.getConnection();
        Room room = queryBuilder.select()
                .where()
                .column(COLUMN_NUMBER_NAME).isEqual(number)
                .executeQueryForObject(connection, new RoomMapper());
        ConnectionManager.closeConnection(connection);
        return room;
    }
}
