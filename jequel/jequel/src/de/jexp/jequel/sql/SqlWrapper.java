package de.jexp.jequel.sql;

import de.jexp.jequel.SqlString;
import de.jexp.jequel.execute.ExecutableStatement;

import javax.sql.DataSource;

/**
 * @author mh14 @ jexp.de
 * @since 24.10.2007 00:26:20 (c) 2007 jexp.de
 */
public interface SqlWrapper extends SqlString {
    ExecutableStatementFactory EXECUTABLE_STATEMENT_FACTORY = new DelegatingExecutableStatementFactory();

    Sql toSql();

    ExecutableStatement executeOn(final DataSource dataSource);
}
