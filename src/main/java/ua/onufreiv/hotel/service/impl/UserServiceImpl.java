package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.UserDao;
import ua.onufreiv.hotel.service.UserService;

/**
 * Created by yurii on 12/29/16.
 */
public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;

    private final UserDao userDao;

    private UserServiceImpl() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        userDao = daoFactory.getUserDao();
    }

    public synchronized static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public User findById(int id) {
        return userDao.find(id);
    }
}
