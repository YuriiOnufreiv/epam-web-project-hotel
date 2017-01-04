package ua.onufreiv.hotel.dao;

import ua.onufreiv.hotel.dao.mysql.MySqlDaoFactory;

/**
 * Created by yurii on 12/23/16.
 */
public abstract class DaoFactory {


    public static DaoFactory getDAOFactory(FactoryType whichFactory) {
        switch (whichFactory) {
            case MYSQL_DB:
                return new MySqlDaoFactory();
            default:
                return null;
        }
    }

    // There will be a method for each DAO that can be
    // created. The concrete factories will have to
    // implement these methods.
    public abstract IUserDao getUserDAO();

    public abstract IPasswordDao getPasswordDAO();

    public abstract IBookRequestDao getBookRequestDao();

    public abstract IRoomTypeDao getRoomTypeDAO();

//    public abstract IUserRoleDAO getUserRoleDAO();
//    public abstract IBillDAO getBillDAO();
//    public abstract IRoomDAO getRoomDAO();

    // List of DAO types supported by the factory
    public enum FactoryType {
        MYSQL_DB;
    }
}
