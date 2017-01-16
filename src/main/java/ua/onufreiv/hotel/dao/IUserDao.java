package ua.onufreiv.hotel.dao;

import ua.onufreiv.hotel.entity.User;

/**
 * Created by yurii on 12/23/16.
 */
public interface IUserDao extends IDao<User> {
    User find(String email);
}
