package de.jexp.bricksandmortar.output;

import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.StepResult;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

/**
 * @author mh14 @ jexp.de
* @since 17.11.2007 00:24:32 (c) 2007 jexp.de
*/
public class LogStepResultWriter extends FileStepResultWriter {
    public LogStepResultWriter(final WorkflowContext ctx) {
        super(ctx);
    }

    protected String createFileName(final String paramName, final int count) {
        return paramName + ((count > 0) ? count : "") + ".log";
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
