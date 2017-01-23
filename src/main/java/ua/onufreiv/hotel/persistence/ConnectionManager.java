package ua.onufreiv.hotel.persistence;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by yurii on 1/5/17.
 */
public class ConnectionManager {
    private final static Logger logger = Logger.getLogger(ConnectionManager.class);

    public static DatabaseType databaseType;
    private static DataSource dataSource;
    private static Connection transactionConnection;
    private static boolean transactionIsActive;
    private static int isolationLevel;

    private static void readDBProperties() {
        switch (DatabaseConfig.getInstance().getProperty(DatabaseConfig.DATABASE_TYPE)) {
            case "mysql":
                databaseType = DatabaseType.MYSQL_DB;
                break;
            default:
                databaseType = null;
        }
        isolationLevel = Integer.parseInt(DatabaseConfig.getInstance()
                .getProperty(DatabaseConfig.DATABASE_TRANSACTIONS_LEVEL));
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
            logger.error("Failed to initialise connection pool: ", e);
        }

        return dataSource;
    }

    private static Connection initializeTransaction(int isolationLevel) {
        if (transactionConnection == null) {
            try {
                transactionConnection = dataSource.getConnection();
                transactionConnection.setAutoCommit(false);
                transactionConnection.setTransactionIsolation(isolationLevel);
                transactionIsActive = true;
                logger.info("Transaction started: " + transactionConnection.toString());
            } catch (SQLException e) {
                logger.error("Failed to start transaction: ", e);
            }
        }
        return transactionConnection;
    }

    public static void createPoolFromJndi() {
        readDBProperties();
        dataSource = initialiseAppropriatePool();
    }

    public static Connection getConnection() {
        try {
            if (transactionIsActive) {
                logger.info("Connection for transaction returned: " + transactionConnection.toString());
                return transactionConnection;
            }
            Connection connection = dataSource.getConnection();
            logger.info("Connection opened: " + connection.toString());
            return connection;
        } catch (SQLException e) {
            logger.error("Failed to get connection: ", e);
        }
        return null;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                if (connection.equals(transactionConnection)) {
                    if (!transactionIsActive) {
                        logger.info("Transaction connection closed: " + transactionConnection.toString());
                        transactionConnection.close();
                    }
                } else {
                    logger.info("Connection closed: " + connection.toString());
                    connection.close();
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to close connection: " + e);
        }
    }

    public static Connection startTransaction() {
        return startTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    public static Connection startTransaction(int isolationLevel) {
        return initializeTransaction(isolationLevel);
    }

    public static void commit() {
        try {
            transactionConnection.commit();
            logger.info("Transaction committed: " + transactionConnection.toString());
        } catch (SQLException e) {
            logger.error("Failed to commit transaction: ", e);
            rollback();
        } finally {
            transactionIsActive = false;
            closeConnection(transactionConnection);
            transactionConnection = null;
            logger.info("Transaction connection set to null");
        }
    }

    public static void rollback() {
        try {
            transactionConnection.rollback();
            logger.info("Transaction rollbacked" + transactionConnection.toString());
        } catch (SQLException e) {
            logger.error("Failed to rollback transaction: ", e);
        }
    }

    public static int getIsolationLevel() {
        return isolationLevel;
    }

    public static void setIsolationLevel(int isolationLevel) {
        ConnectionManager.isolationLevel = isolationLevel;
    }
}