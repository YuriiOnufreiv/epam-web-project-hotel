package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yurii on 1/28/17.
 */
public class SimpleStringMapper implements ResultSetMapper<String> {
    @Override
    public String map(ResultSet rs) throws SQLException {
        return rs.getString(1);
    }
}
