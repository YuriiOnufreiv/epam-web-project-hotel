package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import ua.onufreiv.hotel.entity.ReservedRoom;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for mapping rows of a ResultSet to instance of {@link ReservedRoom} class.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/23/17.
 */
public class ReservedRoomMapper implements ResultSetMapper<ReservedRoom> {
    /**
     * {@inheritDoc}
     */
    @Override
    public ReservedRoom map(ResultSet rs) throws SQLException {
        ReservedRoom reservedRoom = new ReservedRoom();
        reservedRoom.setId(rs.getInt("idReservedRoom"));
        reservedRoom.setRoomId(rs.getInt("roomFK"));
        reservedRoom.setCheckInDate(rs.getDate("checkIn"));
        reservedRoom.setCheckOutDate(rs.getDate("checkOut"));
        return reservedRoom;
    }
}
