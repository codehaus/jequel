package de.jexp.jequel.expression;

/**
 * @author mh14 @ jexp.de
 * @since 29.10.2007 08:04:49 (c) 2007 jexp.de
 */
public interface BooleanOperations {
    BooleanExpression and(BooleanExpression expression);

    BooleanExpression or(BooleanExpression expression);

    BooleanExpression OR(BooleanExpression expression);
}
