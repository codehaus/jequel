package de.jexp.bricksandmortar.output;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;

import de.jexp.bricksandmortar.StepResultWriter;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.StepResult;

/**
 * @author mh14 @ jexp.de
 * @since 16.11.2007 01:11:11 (c) 2007 jexp.de
 */
public abstract class FileStepResultWriter implements StepResultWriter {
    protected final Log logger = LogFactory.getLog(getClass());
    private final WorkflowContext workflowContext;

    protected FileStepResultWriter(final WorkflowContext workflowContext) {
        this.workflowContext = workflowContext;
    }

    public void writeFile(final String paramName, final int count) throws IOException {
        final StepResult stepResult = workflowContext.getResult(paramName);
        if (stepResult == null) return;
        writeStepResult(paramName, count, stepResult);
    }

    protected abstract void writeStepResult(String paramName, int count, StepResult stepResult) throws IOException;

    protected File createFileForWriting(final String paramName, final int count) {
        final String fileName = createFileName(paramName, count);
        final File directory = workflowContext.getDirectoryFile();
        if (!directory.exists()) {
            if (logger.isWarnEnabled()) logger.warn("Creating Directory " + directory);
            directory.mkdirs();
        }
        final File file = new File(directory, fileName);
        if (logger.isInfoEnabled())
            logger.info("Writing " + paramName + " to " + file);
        return file;
    }

    protected String createFileName(final String paramName, final int count) {
        return paramName + ((count > 0) ? count : "") + ".txt";
    }
}
