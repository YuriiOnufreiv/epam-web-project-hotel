package ua.onufreiv.hotel.persistence.jdbc.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by yurii on 1/16/17.
 */
public interface SqlQuery {
    String getSqlStatement();

    int fillPreparedStatement(PreparedStatement preparedStatement) throws SQLException;
}