package de.jexp.bricksandmortar.execution;

import de.jexp.bricksandmortar.*;
import junit.framework.TestCase;

import java.util.Arrays;

import org.springframework.transaction.TransactionStatus;

public class ReportWorkflowTest extends TestCase {
    private ReportWorkflow reportWorkflow;

    public void testNoSteps() {
        final int count = reportWorkflow.runWorkflow();
        final WorkflowContext workflowContext = reportWorkflow.getWorkflowContext();
        assertTrue("empty context", workflowContext.isEmpty());
        assertEquals("no steps", 0, count);
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
        assertEquals("testDir"+reportWorkflow.date(),reportWorkflow.getDirectory());
    }

    public void testException() {
        final RuntimeException runtimeException = new RuntimeException();
        final ReportWorkflow workflow = new ReportWorkflow() {
            protected void handleException(final Throwable t, final TransactionStatus status) {
                assertSame(runtimeException, t);
            }
        };
        workflow.setSteps(Arrays.asList(new WorkflowStep() {
            public void runStep(final WorkflowContext workflowContext) {
                throw runtimeException;
            }

            public String getName() {
                return "Exception";
            }

            public void setLogWriter(final StepResultWriter logWriter) {
            }
        }));
        assertEquals(0,workflow.runWorkflow());
    }

    protected void setUp() {
        reportWorkflow = new ReportWorkflow();
    }

    protected void tearDown() {
    }
}
