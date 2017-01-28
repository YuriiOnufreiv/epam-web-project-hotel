package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.IPasswordDao;
import ua.onufreiv.hotel.persistence.dao.IUserDao;
import ua.onufreiv.hotel.service.IRegisterService;

/**
 * Created by yurii on 1/1/17.
 */
public class RegisterService implements IRegisterService {
    private static RegisterService instance;

    private final IUserDao userDao;
    private final IPasswordDao passwordDao;

    private RegisterService() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        userDao = daoFactory.getUserDao();
        passwordDao = daoFactory.getPasswordDao();
    }

    public synchronized static RegisterService getInstance() {
        if (instance == null) {
            instance = new RegisterService();
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
