package de.jexp.jequel.sql;

import de.jexp.jequel.expression.AppendableExpression;
import de.jexp.jequel.expression.Expression;
import de.jexp.jequel.literals.SelectKeyword;

/**
 * @author mh14 @ jexp.de
 * @since 24.10.2007 08:09:28 (c) 2007 jexp.de
 */
public interface SelectPartExpression<T extends Expression> extends AppendableExpression<T> {
    SqlExpressionFormat SQL_FORMAT = new SqlExpressionFormat();

    SelectKeyword getSelectKeyword();
}
