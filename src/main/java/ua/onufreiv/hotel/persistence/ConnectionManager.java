package ua.onufreiv.hotel.persistence;

import org.apache.log4j.Logger;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by yurii on 1/5/17.
 */
public class ConnectionManager {
    final static Logger logger = Logger.getLogger(ConnectionManager.class);
    public static DaoFactory.FactoryType databaseType;
    private static DataSource dataSource;

//    private static Connection transactionConnection;

    public static Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            logger.info("Connection opened: " + connection.toString());
            return connection;
        } catch (SQLException e) {
            logger.error("Failed to get connection: " + e);
        }
        return null;
    }

    public static void createPoolFromJndi() {
        databaseType = getDbTypeFromProperties();
        dataSource = initialiseAppropriatePool();
    }

    private static DaoFactory.FactoryType getDbTypeFromProperties() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

        DaoFactory.FactoryType type;
        switch ((String) resourceBundle.getObject("db.type")) {
            case "mysql":
                type = DaoFactory.FactoryType.MYSQL_DB;
                break;
            default:
                type = null;
        }

        return type;
    }

    private static DataSource initialiseAppropriatePool() {
        DataSource dataSource = null;
        try {
            switch (databaseType) {
                case MYSQL_DB:
                    InitialContext initContext;
                    initContext = new InitialContext();
                    dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/hotel");
                    logger.info("Connection pool initialised");
                    break;

                default:
                    logger.error("Failed to initialise connection pool: database type is undefined");
                    break;
            }
        } catch (NamingException e) {
            logger.error("Failed to initialise connection pool: " + e);
        }

        return dataSource;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Connection closed: " + connection.toString());
            }
        } catch (SQLException e) {
            logger.error("Failed to close connection: " + e);
        }
    }
}