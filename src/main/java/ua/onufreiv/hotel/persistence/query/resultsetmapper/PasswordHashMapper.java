package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import ua.onufreiv.hotel.entity.PasswordHash;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for mapping rows of a ResultSet to instance of {@link PasswordHash} class.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/23/17.
 */
public class PasswordHashMapper implements ResultSetMapper<PasswordHash> {
    /**
     * {@inheritDoc}
     */
    @Override
    public PasswordHash map(ResultSet rs) throws SQLException {
        PasswordHash passwordHash = new PasswordHash();
        passwordHash.setId(rs.getInt("idPassword"));
        passwordHash.setPwdHash(rs.getString("pwdHash"));
        return passwordHash;
    }
}
