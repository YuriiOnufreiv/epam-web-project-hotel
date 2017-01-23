package ua.onufreiv.hotel.persistence.jdbc.query.resultsetmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yurii on 1/23/17.
 */
public interface ResultSetMapper<T> {
    T map(ResultSet rs) throws SQLException;
}
