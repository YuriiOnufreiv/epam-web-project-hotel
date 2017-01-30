package ua.onufreiv.hotel.persistence.query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

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

    private SqlQueryWhereWrapper queryWhereWrapper;
    private String tableName;

    @Before
    public void setUp() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        tableName = "test";
        queryWhereWrapper = new SqlQueryWhereWrapper(mockSqlWhereQuery);
    }

    // todo
    @Test
    public void executeUpdateQueryUpdate() throws Exception {

    }

    // todo
    @Test
    public void executeUpdateQueryDelete() throws Exception {

    }

    // todo
    @Test
    public void executeQuery() throws Exception {
    }

    // todo
    @Test
    public void executeQueryForObject() throws Exception {

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