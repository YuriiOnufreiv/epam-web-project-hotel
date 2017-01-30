package ua.onufreiv.hotel.persistence.dao;

import ua.onufreiv.hotel.entity.UserRole;

/**
 * Created by yurii on 12/23/16.
 */
public interface UserRoleDao extends Dao<UserRole> {
    boolean idBelongsToAdmin(int id);

    boolean idBelongsToClient(int id);
}
