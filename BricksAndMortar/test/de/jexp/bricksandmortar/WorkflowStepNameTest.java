package de.jexp.bricksandmortar;

import junit.framework.TestCase;

public abstract class WorkflowStepNameTest extends TestCase {
    private NamedWorkflowStep step;

    public void testQueryStepName() {
        step.setName("name");
        assertEquals("name", step.getName());
    }

    public void testQueryStepBeanName() {
        step.setBeanName("beanName");
        assertEquals("beanName", step.getName());
    }

    protected void setUp() {
        step = new NamedWorkflowStep() {
            public void runStep(final WorkflowContext workflowContext) {
            }
        };
    }
}