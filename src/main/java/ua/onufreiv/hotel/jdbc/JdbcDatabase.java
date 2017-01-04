package ua.onufreiv.hotel.jdbc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by yurii on 12/25/16.
 */
public class JdbcDatabase {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final String url;
    private final String login;
    private final String password;
    private Connection connection;

    public JdbcDatabase(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;

    }

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Connection connect() throws SQLException {
        InitialContext initContext;
        try {
            initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/hotel");
            connection = ds.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
            return null;
        }

        return this.connection;
    }

    public PreparedStatement prepareStatement(String sql, boolean returnId) throws SQLException {
        if(returnId) {
            return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }
        return connection.prepareStatement(sql);
    }
}
