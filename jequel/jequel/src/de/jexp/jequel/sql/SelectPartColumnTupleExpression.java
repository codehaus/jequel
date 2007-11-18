package de.jexp.jequel.sql;

import de.jexp.jequel.Delimeter;
import de.jexp.jequel.expression.AbstractExpression;
import de.jexp.jequel.expression.ColumnTupleExpression;
import de.jexp.jequel.literals.SelectKeyword;

/**
 * @author mh14 @ jexp.de
 * @since 22.10.2007 00:04:04 (c) 2007 jexp.de
 */
public class SelectPartColumnTupleExpression extends ColumnTupleExpression implements SelectPartExpression {
    private SelectKeyword selectKeyword;

    public SelectPartColumnTupleExpression(final SelectKeyword selectKeyword) {
        this(selectKeyword, Delimeter.COMMA);
    }

    public SelectPartColumnTupleExpression(final SelectKeyword selectKeyword, final Delimeter delimeter, final AbstractExpression... expressions) {
        super(delimeter, expressions);
        this.selectKeyword = selectKeyword;
    }

    public String toString() {
        return accept(SQL_FORMAT);
    }

    public <R> R accept(final SqlVisitor<R> sqlVisitor) {
        return sqlVisitor.visit(this);
    }

    public SelectKeyword getSelectKeyword() {
        return selectKeyword;
    }


}