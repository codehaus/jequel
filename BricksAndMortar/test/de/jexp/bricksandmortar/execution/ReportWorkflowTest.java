package de.jexp.bricksandmortar.execution;

import de.jexp.bricksandmortar.*;
import junit.framework.TestCase;

public class ReportWorkflowTest extends TestCase {
    private ReportWorkflow reportWorkflow;

    public void testNoSteps() {
        final int count = reportWorkflow.runWorkflow();
        final WorkflowContext workflowContext = reportWorkflow.getWorkflowContext();
        assertTrue("empty context", workflowContext.isEmpty());
        assertEquals("no steps", 0, count);
    }

    private static class TestStep implements WorkflowStep {
        public void runStep(final WorkflowContext workflowContext) {
            workflowContext.addResult(new StepResult<Class>(getName(), Class.class, getClass()));
        }

        public String getName() {
            return getClass().getName();
        }

        public void setLogWriter(final StepResultWriter logWriter) {
        }
    }

    public void testSingleSteps() {
        final TestStep testStep = new TestStep();
        WorkflowStepTestUtils.setSingleStep(reportWorkflow, testStep);
        final int count = reportWorkflow.runWorkflow();
        final WorkflowContext workflowContext = reportWorkflow.getWorkflowContext();
        assertEquals("one steps", 1, count);
        assertFalse("non empty context", workflowContext.isEmpty());
        final StepResult<Class> result = workflowContext.getResult(testStep.getName());
        assertEquals("TestClass in ctx", testStep.getClass(), result.getResult());
    }

    public void testDirectory() {
        reportWorkflow.setDirectory("testDir");
    }

    protected void setUp() {
        reportWorkflow = new ReportWorkflow();
    }

    protected void tearDown() {
    }
}
