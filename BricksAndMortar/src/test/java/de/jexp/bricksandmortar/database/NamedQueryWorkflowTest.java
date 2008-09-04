package de.jexp.bricksandmortar.database;

import de.jexp.bricksandmortar.execution.ReportWorkflow;
import de.jexp.bricksandmortar.WorkflowStepTestUtils;


public class NamedQueryWorkflowTest extends NamedParameterQueryStepTest {
    private ReportWorkflow reportWorkflow;

    public void testRunNamedWorkflow() {
        step.setQuery("select * from article");
        WorkflowStepTestUtils.setSingleStep(reportWorkflow, step);
        reportWorkflow.runWorkflow();
        WorkflowStepTestUtils.checkResult(reportWorkflow.getWorkflowContext(), step);
    }

    protected void setUp() {
        super.setUp();
        reportWorkflow = new ReportWorkflow();
    }
}