package ua.onufreiv.hotel.persistence.jdbc.query;

/**
 * Created by yurii on 1/16/17.
 */
public interface SqlQueryWhereWrappable extends SqlQuery {
    SqlQueryWhereWrapper where();
}
