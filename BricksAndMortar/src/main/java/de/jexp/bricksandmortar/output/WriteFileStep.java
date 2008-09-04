package de.jexp.bricksandmortar.output;

import de.jexp.bricksandmortar.NamedWorkflowStep;
import de.jexp.bricksandmortar.StepResultWriter;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.results.ErrorStepResult;

import java.io.IOException;

public class WriteFileStep extends NamedWorkflowStep {
    private String fileName;

    public void runStep(final WorkflowContext workflowContext) {
        final StepResultWriter stepResultWriter = createResultWriter(workflowContext);
        int count = 0;
        for (final String paramName : getParamNames()) {
            try {
                stepResultWriter.writeFile(paramName, count++);
            } catch (IOException e) {
                if (logger.isErrorEnabled())
                    logger.error("Error creating FileWriter " + stepResultWriter, e);
                setOnWorkflowContext(workflowContext, new ErrorStepResult(getName(), e));
            }
        }
    }

    protected StepResultWriter createResultWriter(final WorkflowContext workflowContext) {
        return new BinaryFileWriter(workflowContext);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }
}
