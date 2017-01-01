package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.dao.IPasswordDao;
import ua.onufreiv.hotel.dao.IUserDao;
import ua.onufreiv.hotel.entities.User;
import ua.onufreiv.hotel.service.IAuthService;
import ua.onufreiv.hotel.util.PasswordEncoder;

/**
 * Created by yurii on 12/27/16.
 */
public class AuthService implements IAuthService {
    @Override
    public User authenticate(String email, String password) {
        IUserDao userDao = DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getUserDAO();
        IPasswordDao passwordDao = DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getPasswordDAO();

        User user = userDao.find(email);
        if(user != null && passwordDao.find(user.getPwdHashId())
                .getPwdHash().equals(PasswordEncoder.encode(password))) {
            return user;
        }

        return null;
    }
}