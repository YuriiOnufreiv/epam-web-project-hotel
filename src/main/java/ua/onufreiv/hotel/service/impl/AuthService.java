package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.IPasswordDao;
import ua.onufreiv.hotel.persistence.dao.IUserDao;
import ua.onufreiv.hotel.service.IAuthService;
import ua.onufreiv.hotel.util.PasswordEncoder;

/**
 * Created by yurii on 12/27/16.
 */
public class AuthService implements IAuthService {
    private static AuthService instance;

    private final IUserDao userDao;
    private final IPasswordDao passwordDao;

    private AuthService() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        userDao = daoFactory.getUserDao();
        passwordDao = daoFactory.getPasswordDao();
    }

    public synchronized static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    @Override
    public User authenticate(String email, String password) {
        User user = userDao.findByEmail(email);
        if (user != null && passwordDao.find(user.getPwdHashId())
                .getPwdHash().equals(PasswordEncoder.getInstance().encode(password))) {
            return user;
        }

        return null;
    }
}
