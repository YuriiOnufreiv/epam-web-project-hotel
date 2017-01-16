package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
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
