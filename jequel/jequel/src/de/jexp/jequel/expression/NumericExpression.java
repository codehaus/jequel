package de.jexp.jequel.expression;

import de.jexp.jequel.Valueable;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 15:16:31
 */
public class NumericExpression extends AbstractExpression<Number> implements Valueable<Number> {
    private final Number value;

    public NumericExpression() {
        this(null);
    }

    public NumericExpression(final Number value) {
        this.value = value;
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }


    public Number getValue() {
        return value;
    }

    public boolean isAtomic() {
        return true;
    }
}