package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.IUserDao;
import ua.onufreiv.hotel.service.IUserService;

/**
 * Created by yurii on 12/29/16.
 */
public class UserService implements IUserService {
    private static UserService instance;

    private final IUserDao userDao;

    private UserService() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        userDao = daoFactory.getUserDao();
    }

    public synchronized static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    @Override
    public User findById(int id) {
        return userDao.find(id);
    }
}
