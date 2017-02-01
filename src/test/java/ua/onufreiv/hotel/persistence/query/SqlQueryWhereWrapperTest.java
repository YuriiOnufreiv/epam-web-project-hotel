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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by yurii on 1/30/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SqlQueryWhereWrapperTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    @Mock
    private SqlQueryWhereWrappable mockSqlWhereQuery;
    @Mock
    private ResultSetMapper mockResultSetMapper;
    @Mock
    private Object mockObject;

    private SqlQueryWhereWrapper queryWhereWrapper;

    @Before
    public void setUp() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSetMapper.map(mockResultSet)).thenReturn(mockObject);

        queryWhereWrapper = new SqlQueryWhereWrapper(mockSqlWhereQuery);
    }

    @Test
    public void executeUpdateQuery() throws Exception {
        when(mockSqlWhereQuery.getSqlStatement()).thenReturn("SELECT * FROM test;");
        when(mockPreparedStatement.executeUpdate()).thenReturn(3);

        addColumnsToQuery();
        int actualId = queryWhereWrapper.executeUpdate(mockConnection);

        verify(mockConnection, times(1)).prepareStatement(queryWhereWrapper.getSqlStatement());
        verifyNoMoreInteractions(mockConnection);

        verify(mockPreparedStatement, times(1)).executeUpdate();

        assertTrue(actualId == 3);
    }

    @Test
    public void executeQuery() throws Exception {
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockSqlWhereQuery.getSqlStatement()).thenReturn("SELECT * FROM test;");

        addColumnsToQuery();
        List<Object> actualObjectList = queryWhereWrapper.executeQuery(mockConnection, mockResultSetMapper);

        verify(mockConnection, times(1)).prepareStatement(queryWhereWrapper.getSqlStatement());
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
    public void executeQueryForObject() throws Exception {
        when(mockResultSet.next()).thenReturn(true);
        when(mockSqlWhereQuery.getSqlStatement()).thenReturn("SELECT * FROM test;");

        addColumnsToQuery();
        Object actualObject = queryWhereWrapper.executeQueryForObject(mockConnection, mockResultSetMapper);

        verify(mockConnection, times(1)).prepareStatement(queryWhereWrapper.getSqlStatement());
        verifyNoMoreInteractions(mockConnection);

        verify(mockPreparedStatement, times(1)).executeQuery();

        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).close();
        verifyNoMoreInteractions(mockResultSet);

        verify(mockResultSetMapper, times(1)).map(mockResultSet);
        verifyNoMoreInteractions(mockResultSet);

        assertTrue(actualObject == mockObject);
    }

    // and, column,
    @Test
    public void getSqlStatement() throws Exception {
        addColumnsToQuery();

        assertEquals("Values size don't match.", 7, queryWhereWrapper.getValues().size());
        assertEquals("Where clause don't match.",
                " WHERE a = ? AND b = ? AND c = ? AND d < DATE(?) AND e > DATE(?) " +
                        "AND f IN (?, ?, ?, ?, ?, ?) AND g NOT IN (?, ?, ?, ?, ?, ?, ?)",
                queryWhereWrapper.getWhereClause().toString());
    }

    @Test
    public void fillPreparedStatement() throws Exception {
        when(mockSqlWhereQuery.getSqlStatement()).thenReturn(";");
        when(mockSqlWhereQuery.fillPreparedStatement(mockPreparedStatement)).thenReturn(3);

        addColumnsToQuery();

        PreparedStatement statement = mockConnection.prepareStatement(queryWhereWrapper.getSqlStatement());
        int actualAmount = queryWhereWrapper.fillPreparedStatement(statement);

        assertTrue(actualAmount == 10);
    }

    private void addColumnsToQuery() {
        queryWhereWrapper.column("a").isEqualTo(true)
                .and().column("b").isEqualTo("string")
                .and().column("c").isEqualTo(1)
                .and().column("d").isLessThan(new Date())
                .and().column("e").isGreaterThan(new Date())
                .and().column("f").in(new ArrayList<>())
                .and().column("g").notIn(new ArrayList<>());
    }

}