package de.jexp.jequel.expression;

/**
 * @author mh14 @ jexp.de
 * @since 05.11.2007 01:33:27 (c) 2007 jexp.de
 */
public class DelegatingExpressionFormat extends DelegatingFormat<ExpressionFormat> implements ExpressionFormat {
    public DelegatingExpressionFormat() {
    }

    public DelegatingExpressionFormat(final String formatClassName) {
        super(formatClassName);
    }

    public <T, V> String visit(final ConstantExpression<T, V> expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public String visit(final NumericExpression expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public String visit(final BooleanConstantExpression expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public String visit(final StringExpression expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public <T> String visit(final UnaryExpression<T> expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public String visit(final BooleanUnaryExpression expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public String visit(final NumericUnaryExpression expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public <T> String visit(final BinaryExpression<T> expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public String visit(final BooleanBinaryExpression expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public String visit(final NumericBinaryExpression expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public String visit(final TupleExpression expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public String visit(final RowTupleExpression expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public <T> String visit(final ParamExpression<T> expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public String visit(final MutableBooleanExpression expression) {
        return formatAround(getFormat().visit(expression), expression);
    }

    public <T, E extends Expression<T>> String visit(final ExpressionAlias<T, E> expression) {
        return formatAround(getFormat().visit(expression), expression);
    }
}
