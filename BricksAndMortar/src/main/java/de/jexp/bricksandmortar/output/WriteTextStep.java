package de.jexp.bricksandmortar.output;

import de.jexp.bricksandmortar.StepResult;
import de.jexp.bricksandmortar.StepResultWriter;
import de.jexp.bricksandmortar.WorkflowContext;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author mh14 @ jexp.de
 * @since 15.11.2007 00:39:50 (c) 2007 jexp.de
 */
public class WriteTextStep extends WriteFileStep {

    protected StepResultWriter createResultWriter(final WorkflowContext workflowContext) {
        return new FileStepResultWriter(workflowContext) {
            protected String createFileName(final String paramName, final int count) {
                if (getFileName() != null) {
                    return getFileName() + ((count > 0) ? count : "");
                }
                return super.createFileName(paramName, 0);
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
        };
    }

}
