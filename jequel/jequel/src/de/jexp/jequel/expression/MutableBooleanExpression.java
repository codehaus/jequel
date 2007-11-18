package de.jexp.jequel.expression;

/**
 * @author mh14 @ jexp.de
 * @since 22.10.2007 00:43:42 (c) 2007 jexp.de
 */
public class MutableBooleanExpression extends AbstractBooleanExpression {
    BooleanExpression expr;

    public MutableBooleanExpression(final BooleanExpression expr) {
        this.expr = expr;
    }

    public BooleanExpression and(final BooleanExpression expression) {
        return expr = (hasValue() ? expr.and(expression) : expression);
    }

    public BooleanExpression or(final BooleanExpression expression) {
        return expr = (hasValue() ? expr.or(expression) : expression);
    }

    public BooleanExpression OR(final BooleanExpression expression) {
        return expr = (hasValue() ? expr.OR(expression) : expression);
    }

    public BooleanExpression getBooleanExpression() {
        return expr;
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }

    public boolean hasValue() {
        return expr != null;
    }

    public <K> void process(final ExpressionProcessor<K> expressionProcessor) {
        if (hasValue()) {
            getBooleanExpression().process(expressionProcessor);
        }
    }

    public boolean isAtomic() {
        return false;
    }
}
