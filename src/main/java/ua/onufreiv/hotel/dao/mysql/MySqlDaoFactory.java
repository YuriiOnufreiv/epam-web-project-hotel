package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.*;
import ua.onufreiv.hotel.jdbc.JdbcDatabase;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlDaoFactory extends DaoFactory {
    private static MySqlDaoFactory instance;
    private JdbcDatabase jdbcDatabase;

    private MySqlDaoFactory() {
        jdbcDatabase = new JdbcDatabase("jdbc:mysql://localhost:3306/hotel", "root", "root");
    }

    public static synchronized MySqlDaoFactory getInstance() {
        if (instance == null) {
            instance = new MySqlDaoFactory();
        }
        return instance;
    }

    @Override
    public IUserDao getUserDao() {
        return new MySqlUserDao(jdbcDatabase);
    }

    @Override
    public IPasswordDao getPasswordDao() {
        return new MySqlPasswordDao(jdbcDatabase);
    }

    @Override
    public IBookRequestDao getBookRequestDao() {
        return new MySqlBookRequestDao(jdbcDatabase);
    }

    @Override
    public IRoomTypeDao getRoomTypeDao() {
        return new MySqlRoomTypeDao(jdbcDatabase);
    }

    @Override
    public IRoomDao getRoomDao() {
        return new MySqlRoomDao(jdbcDatabase);
    }

    @Override
    public IReservedRoomDao getReservedRoomDao() {
        return new MySqlReservedRoomDao(jdbcDatabase);
    }

    @Override
    public IUserRoleDao getUserRoleDao() {
        return new MySqlUserRoleDao(jdbcDatabase);
    }
}
