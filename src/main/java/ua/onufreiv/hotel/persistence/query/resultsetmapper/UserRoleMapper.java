package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import ua.onufreiv.hotel.entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for mapping rows of a ResultSet to instance of {@link UserRole} class.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/23/17.
 */
public class UserRoleMapper implements ResultSetMapper<UserRole> {
    /**
     * {@inheritDoc}
     */
    @Override
    public UserRole map(ResultSet rs) throws SQLException {
        UserRole userRole = new UserRole();
        userRole.setId(rs.getInt("idUserRole"));
        userRole.setRole(rs.getString("role"));
        return userRole;
    }
}
