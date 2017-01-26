package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import ua.onufreiv.hotel.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yurii on 1/23/17.
 */
public class UserMapper implements ResultSetMapper<User> {
    @Override
    public User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("idUser"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNum(rs.getString("phoneNum"));
        user.setUserRoleId(rs.getInt("roleFK"));
        user.setPwdHashId(rs.getInt("pwdFK"));
        return user;
    }
}
