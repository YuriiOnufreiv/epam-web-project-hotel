package ua.onufreiv.hotel.persistence.jdbc.query;

/**
 * Created by yurii on 1/16/17.
 */
public class QueryBuilder {
    private String tableName;

    public QueryBuilder(String tableName) {
        this.tableName = tableName;
    }

    public SqlQuerySelect select() {
        return new SqlQuerySelect();
    }

    public SqlQueryInsert insert() {
        return new SqlQueryInsert();
    }

    public SqlQueryUpdate update() {
        return new SqlQueryUpdate();
    }

    public SqlQueryDelete delete() {
        return new SqlQueryDelete();
    }

    @SuppressWarnings("unchecked")
    public <T extends ISqlWhereWrappableQuery> SqlQueryWhereWrapper wrapWithWhereClause(T whereClauseUsable) {
        return new SqlQueryWhereWrapper(whereClauseUsable);
    }
}
