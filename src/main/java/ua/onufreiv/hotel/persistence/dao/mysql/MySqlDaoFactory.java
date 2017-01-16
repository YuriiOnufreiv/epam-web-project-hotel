package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.persistence.dao.*;

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
        return MySqlUserDao.getInstance();
    }

    @Override
    public IPasswordDao getPasswordDao() {
        return MySqlPasswordDao.getInstance();
    }

    @Override
    public IBookRequestDao getBookRequestDao() {
        return MySqlBookRequestDao.getInstance();
    }

    @Override
    public IRoomTypeDao getRoomTypeDao() {
        return MySqlRoomTypeDao.getInstance();
    }

    @Override
    public IRoomDao getRoomDao() {
        return MySqlRoomDao.getInstance();
    }

    @Override
    public IReservedRoomDao getReservedRoomDao() {
        return MySqlReservedRoomDao.getInstance();
    }

    @Override
    public IUserRoleDao getUserRoleDao() {
        return MySqlUserRoleDao.getInstance();
    }

    @Override
    public IBillDao getBillDao() {
        return MySqlBillDao.getInstance();
    }
}
