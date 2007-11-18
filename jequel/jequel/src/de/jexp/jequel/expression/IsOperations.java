package de.jexp.jequel.expression;

/**
 * @author mh14 @ jexp.de
 * @since 29.10.2007 08:00:56 (c) 2007 jexp.de
 */
public interface IsOperations {
    BooleanExpression is(Object expression);

    BooleanExpression is_not(Object expression);
}
