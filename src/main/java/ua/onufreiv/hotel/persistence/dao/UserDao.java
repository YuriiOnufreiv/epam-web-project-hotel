package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.User;

/**
 * Created by yurii on 12/23/16.
 */
public interface UserDao extends Dao<User> {
    User findByEmail(String email);
}
