package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.PasswordDao;
import ua.onufreiv.hotel.persistence.dao.UserDao;
import ua.onufreiv.hotel.service.AuthService;
import ua.onufreiv.hotel.util.PasswordEncoder;

/**
 * Created by yurii on 12/27/16.
 */
public class AuthServiceImpl implements AuthService {
    private static AuthServiceImpl instance;

    private final UserDao userDao;
    private final PasswordDao passwordDao;

    private AuthServiceImpl() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        userDao = daoFactory.getUserDao();
        passwordDao = daoFactory.getPasswordDao();
    }

    public synchronized static AuthServiceImpl getInstance() {
        if (instance == null) {
            instance = new AuthServiceImpl();
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
