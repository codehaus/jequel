package de.jexp.bricksandmortar.database;

import de.jexp.bricksandmortar.WorkflowStepTest;
import de.jexp.bricksandmortar.WorkflowStepTestUtils;

import java.util.Collections;


public class NamedParameterQueryStepTest extends WorkflowStepTest<NamedParameterQueryStep> {
    public void testQueryStepSelect() {
        step.setQuery("select * from article");
        runAndCheckResult();
    }

    public void testQueryStepSelectParam() {
        step.setQuery("select * from article where name = :name");
        step.setQueryParams(Collections.singletonMap("name", WorkflowStepTestUtils.TEST_ARTICLE));
        runAndCheckResult();
    }


    protected void setUp() {
        step = new NamedParameterQueryStep();
        step.setBeanName("testQueryStep");
        step.setDataSource(getHsqlDataSource());
    }
}