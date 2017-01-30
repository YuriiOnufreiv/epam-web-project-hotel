package ua.onufreiv.hotel.persistence.query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by yurii on 1/30/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SqlQueryDeleteTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;

    private SqlQueryDelete queryDelete;
    private String tableName;

    @Before
    public void setUp() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        tableName = "test";
        queryDelete = new SqlQueryDelete().from(tableName);
    }

    @Test
    public void fromAndGetTable() throws Exception {
        assertEquals("Table names don't match", tableName, queryDelete.getTableName());
    }

    @Test
    public void execute() throws Exception {
        int actualId = queryDelete.execute(mockConnection);

        verify(mockConnection, times(1)).prepareStatement(queryDelete.getSqlStatement());
        verifyNoMoreInteractions(mockConnection);

        verify(mockPreparedStatement, times(1)).executeUpdate();

        assertTrue(actualId == 1);
    }

    @Test
    public void where() throws Exception {
        SqlQueryWhereWrapper whereWrapper = queryDelete.where();
        assertEquals("Query objects don't match", queryDelete, whereWrapper.getBaseQuery());
    }

    @Test
    public void getSqlStatement() throws Exception {
        String expectedSql = "DELETE FROM TEST;";
        assertEquals("Sql statements don't match.", expectedSql, queryDelete.getSqlStatement());
    }

    @Test
    public void fillPreparedStatement() throws Exception {
        PreparedStatement statement = mockConnection.prepareStatement(queryDelete.getSqlStatement());
        int actualAmount = queryDelete.fillPreparedStatement(statement);

        verifyNoMoreInteractions(statement);
        assertTrue(actualAmount == 0);
    }

}