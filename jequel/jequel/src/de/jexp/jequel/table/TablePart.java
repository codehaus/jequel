package de.jexp.jequel.table;

/**
 * @author mh14 @ jexp.de
 * @since 05.11.2007 01:50:11 (c) 2007 jexp.de
 */
public interface TablePart {
    TableExpressionFormat TABLE_FORMAT = new TableExpressionFormat();

    String getName();

    String getTableName();

    <R> R accept(TableVisitor<R> tableVisitor);
}
