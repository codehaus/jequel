package de.jexp.jequel.execute.core;

import de.jexp.jequel.execute.ExecutableParams;
import de.jexp.jequel.execute.ExecutableStatement;
import de.jexp.jequel.processor.ParameterCollectorProcessor;
import de.jexp.jequel.sql.Sql;
import de.jexp.jequel.sql.SqlWrapper;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author mh14 @ jexp.de
 * @since 03.11.2007 15:13:18 (c) 2007 jexp.de
 */
public abstract class AbstractExecutableStatement implements ExecutableStatement, SqlWrapper {
    private DataSource dataSource;
    protected final Sql sql;
    private ExecutableParams executableParams;

    public AbstractExecutableStatement(final DataSource dataSource, final Sql sql) {
        this.dataSource = dataSource;
        this.sql = sql;
    }

    public String getSqlString() {
        return sql.toString();
    }

    public Sql getSql() {
        return sql;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public ExecutableParams getExecutableParams() {
        return executableParams;
    }

    public ExecutableStatement clearParams() {
        this.executableParams = DefaultExecutableParams.extractParams(sql);
        return this;
    }

    public ExecutableStatement withParams(final ExecutableParams executableParams) {
        if (this.executableParams == null)
            this.executableParams = executableParams;
        else
            this.executableParams.addParams(executableParams);
        return this;
    }

    public ExecutableStatement withParams(final Object... params) {
        if (hasNamedParams(sql)) {
            return withParams(DefaultExecutableParams.createNamedParams(params));
        } else {
            return withParams(DefaultExecutableParams.createParams(params));
        }
    }

    // TODO adapt, move responsibility to Expression Params
    private boolean hasNamedParams(final Sql sql) {
        final ParameterCollectorProcessor parameterCollectorProcessor = new ParameterCollectorProcessor();
        parameterCollectorProcessor.process(sql);
        return !parameterCollectorProcessor.getNamedExpressions().isEmpty();
    }

    public ExecutableStatement withParams(final Map<String, Object> params) {
        return withParams(DefaultExecutableParams.createParams(params));
    }

    public Sql toSql() {
        return getSql();
    }

    public String toString() {
        return getSqlString();
    }

    public ExecutableStatement executeOn(final DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }
}

