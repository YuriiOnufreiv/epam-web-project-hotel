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

    public SqlQuerySelect() {
    }

    public SqlQuerySelect(String tableName) {
        this.tableName = tableName;
    }

    public String[] getColumns() {
        return columns;
    }

    /**
     * Sets name of table from which the records will be selected.
     *
     * @param tableName name of table to select from
     * @return {@code this} object
     */
    public SqlQuerySelect from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Sets the names of column that should be selected
     *
     * @param columns names of column to select
     * @return {@code this} object
     */
    public SqlQuerySelect<T> columns(String... columns) {
        this.columns = columns;
        return this;
    }

    /**
     * Executes, with the help of passed connection, SQL SELECT statement that was built.
     * Especially, it creates {@link PreparedStatement} on the basis of {@code getSqlStatement()},
     * sets values to this statement by calling {@code fillPreparedStatement(preparedStatement)},
     * and executes it with the help of {@code executeQuery()} method. Then it maps the FIRST element
     * in obtained {@code ResultSet} with the help of {@code mapper.map()} method and returns
     * SINGLE object.
     *
     * @param connection connection for executing {@link PreparedStatement}
     * @param mapper mapper for mapping ResultSet
     * @return object that was selected; null if no records was selected or some error occurs
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

    /**
     * Executes, with the help of passed connection, SQL SELECT statement that was built.
     * Especially, it creates {@link PreparedStatement} on the basis of {@code getSqlStatement()},
     * sets values to this statement by calling {@code fillPreparedStatement(preparedStatement)},
     * and executes it with the help of {@code executeQuery()} method. Then it maps the ALL elements
     * in obtained {@code ResultSet} with the help of {@code mapper.map()} method and returns
     * LIST of objects.
     *
     * @param connection connection for executing {@link PreparedStatement}
     * @param mapper     mapper for mapping ResultSet
     * @return list of object that was selected; null if no records was selected or some error occurs
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return tableName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQueryWhereWrapper<T, SqlQuerySelect<T>> where() {
        return new SqlQueryWhereWrapper<>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSqlStatement() {
        String columnsToSelect = columns == null || columns.length == 0 ?
                "*" : String.join(", ", columns);

        StringBuilder builder = new StringBuilder("SELECT ");
        builder.append(columnsToSelect)
                .append(" FROM ")
                .append(tableName.toUpperCase())
                .append(";");

        return builder.toString();
    }

    /**
     * This method doesn't sets any values to the {@code preparedStatement} as it is not
     * possible with the SQL SELECT query.
     *
     * @param preparedStatement prepared statement to fill
     * @return 0
     * @throws SQLException
     */
    @Override
    public int fillPreparedStatement(PreparedStatement preparedStatement) {
        return 0;
    }
}
