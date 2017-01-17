package ua.onufreiv.hotel.persistence.jdbc.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by yurii on 1/5/17.
 */
public class SqlQueryDelete implements ISqlWhereWrappableQuery {
    private String tableName;

    public SqlQueryDelete from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public boolean execute(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement());
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public String getSqlStatement() {
        StringBuilder builder = new StringBuilder("DELETE FROM ");
        builder.append(tableName.toUpperCase())
                .append(";");

        return builder.toString();
    }
}
