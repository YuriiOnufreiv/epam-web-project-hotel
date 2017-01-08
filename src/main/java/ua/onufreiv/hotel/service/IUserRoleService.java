package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entities.User;

/**
 * Created by yurii on 1/8/17.
 */
public interface IUserRoleService {
    boolean isAdmin(User user);
    boolean isClient(User user);
}
