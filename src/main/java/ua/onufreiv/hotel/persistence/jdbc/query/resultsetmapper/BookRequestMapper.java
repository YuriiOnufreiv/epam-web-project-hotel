package ua.onufreiv.hotel.persistence.jdbc.query.resultsetmapper;

import ua.onufreiv.hotel.entity.BookRequest;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yurii on 1/23/17.
 */
public class BookRequestMapper implements ResultSetMapper<BookRequest> {
    @Override
    public BookRequest map(ResultSet rs) throws SQLException {
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
}
