package de.jexp.jequel.defaults;

import de.jexp.jequel.expression.Expression;
import de.jexp.jequel.format.DefaultFormat;
import de.jexp.jequel.format.Sql92Format;
import de.jexp.jequel.sql.Sql;

/**
 * @author mh14 @ jexp.de
 * @since 06.11.2007 02:26:43 (c) 2007 jexp.de
 *        Sql92 formatted Sql
 */
public class Sql92 extends Sql {
    static DefaultFormat sqlFormat = new Sql92Format();

    protected Sql92(final Expression... selectFields) {
        super(selectFields);
    }

    public String toString() {
        return sqlFormat.visit(this);
    }
}
