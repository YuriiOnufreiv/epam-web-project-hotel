package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.entity.User;

/**
 * Created by yurii on 12/29/16.
 */
public interface UserService {
    boolean insertUser(User user, PasswordHash passwordHash);

    User findById(int id);

    boolean isUniqueEmail(String email);
}
