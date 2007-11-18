package de.jexp.jequel.expression;

import de.jexp.jequel.Valueable;

/**
 * @author mh14 @ jexp.de
 * @since 18.10.2007 15:00:07 (c) 2007 jexp.de
 */
public class ConstantExpression<T, V> extends AbstractExpression<T> implements Valueable<V> {
    private final V value;
    private String literal;

    public ConstantExpression(final String literal, final V value) {
        this.value = value;
        this.literal = literal;
    }

    public ConstantExpression(final String literal) {
        this(literal, null);
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }

    public String getLiteral() {
        return literal;
    }

    public V getValue() {
        return value;
    }

    public boolean isAtomic() {
        return true;
    }
}
