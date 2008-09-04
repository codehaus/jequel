package de.jexp.bricksandmortar.execution;

import de.jexp.bricksandmortar.WorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.output.LogStepResultWriter;

import java.util.List;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author mhu@salt-solutions.de
 * @copyright (c) Salt Solutions GmbH 2006
 * @since 12.12.2007 14:52:39
 */
public class WorkflowRunner {
    protected static final Log log = LogFactory.getLog(WorkflowRunner.class);


    protected List<? extends WorkflowStep> steps = Collections.emptyList();

    public List<? extends WorkflowStep> getSteps() {
        return steps;
    }

    public void setSteps(final List<? extends WorkflowStep> steps) {
        this.steps = steps;
    }

    private void logStep(final WorkflowStep step, final boolean skip) {
        if (!log.isInfoEnabled()) return;
        log.info((skip ? "Skipping" : "Running") + " Step: "+step.getName());
    }

    protected void runStep(final WorkflowStep step, final WorkflowContext ctx) {
        final boolean skipStep = ctx.skipStep(step.getName());
        logStep(step, skipStep);
        if (skipStep) {
            return;
        }
        step.setLogWriter(new LogStepResultWriter(ctx));
        step.runStep(ctx);
    }
}
