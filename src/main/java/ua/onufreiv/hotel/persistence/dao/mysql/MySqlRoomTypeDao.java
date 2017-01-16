package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.RoomType;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IRoomTypeDao;
import ua.onufreiv.hotel.persistence.jdbc.JdbcQuery;

import java.sql.Connection;
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
    private static MySqlRoomTypeDao instance;

    private static final String QUERY_INSERT = "INSERT INTO ROOM_TYPE (type, descr, price, maxPerson) VALUES (?, ?, ?, ?)";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM ROOM_TYPE";
    private static final String QUERY_SELECT_ALL_ROOM_TYPES = "SELECT type FROM ROOM_TYPE";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM ROOM_TYPE WHERE idRoomType = ?";
    private static final String QUERY_UPDATE = "UPDATE ROOM_TYPE SET type = ?, descr = ?, price = ?, maxPerson = ? WHERE idRoomType = ?";
    private static final String QUERY_DELETE = "DELETE FROM ROOM_TYPE WHERE idRoomType = ?";

    private MySqlRoomTypeDao() {
    }

    public static synchronized MySqlRoomTypeDao getInstance() {
        if (instance == null) {
            instance = new MySqlRoomTypeDao();
        }
        return instance;
    }

    @Override
    public int insert(RoomType roomType) {
        Connection connection = ConnectionManager.getConnection();
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            id = jdbcQuery.insert(connection, QUERY_INSERT,
                    roomType.getType(),
                    roomType.getDescription(),
                    roomType.getPrice(),
                    roomType.getMaxPerson());
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
    public RoomType find(int id) {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                RoomType roomType = DtoMapper.ResultSet.toRoomType(rs);
                ConnectionManager.closeConnection(connection);
                return roomType;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RoomType> findAll() {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_ALL);
            List<RoomType> users = new ArrayList<>();
            while (rs.next()) {
                users.add(DtoMapper.ResultSet.toRoomType(rs));
            }
            ConnectionManager.closeConnection(connection);
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(RoomType roomType) {
        Connection connection = ConnectionManager.getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean update = jdbcQuery.update(connection, QUERY_UPDATE,
                roomType.getType(),
                roomType.getDescription(),
                roomType.getPrice(),
                roomType.getMaxPerson(),
                roomType.getId());
        ConnectionManager.closeConnection(connection);
        return update;
//        return false;
    }

    @Override
    public List<String> getAllRoomTypes() {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_ALL_ROOM_TYPES);
            List<String> types = new ArrayList<>();
            while (rs.next()) {
                types.add(rs.getString(1));
            }
            ConnectionManager.closeConnection(connection);
            return types;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
