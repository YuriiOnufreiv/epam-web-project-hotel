package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.dao.DaoFactory;
import ua.onufreiv.hotel.entities.User;
import ua.onufreiv.hotel.service.IUserRoleService;

/**
 * Created by yurii on 1/8/17.
 */
public class UserRoleService implements IUserRoleService {
    @Override
    public boolean isAdmin(User user) {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getUserRoleDao().isAdmin(user.getUserRoleId());
    }

    @Override
    public boolean isClient(User user) {
        return DaoFactory.getDAOFactory(DaoFactory.FactoryType.MYSQL_DB).getUserRoleDao().isClient(user.getUserRoleId());
    }
}
