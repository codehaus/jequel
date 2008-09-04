package de.jexp.bricksandmortar.output;

import de.jexp.bricksandmortar.results.FileStepResult;
import de.jexp.bricksandmortar.output.FileStepResultWriter;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.StepResult;

import java.io.*;

public class BinaryFileWriter extends FileStepResultWriter {
    public BinaryFileWriter(final WorkflowContext workflowContext) {
        super(workflowContext);
    }

    protected void writeStepResult(final String paramName, final int count, final StepResult stepResult) throws IOException {
        if (!(stepResult instanceof FileStepResult)) {
            if (logger.isErrorEnabled())
                logger.error("Could not write StepResult as Binary File "+stepResult);
            return;
        }
        OutputStream os = null;
        try {
            final FileStepResult fileStepResult= (FileStepResult) stepResult;
            final File file = createFileForWriting(fileStepResult.getFileName(), count);
            os = new BufferedOutputStream(new FileOutputStream(file));
            os.write(fileStepResult.getResult());
            os.flush();
        } finally {
            if (os != null) os.close();
        }
    }

    protected String createFileName(final String fileName, final int count) {
        return fileName;
    }
}