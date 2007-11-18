package de.jexp.jequel.expression;

import de.jexp.jequel.literals.Operator;
import de.jexp.jequel.literals.SqlKeyword;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 15:16:31
 */
public class BinaryExpression<T> extends AbstractExpression<T> implements CompoundExpression {
    private final Expression<T> first;
    private final Operator operator;
    private final Expression<T> second;

    public BinaryExpression(final Expression<T> first, final Operator operator, final Expression<T> second) {
        this.first = first;
        this.operator = operator;
        this.second = second;
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }

    public <K> void process(final ExpressionProcessor<K> expressionProcessor) {
        expressionProcessor.process(first);
        expressionProcessor.process(second);
    }

    public Expression<T> getFirst() {
        return first;
    }

    public Expression<T> getSecond() {
        return second;
    }

    public Operator getOperator() {
        return operator;
    }

    public boolean oneIsNull() {
        return first == Expressions.NULL || second == Expressions.NULL;
    }

    public boolean isAtomic() {
        return false;
    }

    public Collection<? extends Expression> getExpressions() {
        return Arrays.asList(first, second);
    }

    public boolean hasValues() {
        return true;
    }

    public SqlKeyword getDelimeter() {
        return operator;
    }
}