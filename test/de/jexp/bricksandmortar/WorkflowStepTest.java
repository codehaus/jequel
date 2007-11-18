package de.jexp.bricksandmortar;

import junit.framework.TestCase;

import javax.sql.DataSource;

/**
 * Created by mh14 on 05.07.2007 21:39:33
 */
public abstract class WorkflowStepTest<T extends WorkflowStep> extends TestCase {
    private static DataSource dataSource;

    protected T step;

    protected WorkflowContext runAndCheckResult() {
        final WorkflowContext ctx = runStep();
        WorkflowStepTestUtils.checkResult(ctx, step);
        return ctx;
    }

    protected WorkflowContext runStep() {
        final WorkflowContext ctx = new WorkflowContext();
        step.runStep(ctx);
        return ctx;
    }

    protected DataSource getHsqlDataSource() {
        if (dataSource != null) return dataSource;
        dataSource = WorkflowStepTestUtils.createAndSetupHsqlDatasource();
        return dataSource;
    }
}
