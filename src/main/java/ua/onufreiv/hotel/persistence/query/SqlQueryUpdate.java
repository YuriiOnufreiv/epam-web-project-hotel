package ua.onufreiv.hotel.persistence.query;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public class SqlQueryUpdate<T> implements SqlQueryWhereWrappable {
    private final static Logger logger = Logger.getLogger(SqlQueryUpdate.class);

    private String tableName;
    private Map<String, Object> values;

    public SqlQueryUpdate(String tableName) {
        this.tableName = tableName;
        values = new HashMap<>();
    }

    public SqlQueryUpdate table(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SqlQueryUpdate set(String column, Object value) {
        values.put(column, value);
        return this;
    }

    public boolean executeUpdate(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement())) {
            int i = 1;
            for (Object value : values.values()) {
                preparedStatement.setObject(i++, value);
            }

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to execute update statement: ", e);
        }

        return false;
    }

    @Override
    public SqlQueryWhereWrapper<T, SqlQueryUpdate<T>> where() {
        return new SqlQueryWhereWrapper<>(this);
    }

    @Override
    public String getSqlStatement() {
        String[] questionMarks = new String[values.size()];
        Arrays.fill(questionMarks, "?");

        StringBuilder builder = new StringBuilder("UPDATE ");
        builder.append(tableName.toUpperCase())
                .append(" SET ")
                .append(String.join(", ", values.keySet()
                        .stream()
                        .map(s -> s + "=" + "?")
                        .toArray(String[]::new)))
                .append(";");

        return builder.toString();
    }

    @Override
    public int fillPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        int i = 1;
        for (Object value : values.values()) {
            preparedStatement.setObject(i++, value);
        }
        return values.size();
    }
}
