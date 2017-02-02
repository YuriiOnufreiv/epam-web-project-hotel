package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.PasswordDao;
import ua.onufreiv.hotel.persistence.dao.UserDao;
import ua.onufreiv.hotel.persistence.dao.UserRoleDao;
import ua.onufreiv.hotel.service.UserService;

/**
 * Created by yurii on 12/29/16.
 */
public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;

    private final UserDao userDao;
    private final UserRoleDao userRoleDao;
    private final PasswordDao passwordDao;

    private UserServiceImpl() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        userDao = daoFactory.getUserDao();
        userRoleDao = daoFactory.getUserRoleDao();
        passwordDao = daoFactory.getPasswordDao();
    }

    public static synchronized UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean insertUser(User user, PasswordHash passwordHash) {
        int id = passwordDao.insert(passwordHash);

        if (id < 0) {
            return false;
        }

        user.setPwdHashId(id);
        user.setUserRoleId(userRoleDao.findClientId());

        return userDao.insert(user) > 0;
    }

    @Override
    public User findById(int id) {
        return userDao.find(id);
    }

    @Override
    public boolean isUniqueEmail(String email) {
        return userDao.findByEmail(email) == null;
    }
}
