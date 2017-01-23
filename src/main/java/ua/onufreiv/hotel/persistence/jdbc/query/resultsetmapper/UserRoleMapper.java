package ua.onufreiv.hotel.persistence.jdbc.query.resultsetmapper;

import ua.onufreiv.hotel.entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yurii on 1/23/17.
 */
public class UserRoleMapper implements ResultSetMapper<UserRole> {
    @Override
    public UserRole map(ResultSet rs) throws SQLException {
        UserRole userRole = new UserRole();
        userRole.setId(rs.getInt("idUserRole"));
        userRole.setRole(rs.getString("role"));
        return userRole;
    }
}
