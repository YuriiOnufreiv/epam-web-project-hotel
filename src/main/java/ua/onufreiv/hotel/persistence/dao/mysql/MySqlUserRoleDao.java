package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.UserRole;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IUserRoleDao;
import ua.onufreiv.hotel.persistence.jdbc.JdbcQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/8/17.
 */
public class MySqlUserRoleDao implements IUserRoleDao {
    private static MySqlUserRoleDao instance;

    private static final String QUERY_INSERT = "INSERT INTO USER_ROLE (role) VALUES (?)";
    private static final String QUERY_DELETE = "DELETE FROM USER_ROLE WHERE idUserRole = ?";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM USER_ROLE WHERE idUserRole = ?";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM USER_ROLE";
    private static final String QUERY_UPDATE = "UPDATE USER_ROLE SET role = ? WHERE idUserRole = ?";

    private MySqlUserRoleDao() {
    }

    public static synchronized MySqlUserRoleDao getInstance() {
        if (instance == null) {
            instance = new MySqlUserRoleDao();
        }
        return instance;
    }

    @Override
    public int insert(UserRole userRole) {
        Connection connection = ConnectionManager.getConnection();
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            id = jdbcQuery.insert(connection, QUERY_INSERT,
                    userRole.getRole());
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
        return result;//        return false;
    }

    @Override
    public UserRole find(int id) {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                UserRole userRole = DtoMapper.ResultSet.toUserRole(rs);
                ConnectionManager.closeConnection(connection);
                return userRole;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserRole> findAll() {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_ALL);
            List<UserRole> userRoles = new ArrayList<>();
            while (rs.next()) {
                userRoles.add(DtoMapper.ResultSet.toUserRole(rs));
            }
            ConnectionManager.closeConnection(connection);
            return userRoles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(UserRole userRole) {
        Connection connection = ConnectionManager.getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean update = jdbcQuery.update(connection, QUERY_UPDATE,
                userRole.getRole(),
                userRole.getId());
        ConnectionManager.closeConnection(connection);
        return update;
    }

    @Override
    public boolean isAdmin(int id) {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                boolean b = rs.getString("role").equalsIgnoreCase("ADMIN");
                ConnectionManager.closeConnection(connection);
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isClient(int id) {
        Connection connection = ConnectionManager.getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                boolean b = rs.getString("role").equalsIgnoreCase("CLIENT");
                ConnectionManager.closeConnection(connection);
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
