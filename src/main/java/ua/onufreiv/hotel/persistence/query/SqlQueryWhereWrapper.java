package ua.onufreiv.hotel.persistence.query;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.ResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by yurii on 1/16/17.
 */
public class SqlQueryWhereWrapper<T, K extends SqlQueryWhereWrappable> implements SqlQuery {
    private final static Logger logger = Logger.getLogger(SqlQueryWhereWrapper.class);

    private K baseQuery;
    private StringBuilder whereClause;
    private List<Object> values;
    private Column column;

    public SqlQueryWhereWrapper(K baseQuery) {
        this.baseQuery = baseQuery;
        whereClause = new StringBuilder(" WHERE ");
        values = new ArrayList<>();
        column = new Column();
    }

    public Column column(String columnName) {
        whereClause.append(columnName);
        return SqlQueryWhereWrapper.this.column;
    }

    public SqlQueryWhereWrapper<T, K> and() {
        whereClause.append(" AND ");
        return this;
    }

    public int executeUpdate(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement())) {
            fillPreparedStatement(preparedStatement);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to execute statement with 'where' clause: ", e);
            return -1;
        }
    }

    public List<T> executeQuery(Connection connection, ResultSetMapper<T> mapper) {
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
            logger.error("Failed to execute statement with 'where' clause: ", e);
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

    public T executeQueryForObject(Connection connection, ResultSetMapper<T> mapper) {
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement())) {
            fillPreparedStatement(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapper.map(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Failed to execute statement with 'where' clause: ", e);
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
        return baseQuery.getTableName();
    }

    @Override
    public String getSqlStatement() {
        String sqlStatement = baseQuery.getSqlStatement();
        int semicolon = sqlStatement.lastIndexOf(";");
        StringBuilder builder = new StringBuilder(sqlStatement);
        builder.insert(semicolon, whereClause.toString());
        return builder.toString();
    }

    @Override
    public int fillPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        int amount = baseQuery.fillPreparedStatement(preparedStatement);

        for (int i = 0; i < values.size(); i++) {
            preparedStatement.setObject(amount + i + 1, values.get(i));
        }

        return amount + values.size();
    }

    public class Column {
        private Column() {
        }

        public SqlQueryWhereWrapper<T, K> isEqual(Boolean bool) {
            values.add(bool.toString());
            whereClause.append("=").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> isEqual(String str) {
            values.add(str);
            whereClause.append("=").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> isEqual(Integer value) {
            values.add(value);
            whereClause.append("=").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> isEqual(Number value) {
            values.add(value.doubleValue());
            whereClause.append("=").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> less(Number value) {
            values.add(value.doubleValue());
            whereClause.append("<").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> less(Integer value) {
            values.add(value);
            whereClause.append("<").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> less(Date date) {
            values.add(date);
            whereClause.append("<").append("DATE(?)");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> greater(Number value) {
            values.add(value.doubleValue());
            whereClause.append(">").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> greater(Date date) {
            values.add(date);
            whereClause.append(">").append("DATE(?)");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> notIn(List<Integer> exceptRoomIds) {
            values.add(exceptRoomIds);
            String[] questionMarks = new String[values.size()];
            Arrays.fill(questionMarks, "?");
            whereClause.append(" NOT IN ")
                    .append(" (")
                    .append(String.join(",", questionMarks))
                    .append(")");
            return SqlQueryWhereWrapper.this;
        }
    }
}
