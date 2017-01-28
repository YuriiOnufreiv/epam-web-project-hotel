package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.entity.User;

/**
 * Created by yurii on 1/1/17.
 */
public interface IRegisterService {
    boolean insertUser(User user, PasswordHash passwordHash);

    boolean isUniqueEmail(String email);
}
