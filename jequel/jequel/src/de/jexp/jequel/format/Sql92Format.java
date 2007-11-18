package de.jexp.jequel.format;

import de.jexp.jequel.Delimeter;
import de.jexp.jequel.expression.*;
import de.jexp.jequel.literals.NameUtils;
import de.jexp.jequel.literals.Operator;
import de.jexp.jequel.literals.SqlKeyword;
import de.jexp.jequel.sql.SelectPartColumnTupleExpression;
import de.jexp.jequel.sql.SelectPartMutableBooleanExpression;
import de.jexp.jequel.sql.Sql;
import de.jexp.jequel.table.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author mh14 @ jexp.de
 * @since 25.10.2007 03:52:59 (c) 2007 jexp.de
 */
public class Sql92Format implements DefaultFormat {

    protected String parenthese(final String expressionString) {
        return "(" + expressionString + ")";
    }

    public String formatAround(String expressionString, final Expression<?> expression) {
        if (expression.isParenthesed()) {
            expressionString = parenthese(expressionString);
        }
        if (expression instanceof Aliased) {
            final Aliased aliased = (Aliased) expression;
            if (aliased.getAlias() != null)
                expressionString = formatAlias(expressionString, aliased);
        }
        return expressionString;

    }

    protected String formatAlias(final String expressionString, final Aliased aliased) {
        return expressionString + " as " + aliased.getAlias();
    }

    protected <T> String visit(final T constantValue) {
        final String valueString = formatConcrete(constantValue);
        if (constantValue instanceof Expression) {
            return formatAround(valueString, (Expression<?>) constantValue);
        }
        return valueString;
        // return constantValue.toString();
    }

    protected String formatConcrete(Object value) {
        if (value instanceof BooleanBinaryExpression) return visit((BooleanBinaryExpression) value);
        if (value instanceof BooleanUnaryExpression) return visit((BooleanUnaryExpression) value);
        if (value instanceof Field) return visit((Field) value);
        if (value instanceof ForeignKey) return visit((ForeignKey) value);
        if (value instanceof BaseTable) return visit((BaseTable) value);
        if (value instanceof NumericExpression) return visit((NumericExpression) value);
        if (value instanceof ParamExpression) return visit((ParamExpression) value);
        if (value instanceof SelectPartColumnTupleExpression) return visit((SelectPartColumnTupleExpression) value);
        if (value instanceof SelectPartMutableBooleanExpression)
            return visit((SelectPartMutableBooleanExpression) value);
        if (value instanceof Sql) return visit((Sql) value);
        if (value instanceof StringExpression) return visit((StringExpression) value);
        if (value instanceof TableField) return visit((TableField) value);
        if (value instanceof UnaryExpression) return visit((UnaryExpression) value);
        if (value instanceof TupleExpression) return visit((TupleExpression) value);
        if (value instanceof ConstantExpression) return visit((ConstantExpression) value);
        return value.toString();
    }

    public <T, E extends Expression<T>> String visit(final ExpressionAlias<T, E> expression) {
        return visit(expression.getAliased());
    }

    protected <F, S> String formatBinaryExpression(final Expression<F> first, final Operator operator, final Expression<S> second) {
        return visit(first) + " " + visit(operator) + " " + visit(second);
    }


    protected <E extends Expression> String implode(final SqlKeyword delim, final Iterable<E> expressions) {
        final Collection<String> strings = new ArrayList<String>(10);
        for (final E expression : expressions) {
            if (expression != null) {
                final String string = visit(expression);
                if (string.length() > 0) strings.add(string);
            }
        }
        final StringBuilder result = new StringBuilder();
        for (Iterator<String> iterator = strings.iterator(); iterator.hasNext();) {
            result.append(iterator.next());
            if (iterator.hasNext()) result.append(delim);
        }
        return result.toString();
    }

    protected String nameToOperator(final SqlKeyword operator) {
        final String constantName = operator.name();
        return NameUtils.constantNameToLowerCaseLiteral(constantName);
    }

    public <T, V> String visit(final ConstantExpression<T, V> constantExpression) {
        final String literal = constantExpression.getLiteral();
        final V constantValue = constantExpression.getValue();
        return literal != null ? literal : visit(constantValue);
    }

    public String visit(final NumericExpression numericExpression) {
        final Number value = numericExpression.getValue();
        return value != null ? visit(value) : visit((Expression<Number>) numericExpression);
    }

