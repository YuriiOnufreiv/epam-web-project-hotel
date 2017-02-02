package ua.onufreiv.hotel.persistence.query;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class represents builder for SQL DELETE statements
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/5/17.
 */
public class SqlQueryDelete<T> implements SqlQueryWhereWrappable {
    private final static Logger logger = Logger.getLogger(SqlQueryDelete.class);

    private String tableName;

    public SqlQueryDelete() {
    }

    public SqlQueryDelete(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Sets name of table from which the record will be deleted.
     *
     * @param tableName name of table to delete from
     * @return {@code this} object
     */
    public SqlQueryDelete from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Executes with the help of passed connection SQL DELETE statement that was built.
     * Especially, it creates {@link PreparedStatement} on the basis of {@code getSqlStatement()},
     * and executes it with the help of {@code executeUpdate()} method.
     *
     * @param connection connection for executing {@link PreparedStatement}
     * @return amount of deleted rows; -1 in case of error
     */
    public int execute(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement())) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to execute delete statement: ", e);
            return -1;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return tableName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQueryWhereWrapper<T, SqlQueryDelete<T>> where() {
        return new SqlQueryWhereWrapper<>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSqlStatement() {
        StringBuilder builder = new StringBuilder("DELETE FROM ");
        builder.append(tableName.toUpperCase())
                .append(";");

        return builder.toString();
    }

    /**
     * This method doesn't sets any values to the {@code preparedStatement} as it is not
     * possible with the SQL DELETE query.
     *
     * @param preparedStatement prepared statement to fill
     * @return 0
     * @throws SQLException
     */
    @Override
    public int fillPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        return 0;
    }
}
