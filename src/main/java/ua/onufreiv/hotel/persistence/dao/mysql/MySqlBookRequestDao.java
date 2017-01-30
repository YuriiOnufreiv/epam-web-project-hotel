package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.BookRequest;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IBookRequestDao;
import ua.onufreiv.hotel.persistence.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.BookRequestMapper;

import java.sql.Connection;
import java.util.List;

/**
 * Created by yurii on 1/1/17.
 */
public class MySqlBookRequestDao implements IBookRequestDao {
    private static final String TABLE_NAME = "book_request";
    private static final String COLUMN_ID_NAME = "idRequest";
    private static final String COLUMN_CREATION_DATE_TIME_NAME = "creationDateTime";
    private static final String COLUMN_USER_FK_NAME = "userFK";
    private static final String COLUMN_PERSONS_NAME = "persons";
    private static final String COLUMN_ROOM_TYPE_FK_NAME = "roomTypeFK";
    private static final String COLUMN_CHECK_IN_NAME = "checkIn";
    private static final String COLUMN_CHECK_OUT_NAME = "checkOut";
    private static final String COLUMN_PROCESSED_NAME = "processed";

    private static MySqlBookRequestDao instance;
    private QueryBuilder<BookRequest> queryBuilder;

    private MySqlBookRequestDao() {
        queryBuilder = new QueryBuilder<>(TABLE_NAME);
    }

    public static synchronized MySqlBookRequestDao getInstance() {
        if (instance == null) {
            instance = new MySqlBookRequestDao();
        }
        return instance;
    }

    @Override
    public int insert(BookRequest bookRequest) {
        Connection connection = ConnectionManager.getConnection();
        int id = queryBuilder.insert()
                .value(COLUMN_CREATION_DATE_TIME_NAME, bookRequest.getCreationDate())
                .value(COLUMN_USER_FK_NAME, bookRequest.getUserId())
                .value(COLUMN_PERSONS_NAME, bookRequest.getPersons())
                .value(COLUMN_ROOM_TYPE_FK_NAME, bookRequest.getRoomTypeId())
                .value(COLUMN_CHECK_IN_NAME, bookRequest.getCheckIn())
                .value(COLUMN_CHECK_OUT_NAME, bookRequest.getCheckOut())
                .value(COLUMN_PROCESSED_NAME, bookRequest.getProcessed())
                .execute(connection);
        ConnectionManager.closeConnection(connection);
        return id;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionManager.getConnection();
        boolean result = queryBuilder.delete()
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(id)
                .executeUpdate(connection) > 0;
        ConnectionManager.closeConnection(connection);
        return result;
    }

    @Override
    public BookRequest find(int id) {
        Connection connection = ConnectionManager.getConnection();
        BookRequest bookRequest = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(id)
                .executeQueryForObject(connection, new BookRequestMapper());
        ConnectionManager.closeConnection(connection);
        return bookRequest;
    }

    @Override
    public List<BookRequest> findAll() {
        Connection connection = ConnectionManager.getConnection();
        List<BookRequest> bookRequests = queryBuilder.select()
                .selectAll(connection, new BookRequestMapper());
        ConnectionManager.closeConnection(connection);
        return bookRequests;
    }

    @Override
    public boolean update(BookRequest bookRequest) {
        Connection connection = ConnectionManager.getConnection();
        boolean result = queryBuilder.update()
                .set(COLUMN_CREATION_DATE_TIME_NAME, bookRequest.getCreationDate())
                .set(COLUMN_USER_FK_NAME, bookRequest.getUserId())
                .set(COLUMN_PERSONS_NAME, bookRequest.getPersons())
                .set(COLUMN_ROOM_TYPE_FK_NAME, bookRequest.getRoomTypeId())
                .set(COLUMN_CHECK_IN_NAME, bookRequest.getCheckIn())
                .set(COLUMN_CHECK_OUT_NAME, bookRequest.getCheckOut())
                .set(COLUMN_PROCESSED_NAME, bookRequest.getProcessed())
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(bookRequest.getId())
                .executeUpdate(connection) > 0;
        ConnectionManager.closeConnection(connection);
        return result;
    }

    @Override
    public List<BookRequest> findByUserId(int id) {
        Connection connection = ConnectionManager.getConnection();
        List<BookRequest> bookRequests = queryBuilder.select()
                .where()
                .column(COLUMN_USER_FK_NAME).isEqualTo(id)
                .executeQuery(connection, new BookRequestMapper());
        ConnectionManager.closeConnection(connection);
        return bookRequests;
    }

    @Override
    public List<BookRequest> findAllNotProcessed() {
        Connection connection = ConnectionManager.getConnection();
        List<BookRequest> bookRequests = queryBuilder.select()
                .where()
                .column(COLUMN_PROCESSED_NAME).isEqualTo(false)
                .executeQuery(connection, new BookRequestMapper());
        ConnectionManager.closeConnection(connection);
        return bookRequests;
    }
}
