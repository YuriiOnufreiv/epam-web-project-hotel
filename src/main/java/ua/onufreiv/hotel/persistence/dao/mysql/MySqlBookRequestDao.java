package ua.onufreiv.hotel.persistence.dao.mysql;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IBookRequestDao;
import ua.onufreiv.hotel.persistence.jdbc.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.jdbc.query.SqlQueryUpdate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/1/17.
 */
public class MySqlBookRequestDao implements IBookRequestDao {
    private final static Logger logger = Logger.getLogger(MySqlBookRequestDao.class);

    private static final String TABLE_NAME = "book_request";
    private static final String COLUMN_ID_NAME = "idRequest";
    private static final String COLUMN_CREATION_DATE_TIME_NAME = "creationDateTime";
    private static final String COLUMN_USER_FK_NAME = "userFK";
    private static final String COLUMN_PERSONS_NAME = "persons";
    private static final String COLUMN_ROOM_TYPE_FK_NAME = "roomTypeFK";
    private static final String COLUMN_CHECK_IN_NAME = "checkIn";
    private static final String COLUMN_CHECK_OUT_NAME = "checkOut";
    private static final String COLUMN_PROCESSED_NAME = "processed";

//    private static final String QUERY_INSERT = "INSERT INTO BOOK_REQUEST (creationDateTime, userFK, persons, roomTypeFK, checkIn, checkOut, processed) VALUES (?, ?, ?, ?, ?, ?, ?)";
//    private static final String QUERY_SELECT_ALL = "SELECT * FROM BOOK_REQUEST";
//    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM BOOK_REQUEST WHERE idRequest = ?";
//    private static final String QUERY_SELECT_BY_PROCESSED = "SELECT * FROM BOOK_REQUEST WHERE processed = ?";
//    private static final String QUERY_UPDATE = "UPDATE BOOK_REQUEST SET creationDateTime = ?, userFK = ?, persons = ?, roomTypeFK = ?, checkIn = ?, checkOut = ?, processed = ?  WHERE idRequest = ?";
//    private static final String QUERY_DELETE = "DELETE FROM BOOK_REQUEST WHERE idRequest = ?";
//    private static final String QUERY_SELECT_BY_USER_ID = "SELECT * FROM BOOK_REQUEST WHERE userFK = ?";

    private static MySqlBookRequestDao instance;

    private QueryBuilder queryBuilder;

    private MySqlBookRequestDao() {
        queryBuilder = new QueryBuilder(TABLE_NAME);
    }

    public static synchronized MySqlBookRequestDao getInstance() {
        if (instance == null) {
            instance = new MySqlBookRequestDao();
        }
        return instance;
    }

    @Override
    public int insert(BookRequest bookRequest) {
        int id = -1;
        Connection connection = ConnectionManager.getConnection();
        try {
            id = queryBuilder.insert()
                    .value(COLUMN_CREATION_DATE_TIME_NAME, bookRequest.getCreationDate())
                    .value(COLUMN_USER_FK_NAME, bookRequest.getUserId())
                    .value(COLUMN_PERSONS_NAME, bookRequest.getPersons())
                    .value(COLUMN_ROOM_TYPE_FK_NAME, bookRequest.getRoomTypeId())
                    .value(COLUMN_CHECK_IN_NAME, bookRequest.getCheckIn())
                    .value(COLUMN_CHECK_OUT_NAME, bookRequest.getCheckOut())
                    .value(COLUMN_PROCESSED_NAME, bookRequest.getProcessed())
                    .execute(connection);
        } catch (SQLException e) {
            logger.error("Failed to insert new book request: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return id;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionManager.getConnection();
        boolean result = false;
        try {
            result = queryBuilder.wrapWithWhereClause(queryBuilder.delete())
                    .addCondition().column(COLUMN_ID_NAME).isEqual(id)
                    .executeUpdate(connection);
        } catch (SQLException e) {
            logger.error("Failed to delete book request by id: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return result;
    }

    @Override
    public BookRequest find(int id) {
        Connection connection = ConnectionManager.getConnection();
        BookRequest bookRequest = null;
        try {
            ResultSet rs = queryBuilder.wrapWithWhereClause(queryBuilder.select())
                    .addCondition().column(COLUMN_ID_NAME).isEqual(id)
                    .executeQuery(connection);
            if (rs.next()) {
                bookRequest = DtoMapper.ResultSet.toBookRequest(rs);
                ConnectionManager.closeConnection(connection);
            }
        } catch (SQLException e) {
            logger.error("Failed to find book request by id: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return bookRequest;
    }

    @Override
    public List<BookRequest> findAll() {
        Connection connection = ConnectionManager.getConnection();
        List<BookRequest> bookRequests = null;
        try {
            ResultSet rs = queryBuilder.select().execute(connection);
            bookRequests = new ArrayList<>();
            while (rs.next()) {
                bookRequests.add(DtoMapper.ResultSet.toBookRequest(rs));
            }
            ConnectionManager.closeConnection(connection);
        } catch (SQLException e) {
            logger.error("Failed to find all book requests: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return bookRequests;
    }

    @Override
    public boolean update(BookRequest bookRequest) {
        boolean result = false;
        Connection connection = ConnectionManager.getConnection();
        try {
            SqlQueryUpdate queryUpdate = queryBuilder.update()
                    .set(COLUMN_CREATION_DATE_TIME_NAME, bookRequest.getCreationDate())
                    .set(COLUMN_USER_FK_NAME, bookRequest.getUserId())
                    .set(COLUMN_PERSONS_NAME, bookRequest.getPersons())
                    .set(COLUMN_ROOM_TYPE_FK_NAME, bookRequest.getRoomTypeId())
                    .set(COLUMN_CHECK_IN_NAME, bookRequest.getCheckIn())
                    .set(COLUMN_CHECK_OUT_NAME, bookRequest.getCheckOut())
                    .set(COLUMN_PROCESSED_NAME, bookRequest.getProcessed());
            result = queryBuilder.wrapWithWhereClause(queryUpdate)
                    .addCondition().column(COLUMN_ID_NAME).isEqual(bookRequest.getId())
                    .executeUpdate(connection);
        } catch (SQLException e) {
            logger.error("Failed to update book request: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return result;
    }

    @Override
    public List<BookRequest> findByUserId(int id) {
        Connection connection = ConnectionManager.getConnection();
        List<BookRequest> bookRequests = null;
        try {
            ResultSet rs = queryBuilder.wrapWithWhereClause(queryBuilder.select())
                    .addCondition().column(COLUMN_USER_FK_NAME).isEqual(id)
                    .executeQuery(connection);
            bookRequests = new ArrayList<>();
            while (rs.next()) {
                bookRequests.add(DtoMapper.ResultSet.toBookRequest(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to find book request by user id: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return bookRequests;
    }

    @Override
    public List<BookRequest> getNotProcessedRequests() {
        Connection connection = ConnectionManager.getConnection();
        List<BookRequest> bookRequests = null;
        try {
            ResultSet rs = queryBuilder.wrapWithWhereClause(queryBuilder.select())
                    .addCondition().column(COLUMN_PROCESSED_NAME).isEqual(false)
                    .executeQuery(connection);
            bookRequests = new ArrayList<>();
            while (rs.next()) {
                bookRequests.add(DtoMapper.ResultSet.toBookRequest(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to find not processed requests: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return bookRequests;
    }
}
