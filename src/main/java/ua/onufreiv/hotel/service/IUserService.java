package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entities.PasswordHash;
import ua.onufreiv.hotel.entities.User;

/**
 * Created by yurii on 12/29/16.
 */
public interface IUserService {
    void registerNewUser(User user, PasswordHash pwdHash);
}