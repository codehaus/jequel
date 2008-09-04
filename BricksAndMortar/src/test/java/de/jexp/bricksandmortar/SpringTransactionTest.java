package de.jexp.bricksandmortar;

import de.jexp.bricksandmortar.execution.ReportWorkflow;
import de.jexp.bricksandmortar.execution.TestStep;
import junit.framework.TestCase;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class SpringTransactionTest extends TestCase {
    public void testTransactionCommit() {
        final ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("/de/jexp/bricksandmortar/spring/transaction_test.spring.xml");
        final ReportWorkflow simpleReadWorkflow = (ReportWorkflow) ctx.getBean("workflow");
        simpleReadWorkflow.runWorkflow();
        final DataSource dataSource = (DataSource) ctx.getBean("testDataSource");
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        final int articles = template.queryForInt("select count(*) from article");
        final int articles20 = template.queryForInt("select count(*) from article where price = 20");
        System.out.println("articles20 = " + articles20);
        assertEquals("all articles updated", articles, articles20);
    }

    public void testTransactionFail() {
        final ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("/de/jexp/bricksandmortar/spring/transaction_test.spring.xml");
        final TestStep testStep = (TestStep) ctx.getBean("testStep");
        testStep.setFail(true);
        final ReportWorkflow simpleReadWorkflow = (ReportWorkflow) ctx.getBean("workflow");
        simpleReadWorkflow.runWorkflow();
        final DataSource dataSource = (DataSource) ctx.getBean("testDataSource");
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        final int articles = 0;
        final int articles20 = template.queryForInt("select count(*) from article where price = 20");
        assertEquals("all articles updated", articles, articles20);
    }
}