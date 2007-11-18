package de.jexp.jequel.table;

import de.jexp.jequel.expression.Alias;
import de.jexp.jequel.expression.Expression;

/**
 * @author mh14 @ jexp.de
 * @since 29.10.2007 22:39:33 (c) 2007 jexp.de
 */
public interface Field<T> extends Expression<T>, Alias<FieldAlias<T>>, TablePart {
    Field resolve();

    Field<T> primaryKey();

    boolean isPrimaryKey();

    Table getTable();
}
