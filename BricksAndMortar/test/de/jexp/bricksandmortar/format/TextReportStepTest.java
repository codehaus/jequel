package de.jexp.bricksandmortar.format;

import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.WorkflowStepTest;
import de.jexp.bricksandmortar.WorkflowStepTestUtils;
import static de.jexp.bricksandmortar.WorkflowStepTestUtils.*;
import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.results.TextStepResult;
import static de.jexp.bricksandmortar.util.TextUtils.*;

import java.util.Collection;
import java.util.Map;

public class TextReportStepTest extends WorkflowStepTest<TextReportStep> {
    private static final String TEXT_STEP = "textStep";

    public void testTextStep() {
        final Collection<Map<String, ?>> testData = WorkflowStepTestUtils.getDefaultArticleTestDataList();
        final WorkflowContext ctx = new WorkflowContext();
        ctx.addResult(new ListStepResult(step.getResultName(), testData));
        step.runStep(ctx);
        final TextStepResult result = ctx.getResult(TEXT_STEP);
        assertEquals("text step ", NAME + TAB + PRICE + NEWLINE + TEST_ARTICLE + TAB + TEST_PRICE_5 + NEWLINE, result.getResult().toString());

    }

    protected void setUp() {
        step = new TextReportStep();
        step.setResultName("result");
        step.setName(TEXT_STEP);
    }

    protected void tearDown() {
    }
}
