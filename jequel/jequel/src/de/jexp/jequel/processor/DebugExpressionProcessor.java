package de.jexp.jequel.processor;

import de.jexp.jequel.expression.Expression;

/**
 * @author mh14 @ jexp.de
 * @since 24.10.2007 00:16:48 (c) 2007 jexp.de
 */
public class DebugExpressionProcessor extends AbstractExpressionProcessor<String> {
    private final StringBuilder sb = new StringBuilder();

    public String getResult() {
        return sb.toString();
    }

    protected void doProcess(final Expression<?> expression) {
        sb.append(String.format("%s {%s}\n", expression, expression.getClass().getSimpleName()));
    }
}