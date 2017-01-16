package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IUserDao;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.jdbc.ConnectionManager;
import ua.onufreiv.hotel.jdbc.JdbcQuery;
import ua.onufreiv.hotel.jdbc.query.QueryInsert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlUserDao implements IUserDao {
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_ID_NAME = "idUser";
    private static final String COLUMN_NAME_NAME = "name";
    private static final String COLUMN_SURNAME_NAME = "surname";
    private static final String COLUMN_EMAIL_NAME = "email";
    private static final String COLUMN_PHONE_NUM_NAME = "phoneNum";
    private static final String COLUMN_ROLE_FK_NAME = "roleFK";
    private static final String COLUMN_PWD_FK_NAME = "pwdFK";
    private static final String QUERY_INSERT = "INSERT INTO USER (name, surname, email, phoneNum, roleFK, pwdFK) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String QUERY_DELETE = "DELETE FROM USER WHERE idUser = ?";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM USER WHERE idUser = ?";
    private static final String QUERY_SELECT_BY_EMAIL = "SELECT * FROM USER WHERE email = ?";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM USER";
    private static final String QUERY_UPDATE = "UPDATE USER SET name = ?, surname = ?, email = ?, phoneNum = ?, roleFK = ?, pwdFK = ? WHERE idUser = ?";
    private static MySqlUserDao instance;

    private MySqlUserDao() {
    }

    public static synchronized MySqlUserDao getInstance() {
        if (instance == null) {
            instance = new MySqlUserDao();
        }
        return instance;
    }

    @Override
    public int insert(User user) {
        int id = 0;
        Connection connection = ConnectionManager.getConnection();
        QueryInsert insert = new QueryInsert();
        insert.into(TABLE_NAME)
                .value(COLUMN_NAME_NAME, user.getName())
                .value(COLUMN_SURNAME_NAME, user.getSurname())
                .value(COLUMN_EMAIL_NAME, user.getEmail())
                .value(COLUMN_PHONE_NUM_NAME, user.getPhoneNum())
                .value(COLUMN_ROLE_FK_NAME, user.getUserRoleId())
                .value(COLUMN_PWD_FK_NAME, user.getPwdHashId());
        try {
            insert.execute(connection);
        } catch (SQLException e) {
            e.printStackTrace();
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
        return result;
        //return false;
    }

    @Override
    public User find(int id) {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if (rs.next()) {
                User user = DtoMapper.ResultSet.toUser(rs);
                ConnectionManager.closeConnection(connection);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_ALL);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(DtoMapper.ResultSet.toUser(rs));
            }
            ConnectionManager.closeConnection(connection);
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(User user) {
        Connection connection = ConnectionManager.getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean result = jdbcQuery.update(connection, QUERY_UPDATE,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhoneNum(),
                user.getUserRoleId(),
                user.getPwdHashId(),
                user.getId());
        ConnectionManager.closeConnection(connection);
        return result;
    }

    @Override
    public User find(String email) {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_EMAIL, email);
            if (rs != null && rs.next()) {
                User user = DtoMapper.ResultSet.toUser(rs);
                ConnectionManager.closeConnection(connection);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
