package de.jexp.bricksandmortar.format;

import de.jexp.bricksandmortar.util.TextUtils;
import de.jexp.bricksandmortar.results.TextStepResult;
import de.jexp.bricksandmortar.NamedWorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.StepResult;

import java.util.*;
import java.io.StringWriter;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.VelocityContext;

/**
 * Created by mh14 on 06.07.2007 11:30:09
 */
public class VelocityReportStep extends NamedWorkflowStep {
    private VelocityEngine velocityEngine;
    private String template;

    public void runStep(final WorkflowContext workflowContext) {
        final Map<String, StepResult> listStepResults = filterParams(workflowContext, getParamNames(), StepResult.class);
        final String renderedText = renderTemplate(listStepResults);
        final TextStepResult stepResult = new TextStepResult(getName(), renderedText);
        setOnWorkflowContext(workflowContext,stepResult);
    }

    private String renderTemplate(final Map<String, StepResult> stepResults) {
        try {
        final StringWriter writer = new StringWriter();
        final VelocityContext ctx = new VelocityContext(stepResults);
        velocityEngine.mergeTemplate(getTemplate(), ctx,writer);
        return writer.toString();
        } catch(Exception e) {
            throw new RuntimeException("Error rendering velocity template "+getTemplate(),e);
        }

    }
    public void setTemplate(final String template) {
        this.template = template;
    }

    public void setVelocityEngine(final VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public String getTemplate() {
        return template;
    }
}