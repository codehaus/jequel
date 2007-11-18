package de.jexp.jequel.expression;

import static de.jexp.jequel.expression.Expressions.*;
import de.jexp.jequel.literals.Operator;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 00:37:12
 */
public abstract class AbstractExpression<T> implements Expression<T> {
    public BooleanExpression eq(final Object expression) {
        return createBinaryBooleanExpression(Operator.EQ, expression);
    }

    public BooleanExpression ge(final Object expression) {
        return createBinaryBooleanExpression(Operator.GE, expression);
    }

    public BooleanExpression gt(final Object expression) {
        return createBinaryBooleanExpression(Operator.GT, expression);
    }

    public BooleanExpression lt(final Object expression) {
        return createBinaryBooleanExpression(Operator.LT, expression);
    }

    public BooleanExpression le(final Object expression) {
        return createBinaryBooleanExpression(Operator.LE, expression);
    }

    public BooleanExpression ne(final Object expression) {
        return createBinaryBooleanExpression(Operator.NE, expression);
    }

    public BooleanExpression between(final Object start, final Object end) {
        final BooleanExpression andExpression = Expressions.createBinaryBooleanExpression(e(start), Operator.AND, end);
        return createBinaryBooleanExpression(Operator.BETWEEN, andExpression);
    }

    public BooleanExpression like(final Object expression) {
        return createBinaryBooleanExpression(Operator.LIKE, expression);
    }

    public BooleanExpression is(final Object expression) {
        return createBinaryBooleanExpression(Operator.IS, expression);
    }

    public BooleanExpression is_not(final Object expression) {
        return createBinaryBooleanExpression(Operator.IS_NOT, expression);
    }

    public BooleanExpression in(final Object... expressions) {
        return createBinaryBooleanExpression(Operator.IN, e(expressions));
    }

    public NumericBinaryExpression plus(final Object expression) {
        return createBinaryNumericExpression(Operator.PLUS, expression);
    }

    public NumericBinaryExpression minus(final Object expression) {
        return createBinaryNumericExpression(Operator.MINUS, expression);
    }

    public NumericBinaryExpression times(final Object expression) {
        return createBinaryNumericExpression(Operator.TIMES, expression);
    }

    public NumericBinaryExpression by(final Object expression) {
        return createBinaryNumericExpression(Operator.BY, expression);
    }

    protected BooleanBinaryExpression createBinaryBooleanExpression(final Operator operator, final Object expression) {
        return Expressions.createBinaryBooleanExpression((Expression<Boolean>) this, operator, expression);
    }

    protected NumericBinaryExpression createBinaryNumericExpression(final Operator operator, final Object expression) {
        return new NumericBinaryExpression((Expression<Number>) this, operator, e(expression));
    }

    public <K> void process(final ExpressionProcessor<K> expressionProcessor) {
    }

    public boolean isParenthesed() {
        return false;
    }
/*
    public String toString() {
        return SqlFormat.format(this);
    }
*/

    public abstract <R> R accept(final ExpressionVisitor<R> expressionVisitor);
}
