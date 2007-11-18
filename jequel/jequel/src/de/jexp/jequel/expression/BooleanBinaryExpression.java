package de.jexp.jequel.expression;

import de.jexp.jequel.literals.Operator;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 15:16:31
 */
public class BooleanBinaryExpression extends AbstractBooleanExpression {
    private final BinaryExpression<Boolean> binaryExpression;

    public BooleanBinaryExpression(final Expression<Boolean> first, final Operator operator, final Expression<Boolean> second) {
        this.binaryExpression = new BinaryExpression<Boolean>(first, operator, second);
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }

    public BinaryExpression<Boolean> getBinaryExpression() {
        return binaryExpression;
    }

    public <K> void process(final ExpressionProcessor<K> expressionProcessor) {
        expressionProcessor.process(getBinaryExpression());
    }

    public boolean isAtomic() {
        return false;
    }
}