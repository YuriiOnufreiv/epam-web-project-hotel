package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.UserRole;

/**
 * DAO for {@link UserRole} entity; contains additional methods.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/23/16.
 */
public interface UserRoleDao extends Dao<UserRole> {
    /**
     * Checks if specified id belongs to 'admin' role entity
     *
     * @param id id of user role
     * @return true if user role is admin
     */
    boolean idBelongsToAdmin(int id);

    /**
     * Checks if specified id belongs to 'client' role entity
     *
     * @param id id of user role
     * @return true if user role is client
     */
    boolean idBelongsToClient(int id);

    /**
     * Finds the id of 'client' user role
     *
     * @return if of 'client' role
     */
    int findClientId();
}
