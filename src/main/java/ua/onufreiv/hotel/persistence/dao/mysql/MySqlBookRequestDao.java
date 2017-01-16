package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IBookRequestDao;
import ua.onufreiv.hotel.persistence.jdbc.JdbcQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/1/17.
 */
public class MySqlBookRequestDao implements IBookRequestDao {
    private static final String QUERY_INSERT = "INSERT INTO BOOK_REQUEST (creationDateTime, userFK, persons, roomTypeFK, checkIn, checkOut, processed) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM BOOK_REQUEST";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM BOOK_REQUEST WHERE idRequest = ?";
    private static final String QUERY_SELECT_BY_PROCESSED = "SELECT * FROM BOOK_REQUEST WHERE processed = ?";
    private static final String QUERY_UPDATE = "UPDATE BOOK_REQUEST SET creationDateTime = ?, userFK = ?, persons = ?, roomTypeFK = ?, checkIn = ?, checkOut = ?, processed = ?  WHERE idRequest = ?";
    private static final String QUERY_DELETE = "DELETE FROM BOOK_REQUEST WHERE idRequest = ?";
    private static final String QUERY_SELECT_BY_USER_ID = "SELECT * FROM BOOK_REQUEST WHERE userFK = ?";
    private static MySqlBookRequestDao instance;

    private MySqlBookRequestDao() {
    }

    public static synchronized MySqlBookRequestDao getInstance() {
        if (instance == null) {
            instance = new MySqlBookRequestDao();
        }
        return instance;
    }

    @Override
    public int insert(BookRequest bookRequest) {
        int id;
        Connection connection = ConnectionManager.getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        id = jdbcQuery.insert(connection, QUERY_INSERT,
                bookRequest.getCreationDate(),
                bookRequest.getUserId(),
                bookRequest.getPersons(),
                bookRequest.getRoomTypeId(),
                bookRequest.getCheckIn(),
                bookRequest.getCheckOut(),
                bookRequest.getProcessed());
        ConnectionManager.closeConnection(connection);
        return id;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionManager.getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean result = jdbcQuery.delete(connection, QUERY_DELETE, id);
        ConnectionManager.closeConnection(connection);
        return result;
    }

    @Override
    public BookRequest find(int id) {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if (rs.next()) {
                BookRequest bookRequest = DtoMapper.ResultSet.toBookRequest(rs);
                ConnectionManager.closeConnection(connection);
                return bookRequest;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookRequest> findAll() {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_ALL);
            List<BookRequest> bookRequests = new ArrayList<>();
            while (rs.next()) {
                bookRequests.add(DtoMapper.ResultSet.toBookRequest(rs));
            }
            ConnectionManager.closeConnection(connection);
            return bookRequests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(BookRequest bookRequest) {
        Connection connection = ConnectionManager.getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean update = jdbcQuery.update(connection, QUERY_UPDATE,
                bookRequest.getCreationDate(),
                bookRequest.getUserId(),
                bookRequest.getPersons(),
                bookRequest.getRoomTypeId(),
                bookRequest.getCheckIn(),
                bookRequest.getCheckOut(),
                bookRequest.getProcessed(),
                bookRequest.getId());
        ConnectionManager.closeConnection(connection);
        return update;
    }

    @Override
    public List<BookRequest> findByUserId(int id) {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_USER_ID, id);
            List<BookRequest> bookRequests = new ArrayList<>();
            while (rs.next()) {
                bookRequests.add(DtoMapper.ResultSet.toBookRequest(rs));
            }
            ConnectionManager.closeConnection(connection);
            return bookRequests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookRequest> getNotProcessedRequests() {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_PROCESSED, false);
            List<BookRequest> bookRequests = new ArrayList<>();
            while (rs.next()) {
                bookRequests.add(DtoMapper.ResultSet.toBookRequest(rs));
            }
            ConnectionManager.closeConnection(connection);
            return bookRequests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}