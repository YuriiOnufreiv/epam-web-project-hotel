package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import ua.onufreiv.hotel.entity.RoomType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for mapping rows of a ResultSet to instance of {@link RoomType} class.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/23/17.
 */
public class RoomTypeMapper implements ResultSetMapper<RoomType> {
    /**
     * {@inheritDoc}
     */
    @Override
    public RoomType map(ResultSet rs) throws SQLException {
        RoomType roomType = new RoomType();
        roomType.setId(rs.getInt("idRoomType"));
        roomType.setType(rs.getString("type"));
        return roomType;
    }
}
