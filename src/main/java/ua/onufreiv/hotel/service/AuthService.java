package ua.onufreiv.hotel.service;

import ua.onufreiv.hotel.entity.User;

/**
 * Created by yurii on 12/27/16.
 */
public interface AuthService {
    User authenticate(String email, String pwdHash);
}
