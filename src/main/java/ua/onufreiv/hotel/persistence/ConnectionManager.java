package ua.onufreiv.hotel.persistence;

import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is responsible for establishing and management of connections with the database.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/5/17.
 */
public class ConnectionManager {
    private final static Logger logger = Logger.getLogger(ConnectionManager.class);

    private static DataSource dataSource;
    private static Connection transactionConnection;
    private static boolean transactionIsActive;
    private static int isolationLevel = Connection.TRANSACTION_READ_COMMITTED;

    public static DatabaseType databaseType;

    /**
     * Reads properties from the bundle, especially type of database
     * and isolation level of transactions
     */
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

    /**
     * Initializes connection pooling with the required database
     */
    private static void initialiseAppropriatePool() {
        try {
            switch (databaseType) {
                case MYSQL_DB:
                    InitialContext initContext = new InitialContext();
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
    }

    /**
     * Reads data about DB from properties and initializes connection pool.
     */
    public static void createPoolFromJndi() {
        readDBProperties();
        initialiseAppropriatePool();
    }

    /**
     * Returns connections for dealing with the database. If {@code transactionIsActive} value is
     * {@code true}, then it returns connection contained in {@code transactionConnection} field.
     *
     * @return connection to the database
     */
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

    /**
     * Closes connection passed as parameter.
     * Actually, if passed connection equals to {@code transactionConnection}
     * this connection will be closed only if value of {@code transactionIsActive} is {@code true}.
     *
     * @param connection connection to close
     */
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

    /**
     * Initializes transaction with the
     *
     * @return
     */
    public static Connection startTransaction() {
        return startTransaction(isolationLevel);
    }

    /**
     * Initializes transaction with the specified isolation level.
     *
     * @param isolationLevel isolation level of transactions.
     * @return created connection
     */
    public static Connection startTransaction(int isolationLevel) {
        if (transactionConnection == null) {
            try {
                transactionConnection = dataSource.getConnection();
                transactionConnection.setAutoCommit(false);
                transactionConnection.setTransactionIsolation(isolationLevel);
                transactionIsActive = true;
                setIsolationLevel(isolationLevel);
                logger.info("Transaction started: " + transactionConnection.toString());
            } catch (SQLException e) {
                logger.error("Failed to start transaction: ", e);
            }
        }
        return transactionConnection;
    }

    /**
     * Commits the transaction
     *
     * @return true if commit was successful, false otherwise
     */
    public static boolean commit() {
        try {
            transactionConnection.commit();
            logger.info("Transaction committed: " + transactionConnection.toString());
        } catch (SQLException e) {
            logger.error("Failed to commit transaction: ", e);
            rollback();
            return false;
        } finally {
            transactionIsActive = false;
            closeConnection(transactionConnection);
            transactionConnection = null;
            logger.info("Transaction connection set to null");
        }
        return true;
    }

    /**
     * Rollback of transaction
     *
     * @return true if rollback was successful, false otherwise
     */
    public static boolean rollback() {
        try {
            transactionConnection.rollback();
            logger.info("Transaction rollback" + transactionConnection.toString());
        } catch (SQLException e) {
            logger.error("Failed to rollback transaction: ", e);
            return false;
        }
        return true;
    }

    public static int getIsolationLevel() {
        return isolationLevel;
    }

    public static void setIsolationLevel(int isolationLevel) {
        ConnectionManager.isolationLevel = isolationLevel;
    }
}