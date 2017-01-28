package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.User;

/**
 * Created by yurii on 12/23/16.
 */
public interface IUserDao extends IDao<User> {
    User findByEmail(String email);
}
