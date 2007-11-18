package de.jexp.jequel.sql;

import de.jexp.jequel.execute.ExecutableStatement;

import javax.sql.DataSource;

/**
 * @author mh14 @ jexp.de
 * @since 05.11.2007 02:24:29 (c) 2007 jexp.de
 */
public interface ExecutableStatementFactory {
    ExecutableStatement createExecutableStatement(DataSource dataSource, Sql sql);
}
