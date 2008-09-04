package de.jexp.bricksandmortar.format;

import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.WorkflowStepTest;
import de.jexp.bricksandmortar.WorkflowStepTestUtils;
import static de.jexp.bricksandmortar.WorkflowStepTestUtils.*;
import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.results.TextStepResult;
import static de.jexp.bricksandmortar.util.TextUtils.*;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

public class VelocityReportStepTest extends WorkflowStepTest<VelocityReportStep> {
    private static final String STEP_NAME = "velocityStep";

    public void testTextStep() {
        final Collection<Map<String, ?>> testData = WorkflowStepTestUtils.getDefaultArticleTestDataList();
        final WorkflowContext ctx = new WorkflowContext();
        final ListStepResult stepResult = new ListStepResult(step.getParamName(), testData);
        ctx.addResult(stepResult);
        step.runStep(ctx);
        final TextStepResult result = ctx.getResult(STEP_NAME);
        assertEquals("text step ", NAME + " " + PRICE + NEWLINE + TEST_ARTICLE + " " + TEST_PRICE_5 + NEWLINE, result.getResult().toString());

    }

    protected void setUp() {
        step = new VelocityReportStep();
        step.setVelocityEngine(createEngine());
        step.setTemplate(getClass().getPackage().getName().replace('.','/')+"/test.vm");
        step.setParamName("result");
        step.setName(STEP_NAME);
    }

    private VelocityEngine createEngine() {
        try {
            final VelocityEngine engine = new VelocityEngine();
            final Properties props = new Properties();
            props.put("resource.loader", "class");
            props.put("class.resource.loader.class", ClasspathResourceLoader.class.getName());
            engine.init(props);
            return engine;
        } catch (Exception e) {
            throw new RuntimeException("Error creating velocity engine", e);
        }
    }

    protected void tearDown() {
    }
}