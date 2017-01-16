package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.dao.IPasswordDao;
import ua.onufreiv.hotel.dao.IUserDao;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.jdbc.ConnectionManager;
import ua.onufreiv.hotel.service.IAuthService;
import ua.onufreiv.hotel.util.PasswordEncoder;

/**
 * Created by yurii on 12/27/16.
 */
public class AuthService implements IAuthService {
    @Override
    public User authenticate(String email, String password) {
        IUserDao userDao = DaoFactory.getDAOFactory(ConnectionManager.databaseType).getUserDao();
        IPasswordDao passwordDao = DaoFactory.getDAOFactory(ConnectionManager.databaseType).getPasswordDao();

        User user = userDao.find(email);
        if(user != null && passwordDao.find(user.getPwdHashId())
                .getPwdHash().equals(PasswordEncoder.encode(password))) {
            return user;
        }

        return null;
    }
}
