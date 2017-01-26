package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import ua.onufreiv.hotel.entity.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yurii on 1/23/17.
 */
public class RoomMapper implements ResultSetMapper<Room> {
    @Override
    public Room map(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("idRoom"));
        room.setRoomTypeId(rs.getInt("typeFK"));
        room.setNumber(rs.getInt("number"));
        return room;
    }
}
