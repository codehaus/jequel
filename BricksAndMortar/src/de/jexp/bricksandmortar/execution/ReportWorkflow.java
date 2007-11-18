/*
 * ReportWorkflow.java
 *
 * Created on 5. Juli 2007, 13:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package de.jexp.bricksandmortar.execution;

import de.jexp.bricksandmortar.WorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.output.LogStepResultWriter;

import java.util.Collections;
import java.util.List;

public class ReportWorkflow {
    private List<? extends WorkflowStep> steps = Collections.emptyList();
    private final WorkflowContext workflowContext;

    public ReportWorkflow() {
        this.workflowContext = new WorkflowContext();
    }

    public List<? extends WorkflowStep> getSteps() {
        return steps;
    }

    public void setSteps(final List<? extends WorkflowStep> steps) {
        this.steps = steps;
    }

    public int runWorkflow() {
        int successCount = 0;
        for (final WorkflowStep step : steps) {
            runStep(step);
            successCount++;
        }
        return successCount;
    }

    protected void runStep(final WorkflowStep step) {
        final WorkflowContext ctx = getWorkflowContext();
        step.setLogWriter(new LogStepResultWriter(ctx));
        step.runStep(ctx);
    }

    public WorkflowContext getWorkflowContext() {
        return workflowContext;
    }

    public void setDirectory(final String directory) {
        this.workflowContext.setDirectory(directory);
    }

    public String getDirectory() {
        return workflowContext.getDirectory();
    }

    public void setLogResults(final boolean logResults) {
        this.workflowContext.setLogResults(logResults);
    }
}
