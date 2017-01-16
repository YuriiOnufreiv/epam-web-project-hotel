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
    public static final int PHONE_NUMBER_LENGTH = 12;

    @Override
    public boolean isValidPhoneNumber(String number) {
        if (number.length() != PHONE_NUMBER_LENGTH) {
            return false;
        }
        for (int i = 0; i < PHONE_NUMBER_LENGTH; i++) {
            if (!Character.isDigit(number.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isValidPassword(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,8}";
        return password.matches(pattern);
    }

    @Override
    public boolean isUniqueEmail(String email) {
        IUserDao userDao = DaoFactory.getDAOFactory(ConnectionManager.databaseType).getUserDao();
        return userDao.find(email) == null;
    }

    @Override
    public void registerNewUser(User user, PasswordHash passwordHash) {
        IUserDao userDao = DaoFactory.getDAOFactory(ConnectionManager.databaseType).getUserDao();
        IPasswordDao passwordDao = DaoFactory.getDAOFactory(ConnectionManager.databaseType).getPasswordDao();

        int id = passwordDao.insert(passwordHash);

        user.setPwdHashId(id);
        user.setUserRoleId(2);

        userDao.insert(user);
    }
}
