package de.jexp.jequel.sql;

import de.jexp.jequel.Delimeter;
import de.jexp.jequel.execute.ExecutableStatement;
import de.jexp.jequel.expression.BooleanExpression;
import de.jexp.jequel.expression.Expression;
import static de.jexp.jequel.expression.Expressions.*;
import de.jexp.jequel.expression.RowTupleExpression;
import de.jexp.jequel.literals.SelectKeyword;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 17.10.2007 23:26:20
 */


public class Sql extends RowTupleExpression implements
        SelectClauses.FromClause, SelectClauses.WhereClause, SelectClauses.OrderByClause, SelectClauses.HavingClause,
        SelectParts {
    private final SelectPartColumnTupleExpression select = new SelectPartColumnTupleExpression(SelectKeyword.SELECT);
    private final SelectPartColumnTupleExpression from = new SelectPartColumnTupleExpression(SelectKeyword.FROM);
    private final SelectPartMutableBooleanExpression where = new SelectPartMutableBooleanExpression(SelectKeyword.WHERE);
    private final SelectPartColumnTupleExpression groupBy = new SelectPartColumnTupleExpression(SelectKeyword.GROUP_BY);
    private final SelectPartMutableBooleanExpression having = new SelectPartMutableBooleanExpression(SelectKeyword.HAVING);
    private final SelectPartColumnTupleExpression orderBy = new SelectPartColumnTupleExpression(SelectKeyword.ORDER_BY);

    protected Sql(final Expression... selectFields) {
        super(Delimeter.SPACE);
        this.select.append(selectFields);
        super.append(toCollection(select, from, where, groupBy, having, orderBy));
    }

    public Sql toSql() {
        return this;
    }

    public ExecutableStatement executeOn(final DataSource dataSource) {
        return EXECUTABLE_STATEMENT_FACTORY.createExecutableStatement(dataSource, this);
    }

    public static SelectClauses.FromClause Select(final Expression... fields) {
        return new Sql(fields);
    }

    public SelectClauses.FromClause select(final Expression... fields) {
        this.select.append(fields);
        return this;
    }

    public static SelectClauses.FromClause sub_select(final Expression... fields) {
        return new Sql(fields) {
            public boolean isParenthesed() {
                return true;
            }
        };
    }

    public SelectClauses.WhereClause from(final RowTupleExpression... tables) {
        this.from.append(tables);
        return this;
    }


    public SelectClauses.OrderByClause where(final BooleanExpression where) {
        this.where.and(where);
        return this;
    }

    public SelectClauses.GroupByClause order_by(final Expression... orderBy) {
        this.orderBy.append(orderBy);
        return this;
    }

    public SelectClauses.HavingClause group_by(final Expression... groupBy) {
        this.groupBy.append(groupBy);
        return this;
    }

    public Expression having(final BooleanExpression having) {
        this.having.and(having);
        return this;
    }

    public boolean isParenthesed() {
        return false;
    }

    public SelectPartMutableBooleanExpression where() {
        return where;
    }

    public SelectPartMutableBooleanExpression having() {
        return having;
    }

    // Allow Append of Expressions

    public Sql append(final Sql sql) {
        select.append(sql.select.getExpressions());
        from.append(sql.from.getExpressions());
        where.and(sql.where.getBooleanExpression());
        groupBy.append(sql.groupBy.getExpressions());
        having.and(sql.having.getBooleanExpression());
        orderBy.append(sql.orderBy.getExpressions());
        return this;
    }

    public void append(final Expression... expressions) {
        for (final Expression expression : expressions) {
            appendExpression(expression);
        }
    }

    public void append(final Collection<? extends Expression> expressions) {
        for (final Expression expression : expressions) {
            appendExpression(expression);
        }
    }

    private void appendExpression(final Expression expression) {
        if (expression instanceof SelectPartColumnTupleExpression) {
            final SelectPartColumnTupleExpression selectPartExpression = (SelectPartColumnTupleExpression) expression;
            final Collection<Expression> expressions = selectPartExpression.getExpressions();
            switch (selectPartExpression.getSelectKeyword()) {
            case SELECT:
                this.select.append(expressions);
                break;
            case FROM:
                this.from.append(expressions);
                break;
            case GROUP_BY:
                this.groupBy.append(expressions);
                break;
            case ORDER_BY:
                this.orderBy.append(expressions);
                break;
            }
        } else if (expression instanceof SelectPartMutableBooleanExpression) {
            final SelectPartMutableBooleanExpression selectPartExpression = (SelectPartMutableBooleanExpression) expression;
            final BooleanExpression booleanExpression = selectPartExpression.getBooleanExpression();
            switch (selectPartExpression.getSelectKeyword()) {
            case WHERE:
                this.where.and(booleanExpression);
                break;
            case HAVING:
                this.having.and(booleanExpression);
                break;
            }
        }
    }

    public SelectPartColumnTupleExpression getSelect() {
        return select;
    }

    public SelectPartColumnTupleExpression getFrom() {
        return from;
    }

    public SelectPartMutableBooleanExpression getWhere() {
        return where;
    }

    public SelectPartColumnTupleExpression getGroupBy() {
        return groupBy;
    }

    public SelectPartMutableBooleanExpression getHaving() {
        return having;
    }

    public SelectPartColumnTupleExpression getOrderBy() {
        return orderBy;
    }
}

