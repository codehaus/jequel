package de.jexp.jequel.defaults;

import de.jexp.jequel.expression.Expression;
import de.jexp.jequel.format.DefaultFormat;
import de.jexp.jequel.format.OracleSqlFormat;
import de.jexp.jequel.sql.Sql;

/**
 * @author mh14 @ jexp.de
 * @since 06.11.2007 02:26:43 (c) 2007 jexp.de
 *        Sql92 formatted Sql
 */
public class OracleSql extends Sql {
    static DefaultFormat sqlFormat = new OracleSqlFormat();

    protected OracleSql(final Expression... selectFields) {
        super(selectFields);
    }

    public String toString() {
        return sqlFormat.visit(this);
    }
}