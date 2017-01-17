package ua.onufreiv.hotel.persistence.jdbc.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yurii on 1/5/17.
 */
public class SqlQuerySelect implements ISqlWhereWrappableQuery {
    private String tableName;
    private String[] columns;

    public SqlQuerySelect(String tableName) {
        this.tableName = tableName;
    }

    public SqlQuerySelect from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SqlQuerySelect columns(String... columns) {
        this.columns = columns;
        return this;
    }

    public ResultSet execute(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement());
        return preparedStatement.executeQuery();
    }

    public String getSqlStatement() {
        String columnsToSelect = columns == null ?
                "*" : String.join(",", columns);

        StringBuilder builder = new StringBuilder("SELECT ");
        builder.append(columnsToSelect)
                .append(" FROM ")
                .append(tableName.toUpperCase())
                .append(";");

        return builder.toString();
    }
}
