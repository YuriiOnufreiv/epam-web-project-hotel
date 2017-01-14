package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IBillDao;
import ua.onufreiv.hotel.entities.Bill;
import ua.onufreiv.hotel.jdbc.Database;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/10/17.
 */
public class MySqlBillDao implements IBillDao {
    private static final String QUERY_INSERT = "INSERT INTO BILL (bookRequestFK, roomFK, price) VALUES (?, ?, ?)";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM BILL";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM BILL WHERE idBill = ?";
    private static final String QUERY_SELECT_BY_BOOK_REQUEST_ID = "SELECT * FROM BILL WHERE bookRequestFK = ?";
    private static final String QUERY_UPDATE = "UPDATE BILL SET bookRequestFK = ?, roomFK = ?, price = ? WHERE idBill = ?";
    private static final String QUERY_DELETE = "DELETE FROM BILL WHERE idBill = ?";
    private static MySqlBillDao instance;

    private MySqlBillDao() {
    }

    public static synchronized MySqlBillDao getInstance() {
        if (instance == null) {
            instance = new MySqlBillDao();
        }
        return instance;
    }

    @Override
    public int insert(Bill bill) {
        Connection connection = Database.getInstance().getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        int id = jdbcQuery.insert(connection, QUERY_INSERT,
                bill.getBookRequestId(),
                bill.getRoomId(),
                bill.getTotalPrice());
        Database.getInstance().closeConnection(connection);
        return id;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = Database.getInstance().getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean result = jdbcQuery.delete(connection, QUERY_DELETE, id);
        Database.getInstance().closeConnection(connection);
        return result;
    }

    @Override
    public Bill find(int id) {
        Connection connection = Database.getInstance().getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if (rs.next()) {
                Bill bill = DtoMapper.ResultSet.toBill(rs);
                Database.getInstance().closeConnection(connection);
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Bill> findAll() {
        Connection connection = Database.getInstance().getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_ALL);
            List<Bill> bills = new ArrayList<>();
            while (rs.next()) {
                bills.add(DtoMapper.ResultSet.toBill(rs));
            }
            Database.getInstance().closeConnection(connection);
            return bills;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Bill bill) {
        Connection connection = Database.getInstance().getConnection();
        JdbcQuery jdbcQuery = new JdbcQuery();
        boolean result = jdbcQuery.update(connection, QUERY_UPDATE,
                bill.getBookRequestId(),
                bill.getRoomId(),
                bill.getTotalPrice(),
                bill.getId());
        Database.getInstance().closeConnection(connection);
        return result;
    }

    @Override
    public Bill findByBookRequestId(int id) {
        Connection connection = Database.getInstance().getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_BOOK_REQUEST_ID, id);
            if (rs.next()) {
                Bill bill = DtoMapper.ResultSet.toBill(rs);
                Database.getInstance().closeConnection(connection);
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
