package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import ua.onufreiv.hotel.entity.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for mapping rows of a ResultSet to instance of {@link Room} class.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/23/17.
 */
public class RoomMapper implements ResultSetMapper<Room> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Room map(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("idRoom"));
        room.setRoomTypeId(rs.getInt("typeFK"));
        room.setNumber(rs.getInt("number"));
        room.setDescription(rs.getString("descr"));
        room.setPrice(rs.getInt("price"));
        room.setMaxPerson(rs.getInt("maxPerson"));
        return room;
    }
}
