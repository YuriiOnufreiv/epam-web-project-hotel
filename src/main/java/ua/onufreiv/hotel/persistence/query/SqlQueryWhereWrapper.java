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
 * This class contains logic for creating SQL statements
 * that could appear with the WHERE clause.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/16/17.
 */
public class SqlQueryWhereWrapper<T, K extends SqlQueryWhereWrappable> implements SqlQuery {
    private final static Logger logger = Logger.getLogger(SqlQueryWhereWrapper.class);

    /**
     * Query to which the WHERE clause will be added
     */
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

    public StringBuilder getWhereClause() {
        return whereClause;
    }

    public List<Object> getValues() {
        return values;
    }

    /**
     * Adds new condition to the where clause
     *
     * @param columnName name of column
     * @return column
     */
    public Column column(String columnName) {
        whereClause.append(columnName);
        return SqlQueryWhereWrapper.this.column;
    }

    public K getBaseQuery() {
        return baseQuery;
    }

    /**
     * Adds 'and' condition to the where clause
     *
     * @return {@code this} object
     */
    public SqlQueryWhereWrapper<T, K> and() {
        whereClause.append(" AND ");
        return this;
    }

    /**
     * Executes, with the help of passed connection, {@code baseQuery} statement with appended
     * WHERE clause, that was built, to it.
     * Especially, it creates {@link PreparedStatement} on the basis of {@code getSqlStatement()},
     * fills it with values with hte help of {@code fillPreparedStatement()} method
     * and executes it with the help of {@code executeUpdate()} method.
     *
     * @param connection connection for executing {@link PreparedStatement}
     * @return amount of affected rows; -1 in case of error
     */
    public int executeUpdate(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement())) {
            fillPreparedStatement(preparedStatement);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to execute statement with 'where' clause: ", e);
            return -1;
        }
    }

    /**
     * Executes, with the help of passed connection, {@code baseQuery} statement with appended
     * WHERE clause, that was built, to it.
     * Especially, it creates {@link PreparedStatement} on the basis of {@code getSqlStatement()},
     * sets values to this statement by calling {@code fillPreparedStatement(preparedStatement)},
     * and executes it with the help of {@code executeQuery()} method. Then it maps the ALL elements
     * in obtained {@code ResultSet} with the help of {@code mapper.map()} method and returns
     * LIST of objects.
     *
     * @param connection connection for executing {@link PreparedStatement}
     * @param mapper     mapper for mapping ResultSet
     * @return list of object that was queried; null if no records was queried or some error occurs
     */
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

    /**
     * Executes, with the help of passed connection, {@code baseQuery} statement with appended
     * WHERE clause, that was built, to it.
     * Especially, it creates {@link PreparedStatement} on the basis of {@code getSqlStatement()},
     * sets values to this statement by calling {@code fillPreparedStatement(preparedStatement)},
     * and executes it with the help of {@code executeQuery()} method. Then it maps the FIRST element
     * in obtained {@code ResultSet} with the help of {@code mapper.map()} method and returns
     * SINGLE object.
     *
     * @param connection connection for executing {@link PreparedStatement}
     * @param mapper     mapper for mapping ResultSet
     * @return object that was queried; null if no records was queried or some error occurs
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return baseQuery.getTableName();
    }

    /**
     * Returns String representation of PreparedStatement, that characterizes this query.
     * Especially, gets SQL statement of {@code baseQuery} and appends WHERE clause that was built.
     *
     * @return String for {@link PreparedStatement}
     */
    @Override
    public String getSqlStatement() {
        String sqlStatement = baseQuery.getSqlStatement();
        int semicolon = sqlStatement.lastIndexOf(";");
        StringBuilder builder = new StringBuilder(sqlStatement);
        builder.insert(semicolon, whereClause.toString());
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int fillPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        int amount = baseQuery.fillPreparedStatement(preparedStatement);

        for (int i = 0; i < values.size(); i++) {
            preparedStatement.setObject(amount + i + 1, values.get(i));
        }

        return amount + values.size();
    }

    /**
     * Contains logic for specifying column conditions.
     */
    public class Column {
        private Column() {
        }

        public SqlQueryWhereWrapper<T, K> isEqualTo(Boolean bool) {
            values.add(bool.toString());
            whereClause.append(" = ").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> isEqualTo(String str) {
            values.add(str);
            whereClause.append(" = ").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> isEqualTo(Integer value) {
            values.add(value);
            whereClause.append(" = ").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> isLessThan(Date date) {
            values.add(date);
            whereClause.append(" < ").append("DATE(?)");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> isGreaterThan(Date date) {
            values.add(date);
            whereClause.append(" > ").append("DATE(?)");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> in(List<Integer> integers) {
            values.add(integers);
            String[] questionMarks = new String[values.size()];
            Arrays.fill(questionMarks, "?");
            whereClause.append(" IN ")
                    .append("(")
                    .append(String.join(", ", questionMarks))
                    .append(")");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper<T, K> notIn(List<Integer> integers) {
            whereClause.append(" NOT");
            return in(integers);
        }
    }
}
