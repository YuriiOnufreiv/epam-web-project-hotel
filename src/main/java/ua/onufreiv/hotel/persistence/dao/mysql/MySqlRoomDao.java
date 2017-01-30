package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.RoomDao;
import ua.onufreiv.hotel.persistence.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.RoomMapper;

import java.sql.Connection;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class MySqlRoomDao implements RoomDao {
    private static final String TABLE_NAME = "room";
    private static final String COLUMN_ID_NAME = "idRoom";
    private static final String COLUMN_ROOM_TYPE_FK_NAME = "typeFK";
    private static final String COLUMN_NUMBER_NAME = "number";
    private static final String COLUMN_DESCR_NAME = "descr";
    private static final String COLUMN_PRICE_NAME = "price";
    private static final String COLUMN_MAX_PERSON_NAME = "maxPerson";

    private static MySqlRoomDao instance;
    private QueryBuilder<Room> queryBuilder;

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
                .value(COLUMN_DESCR_NAME, room.getDescription())
                .value(COLUMN_PRICE_NAME, room.getPrice())
                .value(COLUMN_MAX_PERSON_NAME, room.getMaxPerson())
                .execute(connection);
        ConnectionManager.closeConnection(connection);
        return id;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionManager.getConnection();
        boolean result = queryBuilder.delete()
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(id)
                .executeUpdate(connection) > 0;
        ConnectionManager.closeConnection(connection);
        return result;
    }

    @Override
    public Room find(int id) {
        Connection connection = ConnectionManager.getConnection();
        Room room = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(id)
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
                .set(COLUMN_DESCR_NAME, room.getDescription())
                .set(COLUMN_PRICE_NAME, room.getPrice())
                .set(COLUMN_MAX_PERSON_NAME, room.getMaxPerson())
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(room.getId())
                .executeUpdate(connection) > 0;
        ConnectionManager.closeConnection(connection);
        return update;
    }

    @Override
    public List<Room> findAllExcept(List<Integer> exceptRoomIds) {
        Connection connection = ConnectionManager.getConnection();
        List<Room> bookRequests = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).notIn(exceptRoomIds)
                .executeQuery(connection, new RoomMapper());
        ConnectionManager.closeConnection(connection);
        return bookRequests;
    }

    @Override
    public Room findByRoomNum(int number) {
        Connection connection = ConnectionManager.getConnection();
        Room room = queryBuilder.select()
                .where()
                .column(COLUMN_NUMBER_NAME).isEqualTo(number)
                .executeQueryForObject(connection, new RoomMapper());
        ConnectionManager.closeConnection(connection);
        return room;
    }
}