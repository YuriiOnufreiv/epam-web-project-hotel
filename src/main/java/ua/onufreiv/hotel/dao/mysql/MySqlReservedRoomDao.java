package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IReservedRoomDao;
import ua.onufreiv.hotel.entities.ReservedRoom;
import ua.onufreiv.hotel.jdbc.Database;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class MySqlReservedRoomDao implements IReservedRoomDao {
    private static final String QUERY_INSERT = "INSERT INTO RESERVED_ROOM (roomFK, checkIn, checkOut) VALUES (?, ?, ?)";
    private static final String QUERY_DELETE = "DELETE FROM RESERVED_ROOM WHERE idReservedRoom = ?";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM RESERVED_ROOM WHERE idReservedRoom = ?";
    private static final String QUERY_SELECT_BY_DATES_RANGE = "SELECT * FROM RESERVED_ROOM WHERE DATE(?) < checkOut and DATE(?) > checkIn;";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM RESERVED_ROOM";
    private static final String QUERY_UPDATE = "UPDATE RESERVED_ROOM SET roomFK = ?, checkIn = ?, checkOut = ? WHERE idReservedRoom = ?";

    public MySqlReservedRoomDao() {
    }


    @Override
    public int insert(ReservedRoom reservedRoom) {
        Connection connection = Database.getInstance().getConnection();
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            id = jdbcQuery.insert(connection, QUERY_INSERT,
                    reservedRoom.getRoomId(),
                    reservedRoom.getCheckInDate(),
                    reservedRoom.getCheckOutDate());
        } finally {
            Database.getInstance().closeConnection(connection);
        }
        return id;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = Database.getInstance().getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean result = jdbcQuery.delete(connection, QUERY_DELETE, id);
        Database.getInstance().closeConnection(connection);
        return result;
        //        return false;
    }

    @Override
    public ReservedRoom find(int id) {
        Connection connection = Database.getInstance().getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if (rs.next()) {
                ReservedRoom reservedRoom = DtoMapper.ResultSet.toReservedRoom(rs);
                Database.getInstance().closeConnection(connection);
                return reservedRoom;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ReservedRoom> findAll() {
        Connection connection = Database.getInstance().getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_ALL);
            List<ReservedRoom> reservedRooms = new ArrayList<>();
            while (rs.next()) {
                reservedRooms.add(DtoMapper.ResultSet.toReservedRoom(rs));
            }
            Database.getInstance().closeConnection(connection);
            return reservedRooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(ReservedRoom reservedRoom) {
        Connection connection = Database.getInstance().getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean update = jdbcQuery.update(connection, QUERY_UPDATE,
                reservedRoom.getRoomId(),
                reservedRoom.getCheckInDate(),
                reservedRoom.getCheckOutDate(),
                reservedRoom.getId());
        Database.getInstance().closeConnection(connection);
        return update;
//        return false;
    }

    @Override
    public List<ReservedRoom> findReservedInDateRange(Date checkInDate, Date checkOutDate) {
        Connection connection = Database.getInstance().getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_DATES_RANGE,checkInDate, checkOutDate);
            List<ReservedRoom> reservedRooms = new ArrayList<>();
            while (rs.next()) {
                reservedRooms.add(DtoMapper.ResultSet.toReservedRoom(rs));
            }
            Database.getInstance().closeConnection(connection);
            return reservedRooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
