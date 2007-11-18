package de.jexp.jequel.expression;

/**
 * @author mh14 @ jexp.de
 * @since 29.10.2007 07:58:13 (c) 2007 jexp.de
 */
public interface NumericOperations {
    BooleanExpression eq(Object expression);

    BooleanExpression ge(Object expression);

    BooleanExpression gt(Object expression);

    BooleanExpression lt(Object expression);

    BooleanExpression le(Object expression);

    BooleanExpression ne(Object expression);

    BooleanExpression between(Object start, Object end);

    NumericBinaryExpression plus(Object expression);

    NumericBinaryExpression minus(Object expression);

    NumericBinaryExpression times(Object expression);

    NumericBinaryExpression by(Object expression);
}
