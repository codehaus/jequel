package de.jexp.jequel.expression;

/**
 * @author mh14 @ jexp.de
 * @since 25.10.2007 03:48:01 (c) 2007 jexp.de
 */
public interface ExpressionVisitor<R> extends Visitor {
    <T, V> R visit(ConstantExpression<T, V> constantExpression);

    R visit(NumericExpression numericExpression);

    R visit(BooleanConstantExpression booleanConstantExpression);

    R visit(StringExpression stringExpression);

    <T> R visit(UnaryExpression<T> unaryExpression);

    R visit(BooleanUnaryExpression booleanUnaryExpression);

    R visit(NumericUnaryExpression numericUnaryExpression);

    <T> R visit(final BinaryExpression<T> binaryExpression);

    R visit(final BooleanBinaryExpression binaryExpression);

    R visit(final NumericBinaryExpression binaryExpression);

    R visit(TupleExpression tupleExpression);

    R visit(RowTupleExpression rowTupleExpression);

    <T> R visit(ParamExpression<T> paramExpression);

    R visit(MutableBooleanExpression mutableBooleanExpression);

    <T, E extends Expression<T>> R visit(final ExpressionAlias<T, E> expression);
}
