package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.*;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlDaoFactory extends DaoFactory {
    private static MySqlDaoFactory instance;

    private MySqlDaoFactory() {}

    public static synchronized MySqlDaoFactory getInstance() {
        if (instance == null) {
            instance = new MySqlDaoFactory();
        }
        return instance;
    }

    @Override
    public IUserDao getUserDao() {
        return new MySqlUserDao();
    }

    @Override
    public IPasswordDao getPasswordDao() {
        return new MySqlPasswordDao();
    }

    @Override
    public IBookRequestDao getBookRequestDao() {
        return new MySqlBookRequestDao();
    }

    @Override
    public IRoomTypeDao getRoomTypeDao() {
        return new MySqlRoomTypeDao();
    }

    @Override
    public IRoomDao getRoomDao() {
        return new MySqlRoomDao();
    }

    @Override
    public IReservedRoomDao getReservedRoomDao() {
        return new MySqlReservedRoomDao();
    }

    @Override
    public IUserRoleDao getUserRoleDao() {
        return new MySqlUserRoleDao();
    }
}
