package de.jexp.bricksandmortar.output;

import de.jexp.bricksandmortar.output.FileStepResultWriter;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.StepResult;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

public class TextFileWriter extends FileStepResultWriter {
    public TextFileWriter(final WorkflowContext workflowContext) {
        super(workflowContext);
    }

    protected void writeStepResult(final String paramName, final int count, final StepResult stepResult) throws IOException {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(createFileForWriting(paramName, count)));
            writer.println("StepResult: " + paramName);
            writer.println(stepResult.textify());
            writer.flush();
        } finally {
            if (writer != null) writer.close();
        }
    }
}
