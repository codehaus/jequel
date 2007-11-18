package de.jexp.bricksandmortar;

import org.springframework.beans.factory.BeanNameAware;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.ArrayList;

import de.jexp.bricksandmortar.output.LogStepResultWriter;

/**
 * Created by mh14 on 06.07.2007 11:08:22
 */
public abstract class NamedWorkflowStep implements WorkflowStep, BeanNameAware {
    protected final Log logger = LogFactory.getLog(getClass());

    private String name;
    private String[] paramNames;
    private StepResultWriter logWriter;

    public void setBeanName(final String name) {
        setName(name);
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected void setOnWorkflowContext(final WorkflowContext ctx, final StepResult<?> result) {
        setOnWorkflowContext(ctx, getName(), result);
    }

    protected void setOnWorkflowContext(final WorkflowContext ctx, final String name, final StepResult<?> result) {
        ctx.addResult(name, result);
        logResults(ctx,name);
    }

    public void setLogWriter(final StepResultWriter logWriter) {
        this.logWriter = logWriter;
    }

    private void logResults(final WorkflowContext ctx, final String name) {
        if (logWriter==null)  return;
        if (isLogResults() || ctx.isLogResults()) {
            try {
                logWriter.writeFile(name, 0);
            } catch (IOException ioe) {
                if(logger.isErrorEnabled()) logger.error("Error writing File ",ioe);
            }
        }
    }

    public String toString() {
        return getClass().getSimpleName()+":"+getName();
    }

    boolean logResults;

    public boolean isLogResults() {
        return logResults;
    }

    public void setLogResults(final boolean logResults) {
        this.logResults = logResults;
    }

    public void setParamName(final String paramName) {
        this.paramNames=new String[]{paramName};
    }

    public String getParamName() {
        return paramNames != null && paramNames.length > 0 ? paramNames[0] : null;
    }

    public String[] getParamNames() {
        return paramNames;
    }

    public void setParamNames(final String...paramNames) {
        this.paramNames = paramNames;
    }

    protected <T extends StepResult<?>> Collection<T> filterParams(final WorkflowContext workflowContext, final String[] paramNames, final Class<T> resultType) {
        final Collection<T> results = new ArrayList<T>(paramNames.length);
        for (final String paramName : paramNames) {
            final StepResult<?> result = workflowContext.getResult(paramName);
            if (resultType.isInstance(result)) results.add((T) result);
        }
        return results;
    }

}
