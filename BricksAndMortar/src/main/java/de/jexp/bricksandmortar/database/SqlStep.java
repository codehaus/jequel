package de.jexp.bricksandmortar.database;

import de.jexp.bricksandmortar.StepResult;
import de.jexp.bricksandmortar.NamedWorkflowStep;
import de.jexp.bricksandmortar.WorkflowContext;
import de.jexp.bricksandmortar.results.ListStepResult;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author mh14 @ jexp.de
 * @since 15.11.2007 07:59:27 (c) 2007 jexp.de
 */
public abstract class SqlStep<T extends StepResult<?>> extends NamedWorkflowStep {
    private String query;
    private Map<String, ?> queryParams;
    private boolean execute=true;
    private String[] skipStepNames;

    public void setQueryParams(final Map<String, ?> queryParams) {
        this.queryParams = queryParams;
    }

    public void setQuery(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public Map<String, ?> getQueryParams() {
        return queryParams;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(final boolean execute) {
        this.execute = execute;
    }

    protected Collection<Map<String, ?>> resolveParams(final WorkflowContext workflowContext) {
        final Collection<Map<String, ?>> result = new LinkedList<Map<String, ?>>();
        if (getQueryParams() != null) result.add(getQueryParams());
        else {
            if (hasParamNames()) {
                for (final String paramName : getParamNames()) {
                    final ListStepResult params = workflowContext.getResult(paramName);
                    if (params == null) continue;
                    final Collection<Map<String, ?>> rows = params.getResult();
                    if (rows == null) continue;
                    for (final Map<String, ?> row : rows) {
                        result.add(row);
                    }
                }
            }
        }
        return result;
    }

    private boolean hasParamNames() {
        return getParamNames() != null;
    }

    public void runStep(final WorkflowContext workflowContext) {
        if (!validConfig()) return;
        final Collection<Map<String, ?>> resolvedParams = resolveParams(workflowContext);
        if (logger.isInfoEnabled())
            logger.info((isExecute() ? "" : "NOT ") +
                    "Executing query "+getQuery()+" with params: "+resolvedParams);
        if (!isExecute()) return;
        executeQueries(workflowContext, resolvedParams);
    }

    protected void executeQueries(final WorkflowContext workflowContext, final Collection<Map<String, ?>> resolvedParams) {
        if (resolvedParams.isEmpty() && !hasParamNames()) {
            final T result = execute(null, 0);
            setOnWorkflowContext(workflowContext, result);
        }
        else {
            int count = 0;
            for (final Map<String, ?> row : resolvedParams) {
                final T stepResult = execute(row, count++);
                setOnWorkflowContext(workflowContext,stepResult.getName(),stepResult);
            }
        }
    }

    protected void setOnWorkflowContext(final WorkflowContext ctx, final String name, final StepResult<?> result) {
        super.setOnWorkflowContext(ctx, name, result);
        if (result.isEmpty()) {
            ctx.addSkipSteps(getSkipIfNoResult());
        }
    }

    protected boolean validConfig() {
        return true;
    }

    protected abstract T execute(final Map<String, ?> row, final int count);

    protected String createResultName(final int count) {
        if (count == 0) return getName();
        return getName() + count;
    }

    public void setSkipIfNoResult(String...stepNames) {
        this.skipStepNames=trimAndFilterNames(stepNames);
    }

    public String[] getSkipIfNoResult() {
        return skipStepNames;
    }
}
