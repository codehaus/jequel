package de.jexp.jequel.expression;

import de.jexp.jequel.Valueable;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 15:16:31
 *        allows decorator pattern
 */
public class BooleanConstantExpression extends AbstractBooleanExpression implements Valueable<Boolean> {
    private final String literal;
    private final Boolean value;

    public BooleanConstantExpression() {
        this(null, true);
    }

    public BooleanConstantExpression(final String literal, final Boolean value) {
        this.literal = literal;
        this.value = value;
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }

    public Boolean getValue() {
        return value;
    }

    public String getLiteral() {
        return literal;
    }

    public boolean isAtomic() {
        return true;
    }
}