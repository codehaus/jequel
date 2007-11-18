package de.jexp.jequel.table;

import de.jexp.jequel.expression.DefaultExpressionAlias;

/**
 * @author mh14 @ jexp.de
 * @since 28.10.2007 23:02:36 (c) 2007 jexp.de
 */
public class FieldAlias<T> extends DefaultExpressionAlias<T, Field<T>> implements Field<T> {
    public FieldAlias(final Field<T> aliased, final String alias) {
        super(aliased, alias);
    }

    public String getTableName() {
        return getAliased().getTableName();
    }

    public Field resolve() {
        return getAliased().resolve();
    }

    public String getName() {
        return getAliased().getName();
    }

    public Field<T> primaryKey() {
        return getAliased().primaryKey();
    }

    public boolean isPrimaryKey() {
        return getAliased().isPrimaryKey();
    }

    public Table getTable() {
        return getAliased().getTable();
    }

    // TODO return Field<T>
    public FieldAlias<T> as(final String alias) {
        return new FieldAlias<T>(getAliased(), alias);
    }

    public FieldAlias<T> AS(final String alias) {
        return new FieldAlias<T>(getAliased(), alias.toUpperCase());
    }

    public <R> R accept(final TableVisitor<R> tableVisitor) {
        return tableVisitor.visit(this);
    }

    public String toString() {
        return accept(TABLE_FORMAT);
    }
}
