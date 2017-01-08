package ua.onufreiv.hotel.dao;

import ua.onufreiv.hotel.entities.UserRole;

/**
 * Created by yurii on 12/23/16.
 */
public interface IUserRoleDao extends IDao<UserRole> {
    boolean isAdmin(int id);
    boolean isClient(int id);
}
