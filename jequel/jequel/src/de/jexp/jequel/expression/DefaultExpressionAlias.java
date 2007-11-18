package de.jexp.jequel.expression;

/**
 * @author mh14 @ jexp.de
 * @since 29.10.2007 20:35:57 (c) 2007 jexp.de
 */
public class DefaultExpressionAlias<T, E extends Expression<T>> implements ExpressionAlias<T, E> {
    private E aliased;
    private String alias;

    public DefaultExpressionAlias(final E aliased, final String alias) {
        this.aliased = aliased;
        this.alias = alias;
    }

    public E getAliased() {
        return aliased;
    }

    public String getAlias() {
        return alias;
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }

    public <K> void process(final ExpressionProcessor<K> expressionProcessor) {
        aliased.process(expressionProcessor);
    }

    public boolean isParenthesed() {
        return false;
    }

    public BooleanExpression eq(final Object expression) {
        return aliased.eq(expression);
    }

    public BooleanExpression ge(final Object expression) {
        return aliased.ge(expression);
    }

    public BooleanExpression gt(final Object expression) {
        return aliased.gt(expression);
    }

    public BooleanExpression lt(final Object expression) {
        return aliased.lt(expression);
    }

    public BooleanExpression le(final Object expression) {
        return aliased.le(expression);
    }

    public BooleanExpression ne(final Object expression) {
        return aliased.ne(expression);
    }

    public BooleanExpression between(final Object start, final Object end) {
        return aliased.between(start, end);
    }

    public NumericBinaryExpression plus(final Object expression) {
        return aliased.plus(expression);
    }

    public NumericBinaryExpression minus(final Object expression) {
        return aliased.minus(expression);
    }

    public NumericBinaryExpression times(final Object expression) {
        return aliased.times(expression);
    }

    public NumericBinaryExpression by(final Object expression) {
        return aliased.by(expression);
    }

    public BooleanExpression in(final Object... expressions) {
        return aliased.in(expressions);
    }

    public BooleanExpression like(final Object expression) {
        return aliased.like(expression);
    }

    public BooleanExpression is(final Object expression) {
        return aliased.is(expression);
    }

    public BooleanExpression is_not(final Object expression) {
        return aliased.is_not(expression);
    }

    public static <T> DefaultExpressionAlias<T, Expression<T>> as(final AbstractExpression<T> aliased, final String alias) {
        return new DefaultExpressionAlias<T, Expression<T>>(aliased, alias);
    }

    public static <T> DefaultExpressionAlias<T, Expression<T>> AS(final AbstractExpression<T> aliased, final String alias) {
        return as(aliased, alias.toUpperCase());
    }

    public boolean isAtomic() {
        return aliased.isAtomic();
    }
}
