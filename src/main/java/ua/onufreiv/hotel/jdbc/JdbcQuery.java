package ua.onufreiv.hotel.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yurii on 12/25/16.
 */
public class JdbcQuery {
    private final JdbcDatabase jdbcDatabase;

    public JdbcQuery(JdbcDatabase jdbcDatabase) throws SQLException {
        this.jdbcDatabase = jdbcDatabase;
    }

    private boolean updateOrDelete(String sql, Object... params) {
        PreparedStatement statement;
        try {
            Connection connection = jdbcDatabase.connect();
            statement = jdbcDatabase.prepareStatement(sql, false);

            for(int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            statement.executeUpdate();
            closeConnection(connection);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int insert(String sql, Object... params) {
        PreparedStatement statement;
        int id = 0;
        try {
            Connection connection = jdbcDatabase.connect();
            statement = jdbcDatabase.prepareStatement(sql, true);

            for(int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            if(statement.executeUpdate() > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                id = generatedKeys.next() ? generatedKeys.getInt(1) : -1;
            }

            closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean update(String sql, Object... params) {
        return updateOrDelete(sql, params);
    }

    public boolean delete(String sql, Object... params) {
        return updateOrDelete(sql, params);
    }

    public ResultSet select(String sql, Object... params) {
        PreparedStatement statement;
        try {
            Connection connection = jdbcDatabase.connect();
            statement = jdbcDatabase.prepareStatement(sql, false);

            for(int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            ResultSet resultSet = statement.executeQuery();
            //closeConnection(connection);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection(Connection connection) {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
