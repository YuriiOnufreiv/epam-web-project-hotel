package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IBookRequestDao;
import ua.onufreiv.hotel.entities.BookRequest;
import ua.onufreiv.hotel.jdbc.JdbcDatabase;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/1/17.
 */
public class MySqlBookRequestDao implements IBookRequestDao {
    private static final String QUERY_INSERT = "INSERT INTO BOOK_REQUEST (userFK, persons, roomTypeFK, checkIn, checkOut, processed) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM BOOK_REQUEST";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM BOOK_REQUEST WHERE idRequest = ?";
    private static final String QUERY_UPDATE = "UPDATE BOOK_REQUEST SET userFK = ?, persons = ?, roomTypeFK = ?, checkIn = ?, checkOut = ?, processed = ?  WHERE idRequest = ?";
    private static final String QUERY_DELETE = "DELETE FROM BOOK_REQUEST WHERE idRequest = ?";
    private static final String QUERY_SELECT_BY_USER_ID = "SELECT * FROM BOOK_REQUEST WHERE userFK = ?";

    private final JdbcDatabase jdbcDatabase;

    public MySqlBookRequestDao(JdbcDatabase jdbcDatabase) {
        this.jdbcDatabase = jdbcDatabase;
    }

    @Override
    public int insert(BookRequest bookRequest) {
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            id = jdbcQuery.insert(QUERY_INSERT,
                    bookRequest.getUserId(),
                    bookRequest.getPersons(),
                    bookRequest.getRoomTypeId(),
                    bookRequest.getCheckIn(),
                    bookRequest.getEndDate(),
                    bookRequest.getProcessed());
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
    public BookRequest find(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                return DtoMapper.ResultSet.toForm(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookRequest> findAll() {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_ALL);
            List<BookRequest> bookRequests = new ArrayList<>();
            while (rs.next()) {
                bookRequests.add(DtoMapper.ResultSet.toForm(rs));
            }
            return bookRequests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(BookRequest bookRequest) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            return jdbcQuery.update(QUERY_UPDATE,
                    bookRequest.getUserId(),
                    bookRequest.getPersons(),
                    bookRequest.getRoomTypeId(),
                    bookRequest.getCheckIn(),
                    bookRequest.getEndDate(),
                    bookRequest.getProcessed(),
                    bookRequest.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<BookRequest> findByUserId(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_USER_ID, id);
            List<BookRequest> bookRequests = new ArrayList<>();
            while (rs.next()) {
                bookRequests.add(DtoMapper.ResultSet.toForm(rs));
            }
            return bookRequests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
