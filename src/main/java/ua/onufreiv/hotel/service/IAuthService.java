package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entities.User;

/**
 * Created by yurii on 12/27/16.
 */
public interface IAuthService {
    User authenticate(String email, String pwdHash);
}
