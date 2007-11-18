package de.jexp.jequel.expression;

import de.jexp.jequel.literals.UnaryOperator;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 15:16:31
 */
public class UnaryExpression<T> extends AbstractExpression<T> {
    private final UnaryOperator operator;
    private final Expression<T> first;

    public UnaryExpression(final UnaryOperator operator, final Expression<T> first) {
        this.first = first;
        this.operator = operator;
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }

    public <K> void process(final ExpressionProcessor<K> expressionProcessor) {
        expressionProcessor.process(first);
    }

    public UnaryOperator getOperator() {
        return operator;
    }

    public Expression<T> getFirst() {
        return first;
    }

    public boolean isAtomic() {
        return false;
    }
}
