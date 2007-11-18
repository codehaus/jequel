package de.jexp.jequel.table;

import de.jexp.jequel.expression.AbstractExpression;
import de.jexp.jequel.expression.ExpressionVisitor;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 17.10.2007 23:38:42
 */
public class TableField<T> extends AbstractExpression<T> implements Field<T> {
    private String name;
    private final Table table;
    private boolean primaryKey;

    TableField(final Table table) {
        this(null, table);
    }

    TableField(final String name, final Table table) {
        this.name = name;
        this.table = table;
    }

    public FieldAlias<T> as(final String alias) {
        return new FieldAlias<T>(this, alias);
    }

    public FieldAlias<T> AS(final String alias) {
        return new FieldAlias<T>(this, alias.toUpperCase());
    }

    public String toString() {
        return accept(TABLE_FORMAT);
    }

    public <R> R accept(final TableVisitor<R> expressionVisitor) {
        return expressionVisitor.visit((TableField<T>) this);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        throw new UnsupportedOperationException("accept only for TableVisitor");
    }

    public String getTableName() {
        return table.getTableName();
    }

    public Field resolve() {
        return table.resolve().getField(name);
    }

    void initName(final String name) {
        if (this.name != null) throw new IllegalStateException("Name already set " + this);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TableField<T> primaryKey() {
        this.primaryKey = true;
        return this;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public Table getTable() {
        return table;
    }

    public Field<T> foreignKey(final Field<T> reference) {
        return new ForeignKey<T>(getTable(), reference);
    }

    public boolean isAtomic() {
        return true;
    }
}
