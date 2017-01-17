package ua.onufreiv.hotel.persistence.jdbc.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yurii on 1/5/17.
 */
public class SqlQueryUpdate implements ISqlWhereWrappableQuery {
    private String tableName;
    private Map<String, Object> values;

    public SqlQueryUpdate(String tableName) {
        this.tableName = tableName;
    }

    public SqlQueryUpdate() {
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

    public boolean execute(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement());

        int i = 1;
        for (Object value : values.values()) {
            preparedStatement.setObject(i++, value);
        }

        return preparedStatement.executeUpdate() > 0;
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
}
