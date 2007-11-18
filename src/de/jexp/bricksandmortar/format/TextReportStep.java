package de.jexp.bricksandmortar.format;

import de.jexp.bricksandmortar.util.TextUtils;
import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.results.TextStepResult;
import de.jexp.bricksandmortar.NamedWorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;

import java.util.*;

/**
 * Created by mh14 on 06.07.2007 11:30:09
 */
public class TextReportStep extends NamedWorkflowStep {
    private String resultName;

    public void runStep(final WorkflowContext workflowContext) {
        final ListStepResult result = (ListStepResult) workflowContext.getResult(getResultName());
        setOnWorkflowContext(workflowContext, processResult(result));
    }
    protected TextStepResult processResult(final ListStepResult result) {
        final Collection<Map<String, ?>> list = result.getResult();
        final StringBuilder sb = createText(list);
        return new TextStepResult(getName(),sb);
    }

    protected StringBuilder createText(final Collection<Map<String, ?>> list) {
        return TextUtils.createText(list);
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(final String resultName) {
        this.resultName = resultName;
    }
}
