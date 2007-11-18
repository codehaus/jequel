package de.jexp.jequel.expression;

/**
 * @author mh14 @ jexp.de
 * @since 24.10.2007 00:01:48 (c) 2007 jexp.de
 */
public interface ExpressionProcessor<T> {
    void process(Expression<?> expression);

    T getResult();
}
