package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.persistence.dao.*;

/**
 * DAO factory class for dealing with MySql database
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/23/16.
 */
public class MySqlDaoFactory extends DaoFactory {
    private static MySqlDaoFactory instance;

    private MySqlDaoFactory() {
    }

    public static synchronized MySqlDaoFactory getInstance() {
        if (instance == null) {
            instance = new MySqlDaoFactory();
        }
        return instance;
    }

    @Override
    public UserDao getUserDao() {
        return MySqlUserDao.getInstance();
    }

    @Override
    public PasswordDao getPasswordDao() {
        return MySqlPasswordDao.getInstance();
    }

    @Override
    public BookRequestDao getBookRequestDao() {
        return MySqlBookRequestDao.getInstance();
    }

    @Override
    public RoomTypeDao getRoomTypeDao() {
        return MySqlRoomTypeDao.getInstance();
    }

    @Override
    public RoomDao getRoomDao() {
        return MySqlRoomDao.getInstance();
    }

    @Override
    public ReservedRoomDao getReservedRoomDao() {
        return MySqlReservedRoomDao.getInstance();
    }

    @Override
    public UserRoleDao getUserRoleDao() {
        return MySqlUserRoleDao.getInstance();
    }

    @Override
    public BillDao getBillDao() {
        return MySqlBillDao.getInstance();
    }
}
