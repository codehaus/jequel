package de.jexp.bricksandmortar.format;

import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.WorkflowStepTest;
import de.jexp.bricksandmortar.WorkflowStepTestUtils;
import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.results.XmlStepResult;
import org.znerd.xmlenc.LineBreak;

import java.util.Collection;
import java.util.Map;

public class XmlReportStepTest extends WorkflowStepTest<XmlReportStep> {
    private static final String STEP_NAME = "xmlStep";
    private WorkflowContext ctx;

    public void testAttributesStep() {
        step.runStep(ctx);
        final XmlStepResult result = ctx.getResult(STEP_NAME);
        assertEquals("xml step ", "<result><row name=\"TestArticle\" price=\"5.00\"/></result>", result.getResult().toString());
    }

    public void testAttributesStepLineBreak() {
        step.setLineBreak(LineBreak.UNIX);
        step.setIdentation(" ");
        step.runStep(ctx);
        final XmlStepResult result = ctx.getResult(STEP_NAME);
        assertEquals("xml step ",
                "<result>\n" +
                        " <row name=\"TestArticle\" price=\"5.00\"/>\n" +
                        "</result>",
                result.getResult().toString());
    }

    public void testElementsStep() {
        step.setElements(true);
        step.runStep(ctx);
        final XmlStepResult result = ctx.getResult(STEP_NAME);
        assertEquals("xml step ", "<result><row><name>TestArticle</name><price>5.00</price></row></result>", result.getResult().toString());
    }

    protected void setUp() {
        step = new XmlReportStep();
        step.setParamName("result");
        step.setName(STEP_NAME);
        final Collection<Map<String, ?>> testData = WorkflowStepTestUtils.getDefaultArticleTestDataList();
        ctx = new WorkflowContext();
        this.ctx.addResult(new ListStepResult(step.getParamName(), testData));
    }

    protected void tearDown() {
    }
}