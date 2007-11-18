package de.jexp.bricksandmortar;

import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.execution.ReportWorkflow;
import junit.framework.Assert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by mh14 on 06.07.2007 11:15:52
 */
public class WorkflowStepTestUtils {
    public static final BigDecimal TEST_PRICE_5 = new BigDecimal("5.00");
    public static final String TEST_ARTICLE = "TestArticle";
    public static final BigDecimal TEST_PRICE_10 = new BigDecimal("10.00");
    public static final String TEST_ARTICLE2 = "TestArticle2";
    public static final String NAME = "NAME";
    public static final String PRICE = "PRICE";

    public static void setSingleStep(final ReportWorkflow workflow, final WorkflowStep queryStep) {
        workflow.setSteps(Collections.singletonList(queryStep));
    }

    public static void checkResult(final WorkflowContext ctx, final WorkflowStep step) {
        final ListStepResult stepResult = ctx.getResult(step.getName());
        Assert.assertNotNull("testSelect result on context " + ctx, stepResult);
        checkSelectList(stepResult.getResult());
    }

    public static void checkSelectList(final Collection<Map<String, ?>> result) {
        Assert.assertEquals("listsize 1", 1, result.size());
        final Map line = result.iterator().next();
        Assert.assertEquals(TEST_ARTICLE, TEST_ARTICLE, line.get(NAME));
        Assert.assertEquals("Price", TEST_PRICE_5, line.get(PRICE));
    }

    public static DataSource createAndSetupHsqlDatasource() {
        final DataSource dataSource = new DriverManagerDataSource("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:data/test", "sa", "");
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("create table article (name varchar(50), price numeric(5,3))");
        jdbcTemplate.update("insert into article (name,price) values(?,?)", new Object[]{TEST_ARTICLE, TEST_PRICE_5});
        return dataSource;
    }

    public static Collection<Map<String, ?>> getDefaultArticleTestDataList() {
        final Map<String, ?> row = createTestRow(TEST_ARTICLE, TEST_PRICE_5);
        final Collection<Map<String, ?>> result = new ArrayList<Map<String, ?>>();
        result.add(row);
        return result;
    }

    public static List<Map<String, ?>> getDefaultArticleTestDataList2() {
        final List<Map<String, ?>> result = new LinkedList<Map<String, ?>>();
        result.add(createTestRow(TEST_ARTICLE, TEST_PRICE_5));
        result.add(createTestRow(TEST_ARTICLE2, TEST_PRICE_10));
        return result;
    }

    private static Map<String, Object> createTestRow(final String name, final BigDecimal price) {
        final Map<String, Object> row = new LinkedHashMap<String, Object>(2);
        row.put(NAME, name);
        row.put(PRICE, price);
        return row;
    }

    public static void assertEquals(final String message, final Collection<?> expected, final Collection<?> got) {
        Assert.assertEquals(message + " size", expected.size(), got.size());
        Assert.assertTrue(message + " elements", expected.containsAll(got));
    }

    public static String readFileToString(final String fileName) throws IOException {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(fileName);
            final char[] buffer = new char[1024 * 10];
            int count;
            final StringBuilder result = new StringBuilder();
            while ((count = fileReader.read(buffer)) != -1) {
                result.append(buffer, 0, count);
            }
            return result.toString();
        } finally {
            if (fileReader != null) fileReader.close();
        }
    }
}
