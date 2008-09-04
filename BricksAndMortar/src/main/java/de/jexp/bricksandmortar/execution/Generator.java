package de.jexp.bricksandmortar.execution;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import de.jexp.bricksandmortar.execution.ReportWorkflow;

public class Generator {
    private static final Log log = LogFactory.getLog(Generator.class);
    private final ApplicationContext applicationContext;

    public Generator(final Collection<String> contexts) {
        if (!contexts.isEmpty())
            applicationContext = new FileSystemXmlApplicationContext(contexts.toArray(new String[contexts.size()]));
        else applicationContext = new ClassPathXmlApplicationContext("classpath:**/*.spring.xml");
    }

    public static void main(final String[] args) {
        System.out.println("ClassPath = " + System.getProperty("java.class.path"));
        final Collection<String> contexts = new ArrayList<String>();
        final Collection<String> targets = new ArrayList<String>();
        for (final String arg : args) {
            if (arg.endsWith(".xml")) contexts.add(arg);
            else targets.add(arg);
        }
        final Generator generator = new Generator(contexts);
        if (targets.isEmpty())
            generator.run();
        else
            generator.run(targets);
    }

    private void run(final Collection<String> targets) {
        for (final String target : targets) {
            final ReportWorkflow workflow = (ReportWorkflow) applicationContext.getBean(target, ReportWorkflow.class);
            runWorkflow(target, workflow);
        }
    }

    private void run() {
        final Map<String, ReportWorkflow> reportGenerators = applicationContext.getBeansOfType(ReportWorkflow.class);
        for (final Map.Entry<String, ReportWorkflow> entry : reportGenerators.entrySet()) {
            final String workflowName = entry.getKey();
            final ReportWorkflow workflow = entry.getValue();
            runWorkflow(workflowName, workflow);
        }
    }

    protected void runWorkflow(final String workflowName, final ReportWorkflow workflow) {
        log.debug("Running Generator: " + workflowName);
        workflow.runWorkflow();
    }

}
