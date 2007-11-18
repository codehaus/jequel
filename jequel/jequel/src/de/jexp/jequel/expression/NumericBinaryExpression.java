package de.jexp.jequel.expression;

import de.jexp.jequel.literals.Operator;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 23:22:55
 */
public class NumericBinaryExpression extends NumericExpression {
    private final BinaryExpression<Number> binaryExpression;

    public NumericBinaryExpression(final Expression<Number> first, final Operator operator, final Expression<Number> second) {
        this.binaryExpression = new BinaryExpression<Number>(first, operator, second);
    }


    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }


    public BinaryExpression<Number> getBinaryExpression() {
        return binaryExpression;
    }

    public <K> void process(final ExpressionProcessor<K> expressionProcessor) {
        expressionProcessor.process(getBinaryExpression());
    }
}