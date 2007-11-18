package de.jexp.jequel.sql;

/**
 * @author mh14 @ jexp.de
 * @since 04.11.2007 14:56:46 (c) 2007 jexp.de
 */
public interface SelectParts {
    SelectPartColumnTupleExpression getSelect();

    SelectPartColumnTupleExpression getFrom();

    SelectPartMutableBooleanExpression getWhere();

    SelectPartColumnTupleExpression getGroupBy();

    SelectPartMutableBooleanExpression getHaving();

    SelectPartColumnTupleExpression getOrderBy();
}
