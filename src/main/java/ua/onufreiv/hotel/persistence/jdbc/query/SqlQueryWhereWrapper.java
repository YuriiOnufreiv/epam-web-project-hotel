package ua.onufreiv.hotel.persistence.jdbc.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public SqlQueryWhereWrapper(T baseQuery) {
        this.baseQuery = baseQuery;
        whereClause = new StringBuilder(" WHERE ");
        values = new ArrayList<>();
        column = new Column();
    }

    public Column column(String columnName) {
        whereClause.append(columnName);
        return column;
    }

    public SqlQueryWhereWrapper and() {
        whereClause.append(" AND ");
        return this;
    }

    public void execute(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getSqlStatement());

        for (int i = 0; i < values.size(); i++) {
            preparedStatement.setObject(i++, values.get(i));
        }

        preparedStatement.executeUpdate();
    }

    public String getSqlStatement() {
        String sqlStatement = baseQuery.getSqlStatement();
        int semicolon = sqlStatement.lastIndexOf(";");
        StringBuilder builder = new StringBuilder(sqlStatement);
        builder.insert(semicolon, whereClause.toString());
        return builder.toString();
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

        public SqlQueryWhereWrapper greater(Number value) {
            values.add(value.doubleValue());
            whereClause.append(">").append("?");
            return SqlQueryWhereWrapper.this;
        }
    }
}
