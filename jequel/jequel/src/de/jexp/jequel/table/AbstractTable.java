package de.jexp.jequel.table;

import de.jexp.jequel.Delimeter;
import de.jexp.jequel.expression.RowTupleExpression;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 14:36:07
 */
public abstract class AbstractTable extends RowTupleExpression implements Table {
    private static final String OID_COLUMN = "OID"; // TODO FieldType PK
    private Map<String, Field<?>> fields = new LinkedHashMap<String, Field<?>>();

    protected AbstractTable() {
        super(Delimeter.COMMA);
    }

    // todo add themselves to the map on creation (without name for order)
    public abstract <T> Field<T> field(Class<T> type);

    public Field getOid() {
        return getField(OID_COLUMN);
    }

    public Field getField(final String name) {
        return getFields().get(name.toUpperCase());
    }

    public Map<String, Field<?>> getFields() {
        if (fields.isEmpty()) {
            initFields();
        }
        return fields;
    }

    protected void initFields() {
        final Class type = getClass();
        for (final java.lang.reflect.Field instanceField : type.getFields()) {
            if (Field.class.isAssignableFrom(instanceField.getType())) {
                final String fieldName = instanceField.getName();
                try {
                    final Field field = (Field) instanceField.get(this);
                    if (field instanceof TableField) {
                        ((TableField) field).initName(fieldName);
                    }
                    fields.put(fieldName, field);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(String.format("Error accessing field %s in table %s", fieldName, this));
                }
            }
        }
        append(fields.values());
        // TODO replace with append at creation time, perhaps remove the fields table at all
        // and use the expressions list of superclass for locating fields
    }

    protected Field<Integer> integer() {
        return field(Integer.class);
    }

    protected Field<String> string() {
        return field(String.class);
    }

    protected Field<BigDecimal> numeric() {
        return field(BigDecimal.class);
    }

    protected Field<Boolean> bool() {
        return field(Boolean.class);
    }

    protected Field<Date> date() {
        return field(Date.class);
    }

    protected Field<Timestamp> timestamp() {
        return field(Timestamp.class);
    }

    public boolean isParenthesed() {
        return false;
    }
}
