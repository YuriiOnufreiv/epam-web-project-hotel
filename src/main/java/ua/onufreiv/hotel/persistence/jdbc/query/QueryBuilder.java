package ua.onufreiv.hotel.persistence.jdbc.query;

/**
 * Created by yurii on 1/16/17.
 */
public class QueryBuilder<T> {
    private String tableName;

    public QueryBuilder(String tableName) {
        this.tableName = tableName;
    }

    public SqlQuerySelect<T> select() {
        return new SqlQuerySelect<T>(tableName);
    }

    public SqlQueryInsert insert() {
        return new SqlQueryInsert(tableName);
    }

    public SqlQueryUpdate update() {
        return new SqlQueryUpdate(tableName);
    }

    public SqlQueryDelete delete() {
        return new SqlQueryDelete(tableName);
    }
}
