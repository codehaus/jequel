package de.jexp.jequel.sql;

import de.jexp.jequel.expression.DelegatingFormat;

/**
 * @author mh14 @ jexp.de
 * @since 05.11.2007 01:33:27 (c) 2007 jexp.de
 */
public class SqlExpressionFormat extends DelegatingFormat<SqlFormat> implements SqlFormat {
    public SqlExpressionFormat() {
    }

    public SqlExpressionFormat(final String formatClassName) {
        super(formatClassName);
    }

    public String visit(final SelectPartColumnTupleExpression sqlPartColumnTupleExpression) {
        return formatAround(getFormat().visit(sqlPartColumnTupleExpression), sqlPartColumnTupleExpression);
    }

    public String visit(final SelectPartMutableBooleanExpression selectPartMutableBooleanExpression) {
        return formatAround(getFormat().visit(selectPartMutableBooleanExpression), selectPartMutableBooleanExpression);
    }
}