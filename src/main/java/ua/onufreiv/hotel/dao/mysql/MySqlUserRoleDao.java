package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IUserRoleDao;
import ua.onufreiv.hotel.entities.UserRole;
import ua.onufreiv.hotel.jdbc.JdbcDatabase;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

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

    private final JdbcDatabase jdbcDatabase;

    public MySqlUserRoleDao(JdbcDatabase jdbcDatabase) {
        this.jdbcDatabase = jdbcDatabase;
    }

    @Override
    public int insert(UserRole userRole) {
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            id = jdbcQuery.insert(QUERY_INSERT,
                    userRole.getRole());
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
    public UserRole find(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                return DtoMapper.ResultSet.toUserRole(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserRole> findAll() {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_ALL);
            List<UserRole> userRoles = new ArrayList<>();
            while (rs.next()) {
                userRoles.add(DtoMapper.ResultSet.toUserRole(rs));
            }
            return userRoles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(UserRole userRole) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            return jdbcQuery.update(QUERY_UPDATE,
                    userRole.getRole(),
                    userRole.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isAdmin(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                return rs.getString("role").equalsIgnoreCase("ADMIN");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isClient(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                return rs.getString("role").equalsIgnoreCase("CLIENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
