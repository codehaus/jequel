package de.jexp.jequel.defaults;

import de.jexp.jequel.expression.*;
import de.jexp.jequel.format.Sql92Format;
import de.jexp.jequel.sql.SelectPartColumnTupleExpression;
import de.jexp.jequel.sql.SelectPartMutableBooleanExpression;
import de.jexp.jequel.table.BaseTable;
import de.jexp.jequel.table.Field;
import de.jexp.jequel.table.JoinTable;

/**
 * @author mh14 @ jexp.de
 * @since 06.11.2007 02:56:24 (c) 2007 jexp.de
 */
public class TestFormat extends Sql92Format {
    private final String testString = " test ";

    public <T, V> String visit(final ConstantExpression<T, V> constantExpression) {
        return testString;
    }

    public String visit(final NumericExpression numericExpression) {
        return testString;
    }

    public String visit(final BooleanConstantExpression booleanConstantExpression) {
        return testString;
    }

    public String visit(final StringExpression stringExpression) {
        return testString;
    }

    public <T> String visit(final UnaryExpression<T> unaryExpression) {
        return testString;
    }

    public String visit(final BooleanUnaryExpression booleanUnaryExpression) {
        return testString;
    }

    public String visit(final NumericUnaryExpression numericUnaryExpression) {
        return testString;
    }

    public <T> String visit(final BinaryExpression<T> binaryExpression) {
        return testString;
    }

    public String visit(final BooleanBinaryExpression binaryExpression) {
        return testString;
    }

    public String visit(final NumericBinaryExpression binaryExpression) {
        return testString;
    }

    public String visit(final TupleExpression tupleExpression) {
        return super.visit(tupleExpression);
    }

    public String visit(final RowTupleExpression rowTupleExpression) {
        return super.visit(rowTupleExpression);
    }

    public <T> String visit(final ParamExpression<T> paramExpression) {
        return testString;
    }

    public String visit(final MutableBooleanExpression mutableBooleanExpression) {
        return testString;
    }

    public <T, E extends Expression<T>> String visit(final ExpressionAlias<T, E> expression) {
        return testString;
    }

    public String formatAround(final String expressionString, final Expression<?> expression) {
        return testString;
    }

    public String visit(final SelectPartColumnTupleExpression sqlPartColumnTupleExpression) {
        return testString;
    }

    public String visit(final SelectPartMutableBooleanExpression selectPartMutableBooleanExpression) {
        return testString;
    }

    public <T> String visit(final Field<T> field) {
        return testString;
    }

    public String visit(final JoinTable joinTable) {
        return testString;
    }

    public String visit(final BaseTable table) {
        return testString;
    }
}
