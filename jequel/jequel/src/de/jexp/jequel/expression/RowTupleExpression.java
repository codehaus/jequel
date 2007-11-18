package de.jexp.jequel.expression;

import de.jexp.jequel.Delimeter;

import java.util.Collection;

/**
 * @author mh14 @ jexp.de
 * @since 21.10.2007 22:35:08 (c) 2007 jexp.de
 *        dynamic set of expressions, instance of Collection, used for in()
 */
public abstract class RowTupleExpression<A extends RowTupleExpression<A>> extends AbstractTupleExpression implements Aliased<A> {
    private String alias;

    public RowTupleExpression(final Delimeter delim, final Collection<? extends Expression<?>> expressions) {
        super(delim, expressions);
    }

    public RowTupleExpression(final Delimeter delim, final Expression<?>... expressions) {
        super(delim, expressions);
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }

    public boolean isParenthesed() {
        return true;
    }

    public A as(final String alias) {
        this.alias = alias;
        return (A) this;
    }

    public A AS(final String alias) {
        return as(alias.toUpperCase());
    }

    public A getAliased() {
        return (A) this;
    }

    public String getAlias() {
        return alias;
    }
}
