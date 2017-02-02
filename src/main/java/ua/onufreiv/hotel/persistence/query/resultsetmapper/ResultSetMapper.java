package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for mapping rows of a ResultSet to instance of concrete class
 * on a per-row basis.
 *
 * @param <T> type of object to create
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/23/17.
 */
public interface ResultSetMapper<T> {

    /**
     * Implementations must implement this method to map each row of data in the ResultSet.
     *
     * @param rs the ResultSet to map
     * @return the result object for the current row
     * @throws SQLException if a SQLException is encountered getting column values
     */
    T map(ResultSet rs) throws SQLException;
}
