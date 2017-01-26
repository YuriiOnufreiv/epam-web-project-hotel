package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import ua.onufreiv.hotel.entity.PasswordHash;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yurii on 1/23/17.
 */
public class PasswordHashMapper implements ResultSetMapper<PasswordHash> {
    @Override
    public PasswordHash map(ResultSet rs) throws SQLException {
        PasswordHash passwordHash = new PasswordHash();
        passwordHash.setId(rs.getInt("idPassword"));
        passwordHash.setPwdHash(rs.getString("pwdHash"));
        return passwordHash;
    }
}
