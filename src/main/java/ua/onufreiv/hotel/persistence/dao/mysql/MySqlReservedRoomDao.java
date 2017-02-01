package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.ReservedRoom;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.ReservedRoomDao;
import ua.onufreiv.hotel.persistence.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.ReservedRoomMapper;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 *DAO for {@link ReservedRoom} entity and MySql database
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/5/17.
 */
public class MySqlReservedRoomDao implements ReservedRoomDao {
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReservedRoom> findAll() {
        Connection connection = ConnectionManager.getConnection();
        List<ReservedRoom> bookRequests = queryBuilder.select()
                .selectAll(connection, new ReservedRoomMapper());
        ConnectionManager.closeConnection(connection);
        return bookRequests;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReservedRoom> findInDateRange(Date startDate, Date endDate) {
        Connection connection = ConnectionManager.getConnection();
        List<ReservedRoom> reservedRooms = queryBuilder.select()
                .where()
                .column(COLUMN_CHECK_OUT_NAME).isGreaterThan(startDate)
                .and()
                .column(COLUMN_CHECK_IN_NAME).isLessThan(endDate)
                .executeQuery(connection, new ReservedRoomMapper());
        ConnectionManager.closeConnection(connection);
        return reservedRooms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean roomIsReserved(int roomId, Date startDate, Date endDate) {
        Connection connection = ConnectionManager.getConnection();
        ReservedRoom reservedRoom = queryBuilder.select()
                .where()
                .column(COLUMN_ROOM_FK_NAME).isEqualTo(roomId)
                .and()
                .column(COLUMN_CHECK_OUT_NAME).isGreaterThan(startDate)
                .and()
                .column(COLUMN_CHECK_IN_NAME).isLessThan(endDate)
                .executeQueryForObject(connection, new ReservedRoomMapper());
        ConnectionManager.closeConnection(connection);
        return reservedRoom != null;
    }

    /**
     * {@inheritDoc}
     */
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
