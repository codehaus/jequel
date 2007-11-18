package de.jexp.jequel.expression;

import de.jexp.jequel.literals.UnaryOperator;

/**
 * @author mh14 @ jexp.de
 * @since 18.10.2007 15:16:31 (c) 2007 jexp.de
 */
public class BooleanUnaryExpression extends BooleanConstantExpression {
    private final UnaryExpression<Boolean> unaryExpression;

    public BooleanUnaryExpression(final UnaryOperator operator, final Expression<Boolean> first) {
        this.unaryExpression = new UnaryExpression<Boolean>(operator, first);
    }

    public String toString() {
        return EXPRESSION_FORMAT.visit(this);
    }

    public UnaryExpression<Boolean> getUnaryExpression() {
        return unaryExpression;
    }

    public boolean isAtomic() {
        return false;
    }
}