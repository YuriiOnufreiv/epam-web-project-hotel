package ua.onufreiv.hotel.persistence.query;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.ResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/5/17.
 */
public class SqlQuerySelect<T> implements SqlQueryWhereWrappable {
    private final static Logger logger = Logger.getLogger(SqlQuerySelect.class);

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

    /**
     * @param connection
     * @param mapper
     * @return object of type T; null in case of exceptions
     */
    public T selectObject(Connection connection, ResultSetMapper<T> mapper) {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement())) {
            fillPreparedStatement(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapper.map(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Failed to execute select statement: ", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Failed to close result set: ", e);
                }
            }
        }
        return null;
    }

    public List<T> selectAll(Connection connection, ResultSetMapper<T> mapper) {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement())) {
            fillPreparedStatement(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            List<T> objects = new ArrayList<>();
            while (resultSet.next()) {
                objects.add(mapper.map(resultSet));
            }
            return objects;
        } catch (SQLException e) {
            logger.error("Failed to execute select statement: ", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Failed to close result set: ", e);
                }
            }
        }
        return null;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public SqlQueryWhereWrapper<T, SqlQuerySelect<T>> where() {
        return new SqlQueryWhereWrapper<>(this);
    }

    @Override
    public String getSqlStatement() {
        String columnsToSelect = columns == null || columns.length == 0 ?
                "*" : String.join(",", columns);

        StringBuilder builder = new StringBuilder("SELECT ");
        builder.append(columnsToSelect)
                .append(" FROM ")
                .append(tableName.toUpperCase())
                .append(";");

        return builder.toString();
    }

    @Override
    public int fillPreparedStatement(PreparedStatement preparedStatement) {
        return 0;
    }
}
