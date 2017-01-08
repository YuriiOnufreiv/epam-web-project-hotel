package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IRoomTypeDao;
import ua.onufreiv.hotel.entities.RoomType;
import ua.onufreiv.hotel.jdbc.JdbcDatabase;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public class MySqlRoomTypeDao implements IRoomTypeDao {
    private static final String QUERY_INSERT = "INSERT INTO ROOM_TYPE (type, descr, price, maxPerson) VALUES (?, ?, ?, ?)";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM ROOM_TYPE";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM ROOM_TYPE WHERE idRoomType = ?";
    private static final String QUERY_UPDATE = "UPDATE ROOM_TYPE SET type = ?, descr = ?, price = ?, maxPerson = ? WHERE idRoomType = ?";
    private static final String QUERY_DELETE = "DELETE FROM ROOM_TYPE WHERE idRoomType = ?";

    private final JdbcDatabase jdbcDatabase;

    public MySqlRoomTypeDao(JdbcDatabase jdbcDatabase) {
        this.jdbcDatabase = jdbcDatabase;
    }

    @Override
    public int insert(RoomType roomType) {
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            id = jdbcQuery.insert(QUERY_INSERT,
                    roomType.getType(),
                    roomType.getDescription(),
                    roomType.getPrice(),
                    roomType.getMaxPerson());
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
    public RoomType find(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                return DtoMapper.ResultSet.toRoomType(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RoomType> findAll() {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_ALL);
            List<RoomType> users = new ArrayList<>();
            while (rs.next()) {
                users.add(DtoMapper.ResultSet.toRoomType(rs));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(RoomType roomType) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            return jdbcQuery.update(QUERY_UPDATE,
                    roomType.getType(),
                    roomType.getDescription(),
                    roomType.getPrice(),
                    roomType.getMaxPerson(),
                    roomType.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Map<Integer, String> getIdTypeTitleMap() {
        List<RoomType> roomTypes = findAll();

        if(roomTypes == null) {
            return null;
        }

        Map<Integer, String> idTypeTitleMap = new HashMap<>(roomTypes.size());
        for(RoomType roomType : roomTypes) {
            idTypeTitleMap.put(roomType.getId(), roomType.getType());
        }
        return idTypeTitleMap;
    }

    @Override
    public Map<Integer, RoomType> getIdTypeMap() {
        List<RoomType> roomTypes = findAll();

        if(roomTypes == null) {
            return null;
        }

        Map<Integer, RoomType> idTypeTitleMap = new HashMap<>(roomTypes.size());
        for(RoomType roomType : roomTypes) {
            idTypeTitleMap.put(roomType.getId(), roomType);
        }
        return idTypeTitleMap;
    }
}
