package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IFormDao;
import ua.onufreiv.hotel.entities.Form;
import ua.onufreiv.hotel.entities.User;
import ua.onufreiv.hotel.jdbc.JdbcDatabase;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 1/1/17.
 */
public class MySqlFormDao implements IFormDao {
    private static final String QUERY_INSERT = "INSERT INTO FORM (userFK, personAmount, roomTypeFK, startDate, endDate, processed) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String QUERY_SELECT_ALL = "SELECT * FROM FORM";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM FORM WHERE idForm = ?";
    private static final String QUERY_UPDATE = "UPDATE FORM SET userFK = ?, personAmount = ?, roomTypeFK = ?, startDate = ?, endDate = ?, processed = ?  WHERE idForm = ?";
    private static final String QUERY_DELETE = "DELETE FROM FORM WHERE idForm = ?";

    private final JdbcDatabase jdbcDatabase;

    public MySqlFormDao(JdbcDatabase jdbcDatabase) {
        this.jdbcDatabase = jdbcDatabase;
    }

    @Override
    public int insert(Form form) {
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            id = jdbcQuery.insert(QUERY_INSERT,
                    form.getUserId(),
                    form.getPersonAmount(),
                    form.getRoomTypeId(),
                    form.getStartDate(),
                    form.getEndDate(),
                    form.getProcessed());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return id;
    }

    @Override
    public boolean delete(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            return jdbcQuery.delete(QUERY_DELETE, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Form find(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                return DtoMapper.ResultSet.toForm(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Form> findAll() {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_ALL);
            List<Form> users = new ArrayList<>();
            while (rs.next()) {
                users.add(DtoMapper.ResultSet.toForm(rs));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Form form) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            return jdbcQuery.update(QUERY_UPDATE,
                    form.getUserId(),
                    form.getPersonAmount(),
                    form.getRoomTypeId(),
                    form.getStartDate(),
                    form.getEndDate(),
                    form.getProcessed(),
                    form.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
