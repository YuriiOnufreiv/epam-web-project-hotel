package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.entities.Form;
import ua.onufreiv.hotel.entities.PasswordHash;
import ua.onufreiv.hotel.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yurii on 12/25/16.
 */
public class DtoMapper {
    public static class ResultSet {
        public static User toUser(java.sql.ResultSet rs) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("idUser"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setEmail(rs.getString("email"));
            user.setTelephone(rs.getString("tel"));
            user.setUserRoleId(rs.getInt("roleFK"));
            user.setPwdHashId(rs.getInt("pwdFK"));
            return user;
        }

        public static PasswordHash toPasswordHash(java.sql.ResultSet rs) throws SQLException {
            PasswordHash passwordHash = new PasswordHash();
            passwordHash.setId(rs.getInt("idPassword"));
            passwordHash.setPwdHash(rs.getString("pwdHash"));
            return passwordHash;
        }

        public static Form toForm(java.sql.ResultSet rs) throws SQLException {
            Form form = new Form();
            form.setId(rs.getInt("idForm"));
            form.setUserId(rs.getInt("userFK"));
            form.setPersonAmount(rs.getInt("personAmount"));
            form.setRoomTypeId(rs.getInt("roomTypeFK"));
            form.setStartDate(rs.getDate("startDate"));
            form.setEndDate(rs.getDate("endDate"));
            form.setProcessed(rs.getBoolean("processed"));
            return form;
        }
    }
}
