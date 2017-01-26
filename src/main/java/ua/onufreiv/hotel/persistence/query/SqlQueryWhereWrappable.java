package ua.onufreiv.hotel.persistence.query;

/**
 * Created by yurii on 1/16/17.
 */
public interface SqlQueryWhereWrappable extends SqlQuery {
    SqlQueryWhereWrapper where();
}
