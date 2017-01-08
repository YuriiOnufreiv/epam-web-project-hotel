package ua.onufreiv.hotel.jdbc;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by yurii on 1/5/17.
 */
public class Database {
    final static Logger logger = Logger.getLogger(Database.class);

    private static Database databaseInstance;
    private DataSource dataSource;
    private Connection transactionConnection;

    private Database() {
        InitialContext initContext;
        try {
            initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/hotel");
            logger.info("Connection pool initialised");
        } catch (NamingException e) {
            logger.error("Failed to initialise connection pool: " + e);
        }
    }

    public static Database getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    public Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            logger.info("Connection opened: " + connection.toString());
            return connection;
        } catch (SQLException e) {
            logger.error("Failed to get connection: " + e);
        }
        return null;
    }

    public void startTransaction() {
        transactionConnection = getConnection();
        try {
            transactionConnection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Failed to set auto commit to false: " + e);
        }
    }

    public void commit() {

    }

    public void closeConnection(Connection connection) {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Connection closed: " + connection.toString());
            }
        } catch (SQLException e) {
            logger.error("Failed to close connection: " + e);
        }
    }
}