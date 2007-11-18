/**
 * START_TAG_PLACEHOLDER FOREGROUND_PROCESSING RUNS_AS_JAVA_ON_CLIENT FINISH_TAG_PLACEHOLDER
 */
package de.jexp.jequel.expression;

/**
 * @author mhu@salt-solutions.de
 * @copyright (c) Salt Solutions GmbH 2006
 * @since 22.10.2007 11:33:07
 */

public class ParamExpression<T> extends ConstantExpression<T, T> {

    public ParamExpression(final String paramName) {
        super(paramName, (T) null);
    }

    public ParamExpression(final String paramName, final T paramValue) {
        super(paramName, paramValue);
    }

    public ParamExpression(final T paramValue) {
        super(null, paramValue);
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }

    public boolean isNamedExpression() {
        return getLiteral() != null;
    }
}
