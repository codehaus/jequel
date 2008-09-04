package de.jexp.bricksandmortar.output;

import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.WorkflowStepTestUtils;
import de.jexp.bricksandmortar.results.TextStepResult;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author mh14 @ jexp.de
 * @since 15.11.2007 00:48:53 (c) 2007 jexp.de
 */
public class WriteTextStepTest extends TestCase {
    private static final String TEST_FILE = "WriterTestStep.txt";

    public void testWriteTextStep() throws IOException {
        final WriteTextStep writeTextStep = new WriteTextStep();
        writeTextStep.setParamName("test");
        writeTextStep.setFileName(TEST_FILE);
        final WorkflowContext workflowContext = new WorkflowContext();
        workflowContext.addResult(new TextStepResult("test", "content"));
        writeTextStep.runStep(workflowContext);
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("StepResult: " + "test");
        printWriter.println("content");
        assertEquals(stringWriter.getBuffer().toString(), WorkflowStepTestUtils.readFileToString(TEST_FILE));
    }
}
