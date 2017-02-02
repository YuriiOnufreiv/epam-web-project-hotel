package ua.onufreiv.hotel.persistence.query.resultsetmapper;

import ua.onufreiv.hotel.entity.Bill;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for mapping rows of a ResultSet to instance of {@link Bill} class.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/23/17.
 */
public class BillMapper implements ResultSetMapper<Bill> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Bill map(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setId(rs.getInt("idBill"));
        bill.setCreationDate(rs.getTimestamp("creationDateTime"));
        bill.setBookRequestId(rs.getInt("bookRequestFK"));
        bill.setRoomId(rs.getInt("roomFK"));
        bill.setTotalPrice(rs.getLong("price"));
        return bill;
    }
}
