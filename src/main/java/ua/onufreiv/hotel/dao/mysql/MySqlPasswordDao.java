package ua.onufreiv.hotel.dao.mysql;

import ua.onufreiv.hotel.dao.IPasswordDao;
import ua.onufreiv.hotel.entities.PasswordHash;
import ua.onufreiv.hotel.jdbc.Database;
import ua.onufreiv.hotel.jdbc.JdbcQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by yurii on 12/23/16.
 */
public class MySqlPasswordDao implements IPasswordDao {
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM PASSWORD WHERE idPassword = ?";
    private static final String QUERY_INSERT = "INSERT INTO PASSWORD (pwdhash) VALUES (?)";

    public MySqlPasswordDao() {
    }

    @Override
    public int insert(PasswordHash passwordHash) {
        Connection connection = Database.getInstance().getConnection();
        int id = 0;
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            id = jdbcQuery.insert(connection, QUERY_INSERT,
                    passwordHash.getPwdHash());
        } finally {
            Database.getInstance().closeConnection(connection);
        }
        return id;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public PasswordHash find(int id) {
        Connection connection = Database.getInstance().getConnection();
        try {
            JdbcQuery jdbcQuery = new JdbcQuery();
            ResultSet rs = jdbcQuery.select(connection, QUERY_SELECT_BY_ID, id);
            if(rs.next()) {
                PasswordHash passwordHash = DtoMapper.ResultSet.toPasswordHash(rs);
                Database.getInstance().closeConnection(connection);
                return passwordHash;
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
