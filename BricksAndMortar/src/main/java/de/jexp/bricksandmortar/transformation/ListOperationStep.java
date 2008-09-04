package de.jexp.bricksandmortar.transformation;

import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.NamedWorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by mh14 on 07.07.2007 08:01:12
 */
public class ListOperationStep extends NamedWorkflowStep {
    private String[] keys;
    private ListOperation operation = ListOperation.NOOP;
    private boolean distinct;
    public void runStep(final WorkflowContext workflowContext) {
        final Collection<ListStepResult> sources = filterParams(workflowContext, getParamNames(), ListStepResult.class).values();
        final ListStepResult result = performOperation(sources);
        setOnWorkflowContext(workflowContext, result);
    }

    private ListStepResult performOperation(final Collection<ListStepResult> sources) {
        if (logger.isInfoEnabled())
            logger.info("Executing operation " + operation + " on " + Arrays.asList(getParamNames()));
        return operation.perform(sources,distinct, keys);
    }

    public ListOperation getOperation() {
        return operation;
    }

    public void setOperation(final ListOperation operation) {
        this.operation = operation;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(final String[] keys) {
        this.keys = keys;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(final boolean distinct) {
        this.distinct = distinct;
    }
}

