package de.jexp.bricksandmortar;

import junit.framework.TestCase;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import de.jexp.bricksandmortar.execution.ReportWorkflow;

public class SimpleSpringTest extends TestCase {
    public void testSimpleSpring() {
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/de/jexp/bricksandmortar/spring/simple_read.spring.xml");
        final ReportWorkflow simpleReadWorkflow = (ReportWorkflow) applicationContext.getBean("simpleReadWorkflow", ReportWorkflow.class);
        simpleReadWorkflow.runWorkflow();
    }
}
