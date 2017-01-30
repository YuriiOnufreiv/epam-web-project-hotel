package ua.onufreiv.hotel.persistence.query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by yurii on 1/29/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SqlQueryInsertTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private SqlQueryInsert queryInsert;
    private String tableName;

    @Before
    public void setUp() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.getInt(1)).thenReturn(2);

        queryInsert = new SqlQueryInsert();
        tableName = "test";
    }

    @Test
    public void intoAndGetTableName() throws Exception {
        queryInsert.into(tableName);
        assertEquals("Table names don't match", tableName, queryInsert.getTableName());
    }

    @Test
    public void valueAndGetValues() throws Exception {
        Map<String, Object> values = new HashMap<>();
        values.put("first", new Object());
        values.put("second", new Object());
        values.put("third", new Object());

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            queryInsert.value(entry.getKey(), entry.getValue());
        }

        assertEquals("Sizes of map don't match.", queryInsert.getValues().size(), values.size());
        assertEquals("Content of maps don't match.", values, queryInsert.getValues());
    }

    @Test
    public void execute() throws Exception {
        queryInsert.into(tableName)
                .value("a", 1)
                .value("b", 2.0)
                .value("c", false);

        int actualId = queryInsert.execute(mockConnection);

        verify(mockConnection, times(1)).prepareStatement(queryInsert.getSqlStatement(), Statement.RETURN_GENERATED_KEYS);
        verifyNoMoreInteractions(mockConnection);

        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).getGeneratedKeys();

        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt(1);
        verify(mockResultSet, times(1)).close();
        verifyNoMoreInteractions(mockResultSet);

        assertTrue(actualId == 2);
    }

    @Test
    public void getSqlStatement() throws Exception {
        queryInsert.into(tableName)
                .value("a", 1)
                .value("b", 2.0)
                .value("c", true);

        String expectedSql = "INSERT INTO TEST (a, b, c) VALUES (?, ?, ?);";
        assertEquals("Sql statements don't match.", expectedSql, queryInsert.getSqlStatement());
    }

    @Test
    public void fillPreparedStatement() throws Exception {
        queryInsert.into(tableName)
                .value("a", 1)
                .value("b", 2.0)
                .value("c", false);

        PreparedStatement statement = mockConnection.prepareStatement(queryInsert.getSqlStatement());
        int actualAmount = queryInsert.fillPreparedStatement(statement);

        verify(statement).setObject(1, 1);
        verify(statement).setObject(2, 2.0);
        verify(statement).setObject(3, false);
        verifyNoMoreInteractions(statement);
        assertTrue(actualAmount == 3);
    }

}