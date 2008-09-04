package de.jexp.bricksandmortar.transformation;

import de.jexp.bricksandmortar.WorkflowStepTestUtils;
import static de.jexp.bricksandmortar.WorkflowStepTestUtils.NAME;
import static de.jexp.bricksandmortar.WorkflowStepTestUtils.PRICE;
import de.jexp.bricksandmortar.results.ListStepResult;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.List;

public class ListOperationKeyTest extends TestCase {
    private static final String TEST_LIST_NAME = "testList";
    private static final String TEST_LIST_NAME2 = "testList2";
    private static final String TEST_LIST_NAME3 = "testList3";
    private final Collection<ListStepResult> sources =
            Arrays.asList(
                    new ListStepResult(TEST_LIST_NAME2, WorkflowStepTestUtils.getDefaultArticleTestDataList2()),
                    new ListStepResult(TEST_LIST_NAME, WorkflowStepTestUtils.getDefaultArticleTestDataList()),
                    new ListStepResult(TEST_LIST_NAME3, WorkflowStepTestUtils.getDefaultArticleTestDataListSameKey()));


    public void testTranspose() {
        final ListStepResult result = ListOperation.TRANSPOSE.perform(sources, true);
        final Collection<Map<String,?>> rows = result.getResult();
        System.out.println("rows = " + rows);
        assertEquals(1,rows.size());
        final Map<String, ?> row = rows.iterator().next();
        assertEquals(2, row.size());
        assertEquals(2, ((Collection)row.get(NAME)).size());
    }

    public void testTransposeKey() {
        final ListStepResult result = ListOperation.TRANSPOSE.perform(sources, true, NAME);
        final Collection<Map<String,?>> rows = result.getResult();
        System.out.println("rows = " + rows);
        assertEquals(1,rows.size());
        final Map<String, ?> row = rows.iterator().next();
        assertEquals(1, row.size());
        assertEquals(2, ((Collection)row.get(NAME)).size());
    }

    public void testDifference() {
        final List<ListStepResult> sources = Arrays.asList(
                new ListStepResult(TEST_LIST_NAME2, WorkflowStepTestUtils.getDefaultArticleTestDataList2()),
                new ListStepResult(TEST_LIST_NAME, WorkflowStepTestUtils.getDefaultArticleTestDataList()));
        final ListStepResult result = ListOperation.DIFFERENCE.perform(sources,true);
        final Collection<Map<String,?>> rows = result.getResult();
        System.out.println("rows = " + rows);
        assertEquals(1,rows.size());
        final Map<String, ?> row = rows.iterator().next();
        assertEquals(2, row.size());
        assertEquals(WorkflowStepTestUtils.TEST_ARTICLE2, row.get(NAME));
    }

    public void testDifferenceKeyPrice() {
        final List<ListStepResult> sources = Arrays.asList(
                new ListStepResult(TEST_LIST_NAME2, WorkflowStepTestUtils.getDefaultArticleTestDataList2()),
                new ListStepResult(TEST_LIST_NAME, WorkflowStepTestUtils.getDefaultArticleTestDataList()));
        System.out.println("sources = " + sources);
        final ListStepResult result = ListOperation.DIFFERENCE.perform(sources,true,PRICE);
        final Collection<Map<String,?>> rows = result.getResult();
        System.out.println("rows = " + rows);
        assertEquals(1,rows.size());
        final Map<String, ?> row = rows.iterator().next();
        assertEquals(2, row.size());
        assertEquals(WorkflowStepTestUtils.TEST_PRICE_10, row.get(PRICE));
    }

    public void testDifferenceKeyPriceDoubleEntries() {
        List<Map<String, ?>> firstList = WorkflowStepTestUtils.getDefaultArticleTestDataList2();
        firstList.addAll(firstList);
        final List<ListStepResult> sources = Arrays.asList(
                new ListStepResult(TEST_LIST_NAME2, firstList),
                new ListStepResult(TEST_LIST_NAME, WorkflowStepTestUtils.getDefaultArticleTestDataList()));
        System.out.println("sources = " + sources);
        final ListStepResult result = ListOperation.DIFFERENCE.perform(sources,false,PRICE);
        final Collection<Map<String,?>> rows = result.getResult();
        System.out.println("rows = " + rows);
        assertEquals(2,rows.size());
        for (Map<String, ?> row : rows) {
            assertEquals(2, row.size());
            assertEquals(WorkflowStepTestUtils.TEST_PRICE_10, row.get(PRICE));
        }
    }

