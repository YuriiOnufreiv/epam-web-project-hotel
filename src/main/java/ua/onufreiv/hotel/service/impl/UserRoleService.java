package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.IUserRoleDao;
import ua.onufreiv.hotel.service.IUserRoleService;

/**
 * Created by yurii on 1/8/17.
 */
public class UserRoleService implements IUserRoleService {
    private static UserRoleService instance;

    private final IUserRoleDao userRoleDao;

    private UserRoleService() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        userRoleDao = daoFactory.getUserRoleDao();
    }

    public synchronized static UserRoleService getInstance() {
        if (instance == null) {
            instance = new UserRoleService();
        }
        return instance;
    }

    @Override
    public boolean userIsAdmin(User user) {
        return userRoleDao.idBelongsToAdmin(user.getUserRoleId());
    }

    @Override
    public boolean userIsClient(User user) {
        return userRoleDao.idBelongsToClient(user.getUserRoleId());
    }
}
