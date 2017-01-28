package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.entity.User;

/**
 * Created by yurii on 1/1/17.
 */
public interface IRegisterService {
    boolean isValidPhoneNumber(String number);

    boolean isValidPassword(String password);

    boolean isUniqueEmail(String email);

    boolean registerNewUser(User user, PasswordHash passwordHash);
}
