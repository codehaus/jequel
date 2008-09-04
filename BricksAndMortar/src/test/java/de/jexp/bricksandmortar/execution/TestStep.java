package de.jexp.bricksandmortar.execution;

import de.jexp.bricksandmortar.WorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.StepResult;
import de.jexp.bricksandmortar.StepResultWriter;

public class TestStep implements WorkflowStep {
    private boolean fail;

    public void runStep(final WorkflowContext workflowContext) {
        if (fail) throw new RuntimeException("TestStep failed");
        workflowContext.addResult(new StepResult<Class>(getName(), Class.class, getClass()));
    }

    public String getName() {
        return getClass().getName();
    }

    public void setLogWriter(final StepResultWriter logWriter) {
    }
    public void setFail(boolean fail) {
        this.fail=fail;
    }
}
