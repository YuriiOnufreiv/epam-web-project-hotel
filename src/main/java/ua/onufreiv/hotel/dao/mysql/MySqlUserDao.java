package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IUserDao;
import ua.onufreiv.hotel.entities.User;
import ua.onufreiv.hotel.jdbc.ConnectionManager;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlUserDao implements IUserDao {
    private static MySqlUserDao instance;

    private static final String QUERY_INSERT = "INSERT INTO USER (name, surname, email, phoneNum, roleFK, pwdFK) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String QUERY_DELETE = "DELETE FROM USER WHERE idUser = ?";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM USER WHERE idUser = ?";
    private static final String QUERY_SELECT_BY_EMAIL = "SELECT * FROM USER WHERE email = ?";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM USER";
    private static final String QUERY_UPDATE = "UPDATE USER SET name = ?, surname = ?, email = ?, phoneNum = ?, roleFK = ?, pwdFK = ? WHERE idUser = ?";

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
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            id = jdbcQuery.insert(connection, QUERY_INSERT,
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getPhoneNum(),
                    user.getUserRoleId(),
                    user.getPwdHashId());
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
