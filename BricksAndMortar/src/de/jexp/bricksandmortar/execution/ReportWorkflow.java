/*
 * ReportWorkflow.java
 *
 * Created on 5. Juli 2007, 13:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package de.jexp.bricksandmortar.execution;

import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.WorkflowStep;
import de.jexp.bricksandmortar.mail.SendMailStep;
import de.jexp.bricksandmortar.output.LogStepResultWriter;
import de.jexp.bricksandmortar.results.ErrorStepResult;
import de.jexp.bricksandmortar.results.TextStepResult;
import org.springframework.beans.factory.BeanNameAware;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ReportWorkflow implements BeanNameAware {
    private List<? extends WorkflowStep> steps = Collections.emptyList();
    private final WorkflowContext workflowContext;
    private String name;
    private SendMailStep errorMail;

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
            try {
                runStep(step);
                successCount++;
            } catch (final Throwable t) {
                handleException(t);
                break;
            }
        }
        return successCount;
    }

    protected void handleException(final Throwable t) {
        t.printStackTrace();
        final SendMailStep errorMailSender = getErrorMail();
        if (errorMailSender != null) {
            final ErrorStepResult error = new ErrorStepResult(getName(), t);
            final TextStepResult errorText = new TextStepResult("Error executing workflow "+getName(),error.textify());
            errorMailSender.sendTextMessage(Arrays.asList(errorText));
        }
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
        this.workflowContext.setDirectory(directory + date());
    }

    public String date() {
        return new SimpleDateFormat("_yyyyMMdd_HHmmss").format(new Date());
    }

    public String getDirectory() {
        return workflowContext.getDirectory();
    }

    public void setLogResults(final boolean logResults) {
        this.workflowContext.setLogResults(logResults);
    }

    public void setBeanName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public SendMailStep getErrorMail() {
        return errorMail;
    }

    public void setErrorMail(final SendMailStep errorMail) {
        this.errorMail = errorMail;
    }
}
