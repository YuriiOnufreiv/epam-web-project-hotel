package ua.onufreiv.hotel.persistence.dao.mysql;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IUserDao;
import ua.onufreiv.hotel.persistence.jdbc.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.jdbc.query.SqlQueryUpdate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private QueryBuilder queryBuilder;

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
        int id = -1;
        Connection connection = ConnectionManager.getConnection();
        try {
            id = queryBuilder.insert()
                    .value(COLUMN_NAME_NAME, user.getName())
                    .value(COLUMN_SURNAME_NAME, user.getSurname())
                    .value(COLUMN_EMAIL_NAME, user.getEmail())
                    .value(COLUMN_PHONE_NUM_NAME, user.getPhoneNum())
                    .value(COLUMN_ROLE_FK_NAME, user.getUserRoleId())
                    .value(COLUMN_PWD_FK_NAME, user.getPwdHashId())
                    .execute(connection);
        } catch (SQLException e) {
            logger.error("Failed to insert new user: ", e);
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
            logger.error("Failed to delete user by id: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return result;
    }

    @Override
    public User find(int id) {
        Connection connection = ConnectionManager.getConnection();
        User user = null;
        try {
            ResultSet rs = queryBuilder.wrapWithWhereClause(queryBuilder.select())
                    .addCondition().column(COLUMN_ID_NAME).isEqual(id)
                    .executeQuery(connection);
            if (rs.next()) {
                user = DtoMapper.ResultSet.toUser(rs);
                ConnectionManager.closeConnection(connection);
                return user;
            }
        } catch (SQLException e) {
            logger.error("Failed to find user by id: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        Connection connection = ConnectionManager.getConnection();
        List<User> users = null;
        try {
            ResultSet rs = queryBuilder.select().execute(connection);
            users = new ArrayList<>();
            while (rs.next()) {
                users.add(DtoMapper.ResultSet.toUser(rs));
            }
            ConnectionManager.closeConnection(connection);
            return users;
        } catch (SQLException e) {
            logger.error("Failed to find all users: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return users;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        Connection connection = ConnectionManager.getConnection();
        try {
            SqlQueryUpdate queryUpdate = queryBuilder.update()
                    .set(COLUMN_NAME_NAME, user.getName())
                    .set(COLUMN_SURNAME_NAME, user.getSurname())
                    .set(COLUMN_EMAIL_NAME, user.getEmail())
                    .set(COLUMN_PHONE_NUM_NAME, user.getPhoneNum())
                    .set(COLUMN_ROLE_FK_NAME, user.getUserRoleId())
                    .set(COLUMN_PWD_FK_NAME, user.getPwdHashId());
            result = queryBuilder.wrapWithWhereClause(queryUpdate)
                    .addCondition().column(COLUMN_ID_NAME).isEqual(user.getId())
                    .executeUpdate(connection);
        } catch (SQLException e) {
            logger.error("Failed to update user: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return result;
    }

    @Override
    public User find(String email) {
        Connection connection = ConnectionManager.getConnection();
        User user = null;
        try {
            ResultSet rs = queryBuilder.wrapWithWhereClause(queryBuilder.select())
                    .addCondition().column(COLUMN_EMAIL_NAME).isEqual(email)
                    .executeQuery(connection);
            if (rs != null && rs.next()) {
                user = DtoMapper.ResultSet.toUser(rs);
                ConnectionManager.closeConnection(connection);
                return user;
            }
        } catch (SQLException e) {
            logger.error("Failed to find user by email: ", e);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return user;
    }
}
