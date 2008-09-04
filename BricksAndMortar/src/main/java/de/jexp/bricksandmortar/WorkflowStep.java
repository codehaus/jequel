/*
 * WorkflowStep.java
 *
 * Created on 5. Juli 2007, 13:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package de.jexp.bricksandmortar;

/**
 * @author mh14
 */
public interface WorkflowStep {
    void runStep(WorkflowContext workflowContext);

    String getName();

    void setLogWriter(final StepResultWriter logWriter);
}
