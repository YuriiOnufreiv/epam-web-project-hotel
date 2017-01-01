package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.dao.IPasswordDao;
import ua.onufreiv.hotel.dao.IUserDao;
import ua.onufreiv.hotel.entities.PasswordHash;
import ua.onufreiv.hotel.entities.User;
import ua.onufreiv.hotel.service.impl.IRegisterService;

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
        IUserDao userDao = DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getUserDAO();
        return userDao.find(email) == null;
    }

    @Override
    public void registerNewUser(User user, PasswordHash passwordHash) {
        IUserDao userDao = DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getUserDAO();
        IPasswordDao passwordDao = DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getPasswordDAO();

        int id = passwordDao.insert(passwordHash);

        user.setPwdHashId(id);
        user.setUserRoleId(2);

        userDao.insert(user);
    }
}