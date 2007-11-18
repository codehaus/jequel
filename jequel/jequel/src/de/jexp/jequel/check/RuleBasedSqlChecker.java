package de.jexp.jequel.check;

import de.jexp.jequel.expression.Aliased;
import de.jexp.jequel.expression.Expression;
import de.jexp.jequel.expression.ExpressionProcessor;
import de.jexp.jequel.sql.Sql;
import de.jexp.jequel.table.Field;
import de.jexp.jequel.table.Table;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author mh14 @ jexp.de
 * @since 06.11.2007 23:43:30 (c) 2007 jexp.de
 *        check compliance of the sql by application of rules
 */
public class RuleBasedSqlChecker {
    public class TableIsUsedByOneFieldSpec extends CheckSpec {
        private String relationName;

        public TableIsUsedByOneFieldSpec(final String relationName) {
            this.relationName = relationName;
        }

        public void check(final Expression<?> expression) {
            if (expression instanceof Field) {
                if (((Field) expression).getTableName().equals(relationName)) setSatisfied();
            }
        }

    }

    private final Sql sql;

    public RuleBasedSqlChecker(final Sql sql) {
        this.sql = sql;
    }

    public TableUsageCheckResult checkTableUses() {
        final Collection<String> relationNames = getRelationNames();
        final TableUsageCheckResult tableUsageCheckResult = new TableUsageCheckResult(relationNames);
        for (final String relationName : relationNames) {
            final TableIsUsedByOneFieldSpec isUsedByOneFieldSpec = new TableIsUsedByOneFieldSpec(relationName);
            final SpecProcessor<TableIsUsedByOneFieldSpec> specProcessor = new SpecProcessor<TableIsUsedByOneFieldSpec>(isUsedByOneFieldSpec);
            specProcessor.process(sql);
            if (isUsedByOneFieldSpec.isSatisfied())
                tableUsageCheckResult.addUsedTable(relationName);
        }
        return null;
    }

    protected Collection<String> getRelationNames() {
        final Collection<String> relationNames = new ArrayList<String>(10);
        for (final Expression<?> relation : sql.getFrom().getExpressions()) {
            relationNames.add(getRelationName(relation));
        }
        return relationNames;
    }

    protected String getRelationName(final Expression<?> relation) {
        String relationName = null;
        if (relation instanceof Aliased) {
            Aliased aliased = (Aliased) relation;
            if (aliased.getAlias() != null) {
                relationName = aliased.getAlias();
            } else if (aliased instanceof Table) {
                relationName = ((Table) aliased).getName();
            }
        }
        return relationName;
    }

    class SpecProcessor<T extends CheckSpec> implements ExpressionProcessor<T> {
        private final T checkSpec;

        SpecProcessor(final T checkSpec) {
            this.checkSpec = checkSpec;
        }

        public void process(final Expression<?> expression) {
            checkSpec.check(expression);
            if (!checkSpec.isSatisfied() && !checkSpec.isViolated()) {
                expression.process(this);
            }
        }

        public T getResult() {
            return checkSpec;
        }
    }
}
