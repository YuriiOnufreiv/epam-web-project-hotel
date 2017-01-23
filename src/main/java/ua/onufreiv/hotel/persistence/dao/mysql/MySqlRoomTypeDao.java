package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.RoomType;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IRoomTypeDao;
import ua.onufreiv.hotel.persistence.jdbc.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.jdbc.query.resultsetmapper.RoomTypeMapper;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yurii on 1/4/17.
 */
public class MySqlRoomTypeDao implements IRoomTypeDao {
    private static final String TABLE_NAME = "room_type";
    private static final String COLUMN_ID_NAME = "idRoomType";
    private static final String COLUMN_TYPE_NAME = "type";
    private static final String COLUMN_DESCR_NAME = "descr";
    private static final String COLUMN_PRICE_NAME = "price";
    private static final String COLUMN_MAX_PERSON_NAME = "maxPerson";

    private static MySqlRoomTypeDao instance;
    private final QueryBuilder<RoomType> queryBuilder;

//    private static final String QUERY_INSERT = "INSERT INTO ROOM_TYPE (type, descr, price, maxPerson) VALUES (?, ?, ?, ?)";
//    private static final String QUERY_SELECT_ALL = "SELECT * FROM ROOM_TYPE";
//    private static final String QUERY_SELECT_ALL_ROOM_TYPES = "SELECT type FROM ROOM_TYPE";
//    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM ROOM_TYPE WHERE idRoomType = ?";
//    private static final String QUERY_UPDATE = "UPDATE ROOM_TYPE SET type = ?, descr = ?, price = ?, maxPerson = ? WHERE idRoomType = ?";
//    private static final String QUERY_DELETE = "DELETE FROM ROOM_TYPE WHERE idRoomType = ?";

    private MySqlRoomTypeDao() {
        queryBuilder = new QueryBuilder<>(TABLE_NAME);
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
        int id = queryBuilder.insert()
                .value(COLUMN_TYPE_NAME, roomType.getType())
                .value(COLUMN_DESCR_NAME, roomType.getDescription())
                .value(COLUMN_PRICE_NAME, roomType.getPrice())
                .value(COLUMN_MAX_PERSON_NAME, roomType.getMaxPerson())
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
    public RoomType find(int id) {
        Connection connection = ConnectionManager.getConnection();
        RoomType RoomType = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).isEqual(id)
                .executeQueryForObject(connection, new RoomTypeMapper());
        ConnectionManager.closeConnection(connection);
        return RoomType;
    }

    @Override
    public List<RoomType> findAll() {
        Connection connection = ConnectionManager.getConnection();
        List<RoomType> roomTypes = queryBuilder.select()
                .selectAll(connection, new RoomTypeMapper());
        ConnectionManager.closeConnection(connection);
        return roomTypes;
    }

    @Override
    public boolean update(RoomType roomType) {
        Connection connection = ConnectionManager.getConnection();
        boolean update = queryBuilder.update()
                .set(COLUMN_TYPE_NAME, roomType.getType())
                .set(COLUMN_DESCR_NAME, roomType.getDescription())
                .set(COLUMN_PRICE_NAME, roomType.getPrice())
                .set(COLUMN_MAX_PERSON_NAME, roomType.getMaxPerson())
                .where()
                .column(COLUMN_ID_NAME).isEqual(roomType.getId())
                .executeUpdate(connection);
        ConnectionManager.closeConnection(connection);
        return update;
    }

    @Override
    public List<String> getAllRoomTypes() {
        Connection connection = ConnectionManager.getConnection();
        List<RoomType> roomTypes = queryBuilder.select()
                .selectAll(connection, new RoomTypeMapper());
        List<String> types = new ArrayList<>();
        for (RoomType roomType : roomTypes) {
            types.add(roomType.getType());
        }
        ConnectionManager.closeConnection(connection);
        return types;
    }

    @Override
    public Map<Integer, String> getIdTypeTitleMap() {
        List<RoomType> roomTypes = findAll();

        if (roomTypes == null) {
            return null;
        }

        Map<Integer, String> idTypeTitleMap = new HashMap<>(roomTypes.size());
        for (RoomType roomType : roomTypes) {
            idTypeTitleMap.put(roomType.getId(), roomType.getType());
        }
        return idTypeTitleMap;
    }

    @Override
    public Map<Integer, RoomType> getIdTypeMap() {
        List<RoomType> roomTypes = findAll();

        if (roomTypes == null) {
            return null;
        }

        Map<Integer, RoomType> idTypeTitleMap = new HashMap<>(roomTypes.size());
        for (RoomType roomType : roomTypes) {
            idTypeTitleMap.put(roomType.getId(), roomType);
        }
        return idTypeTitleMap;
    }
}
