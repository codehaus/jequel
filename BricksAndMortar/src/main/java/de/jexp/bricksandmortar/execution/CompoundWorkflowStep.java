package de.jexp.bricksandmortar.execution;

import de.jexp.bricksandmortar.StepResultWriter;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.WorkflowStep;
import org.springframework.beans.factory.BeanNameAware;

/**
 * @author mhu@salt-solutions.de
 * @copyright (c) Salt Solutions GmbH 2006
 * @since 12.12.2007 14:54:06
 */
public class CompoundWorkflowStep extends WorkflowRunner implements WorkflowStep, BeanNameAware {
    private String name;
    private StepResultWriter logWriter;

    public void runStep(final WorkflowContext workflowContext) {
        for (final WorkflowStep step : steps) {
            step.setLogWriter(logWriter);
            runStep(step, workflowContext);
        }
    }

    public String getName() {
        return name;
    }

    public void setLogWriter(final StepResultWriter logWriter) {
        this.logWriter = logWriter;
    }

    public void setBeanName(final String name) {
        this.name = name;
    }
}
