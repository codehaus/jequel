package de.jexp.jequel.sql;

import de.jexp.jequel.execute.ExecutableStatement;
import de.jexp.jequel.util.ClassUtils;

import javax.sql.DataSource;

/**
 * @author mh14 @ jexp.de
 * @since 05.11.2007 02:07:27 (c) 2007 jexp.de
 */
public class DelegatingExecutableStatementFactory implements ExecutableStatementFactory {
    ExecutableStatementFactory executableStatementFactory;
    private static final String DEFAULT_STATEMENT_FACTORY = "de.jexp.jequel.execute.spring.SpringExecutableStatementFactory";

    public DelegatingExecutableStatementFactory() {
        this(DEFAULT_STATEMENT_FACTORY);
    }

    public DelegatingExecutableStatementFactory(final String statementFactory) {
        final ExecutableStatementFactory factory = ClassUtils.newInstance(statementFactory);
        setExecutableStatementFactory(factory);
    }

    public ExecutableStatementFactory getExecutableStatementFactory() {
        return executableStatementFactory;
    }

    public void setExecutableStatementFactory(final ExecutableStatementFactory executableStatementFactory) {
        this.executableStatementFactory = executableStatementFactory;
    }

    public ExecutableStatement createExecutableStatement(final DataSource dataSource, final Sql sql) {
        return getExecutableStatementFactory().createExecutableStatement(dataSource, sql);
    }
}
