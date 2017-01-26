package ua.onufreiv.hotel.persistence.dao.mysql;

import ua.onufreiv.hotel.entity.UserRole;
import ua.onufreiv.hotel.persistence.ConnectionManager;
import ua.onufreiv.hotel.persistence.dao.IUserRoleDao;
import ua.onufreiv.hotel.persistence.query.QueryBuilder;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.UserRoleMapper;

import java.sql.Connection;
import java.util.List;

/**
 * Created by yurii on 1/8/17.
 */
public class MySqlUserRoleDao implements IUserRoleDao {
    private static final String TABLE_NAME = "user_role";
    private static final String COLUMN_ID_NAME = "idUserRole";
    private static final String COLUMN_ROLE_NAME = "role";

    private static MySqlUserRoleDao instance;
    private final QueryBuilder<UserRole> queryBuilder;

//    private static final String QUERY_INSERT = "INSERT INTO USER_ROLE (role) VALUES (?)";
//    private static final String QUERY_DELETE = "DELETE FROM USER_ROLE WHERE idUserRole = ?";
//    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM USER_ROLE WHERE idUserRole = ?";
//    private static final String QUERY_SELECT_ALL = "SELECT * FROM USER_ROLE";
//    private static final String QUERY_UPDATE = "UPDATE USER_ROLE SET role = ? WHERE idUserRole = ?";

    private MySqlUserRoleDao() {
        queryBuilder = new QueryBuilder<>(TABLE_NAME);
    }

    public static synchronized MySqlUserRoleDao getInstance() {
        if (instance == null) {
            instance = new MySqlUserRoleDao();
        }
        return instance;
    }

    @Override
    public int insert(UserRole userRole) {
        Connection connection = ConnectionManager.getConnection();
        int id = queryBuilder.insert()
                .value(COLUMN_ROLE_NAME, userRole.getRole())
                .execute(connection);
        ConnectionManager.closeConnection(connection);
        return id;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = ConnectionManager.getConnection();
        boolean result = queryBuilder.delete()
                .where()
                .column(COLUMN_ID_NAME).isEqual(id)
                .executeUpdate(connection);
        ConnectionManager.closeConnection(connection);
        return result;
    }

    @Override
    public UserRole find(int id) {
        Connection connection = ConnectionManager.getConnection();
        UserRole userRole = queryBuilder.select()
                .where()
                .column(COLUMN_ID_NAME).isEqual(id)
                .executeQueryForObject(connection, new UserRoleMapper());
        ConnectionManager.closeConnection(connection);
        return userRole;
    }

    @Override
    public List<UserRole> findAll() {
        Connection connection = ConnectionManager.getConnection();
        List<UserRole> bookRequests = queryBuilder.select()
                .selectAll(connection, new UserRoleMapper());
        ConnectionManager.closeConnection(connection);
        return bookRequests;
    }

    @Override
    public boolean update(UserRole userRole) {
        Connection connection = ConnectionManager.getConnection();
        boolean update = queryBuilder.update()
                .set(COLUMN_ROLE_NAME, userRole.getRole())
                .where()
                .column(COLUMN_ID_NAME).isEqual(userRole.getId())
                .executeUpdate(connection);
        ConnectionManager.closeConnection(connection);
        return update;
    }

    @Override
    public boolean isAdmin(int id) {
        UserRole userRole = find(id);
        return userRole != null && userRole.getRole().equalsIgnoreCase("ADMIN");
    }

    @Override
    public boolean isClient(int id) {
        UserRole userRole = find(id);
        return userRole != null && userRole.getRole().equalsIgnoreCase("CLIENT");
    }
}
