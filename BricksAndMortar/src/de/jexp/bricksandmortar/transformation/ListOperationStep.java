package de.jexp.bricksandmortar.transformation;

import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.NamedWorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mh14 on 07.07.2007 08:01:12
 */
public class ListOperationStep extends NamedWorkflowStep {
    private ListOperation operation = ListOperation.NOOP;

    public void runStep(final WorkflowContext workflowContext) {
        final List<ListStepResult> sources = getListResults(workflowContext);
        final ListStepResult result = performOperation(sources);
        setOnWorkflowContext(workflowContext, result);
    }

    private ListStepResult performOperation(final List<ListStepResult> sources) {
        if (logger.isInfoEnabled())
            logger.info("Executing operation " + operation + " on " + Arrays.asList(getParamNames()));
        return operation.perform(sources);
    }

    private List<ListStepResult> getListResults(final WorkflowContext workflowContext) {
        final List<ListStepResult> listStepResults = new LinkedList<ListStepResult>();
        for (final String paramName : getParamNames()) {
            final ListStepResult listStepResult = workflowContext.getResult(paramName);
            listStepResults.add(listStepResult);
        }
        return listStepResults;
    }

    public ListOperation getOperation() {
        return operation;
    }

    public void setOperation(final ListOperation operation) {
        this.operation = operation;
    }
}

