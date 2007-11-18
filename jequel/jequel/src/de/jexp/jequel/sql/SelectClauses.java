package de.jexp.jequel.sql;

import de.jexp.jequel.expression.BooleanExpression;
import de.jexp.jequel.expression.Expression;
import de.jexp.jequel.expression.RowTupleExpression;

/**
 * @author mh14 @ jexp.de
 * @since 25.10.2007 01:51:35 (c) 2007 jexp.de
 */
public interface SelectClauses {
    public static interface FromClause extends SqlWrapper {
        WhereClause from(final RowTupleExpression... tables);
    }

    public static interface GroupByClause extends SqlWrapper {
        HavingClause group_by(Expression... groupBy);
    }

    public static interface HavingClause extends SqlWrapper {
        Expression having(final BooleanExpression having);
    }

    public static interface OrderByClause extends GroupByClause, SqlWrapper {
        GroupByClause order_by(final Expression... orderBy);
    }

    public static interface WhereClause extends OrderByClause, GroupByClause, SqlWrapper {
        OrderByClause where(final BooleanExpression where);
    }
}
