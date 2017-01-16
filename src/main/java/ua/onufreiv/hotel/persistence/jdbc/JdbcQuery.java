package ua.onufreiv.hotel.persistence.jdbc;

import java.sql.*;

/**
 * Created by yurii on 12/25/16.
 */
public class JdbcQuery {
    public JdbcQuery() {
    }

    private boolean updateOrDelete(Connection connection, String sql, Object... params) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);

            for(int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int insert(Connection connection, String sql, Object... params) {
        PreparedStatement statement;
        int id = 0;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for(int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            if(statement.executeUpdate() > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                id = generatedKeys.next() ? generatedKeys.getInt(1) : -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean update(Connection connection, String sql, Object... params) {
        return updateOrDelete(connection, sql, params);
    }

    public boolean delete(Connection connection, String sql, Object... params) {
        return updateOrDelete(connection, sql, params);
    }

    public ResultSet select(Connection connection, String sql, Object... params) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);

            for(int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
