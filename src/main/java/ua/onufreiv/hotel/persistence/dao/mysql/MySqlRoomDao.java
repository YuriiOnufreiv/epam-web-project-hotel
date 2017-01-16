package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.Room;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IRoomDao;
import ua.onufreiv.hotel.persistence.jdbc.JdbcQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class MySqlRoomDao implements IRoomDao {
    private static MySqlRoomDao instance;

    private static final String QUERY_INSERT = "INSERT INTO ROOM (typeFK, number) VALUES (?, ?)";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM ROOM";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM ROOM WHERE idRoom = ?";
    private static final String QUERY_SELECT_BY_ROOM_NUM = "SELECT * FROM ROOM WHERE number = ?";
    private static final String QUERY_UPDATE = "UPDATE ROOM SET typeFK = ?, number = ? WHERE idRoom = ?";
    private static final String QUERY_DELETE = "DELETE FROM ROOM WHERE idRoom = ?";

    private MySqlRoomDao() {
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
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            id = jdbcQuery.insert(connection, QUERY_INSERT,
                    room.getRoomTypeId(),
                    room.getNumber());
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return id;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionManager.getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean result = jdbcQuery.delete(connection, QUERY_DELETE, id);
        ConnectionManager.closeConnection(connection);
        return result;//        return false;
    }

    @Override
    public Room find(int id) {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                Room room = DtoMapper.ResultSet.toRoom(rs);
                ConnectionManager.closeConnection(connection);
                return room;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Room> findAll() {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_ALL);
            List<Room> users = new ArrayList<>();
            while (rs.next()) {
                users.add(DtoMapper.ResultSet.toRoom(rs));
            }
            ConnectionManager.closeConnection(connection);
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Room room) {
        Connection connection = ConnectionManager.getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean update = jdbcQuery.update(connection, QUERY_UPDATE,
                room.getRoomTypeId(),
                room.getNumber(),
                room.getId());
        ConnectionManager.closeConnection(connection);
        return update;
//        return false;
    }

    @Override
    public List<Room> findAllExcept(List<Integer> exceptRoomIds) {
        Connection connection = ConnectionManager.getConnection();
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
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ROOM_NUM, number);
            if(rs.next()) {
                Room room = DtoMapper.ResultSet.toRoom(rs);
                ConnectionManager.closeConnection(connection);
                return room;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
