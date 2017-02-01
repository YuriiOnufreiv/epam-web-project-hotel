package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.User;

/**
 * DAO for {@link User} entity; contains additional method.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/23/16.
 */
public interface UserDao extends Dao<User> {
    /**
     * Finds user by it's email
     *
     * @param email email of user
     * @return user with such an email, {@code null} if there is no such user
     */
    User findByEmail(String email);
}
