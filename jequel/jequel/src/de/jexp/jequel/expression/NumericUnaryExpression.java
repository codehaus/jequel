package de.jexp.jequel.expression;

import de.jexp.jequel.literals.UnaryOperator;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 23:22:55
 */
public class NumericUnaryExpression extends NumericExpression {
    private final UnaryExpression<Number> unaryExpression;

    public NumericUnaryExpression(final UnaryOperator operator, final Expression<Number> first) {
        this.unaryExpression = new UnaryExpression<Number>(operator, first);
    }

    public String toString() {
        return EXPRESSION_FORMAT.visit(this);
    }

    public <K> void process(final ExpressionProcessor<K> expressionProcessor) {
        expressionProcessor.process(getUnaryExpression());
    }

    public UnaryExpression<Number> getUnaryExpression() {
        return unaryExpression;
    }

    public boolean isAtomic() {
        return false;
    }
}
