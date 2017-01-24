package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.User;

/**
 * Created by yurii on 1/8/17.
 */
public interface IUserRoleService {
    boolean userIsAdmin(User user);

    boolean userIsClient(User user);
}
