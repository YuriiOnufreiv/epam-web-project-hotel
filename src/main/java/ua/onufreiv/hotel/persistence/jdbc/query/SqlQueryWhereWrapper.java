package ua.onufreiv.hotel.persistence.jdbc.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/16/17.
 */
public class SqlQueryWhereWrapper<T extends ISqlWhereWrappableQuery> implements ISqlQuery {
    private T baseQuery;
    private StringBuilder whereClause;
    private List<Object> values;
    private Column column;
    private Condition condition;

    public SqlQueryWhereWrapper(T baseQuery) {
        this.baseQuery = baseQuery;
        whereClause = new StringBuilder(" WHERE ");
        values = new ArrayList<>();
        column = new Column();
        condition = new Condition();
    }

    public Condition addCondition() {
        //whereClause.append(columnName);
        return condition;
    }

    public SqlQueryWhereWrapper and() {
        whereClause.append(" AND ");
        return this;
    }

    public boolean executeUpdate(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement());
        fillPreparedStatement(preparedStatement);
        return preparedStatement.executeUpdate() > 0;
    }

    public ResultSet executeQuery(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement());
        fillPreparedStatement(preparedStatement);
        return preparedStatement.executeQuery();
    }

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

    public class Condition {
        private Condition() {
        }

        public Column column(String columnName) {
            whereClause.append(columnName);
            return SqlQueryWhereWrapper.this.column;
        }
    }

    public class Column {
        private Column() {
        }

        public SqlQueryWhereWrapper isEqual(String str) {
            values.add(str);
            whereClause.append("=").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper isEqual(Number value) {
            values.add(value.doubleValue());
            whereClause.append("=").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper less(Number value) {
            values.add(value.doubleValue());
            whereClause.append("<").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper isEqual(Integer value) {
            values.add(value);
            whereClause.append("=").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper less(Integer value) {
            values.add(value);
            whereClause.append("<").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper greater(Number value) {
            values.add(value.doubleValue());
            whereClause.append(">").append("?");
            return SqlQueryWhereWrapper.this;
        }

        public SqlQueryWhereWrapper isEqual(Boolean bool) {
            values.add(bool.toString());
            whereClause.append("=").append("?");
            return SqlQueryWhereWrapper.this;
        }
    }
}
