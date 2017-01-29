package ua.onufreiv.hotel.persistence.query;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public class SqlQueryInsert implements SqlQuery {
    private final static Logger logger = Logger.getLogger(SqlQueryInsert.class);

    private String tableName;
    private Map<String, Object> values;

    public SqlQueryInsert() {
        values = new HashMap<>();
    }

    public SqlQueryInsert(String tableName) {
        this();
        this.tableName = tableName;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public SqlQueryInsert into(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SqlQueryInsert value(String column, Object value) {
        values.put(column, value);
        return this;
    }

    public int execute(Connection connection) {
        ResultSet generatedKeys = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement(),
                Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(preparedStatement);

            if (preparedStatement.executeUpdate() > 0) {
                generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.next();
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Failed to execute insertBookRequest statement: ", e);
        } finally {
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    logger.error("Failed to close result set: ", e);
                }
            }
        }
        return -1;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getSqlStatement() {
        String[] questionMarks = new String[values.size()];
        Arrays.fill(questionMarks, "?");

        StringBuilder builder = new StringBuilder("INSERT INTO ");
        builder.append(tableName.toUpperCase())
                .append(" (").append(String.join(", ", values.keySet())).append(")")
                .append(" VALUES").append(" (").append(String.join(", ", questionMarks)).append(")")
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
