package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for mapping row of a ResultSet to instance of {@link String} class.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/28/17.
 */
public class SimpleStringMapper implements ResultSetMapper<String> {
    /**
     * {@inheritDoc}
     */
    @Override
    public String map(ResultSet rs) throws SQLException {
        return rs.getString(1);
    }
}
