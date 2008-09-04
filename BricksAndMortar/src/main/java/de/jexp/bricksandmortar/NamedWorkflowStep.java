package de.jexp.bricksandmortar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;

import java.io.IOException;
import java.util.*;

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
        if (logWriter == null) return;
        if (isLogResults()!=null && !isLogResults()) return;
        if (!ctx.isLogResults()) return;
        try {
            logWriter.writeFile(name, 0);
        } catch (IOException ioe) {
            if (logger.isErrorEnabled()) logger.error("Error writing File ", ioe);
        }
    }

    public String toString() {
        return getClass().getSimpleName()+":"+getName();
    }

    Boolean logResults;

    public Boolean isLogResults() {
        return logResults;
    }

    public void setLogResults(final Boolean logResults) {
        this.logResults = logResults;
    }

    public void setParamName(final String paramName) {
        setParamNames(paramName);
    }

    public String getParamName() {
        return paramNames != null && paramNames.length > 0 ? paramNames[0] : null;
    }

    public String[] getParamNames() {
        return paramNames;
    }

    public void setParamNames(final String...paramNames) {
        this.paramNames = trimAndFilterNames(paramNames);
    }

    protected String[] trimAndFilterNames(final String[] paramNames) {
        if (paramNames==null) return null;
        final Collection<String> names=new ArrayList<String>(paramNames.length);
        for (final String paramName : paramNames) {
            if (paramName!=null) names.add(paramName.trim());
        }
        return names.toArray(new String[names.size()]);
    }

    @SuppressWarnings({"unchecked"})
    protected <T extends StepResult<?>> Map<String,T> filterParams(final WorkflowContext workflowContext, final String[] paramNames, final Class<T> resultType) {
        final Map<String,T> results = new LinkedHashMap<String,T>(paramNames.length);
        for (final String paramName : paramNames) {
            final StepResult<?> result = workflowContext.getResult(paramName);
            if (resultType.isInstance(result)) results.put(paramName, (T) result);
        }
        return results;
    }
}
