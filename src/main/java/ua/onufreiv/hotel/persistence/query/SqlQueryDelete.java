package ua.onufreiv.hotel.persistence.query;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by yurii on 1/5/17.
 */
public class SqlQueryDelete<T> implements SqlQueryWhereWrappable {
    private final static Logger logger = Logger.getLogger(SqlQueryDelete.class);

    private String tableName;

    public SqlQueryDelete() {
    }

    public SqlQueryDelete(String tableName) {
        this.tableName = tableName;
    }

    public SqlQueryDelete from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public int execute(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement())) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to execute delete statement: ", e);
            return -1;
        }
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public SqlQueryWhereWrapper<T, SqlQueryDelete<T>> where() {
        return new SqlQueryWhereWrapper<>(this);
    }

    @Override
    public String getSqlStatement() {
        StringBuilder builder = new StringBuilder("DELETE FROM ");
        builder.append(tableName.toUpperCase())
                .append(";");

        return builder.toString();
    }

    @Override
    public int fillPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        return 0;
    }
}
