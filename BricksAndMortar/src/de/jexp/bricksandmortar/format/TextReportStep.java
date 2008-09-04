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
    private String header;
    private String footer;
    public void runStep(final WorkflowContext workflowContext) {
        final Collection<ListStepResult> listStepResults = filterParams(workflowContext, getParamNames(), ListStepResult.class);
        for (ListStepResult listStepResult : listStepResults) {
            setOnWorkflowContext(workflowContext, formatAsText(listStepResult));
        }
    }
    protected TextStepResult formatAsText(final ListStepResult result) {
        final Collection<Map<String, ?>> list = result.getResult();
        final StringBuilder sb = createText(list);
        return new TextStepResult(getName(),sb,header,footer);
    }

    protected StringBuilder createText(final Collection<Map<String, ?>> list) {
        return TextUtils.createText(list);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(final String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(final String footer) {
        this.footer = footer;
    }
}
