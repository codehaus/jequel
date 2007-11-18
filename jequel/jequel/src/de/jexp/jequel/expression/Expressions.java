package de.jexp.jequel.expression;

import de.jexp.jequel.Delimeter;
import de.jexp.jequel.literals.Operator;
import de.jexp.jequel.literals.UnaryOperator;
import static de.jexp.jequel.literals.UnaryOperator.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 14:57:46
 */
public abstract class Expressions {
    public final static ConstantExpression<Void, Void> NULL = new ConstantExpression<Void, Void>("NULL");
    public final static ConstantExpression<Void, Void> STAR = new ConstantExpression<Void, Void>("*"); // TODO other type

    public final static BooleanExpression TRUE = new BooleanConstantExpression("TRUE", true);
    public final static BooleanExpression FALSE = new BooleanConstantExpression("FALSE", false);

    public static BooleanUnaryExpression not(final Object expression) {
        return new BooleanUnaryExpression(NOT, e(expression));
    }

    // TODO numericExpr als Param
    public static Expression<Number> sum(final Object expression) {
        return new NumericUnaryExpression(SUM, e(expression));
    }

    public static Expression<Number> avg(final Object expression) {
        return new NumericUnaryExpression(AVG, e(expression));
    }

    public static Expression<Number> count(final Object expression) {
        return new NumericUnaryExpression(COUNT, e(expression));
    }

    public static Expression<Number> round(final Object expression) {
        return new NumericUnaryExpression(ROUND, e(expression));
    }

    public static Expression<Number> to_number(final Object expression) {
        return new NumericUnaryExpression(TO_NUMBER, e(expression));
    }

    public static BooleanUnaryExpression exits(final Object expression) {
        return new BooleanUnaryExpression(EXISTS, e(expression));
    }

    public static BooleanUnaryExpression not_exits(final Object expression) {
        return new BooleanUnaryExpression(NOT_EXISTS, e(expression));
    }

    public static UnaryExpression min(final Object expression) {
        return new UnaryExpression(MIN, e(expression));
    }

    public static UnaryExpression max(final Object expression) {
        return new UnaryExpression(MAX, e(expression));
    }

    public static Expression<Number> count() {
        return count(STAR);
    }

    /*
    public static  <T> Expression<T> e(final Expression<T> expression) {
        return expression;
    }

    public static  Expression<Boolean> e(final Boolean value) {
        return value ? TRUE : FALSE;
    }

    public static  Expression<Number> e(final Number value) {
        return new NumericExpression(value);
    }

    public static <T> ConstantExpression<T> e(final T value) {
        return new ConstantExpression<T>(value);
    }
    */
    public static Expression e(final Object... expression) {
        return e(createExpressionCollection(expression));
    }


    public static Expression e(final Object expression) {
        if (expression == null) return NULL;
        if (expression instanceof AbstractExpression) return (AbstractExpression) expression;
        if (expression instanceof Boolean) return ((Boolean) expression ? TRUE : FALSE);
        if (expression instanceof String) return new StringExpression((String) expression);
        if (expression instanceof Number) return new NumericExpression((Number) expression);
        if (expression instanceof Iterable) {
            return new RowTupleExpression(Delimeter.COMMA, createExpressionCollection((Iterable<?>) expression)) {
            };
        }
        if (expression.getClass().isArray()) {
            return new RowTupleExpression(Delimeter.COMMA, createExpressionCollection((Object[]) expression)) {
            };
        }
        return new ConstantExpression(null, expression);
    }

    public static ConstantExpression<String, String> sql(final String sqlString) {
        return new ConstantExpression<String, String>(sqlString);
    }

    private static Collection<Expression<?>> createExpressionCollection(final Object... expression) {
        if (expression == null || expression.length == 0) return Collections.emptyList();
        final Collection<Expression<?>> expressions = new LinkedList<Expression<?>>();
        for (final Object element : expression) {
            expressions.add(e(element));
        }
        return expressions;
    }

    private static Collection<Expression<?>> createExpressionCollection(final Iterable<?> expression) {
        if (expression == null) return Collections.emptyList();
        final Collection<Expression<?>> expressions = new LinkedList<Expression<?>>();
        for (final Object element : expression) {
            expressions.add(e(element));
        }
        return expressions;
    }

    static ColumnTupleExpression createColumnTuple(final Delimeter delim, final AbstractExpression... expressions) {
        return new ColumnTupleExpression(delim, expressions);
    }

    public static ParamExpression named(final String paramName) {
        return new ParamExpression(paramName);
    }

    public static <T> ParamExpression<T> named(final String paramName, final T paramValue) {
        return new ParamExpression<T>(paramName, paramValue);
    }

    public static <T> ParamExpression<Collection<T>> named(final String paramName, final T... paramValues) {
        return new ParamExpression<Collection<T>>(paramName, Arrays.asList(paramValues));
    }

    public static <T> ParamExpression<T> param(final T paramValue) {
        return new ParamExpression<T>(paramValue);
    }

    public static <T> ParamExpression<Collection<T>> param(final T... paramValues) {
        return new ParamExpression<Collection<T>>(Arrays.asList(paramValues));
    }

    public static Collection<Expression<?>> toCollection(final Expression<?>... expressions) {
        return createExpressionCollection((Expression[]) expressions);
    }

    protected static BooleanBinaryExpression createBinaryBooleanExpression(final Expression<Boolean> first, final Operator operator, final Object expression) {
        return new BooleanBinaryExpression(first, operator, e(expression));
    }

    public static NumericUnaryExpression nvl(final Object... expressions) {
        return new NumericUnaryExpression(UnaryOperator.NVL, e(expressions));
    }

}
