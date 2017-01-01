package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.jdbc.JdbcDatabase;
import ua.onufreiv.hotel.jdbc.JdbcQuery;
import ua.onufreiv.hotel.dao.IUserDao;
import ua.onufreiv.hotel.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlUserDao implements IUserDao {
    private static final String QUERY_INSERT = "INSERT INTO USER (name, surname, email, tel, roleFK, pwdFK) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String QUERY_DELETE = "DELETE FROM USER WHERE idUser = ?";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM USER WHERE idUser = ?";
    private static final String QUERY_SELECT_BY_EMAIL = "SELECT * FROM USER WHERE email = ?";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM USER";
    private static final String QUERY_UPDATE = "UPDATE USER SET name = ?, surname = ?, email = ?, tel = ?, roleFK = ?, pwdFK = ? WHERE idUser = ?";

    private final JdbcDatabase jdbcDatabase;

    public MySqlUserDao(JdbcDatabase jdbcDatabase) {
        this.jdbcDatabase = jdbcDatabase;
    }

    @Override
    public int insert(User user) {
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            id = jdbcQuery.insert(QUERY_INSERT,
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getTelephone(),
                    user.getUserRoleId(),
                    user.getPwdHashId());
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
    public User find(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                return DtoMapper.ResultSet.toUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_ALL);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(DtoMapper.ResultSet.toUser(rs));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(User user) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            return jdbcQuery.update(QUERY_UPDATE,
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getTelephone(),
                    user.getUserRoleId(),
                    user.getPwdHashId(),
                    user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User find(String email) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_EMAIL, email);
            if(rs != null && rs.next()) {
                return DtoMapper.ResultSet.toUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
