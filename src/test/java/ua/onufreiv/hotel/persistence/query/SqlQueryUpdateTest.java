package ua.onufreiv.hotel.persistence.query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by yurii on 1/30/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SqlQueryUpdateTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;

    private SqlQueryUpdate queryUpdate;
    private String tableName;

    @Before
    public void setUp() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(3);

        tableName = "test";
        queryUpdate = new SqlQueryUpdate(tableName);
    }

    @Test
    public void tableAndGetTableName() throws Exception {
        queryUpdate.table(tableName);
        assertEquals("Table names don't match", tableName, queryUpdate.getTableName());
    }

    @Test
    public void setAndGetValues() throws Exception {
        Map<String, Object> values = new HashMap<>();
        values.put("first", new Object());
        values.put("second", new Object());
        values.put("third", new Object());

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            queryUpdate.set(entry.getKey(), entry.getValue());
        }

        assertEquals("Sizes of map don't match.", queryUpdate.getValues().size(), values.size());
        assertEquals("Content of maps don't match.", values, queryUpdate.getValues());
    }

    @Test
    public void executeUpdate() throws Exception {
        queryUpdate.set("a", 1)
                .set("b", 2.0)
                .set("c", false);

        int actualAmount = queryUpdate.executeUpdate(mockConnection);

        verify(mockConnection, times(1)).prepareStatement(queryUpdate.getSqlStatement());
        verifyNoMoreInteractions(mockConnection);

        verify(mockPreparedStatement, times(1)).executeUpdate();

        assertTrue(actualAmount == 3);
    }

    @Test
    public void where() throws Exception {
        SqlQueryWhereWrapper whereWrapper = queryUpdate.where();
        assertEquals("Query objects don't match", queryUpdate, whereWrapper.getBaseQuery());
    }

    @Test
    public void getSqlStatement() throws Exception {
        queryUpdate.set("a", 1)
                .set("b", 2.0)
                .set("c", false);

        String expectedSql = "UPDATE TEST SET a = ?, b = ?, c = ?;";
        assertEquals("Sql statements don't match.", expectedSql, queryUpdate.getSqlStatement());
    }

    @Test
    public void fillPreparedStatement() throws Exception {
        queryUpdate.set("a", 1)
                .set("b", 2.0)
                .set("c", false);

        PreparedStatement statement = mockConnection.prepareStatement(queryUpdate.getSqlStatement());
        int actualAmount = queryUpdate.fillPreparedStatement(statement);

        verify(statement).setObject(1, 1);
        verify(statement).setObject(2, 2.0);
        verify(statement).setObject(3, false);
        verifyNoMoreInteractions(statement);
        assertTrue(actualAmount == 3);
    }

}