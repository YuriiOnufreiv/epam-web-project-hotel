package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IPasswordDao;
import ua.onufreiv.hotel.entities.PasswordHash;
import ua.onufreiv.hotel.jdbc.JdbcDatabase;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlPasswordDao implements IPasswordDao {
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM PASSWORD WHERE idPassword = ?";
    private static final String QUERY_INSERT = "INSERT INTO PASSWORD (pwdhash) VALUES (?)";

    private final JdbcDatabase jdbcDatabase;

    public MySqlPasswordDao(JdbcDatabase jdbcDatabase) {
        this.jdbcDatabase = jdbcDatabase;
    }

    @Override
    public int insert(PasswordHash passwordHash) {
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            id = jdbcQuery.insert(QUERY_INSERT,
                    passwordHash.getPwdHash());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return id;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public PasswordHash find(int id) {
        try {
            JdbcQuery jdbcQuery = new JdbcQuery(jdbcDatabase);
            ResultSet rs = jdbcQuery.select(QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                return DtoMapper.ResultSet.toPasswordHash(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PasswordHash> findAll() {
        return null;
    }

    @Override
    public boolean update(PasswordHash passwordHash) {
        return false;
    }
}
