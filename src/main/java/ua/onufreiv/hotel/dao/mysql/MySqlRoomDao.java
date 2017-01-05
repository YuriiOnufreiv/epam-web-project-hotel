package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IRoomDao;
import ua.onufreiv.hotel.entities.Room;
import ua.onufreiv.hotel.jdbc.JdbcDatabase;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class MySqlRoomDao implements IRoomDao {
    private static final String QUERY_INSERT = "INSERT INTO ROOM (typeFK, number) VALUES (?, ?)";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM ROOM";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM ROOM WHERE idRoom = ?";
    private static final String QUERY_UPDATE = "UPDATE ROOM SET typeFK = ?, number = ? WHERE idRoom = ?";
    private static final String QUERY_DELETE = "DELETE FROM ROOM WHERE idRoom = ?";

    private final JdbcDatabase jdbcDatabase;

    public MySqlRoomDao(JdbcDatabase jdbcDatabase) {
        this.jdbcDatabase = jdbcDatabase;
    }

    @Override
    public int insert(Room room) {
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            id = jdbcQuery.insert(QUERY_INSERT,
                    room.getRoomTypeId(),
                    room.getNumber());
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
    public Room find(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                return DtoMapper.ResultSet.toRoom(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Room> findAll() {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_ALL);
            List<Room> users = new ArrayList<>();
            while (rs.next()) {
                users.add(DtoMapper.ResultSet.toRoom(rs));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Room room) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            return jdbcQuery.update(QUERY_UPDATE,
                    room.getRoomTypeId(),
                    room.getNumber(),
                    room.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
