package ua.onufreiv.hotel.service.impl;

import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.DaoFactory;
import ua.onufreiv.hotel.persistence.dao.UserRoleDao;
import ua.onufreiv.hotel.service.UserRoleService;

/**
 * Created by yurii on 1/8/17.
 */
public class UserRoleServiceImpl implements UserRoleService {
    private static UserRoleServiceImpl instance;

    private final UserRoleDao userRoleDao;

    private UserRoleServiceImpl() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(ConnectionManager.databaseType);
        userRoleDao = daoFactory.getUserRoleDao();
    }

    public static synchronized UserRoleServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserRoleServiceImpl();
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
