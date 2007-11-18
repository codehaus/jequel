package de.jexp.bricksandmortar.database;

import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.WorkflowStepTest;
import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.results.UpdateStepResult;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mh14 @ jexp.de
 * @since 15.11.2007 08:05:35 (c) 2007 jexp.de
 */
public class NamedParameterUpdateStepTest extends WorkflowStepTest<NamedParameterUpdateStep> {
    private static final String TEST_STEP_NAME = "testUpdateStep";
    private Connection connection;

    public void testQueryStepDelete() {
        step.setQuery("delete from article");
        runAndCheckResult(1, "select count(*) from article", 0);
    }

    protected WorkflowContext runAndCheckResult(final int count, final String checkQuery, final Object expectedResult) {
        final WorkflowContext ctx = runStep();
        return checkResult(ctx, count, checkQuery, expectedResult);
    }

    private WorkflowContext checkResult(final WorkflowContext ctx, final int count, final String checkQuery, final Object expectedResult) {
        final UpdateStepResult result = ctx.getResult(TEST_STEP_NAME);
        assertEquals(count, (int) result.getResult());
        final JdbcOperations jdbcOperations = step.getNamedJdbcTemplate().getJdbcOperations();
        assertEquals(expectedResult, jdbcOperations.queryForObject(checkQuery, expectedResult.getClass()));
        return ctx;
    }

    public void testQueryStepInsertParam() {
        step.setQuery("insert into article (name,price) values(:name,:price)");
        step.setQueryParams(createQueryParams());
        runAndCheckResult(1, "select price from article where name ='Article2'", 10);
    }

    public void testQueryStepBatchParam() {
        step.setQuery("insert into article (name,price) values(:name,:price)");

        step.setParamName("batchTest");
        final WorkflowContext ctx = new WorkflowContext();
        final Map<String, Object> params = createQueryParams();
        final List<Map<String, ?>> result = new ArrayList<Map<String, ?>>();
        result.add(params);
        result.add(params);
        result.add(params);
        ctx.addResult(new ListStepResult("batchTest", result));
        step.runStep(ctx);
        System.out.println("ctx = " + ctx);
        checkResult(ctx, 1, "select count(*) from article", 4);
        assertEquals(1, (int) ctx.<UpdateStepResult>getResult(TEST_STEP_NAME + 1).getResult());
        assertEquals(1, (int) ctx.<UpdateStepResult>getResult(TEST_STEP_NAME + 2).getResult());
    }

    protected Map<String, Object> createQueryParams() {
        final Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("name", "Article2");
        queryParams.put("price", 10);
        return queryParams;
    }

    protected void setUp() throws SQLException {
        step = new NamedParameterUpdateStep();
        step.setBeanName(TEST_STEP_NAME);
        step.setDataSource(createRollbackDataSource());
    }

    protected DataSource createRollbackDataSource() throws SQLException {
        connection = getHsqlDataSource().getConnection();
        connection.setAutoCommit(false);
        return new SingleConnectionDataSource(connection, true);
    }

    protected void tearDown() throws Exception {
        connection.rollback();
        super.tearDown();
    }
}
