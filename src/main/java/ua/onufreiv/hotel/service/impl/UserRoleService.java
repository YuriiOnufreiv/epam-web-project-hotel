package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.service.IUserRoleService;

/**
 * Created by yurii on 1/8/17.
 */
public class UserRoleService implements IUserRoleService {
    @Override
    public boolean userIsAdmin(User user) {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getUserRoleDao().isAdmin(user.getUserRoleId());
    }

    @Override
    public boolean userIsClient(User user) {
        return DaoFactory.getDAOFactory(ConnectionManager.databaseType).getUserRoleDao().isClient(user.getUserRoleId());
    }
}
