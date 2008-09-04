package de.jexp.bricksandmortar.transformation;

import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.WorkflowStepTest;
import de.jexp.bricksandmortar.WorkflowStepTestUtils;
import de.jexp.bricksandmortar.results.ListStepResult;

import java.util.Collection;
import java.util.Map;

public class ListOperationStepTest extends WorkflowStepTest<ListOperationStep> {
    private static final String LIST_OPERATION = "listOperation";
    private static final String TEST_LIST_NAME = "testList";
    private static final String TEST_LIST_NAME2 = "testList2";
    private static final ListStepResult SOURCE_LIST_RESULT1 = new ListStepResult(TEST_LIST_NAME,
            WorkflowStepTestUtils.getDefaultArticleTestDataList());

    private static final ListStepResult SOURCE_LIST_RESULT2 = new ListStepResult(TEST_LIST_NAME2,
            WorkflowStepTestUtils.getDefaultArticleTestDataList2());

    private static final ListStepResult SOURCE_LIST_RESULT_SAME_KEY = new ListStepResult(TEST_LIST_NAME2,
            WorkflowStepTestUtils.getDefaultArticleTestDataListSameKey());


    public void testSourceListName() {
        step.setParamName(TEST_LIST_NAME);
        assertEquals("source list name", TEST_LIST_NAME, step.getParamName());
    }

    public void testNoopListOperationStep() {
        final WorkflowContext ctx = new WorkflowContext();
        addParams(new ListStepResult[]{SOURCE_LIST_RESULT1}, ctx);
        step.runStep(ctx);
        final ListStepResult result = ctx.getResult(step.getName());
        WorkflowStepTestUtils.assertEquals("unmodified list", SOURCE_LIST_RESULT1.getResult(), result.getResult());
    }

    public void testIntersectListOperationStep() {
        final WorkflowContext ctx = new WorkflowContext();
        addParams(new ListStepResult[]{SOURCE_LIST_RESULT1, SOURCE_LIST_RESULT2}, ctx);
        step.setOperation(ListOperation.INTERSECTION);
        step.runStep(ctx);
        final ListStepResult result = ctx.getResult(step.getName());
        WorkflowStepTestUtils.assertEquals("intersection", SOURCE_LIST_RESULT1.getResult(), result.getResult());
    }

    public void testDifferenceListOperationStep() {
        final WorkflowContext ctx = new WorkflowContext();
        addParams(new ListStepResult[]{SOURCE_LIST_RESULT1, SOURCE_LIST_RESULT2}, ctx);
        step.setOperation(ListOperation.DIFFERENCE);
        step.runStep(ctx);
        final ListStepResult result = ctx.getResult(step.getName());
        assertTrue("difference", result.getResult().isEmpty());
    }

    public void testDifferenceListOperationStepOne() {
        final WorkflowContext ctx = new WorkflowContext();
        addParams(new ListStepResult[]{SOURCE_LIST_RESULT2, SOURCE_LIST_RESULT1}, ctx);
        step.setOperation(ListOperation.DIFFERENCE);
        step.runStep(ctx);
        final ListStepResult result = ctx.getResult(step.getName());
        final Collection<Map<String, ?>> resultEntry = result.getResult();
        assertEquals("difference one", 1, resultEntry.size());
        assertEquals("Article 2", WorkflowStepTestUtils.TEST_ARTICLE2, resultEntry.iterator().next().get(WorkflowStepTestUtils.NAME));
    }

    public void testIntersectListOperationParamStep() {
        final WorkflowContext ctx = new WorkflowContext();
        addParams(new ListStepResult[]{SOURCE_LIST_RESULT1, SOURCE_LIST_RESULT2}, ctx);
        step.setOperation(ListOperation.INTERSECTION);
        step.runStep(ctx);
        final ListStepResult result = ctx.getResult(step.getName());
        WorkflowStepTestUtils.assertEquals("unmodified list", SOURCE_LIST_RESULT1.getResult(), result.getResult());
    }

    public void testUnionListOperationStep() {
        final WorkflowContext ctx = new WorkflowContext();
        addParams(new ListStepResult[]{SOURCE_LIST_RESULT1, SOURCE_LIST_RESULT2}, ctx);
        step.setOperation(ListOperation.UNION);
        step.runStep(ctx);
        final ListStepResult result = ctx.getResult(step.getName());
        WorkflowStepTestUtils.assertEquals("unmodified list", SOURCE_LIST_RESULT2.getResult(), result.getResult());
    }

    private void addParams(final ListStepResult[] listStepResults, final WorkflowContext ctx) {
        final String[] paramNames = new String[listStepResults.length];
        int i = 0;
        for (final ListStepResult sourceList : listStepResults) {
            ctx.addResult(sourceList);
            paramNames[i++] = sourceList.getName();
        }
        step.setParamNames(paramNames);
    }

    protected void setUp() {
        step = new ListOperationStep();
        step.setName(LIST_OPERATION);
    }

    protected void tearDown() {
    }
}
