package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entities.PasswordHash;
import ua.onufreiv.hotel.entities.User;

/**
 * Created by yurii on 1/1/17.
 */
public interface IRegisterService {
    boolean isValidPhoneNumber(String number);
    boolean isValidPassword(String password);

    boolean isUniqueEmail(String email);

    void registerNewUser(User user, PasswordHash passwordHash);
}
