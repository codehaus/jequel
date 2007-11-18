package de.jexp.jequel.processor;

import de.jexp.jequel.expression.Expression;
import de.jexp.jequel.expression.ExpressionProcessor;

/**
 * @author mh14 @ jexp.de
 * @since 26.10.2007 02:00:56 (c) 2007 jexp.de
 */
public abstract class AbstractExpressionProcessor<T> implements ExpressionProcessor<T> {
    public void process(final Expression<?> expression) {
        doProcess(expression);
        expression.process(this);
    }

    protected abstract void doProcess(Expression<?> expression);

    public abstract T getResult();
}
