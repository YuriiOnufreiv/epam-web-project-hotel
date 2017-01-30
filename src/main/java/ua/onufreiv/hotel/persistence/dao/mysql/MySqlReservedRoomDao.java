package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.ReservedRoom;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IReservedRoomDao;
import ua.onufreiv.hotel.persistence.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.ReservedRoomMapper;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class MySqlReservedRoomDao implements IReservedRoomDao {
    private static final String TABLE_NAME = "reserved_room";
    private static final String COLUMN_ID_NAME = "idReservedRoom";
    private static final String COLUMN_ROOM_FK_NAME = "roomFK";
    private static final String COLUMN_CHECK_IN_NAME = "checkIn";
    private static final String COLUMN_CHECK_OUT_NAME = "checkOut";

    private static MySqlReservedRoomDao instance;
    private QueryBuilder<ReservedRoom> queryBuilder;

    private MySqlReservedRoomDao() {
        queryBuilder = new QueryBuilder<>(TABLE_NAME);
    }

    public static synchronized MySqlReservedRoomDao getInstance() {
        if (instance == null) {
            instance = new MySqlReservedRoomDao();
        }
        return instance;
    }

    @Override
    public int insert(ReservedRoom reservedRoom) {
        Connection connection = ConnectionManager.getConnection();
        int id = queryBuilder.insert()
                .value(COLUMN_ROOM_FK_NAME, reservedRoom.getRoomId())
                .value(COLUMN_CHECK_IN_NAME, reservedRoom.getCheckInDate())
                .value(COLUMN_CHECK_OUT_NAME, reservedRoom.getCheckOutDate())
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
    public ReservedRoom find(int id) {
        Connection connection = ConnectionManager.getConnection();
        ReservedRoom reservedRoom = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(id)
                .executeQueryForObject(connection, new ReservedRoomMapper());
        ConnectionManager.closeConnection(connection);
        return reservedRoom;
    }

    @Override
    public List<ReservedRoom> findAll() {
        Connection connection = ConnectionManager.getConnection();
        List<ReservedRoom> bookRequests = queryBuilder.select()
                .selectAll(connection, new ReservedRoomMapper());
        ConnectionManager.closeConnection(connection);
        return bookRequests;
    }

    @Override
    public boolean update(ReservedRoom reservedRoom) {
        Connection connection = ConnectionManager.getConnection();
        boolean result = queryBuilder.update()
                .set(COLUMN_ROOM_FK_NAME, reservedRoom.getRoomId())
                .set(COLUMN_CHECK_IN_NAME, reservedRoom.getCheckInDate())
                .set(COLUMN_CHECK_OUT_NAME, reservedRoom.getCheckOutDate())
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(reservedRoom.getId())
                .executeUpdate(connection) > 0;
        ConnectionManager.closeConnection(connection);
        return result;
    }

    @Override
    public List<ReservedRoom> findInDateRange(Date checkInDate, Date checkOutDate) {
        Connection connection = ConnectionManager.getConnection();
        List<ReservedRoom> reservedRooms = queryBuilder.select()
                .where()
                .column(COLUMN_CHECK_OUT_NAME).isGreaterThan(checkInDate)
                .and()
                .column(COLUMN_CHECK_IN_NAME).isLessThan(checkOutDate)
                .executeQuery(connection, new ReservedRoomMapper());
        ConnectionManager.closeConnection(connection);
        return reservedRooms;
    }

    @Override
    public boolean roomIsReserved(int roomId, Date checkInDate, Date checkOutDate) {
        Connection connection = ConnectionManager.getConnection();
        ReservedRoom reservedRoom = queryBuilder.select()
                .where()
                .column(COLUMN_ROOM_FK_NAME).isEqualTo(roomId)
                .and()
                .column(COLUMN_CHECK_OUT_NAME).isGreaterThan(checkInDate)
                .and()
                .column(COLUMN_CHECK_IN_NAME).isLessThan(checkOutDate)
                .executeQueryForObject(connection, new ReservedRoomMapper());
        ConnectionManager.closeConnection(connection);
        return reservedRoom != null;
    }

    @Override
    public boolean deleteExpired() {
        Connection connection = ConnectionManager.getConnection();
        boolean result = queryBuilder.delete()
                .where()
                .column(COLUMN_CHECK_OUT_NAME).isLessThan(new Date())
                .executeUpdate(connection) > 0;
        ConnectionManager.closeConnection(connection);
        return result;
    }
}