    public String visit(final BooleanConstantExpression booleanConstantExpression) {
        final String literal = booleanConstantExpression.getLiteral();
        return literal != null ? literal : visit((BooleanExpression) booleanConstantExpression);
    }

    public String visit(final StringExpression stringExpression) {
        return "'" + visit((ConstantExpression<String, String>) stringExpression) + "'";
    }

    public <T> String visit(final UnaryExpression<T> unaryExpression) {
        return visit(unaryExpression.getOperator()) + parenthese(visit(unaryExpression.getFirst()));
    }

    public String visit(final BooleanUnaryExpression booleanUnaryExpression) {
        return visit(booleanUnaryExpression.getUnaryExpression());
    }

    public String visit(final NumericUnaryExpression numericUnaryExpression) {
        return visit(numericUnaryExpression.getUnaryExpression());
    }

    public <T> String visit(final BinaryExpression<T> binaryExpression) {
        final Expression<T> first = binaryExpression.getFirst();
        final Expression<T> second = binaryExpression.getSecond();
        final Operator operator = binaryExpression.getOperator();
        if (!binaryExpression.oneIsNull())
            return formatBinaryExpression(first, operator, second);
        if (operator == Operator.EQ)
            return formatBinaryExpression(first, Operator.IS, second);
        if (operator == Operator.NE)
            return formatBinaryExpression(first, Operator.IS_NOT, second);
        return formatBinaryExpression(first, operator, second); // TODO not all Operators usable
    }

    public String visit(final BooleanBinaryExpression binaryExpression) {
        return visit(binaryExpression.getBinaryExpression());
    }


    public String visit(final NumericBinaryExpression binaryExpression) {
        return visit(binaryExpression.getBinaryExpression());
    }

    public String visit(final TupleExpression tupleExpression) {
        return implode(tupleExpression.getDelimeter(), tupleExpression.getExpressions());
    }

    public String visit(final RowTupleExpression rowTupleExpression) {
        return visit((TupleExpression) rowTupleExpression);
    }

    public <T> String visit(final ParamExpression<T> paramExpression) {
        if (paramExpression.isNamedExpression())
            return ":" + paramExpression.getLiteral();
        return formatPreparedStatementParameter(paramExpression);
    }

    private <T> String formatPreparedStatementParameter(final ParamExpression<T> paramExpression) {
        final T value = paramExpression.getValue();
        if (value instanceof Iterable) {
            final StringBuilder sb = new StringBuilder();
            for (Iterator it = ((Iterable) value).iterator(); it.hasNext();) {
                it.next();
                sb.append("?");
                if (it.hasNext()) sb.append(", ");
            }
            return sb.toString();
        } else return "?";
    }

    public String visit(final SqlKeyword operator) {
        final String sqlKeyword = operator.getSqlKeyword();
        if (sqlKeyword != null) return sqlKeyword;
        else return nameToOperator(operator);
    }

    public String visit(final SelectPartColumnTupleExpression sqlPartColumnTupleExpression) {
        if (sqlPartColumnTupleExpression.hasValues())
            return visit(sqlPartColumnTupleExpression.getSelectKeyword()) + " " + visit((ColumnTupleExpression) sqlPartColumnTupleExpression);
        else return "";
    }

    public String visit(final MutableBooleanExpression mutableBooleanExpression) {
        return mutableBooleanExpression.hasValue() ? visit(mutableBooleanExpression.getBooleanExpression()) : "";
    }

    public String visit(final SelectPartMutableBooleanExpression selectPartMutableBooleanExpression) {
        return selectPartMutableBooleanExpression.hasValue() ?
                visit(selectPartMutableBooleanExpression.getSelectKeyword()) + " " + visit((MutableBooleanExpression) selectPartMutableBooleanExpression) : "";
    }

    public String visit(final JoinTable joinTable) {
        return parenthese(
                formatAround(visit(joinTable.getFirst()), joinTable.getFirst())
                        + " join " +
                        formatAround(visit(joinTable.getSecond()), joinTable.getSecond())
                        + " on " +
                        parenthese(visit(joinTable.getJoinExpression())));
    }

    public String visit(final BaseTable table) {
        if (table instanceof JoinTable) return visit((JoinTable) table);
        return table.getName();
    }

    public <T> String visit(final Field<T> field) {
        return field.getTableName() + Delimeter.POINT + field.getName();
    }
}
