package ua.onufreiv.hotel.persistence.query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.onufreiv.hotel.persistence.query.resultsetmapper.ResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by yurii on 1/30/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SqlQuerySelectTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    @Mock
    private ResultSetMapper mockResultSetMapper;
    @Mock
    private Object mockObject;

    private SqlQuerySelect querySelect;
    private String tableName;

    @Before
    public void setUp() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSetMapper.map(mockResultSet)).thenReturn(mockObject);

        tableName = "test";
        querySelect = new SqlQuerySelect(tableName);
    }

    @Test
    public void fromAndGetTableName() throws Exception {
        querySelect.from(tableName);
        assertEquals("Table names don't match", tableName, querySelect.getTableName());
    }

    @Test
    public void columns() throws Exception {
        String[] columns = {"a", "b", "c"};
        querySelect.columns(columns);
        assertArrayEquals("Columns list don't match", columns, querySelect.getColumns());
    }

    @Test
    public void selectObject() throws Exception {
        when(mockResultSet.next()).thenReturn(true);

        querySelect.columns("a", "b", "c");
        Object actualObject = querySelect.selectObject(mockConnection, mockResultSetMapper);

        verify(mockConnection, times(1)).prepareStatement(querySelect.getSqlStatement());
        verifyNoMoreInteractions(mockConnection);

        verify(mockPreparedStatement, times(1)).executeQuery();

        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).close();
        verifyNoMoreInteractions(mockResultSet);

        verify(mockResultSetMapper, times(1)).map(mockResultSet);
        verifyNoMoreInteractions(mockResultSet);

        assertTrue(actualObject == mockObject);
    }

    @Test
    public void selectAll() throws Exception {
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        querySelect.columns("a", "b", "c");
        List<Object> actualObjectList = querySelect.selectAll(mockConnection, mockResultSetMapper);

        verify(mockConnection, times(1)).prepareStatement(querySelect.getSqlStatement());
        verifyNoMoreInteractions(mockConnection);

        verify(mockPreparedStatement, times(1)).executeQuery();

        verify(mockResultSet, times(3)).next();
        verify(mockResultSet, times(1)).close();
        verifyNoMoreInteractions(mockResultSet);

        verify(mockResultSetMapper, times(2)).map(mockResultSet);
        verifyNoMoreInteractions(mockResultSet);

        assertTrue(actualObjectList.size() == 2);
    }

    @Test
    public void where() throws Exception {
        SqlQueryWhereWrapper whereWrapper = querySelect.where();
        assertEquals("Query objects don't match", querySelect, whereWrapper.getBaseQuery());
    }

    @Test
    public void getSqlStatement() throws Exception {
        querySelect.columns("a", "b", "c");

        String expectedSql = "SELECT a, b, c FROM TEST;";
        assertEquals("Sql statements don't match.", expectedSql, querySelect.getSqlStatement());
    }

    @Test
    public void fillPreparedStatement() throws Exception {
        PreparedStatement statement = mockConnection.prepareStatement(querySelect.getSqlStatement());
        int actualAmount = querySelect.fillPreparedStatement(statement);

        verifyNoMoreInteractions(statement);
        assertTrue(actualAmount == 0);
    }

}