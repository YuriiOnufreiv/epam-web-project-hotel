package ua.onufreiv.hotel.persistence.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * An interface that represents possible SQL query
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/16/17.
 */
public interface SqlQuery {
    /**
     * Returns name of table with which this SQL statement will be executed.
     *
     * @return name of table
     */
    String getTableName();

    /**
     * Returns String representation of PreparedStatement, that characterizes this query
     *
     * @return String for {@link PreparedStatement}
     */
    String getSqlStatement();

    /**
     * Fills the result of {@link SqlQuery#getSqlStatement()} with values and returns amount
     * of values that were added to the specified PreparedStatement object.
     *
     * @param preparedStatement prepared statement to fill
     * @return amount of added value
     * @throws SQLException if parameterIndex does not correspond to a parameter
     *                      marker in the SQL statement; if a database access error occurs;
     */
    int fillPreparedStatement(PreparedStatement preparedStatement) throws SQLException;
}