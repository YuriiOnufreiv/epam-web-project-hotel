package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IUserRoleDao;
import ua.onufreiv.hotel.entities.UserRole;
import ua.onufreiv.hotel.jdbc.Database;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/8/17.
 */
public class MySqlUserRoleDao implements IUserRoleDao {
    private static final String QUERY_INSERT = "INSERT INTO USER_ROLE (role) VALUES (?)";
    private static final String QUERY_DELETE = "DELETE FROM USER_ROLE WHERE idUserRole = ?";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM USER_ROLE WHERE idUserRole = ?";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM USER_ROLE";
    private static final String QUERY_UPDATE = "UPDATE USER_ROLE SET role = ? WHERE idUserRole = ?";

    public MySqlUserRoleDao() {
    }

    @Override
    public int insert(UserRole userRole) {
        Connection connection = Database.getInstance().getConnection();
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            id = jdbcQuery.insert(connection, QUERY_INSERT,
                    userRole.getRole());
        } finally {
            Database.getInstance().closeConnection(connection);
        }
        return id;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = Database.getInstance().getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean result = jdbcQuery.delete(connection, QUERY_DELETE, id);
        Database.getInstance().closeConnection(connection);
        return result;//        return false;
    }

    @Override
    public UserRole find(int id) {
        Connection connection = Database.getInstance().getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                UserRole userRole = DtoMapper.ResultSet.toUserRole(rs);
                Database.getInstance().closeConnection(connection);
                return userRole;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserRole> findAll() {
        Connection connection = Database.getInstance().getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_ALL);
            List<UserRole> userRoles = new ArrayList<>();
            while (rs.next()) {
                userRoles.add(DtoMapper.ResultSet.toUserRole(rs));
            }
            Database.getInstance().closeConnection(connection);
            return userRoles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(UserRole userRole) {
        Connection connection = Database.getInstance().getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean update = jdbcQuery.update(connection, QUERY_UPDATE,
                userRole.getRole(),
                userRole.getId());
        Database.getInstance().closeConnection(connection);
        return update;
//        return false;
    }

    @Override
    public boolean isAdmin(int id) {
        Connection connection = Database.getInstance().getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                boolean b = rs.getString("role").equalsIgnoreCase("ADMIN");
                Database.getInstance().closeConnection(connection);
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isClient(int id) {
        Connection connection = Database.getInstance().getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                boolean b = rs.getString("role").equalsIgnoreCase("CLIENT");
                Database.getInstance().closeConnection(connection);
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
