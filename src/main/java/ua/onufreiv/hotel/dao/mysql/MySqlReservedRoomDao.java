package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IReservedRoomDao;
import ua.onufreiv.hotel.entities.ReservedRoom;
import ua.onufreiv.hotel.jdbc.JdbcDatabase;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

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

    private final JdbcDatabase jdbcDatabase;

    public MySqlReservedRoomDao(JdbcDatabase jdbcDatabase) {
        this.jdbcDatabase = jdbcDatabase;
    }


    @Override
    public int insert(ReservedRoom reservedRoom) {
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            id = jdbcQuery.insert(QUERY_INSERT,
                    reservedRoom.getRoomId(),
                    reservedRoom.getCheckInDate(),
                    reservedRoom.getCheckOutDate());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return id;
    }

    @Override
    public boolean delete(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            return jdbcQuery.delete(QUERY_DELETE, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ReservedRoom find(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_ID, id);
            if (rs.next()) {
                return DtoMapper.ResultSet.toReservedRoom(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ReservedRoom> findAll() {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_ALL);
            List<ReservedRoom> reservedRooms = new ArrayList<>();
            while (rs.next()) {
                reservedRooms.add(DtoMapper.ResultSet.toReservedRoom(rs));
            }
            return reservedRooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(ReservedRoom reservedRoom) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            return jdbcQuery.update(QUERY_UPDATE,
                    reservedRoom.getRoomId(),
                    reservedRoom.getCheckInDate(),
                    reservedRoom.getCheckOutDate(),
                    reservedRoom.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ReservedRoom> findReservedInDateRange(Date checkInDate, Date checkOutDate) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_DATES_RANGE,checkInDate, checkOutDate);
            List<ReservedRoom> reservedRooms = new ArrayList<>();
            while (rs.next()) {
                reservedRooms.add(DtoMapper.ResultSet.toReservedRoom(rs));
            }
            return reservedRooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
