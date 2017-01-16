package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.entity.*;

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
            user.setPhoneNum(rs.getString("phoneNum"));
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

        public static BookRequest toBookRequest(java.sql.ResultSet rs) throws SQLException {
            BookRequest bookRequest = new BookRequest();
            bookRequest.setCreationDate(rs.getTimestamp("creationDateTime"));
            bookRequest.setId(rs.getInt("idRequest"));
            bookRequest.setUserId(rs.getInt("userFK"));
            bookRequest.setPersons(rs.getInt("persons"));
            bookRequest.setRoomTypeId(rs.getInt("roomTypeFK"));
            bookRequest.setCheckIn(rs.getDate("checkIn"));
            bookRequest.setCheckOut(rs.getDate("checkOut"));
            bookRequest.setProcessed(rs.getBoolean("processed"));
            return bookRequest;
        }

        public static RoomType toRoomType(java.sql.ResultSet rs) throws SQLException {
            RoomType roomType = new RoomType();
            roomType.setId(rs.getInt("idRoomType"));
            roomType.setType(rs.getString("type"));
            roomType.setDescription(rs.getString("descr"));
            roomType.setPrice(rs.getInt("price"));
            roomType.setMaxPerson(rs.getInt("maxPerson"));
            return roomType;
        }

        public static Room toRoom(java.sql.ResultSet rs) throws SQLException {
            Room room = new Room();
            room.setId(rs.getInt("idRoom"));
            room.setRoomTypeId(rs.getInt("typeFK"));
            room.setNumber(rs.getInt("number"));
            return room;
        }

        public static ReservedRoom toReservedRoom(java.sql.ResultSet rs) throws SQLException {
            ReservedRoom reservedRoom = new ReservedRoom();
            reservedRoom.setId(rs.getInt("idReservedRoom"));
            reservedRoom.setRoomId(rs.getInt("roomFK"));
            reservedRoom.setCheckInDate(rs.getDate("checkIn"));
            reservedRoom.setCheckOutDate(rs.getDate("checkOut"));
            return reservedRoom;
        }

        public static UserRole toUserRole(java.sql.ResultSet rs) throws SQLException {
            UserRole userRole = new UserRole();
            userRole.setId(rs.getInt("idUserRole"));
            userRole.setRole(rs.getString("role"));
            return userRole;
        }

        public static Bill toBill(java.sql.ResultSet rs) throws SQLException {
            Bill bill = new Bill();
            bill.setId(rs.getInt("idBill"));
            bill.setCreationDate(rs.getTimestamp("creationDateTime"));
            bill.setBookRequestId(rs.getInt("bookRequestFK"));
            bill.setRoomId(rs.getInt("roomFK"));
            bill.setTotalPrice(rs.getLong("price"));
            return bill;
        }
    }
}
