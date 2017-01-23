package ua.onufreiv.hotel.persistence.dao.mysql;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IUserDao;
import ua.onufreiv.hotel.persistence.jdbc.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.jdbc.query.resultsetmapper.UserMapper;

import java.sql.Connection;
import java.util.List;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlUserDao implements IUserDao {
    private final static Logger logger = Logger.getLogger(MySqlUserDao.class);

    private static final String TABLE_NAME = "user";
    private static final String COLUMN_ID_NAME = "idUser";
    private static final String COLUMN_NAME_NAME = "name";
    private static final String COLUMN_SURNAME_NAME = "surname";
    private static final String COLUMN_EMAIL_NAME = "email";
    private static final String COLUMN_PHONE_NUM_NAME = "phoneNum";
    private static final String COLUMN_ROLE_FK_NAME = "roleFK";
    private static final String COLUMN_PWD_FK_NAME = "pwdFK";

    private static MySqlUserDao instance;
    private QueryBuilder<User> queryBuilder;

    private MySqlUserDao() {
        queryBuilder = new QueryBuilder(TABLE_NAME);
    }

    public static synchronized MySqlUserDao getInstance() {
        if (instance == null) {
            instance = new MySqlUserDao();
        }
        return instance;
    }

    @Override
    public int insert(User user) {
        Connection connection = ConnectionManager.getConnection();
        int id = queryBuilder.insert()
                .value(COLUMN_NAME_NAME, user.getName())
                .value(COLUMN_SURNAME_NAME, user.getSurname())
                .value(COLUMN_EMAIL_NAME, user.getEmail())
                .value(COLUMN_PHONE_NUM_NAME, user.getPhoneNum())
                .value(COLUMN_ROLE_FK_NAME, user.getUserRoleId())
                .value(COLUMN_PWD_FK_NAME, user.getPwdHashId())
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
    public User find(int id) {
        Connection connection = ConnectionManager.getConnection();
        User user = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).isEqual(id)
                .executeQueryForObject(connection, new UserMapper());
        ConnectionManager.closeConnection(connection);
        return user;
    }

    @Override
    public List<User> findAll() {
        Connection connection = ConnectionManager.getConnection();
        List<User> users = queryBuilder.select()
                .selectAll(connection, new UserMapper());
        ConnectionManager.closeConnection(connection);
        return users;
    }

    @Override
    public boolean update(User user) {
        Connection connection = ConnectionManager.getConnection();
        boolean result = queryBuilder.update()
                .set(COLUMN_NAME_NAME, user.getName())
                .set(COLUMN_SURNAME_NAME, user.getSurname())
                .set(COLUMN_EMAIL_NAME, user.getEmail())
                .set(COLUMN_PHONE_NUM_NAME, user.getPhoneNum())
                .set(COLUMN_ROLE_FK_NAME, user.getUserRoleId())
                .set(COLUMN_PWD_FK_NAME, user.getPwdHashId())
                .where()
                .column(COLUMN_ID_NAME).isEqual(user.getId())
                .executeUpdate(connection);
        ConnectionManager.closeConnection(connection);
        return result;
    }

    @Override
    public User find(String email) {
        Connection connection = ConnectionManager.getConnection();
        User user = queryBuilder.select()
                .where()
                .column(COLUMN_EMAIL_NAME).isEqual(email)
                .executeQueryForObject(connection, new UserMapper());
        ConnectionManager.closeConnection(connection);
        return user;
    }
}
