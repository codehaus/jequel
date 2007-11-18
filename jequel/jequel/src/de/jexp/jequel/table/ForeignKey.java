package de.jexp.jequel.table;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 00:42:59
 */
public class ForeignKey<T> extends TableField<T> {
    private Field<T> field;
    private FieldReference<T> reference;

    ForeignKey(final Table table, final Field<T> field) {
        super(table);
        this.field = field;
    }

    public ForeignKey(final Table table, final FieldReference<T> reference) {
        super(table);
        this.reference = reference;
    }

    public Field<T> getField() {
        if (field != null) return field;
        field = reference.resolve();
        return field;
    }

    public boolean references(final Field<T> other) {
        return getField().equals(other);
    }
}
