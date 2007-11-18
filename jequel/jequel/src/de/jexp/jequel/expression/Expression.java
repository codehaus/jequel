package de.jexp.jequel.expression;

import de.jexp.jequel.SqlString;

/**
 * @author mh14 @ jexp.de
 * @since 29.10.2007 08:02:04 (c) 2007 jexp.de
 */
public interface Expression<T> extends SqlString, NumericOperations, ListOperations, StringOperations, IsOperations {
    DelegatingExpressionFormat EXPRESSION_FORMAT = new DelegatingExpressionFormat();

    <K> void process(ExpressionProcessor<K> expressionProcessor);

    boolean isParenthesed();

    boolean isAtomic();

    <R> R accept(ExpressionVisitor<R> expressionVisitor);

}
