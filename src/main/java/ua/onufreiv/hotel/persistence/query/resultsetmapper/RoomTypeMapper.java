package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import ua.onufreiv.hotel.entity.RoomType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yurii on 1/23/17.
 */
public class RoomTypeMapper implements ResultSetMapper<RoomType> {
    @Override
    public RoomType map(ResultSet rs) throws SQLException {
        RoomType roomType = new RoomType();
        roomType.setId(rs.getInt("idRoomType"));
        roomType.setType(rs.getString("type"));
        roomType.setDescription(rs.getString("descr"));
        roomType.setPrice(rs.getInt("price"));
        roomType.setMaxPerson(rs.getInt("maxPerson"));
        return roomType;
    }
}
