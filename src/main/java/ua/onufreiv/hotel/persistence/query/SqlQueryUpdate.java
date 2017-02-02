package ua.onufreiv.hotel.persistence.query;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents builder for SQL UPDATE statements
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/5/17.
 */
public class SqlQueryUpdate<T> implements SqlQueryWhereWrappable {
    private final static Logger logger = Logger.getLogger(SqlQueryUpdate.class);

    private String tableName;
    private Map<String, Object> values;

    public SqlQueryUpdate() {
        values = new HashMap<>();
    }

    public SqlQueryUpdate(String tableName) {
        this();
        this.tableName = tableName;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    /**
     * Sets name of table in which the record will be updated.
     *
     * @param tableName name of table to update in
     * @return {@code this} object
     */
    public SqlQueryUpdate table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Adds column name which will be updated and it's new value
     *
     * @param column column name
     * @param value  new value of this column
     * @return {@code this} object
     */
    public SqlQueryUpdate set(String column, Object value) {
        values.put(column, value);
        return this;
    }

    /**
     * Executes with the help of passed connection SQL UPDATE statement that was built.
     * Especially, it creates {@link PreparedStatement} on the basis of {@code getSqlStatement()},
     * and executes it with the help of {@code executeUpdate()} method.
     *
     * @param connection connection for executing {@link PreparedStatement}
     * @return amount of updated rows; -1 in case of error
     */
    public int executeUpdate(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement())) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to execute update statement: ", e);
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
    public SqlQueryWhereWrapper<T, SqlQueryUpdate<T>> where() {
        return new SqlQueryWhereWrapper<>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSqlStatement() {
        String[] questionMarks = new String[values.size()];
        Arrays.fill(questionMarks, "?");

        StringBuilder builder = new StringBuilder("UPDATE ");
        builder.append(tableName.toUpperCase())
                .append(" SET ")
                .append(String.join(", ", values.keySet()
                        .stream()
                        .map(s -> s + " = " + "?")
                        .toArray(String[]::new)))
                .append(";");

        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int fillPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        int i = 1;
        for (Object value : values.values()) {
            preparedStatement.setObject(i++, value);
        }
        return values.size();
    }
}
