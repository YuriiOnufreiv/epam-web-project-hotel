package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.PasswordDao;
import ua.onufreiv.hotel.persistence.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.PasswordHashMapper;

import java.sql.Connection;
import java.util.List;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlPasswordDao implements PasswordDao {
    private static final String TABLE_NAME = "password";
    private static final String COLUMN_ID_NAME = "idPassword";
    private static final String COLUMN_PWD_HASH_NAME = "pwdHash";

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
        Connection connection = ConnectionManager.getConnection();
        boolean result = queryBuilder.delete()
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(id)
                .executeUpdate(connection) > 0;
        ConnectionManager.closeConnection(connection);
        return result;
    }

    @Override
    public PasswordHash find(int id) {
        Connection connection = ConnectionManager.getConnection();
        PasswordHash bookRequest = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(id)
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
        Connection connection = ConnectionManager.getConnection();
        boolean update = queryBuilder.update()
                .set(COLUMN_PWD_HASH_NAME, passwordHash.getPwdHash())
                .where()
                .column(COLUMN_ID_NAME).isEqualTo(passwordHash.getId())
                .executeUpdate(connection) > 0;
        ConnectionManager.closeConnection(connection);
        return update;
    }
}
