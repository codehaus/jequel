/**
 * START_TAG_PLACEHOLDER FOREGROUND_PROCESSING RUNS_AS_JAVA_ON_CLIENT FINISH_TAG_PLACEHOLDER
 */
package de.jexp.jequel.expression;

/**
 * @author mhu@salt-solutions.de
 * @copyright (c) Salt Solutions GmbH 2006
 * @since 22.10.2007 10:26:01
 */

public class StringExpression extends ConstantExpression<String, String> {
    public StringExpression(final String value) {
        super(null, value);
    }

    public String toString() {
        return EXPRESSION_FORMAT.visit(this);
    }

}
