package de.jexp.jequel.execute.core;

import de.jexp.jequel.execute.ExecutableParams;
import de.jexp.jequel.expression.ParamExpression;
import de.jexp.jequel.processor.ParameterCollectorProcessor;
import de.jexp.jequel.sql.Sql;

import java.util.*;

/**
 * @author mh14 @ jexp.de
 * @since 03.11.2007 16:38:02 (c) 2007 jexp.de
 */
public class DefaultExecutableParams implements ExecutableParams {
    private final Map<String, Object> namedParams = new LinkedHashMap<String, Object>();
    private final List<Object> params = new LinkedList<Object>();

    public DefaultExecutableParams(final ExecutableParams params) {
        this.namedParams.putAll(params.getNamedParams());
        this.params.addAll(params.getParamValues());
    }

    public DefaultExecutableParams(final Map<String, Object> namedParams) {
        this.namedParams.putAll(namedParams);
    }

    public DefaultExecutableParams(final Collection<?> params) {
        this.params.addAll(params);
    }

    protected void addParams(final Collection<?> params) {
        int i = 0;
        for (final Object param : params) {
            if (i >= this.params.size()) break; // this.params.add(param);
            else this.params.set(i, param);
            i++;
        }
    }

    public DefaultExecutableParams() {
    }

    public void addParam(final Object value) {
        this.params.add(value);
    }

    public void addParam(final String name, final Object value) {
        this.namedParams.put(name, value);
    }

    public void addParams(final ExecutableParams executableParams) {
        for (final Map.Entry<String, Object> namedParam : executableParams.getNamedParams().entrySet()) {
            addParam(namedParam.getKey(), namedParam.getValue());
        }
        addParams(executableParams.getParamValues());
    }

    public boolean hasParams() {
        return getParamCount() > 0;
    }

    public boolean hasOnlyNamed() {
        return params.isEmpty();
    }

    public int getParamCount() {
        return namedParams.size() + params.size();
    }

    public Collection<?> getParamValues() {
        return params;
    }

    public Collection<String> getParamNames() {
        return namedParams.keySet();
    }

    public Map<String, Object> getNamedParams() {
        return namedParams;
    }

    public static DefaultExecutableParams extractParams(final Sql sql) {
        final ParameterCollectorProcessor parameterCollectorProcessor = getParamsFromSql(sql);

        final DefaultExecutableParams executableParams = new DefaultExecutableParams();
        for (final ParamExpression namedExpression : parameterCollectorProcessor.getNamedExpressions()) {
            executableParams.addParam(namedExpression.getLiteral(), namedExpression.getValue());
        }
        for (final ParamExpression paramExpression : parameterCollectorProcessor.getParamExpressions()) {
            executableParams.addParam(paramExpression.getValue());
        }
        return executableParams;
    }

    public static ParameterCollectorProcessor getParamsFromSql(final Sql sql) {
        final ParameterCollectorProcessor parameterCollectorProcessor = new ParameterCollectorProcessor();
        parameterCollectorProcessor.process(sql);
        return parameterCollectorProcessor;
    }


    public static ExecutableParams createParams(final Object[] params) {
        return new DefaultExecutableParams(Arrays.asList(params));
    }

    public static ExecutableParams createNamedParams(final Object[] params) {
        if (params.length % 2 != 0) return createParams(params);
        final DefaultExecutableParams namedParams = new DefaultExecutableParams();
        for (int i = 0; i < params.length; i += 2) {
            if (params[i] instanceof String) {
                namedParams.addParam((String) params[i], params[i + 1]);
            } else {
                return createParams(params);
            }
        }
        return namedParams;
    }

    public static ExecutableParams createParams(final Collection<?> params) {
        return new DefaultExecutableParams(params);
    }

    public static ExecutableParams createParams(final Map<String, Object> params) {
        return new DefaultExecutableParams(params);
    }
}
