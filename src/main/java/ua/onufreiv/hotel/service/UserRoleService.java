package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.User;

/**
 * Created by yurii on 1/8/17.
 */
public interface UserRoleService {
    boolean userIsAdmin(User user);

    boolean userIsClient(User user);
}
