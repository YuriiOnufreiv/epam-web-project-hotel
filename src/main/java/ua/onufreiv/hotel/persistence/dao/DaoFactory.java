package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.persistence.DatabaseType;
import ua.onufreiv.hotel.persistence.dao.mysql.MySqlDaoFactory;

/**
 * DAO factory class
 */
public abstract class DaoFactory {

    /**
     * Returns DAO factory on the basis of specified type
     *
     * @param whichFactory type of factory
     * @return DAO factory
     * @see DatabaseType
     */
    public static DaoFactory getDAOFactory(DatabaseType whichFactory) {
        switch (whichFactory) {
            case MYSQL_DB:
                return MySqlDaoFactory.getInstance();
            default:
                return null;
        }
    }

    // There will be a method for each DAO that can be
    // created. The concrete factories will have to
    // implement these methods.
    public abstract UserDao getUserDao();

    public abstract PasswordDao getPasswordDao();

    public abstract BookRequestDao getBookRequestDao();

    public abstract RoomTypeDao getRoomTypeDao();

    public abstract RoomDao getRoomDao();

    public abstract ReservedRoomDao getReservedRoomDao();

    public abstract UserRoleDao getUserRoleDao();

    public abstract BillDao getBillDao();
}
