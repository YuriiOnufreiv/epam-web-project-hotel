package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.entities.PasswordHash;
import ua.onufreiv.hotel.entities.User;
import ua.onufreiv.hotel.jdbc.ConnectionManager;
import ua.onufreiv.hotel.service.IUserService;

/**
 * Created by yurii on 12/29/16.
 */
public class UserService implements IUserService {
    @Override
    public User getById(int id) {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getUserDao().find(id);
    }

    @Override
    public void registerNewUser(User user, PasswordHash pwdHash) {

    }
}
