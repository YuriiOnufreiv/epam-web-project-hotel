package ua.onufreiv.hotel.persistence.jdbc.query;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public class SqlQueryInsert implements ISqlQuery {
    private String tableName;
    private Map<String, Object> values;

    public SqlQueryInsert() {
        values = new HashMap<>();
    }

    public SqlQueryInsert(String tableName) {
        this();
        this.tableName = tableName;
    }

    public SqlQueryInsert into(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SqlQueryInsert value(String column, Object value) {
        values.put(column, value);
        return this;
    }

    public int execute(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement(), Statement.RETURN_GENERATED_KEYS);

        int i = 1;
        for (Object value : values.values()) {
            preparedStatement.setObject(i++, value);
        }

        if (preparedStatement.executeUpdate() <= 0) {
            return -1;
        }

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        return generatedKeys.next() ? generatedKeys.getInt(1) : -1;
    }

    @Override
    public String getSqlStatement() {
        String[] questionMarks = new String[values.size()];
        Arrays.fill(questionMarks, "?");

        StringBuilder builder = new StringBuilder("INSERT INTO ");
        builder.append(tableName.toUpperCase())
                .append(" (").append(String.join(",", values.keySet())).append(")")
                .append(" VALUES").append(" (").append(String.join(",", questionMarks)).append(")")
                .append(";");

        return builder.toString();
    }
}
