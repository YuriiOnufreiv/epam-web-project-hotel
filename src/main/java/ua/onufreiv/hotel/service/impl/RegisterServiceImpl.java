package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.PasswordDao;
import ua.onufreiv.hotel.persistence.dao.UserDao;
import ua.onufreiv.hotel.service.RegisterService;

/**
 * Created by yurii on 1/1/17.
 */
public class RegisterServiceImpl implements RegisterService {
    private static RegisterServiceImpl instance;

    private final UserDao userDao;
    private final PasswordDao passwordDao;

    private RegisterServiceImpl() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        userDao = daoFactory.getUserDao();
        passwordDao = daoFactory.getPasswordDao();
    }

    public synchronized static RegisterServiceImpl getInstance() {
        if (instance == null) {
            instance = new RegisterServiceImpl();
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
        user.setUserRoleId(2);

        return userDao.insert(user) > 0;
    }

    @Override
    public boolean isUniqueEmail(String email) {
        return userDao.findByEmail(email) == null;
    }
}