    public void testDifferenceKeyPriceNonDistinct() {
        final List<ListStepResult> sources = Arrays.asList(
                new ListStepResult(TEST_LIST_NAME2, WorkflowStepTestUtils.getDefaultArticleTestDataList2()),
                new ListStepResult(TEST_LIST_NAME, WorkflowStepTestUtils.getDefaultArticleTestDataList()));
        System.out.println("sources = " + sources);
        final ListStepResult result = ListOperation.DIFFERENCE.perform(sources,false,PRICE);
        final Collection<Map<String,?>> rows = result.getResult();
        System.out.println("rows = " + rows);
        assertEquals(1,rows.size());
        final Map<String, ?> row = rows.iterator().next();
        assertEquals(2, row.size());
        assertEquals(WorkflowStepTestUtils.TEST_PRICE_10, row.get(PRICE));
    }

    public void testUnion() {
        final List<ListStepResult> sources = Arrays.asList(
                new ListStepResult(TEST_LIST_NAME2, WorkflowStepTestUtils.getDefaultArticleTestDataList2()),
                new ListStepResult(TEST_LIST_NAME, WorkflowStepTestUtils.getDefaultArticleTestDataList()));
        final ListStepResult result = ListOperation.UNION.perform(sources,true);
        final Collection<Map<String,?>> rows = result.getResult();
        System.out.println("rows = " + rows);
        assertEquals(2,rows.size());
        final Map<String, ?> row = rows.iterator().next();
        assertEquals(2, row.size());
        assertEquals(WorkflowStepTestUtils.TEST_ARTICLE2, row.get(NAME));
    }

    public void testUnionKeyPrice() {
        final List<ListStepResult> sources = Arrays.asList(
                new ListStepResult(TEST_LIST_NAME2, WorkflowStepTestUtils.getDefaultArticleTestDataList2()),
                new ListStepResult(TEST_LIST_NAME, WorkflowStepTestUtils.getDefaultArticleTestDataList()));
        final ListStepResult result = ListOperation.UNION.perform(sources,true, PRICE);
        final Collection<Map<String,?>> rows = result.getResult();
        System.out.println("rows = " + rows);
        assertEquals(2,rows.size());
        final Map<String, ?> row = rows.iterator().next();
        assertEquals(2, row.size());
    }
    public void testIntersection() {
        final List<ListStepResult> sources = Arrays.asList(
                new ListStepResult(TEST_LIST_NAME2, WorkflowStepTestUtils.getDefaultArticleTestDataList2()),
                new ListStepResult(TEST_LIST_NAME, WorkflowStepTestUtils.getDefaultArticleTestDataList()));
        final ListStepResult result = ListOperation.INTERSECTION.perform(sources,true);
        final Collection<Map<String,?>> rows = result.getResult();
        System.out.println("rows = " + rows);
        assertEquals(1,rows.size());
        final Map<String, ?> row = rows.iterator().next();
        assertEquals(2, row.size());
        assertEquals(WorkflowStepTestUtils.TEST_ARTICLE, row.get(NAME));
    }

    public void testIntersectionKeyPrice() {
        final List<ListStepResult> sources = Arrays.asList(
                new ListStepResult(TEST_LIST_NAME2, WorkflowStepTestUtils.getDefaultArticleTestDataList2()),
                new ListStepResult(TEST_LIST_NAME, WorkflowStepTestUtils.getDefaultArticleTestDataList()));
        final ListStepResult result = ListOperation.INTERSECTION.perform(sources,true,PRICE);
        final Collection<Map<String,?>> rows = result.getResult();
        System.out.println("rows = " + rows);
        assertEquals(1,rows.size());
        final Map<String, ?> row = rows.iterator().next();
        assertEquals(WorkflowStepTestUtils.TEST_PRICE_5, row.get(PRICE));
        assertEquals(2, row.size());
    }
}