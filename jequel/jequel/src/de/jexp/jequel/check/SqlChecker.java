package de.jexp.jequel.check;

import de.jexp.jequel.expression.Aliased;
import de.jexp.jequel.expression.Expression;
import de.jexp.jequel.expression.ExpressionProcessor;
import de.jexp.jequel.expression.UnaryExpression;
import de.jexp.jequel.processor.AbstractExpressionProcessor;
import de.jexp.jequel.sql.SelectPartExpression;
import de.jexp.jequel.sql.Sql;
import de.jexp.jequel.table.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author mh14 @ jexp.de
 * @since 04.11.2007 14:50:38 (c) 2007 jexp.de
 *        define Rules as Checks, Rules can be validated
 */
public class SqlChecker {
    private final Sql sql;

    public SqlChecker(final Sql sql) {
        this.sql = sql;
    }

    public TableUsageCheckResult checkUsedTables() {
        final TableUsageCheckResult checkResult = new TableUsageCheckResult(getFromSymbols());
        checkResult.addUsedTables(getUsedTables());
        return checkResult;
    }

    // group by, check fields in having
    // check usage in select only with NumericUnaryExpressions

    public Collection<String> checkGroupBy() {
        final Collection<String> groupByExpressions = getGroupByExpressions();
        final Collection<String> ungroupedSelectExpressions = new LinkedList<String>();
        for (final Expression<?> anSelectExpression : sql.getSelect().getExpressions()) {
            final Object selectExpression = anSelectExpression instanceof Aliased ? ((Aliased) anSelectExpression).getAliased() : anSelectExpression;
            final String selectExpressionString = selectExpression.toString();
            if (!groupByExpressions.contains(selectExpressionString)) {
                // either numeric, todo shouldnt contain group by expression
                if (!(selectExpression instanceof UnaryExpression) &&
                        !(selectExpression instanceof Sql)) {
                    ungroupedSelectExpressions.add(selectExpressionString);
                }
            }
        }
        sql.getHaving();
        // TODO Having
        return ungroupedSelectExpressions;
    }

    public Collection<String> getGroupByExpressions() {
        final Collection<String> groupByExpressions = new LinkedList<String>();
        for (final Expression<?> groupByExpression : sql.getGroupBy().getExpressions()) {
            groupByExpressions.add(groupByExpression.toString());
        }
        return groupByExpressions;
    }

    public Collection<String> getFromSymbols() {
        return getSymbols(sql.getFrom());
    }

    public Collection<String> getUsedTables() {
        final TableNameCollector collector = new TableNameCollector();
        collector.process(sql.getSelect());
        collector.process(sql.getWhere());
        collector.process(sql.getOrderBy());
        collector.process(sql.getGroupBy());
        collector.process(sql.getHaving());
        return collector.getResult();
    }

    public static Collection<String> getSymbols(final Expression expression) {
        final ExpressionProcessor<Collection<String>> symbolCollector = new SymbolCollector();
        symbolCollector.process(expression);
        return symbolCollector.getResult();
    }

    private static class SymbolCollector implements ExpressionProcessor<Collection<String>> {
        final Collection<String> symbols = new ArrayList<String>();

        public void process(final Expression<?> expression) {
            if (expression instanceof SelectPartExpression) {
                expression.process(this);
                return;
            }
            if (expression instanceof Aliased && ((Aliased) expression).getAlias() != null) {
                symbols.add(((Aliased) expression).getAlias());
            } else {
                symbols.add(expression.toString());
            }
        }

        public Collection<String> getResult() {
            return symbols;
        }
    }

    private static class LeafExpressionCollector implements ExpressionProcessor<Collection<Expression<?>>> {
        final Collection<Expression<?>> expressions = new ArrayList<Expression<?>>();

        public void process(final Expression<?> expression) {
            if (expression instanceof SelectPartExpression) {
                expression.process(this);
                return;
            }
            if (expression instanceof Aliased) {
                final Expression<?> aliased = ((Aliased<Expression<?>>) expression).getAliased();
                expressions.add(aliased);
            } else {
                expressions.add(expression);
            }
        }

        public Collection<Expression<?>> getResult() {
            return expressions;
        }
    }

    private static class TableNameCollector extends AbstractExpressionProcessor<Collection<String>> {
        final Collection<String> tables = new HashSet<String>();

        public void doProcess(final Expression<?> expression) {
            if (expression instanceof Field) {
                tables.add(((Field) expression).getTableName());
            }
        }

        public Collection<String> getResult() {
            return tables;
        }
    }
}
