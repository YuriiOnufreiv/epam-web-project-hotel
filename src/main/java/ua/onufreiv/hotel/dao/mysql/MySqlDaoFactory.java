package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IBookRequestDao;
import ua.onufreiv.hotel.jdbc.JdbcDatabase;
import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.dao.IPasswordDao;
import ua.onufreiv.hotel.dao.IUserDao;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlDaoFactory extends DaoFactory {
    private JdbcDatabase jdbcDatabase;

    public MySqlDaoFactory() {
        jdbcDatabase = new JdbcDatabase("jdbc:mysql://localhost:3306/hotel", "root", "root");
    }

    @Override
    public IUserDao getUserDAO() {
        return new MySqlUserDao(jdbcDatabase);
    }

    @Override
    public IPasswordDao getPasswordDAO() {
        return new MySqlPasswordDao(jdbcDatabase);
    }

    @Override
    public IBookRequestDao getBookRequestDao() {
        return new MySqlBookRequestDao(jdbcDatabase);
    }
}
