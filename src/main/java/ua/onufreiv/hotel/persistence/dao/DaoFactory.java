package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.persistence.DatabaseType;
import ua.onufreiv.hotel.persistence.dao.mysql.MySqlDaoFactory;

/**
 * Created by yurii on 12/23/16.
 */
public abstract class DaoFactory {


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
    public abstract IUserDao getUserDao();

    public abstract IPasswordDao getPasswordDao();

    public abstract IBookRequestDao getBookRequestDao();

    public abstract IRoomTypeDao getRoomTypeDao();

    public abstract IRoomDao getRoomDao();

    public abstract IReservedRoomDao getReservedRoomDao();

    public abstract IUserRoleDao getUserRoleDao();

    public abstract IBillDao getBillDao();
}
