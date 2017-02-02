package ua.onufreiv.hotel.persistence.query;

/**
 * An interface that represents possible SQL query that could appear with the
 * WHERE clause in it.
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/16/17.
 */
public interface SqlQueryWhereWrappable extends SqlQuery {
    /**
     * Wraps SqlQuery with the {@link SqlQueryWhereWrapper} instance
     *
     * @return SqlQuery wrapped with {@code SqlQueryWhereWrapper}
     */
    SqlQueryWhereWrapper where();
}
