package ua.onufreiv.hotel.persistence.dao.mysql;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IPasswordDao;
import ua.onufreiv.hotel.persistence.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.PasswordHashMapper;

import java.sql.Connection;
import java.util.List;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlPasswordDao implements IPasswordDao {
    private final static Logger logger = Logger.getLogger(MySqlPasswordDao.class);

    private static final String TABLE_NAME = "password";
    private static final String COLUMN_ID_NAME = "idPassword";
    private static final String COLUMN_PWD_HASH_NAME = "pwdhash";

//    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM PASSWORD WHERE idPassword = ?";
//    private static final String QUERY_INSERT = "INSERT INTO PASSWORD (pwdhash) VALUES (?)";

    private static MySqlPasswordDao instance;
    private QueryBuilder<PasswordHash> queryBuilder;

    private MySqlPasswordDao() {
        queryBuilder = new QueryBuilder<>(TABLE_NAME);
    }

    public static synchronized MySqlPasswordDao getInstance() {
        if (instance == null) {
            instance = new MySqlPasswordDao();
        }
        return instance;
    }

    @Override
    public int insert(PasswordHash passwordHash) {
        Connection connection = ConnectionManager.getConnection();
        int id = queryBuilder.insert()
                .value(COLUMN_PWD_HASH_NAME, passwordHash.getPwdHash())
                .execute(connection);
        ConnectionManager.closeConnection(connection);
        return id;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public PasswordHash find(int id) {
        Connection connection = ConnectionManager.getConnection();
        PasswordHash bookRequest = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).isEqual(id)
                .executeQueryForObject(connection, new PasswordHashMapper());
        ConnectionManager.closeConnection(connection);
        return bookRequest;
    }

    @Override
    public List<PasswordHash> findAll() {
        return null;
    }

    @Override
    public boolean update(PasswordHash passwordHash) {
        return false;
    }
}
