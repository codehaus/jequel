package de.jexp.jequel.expression;

import de.jexp.jequel.Delimeter;
import de.jexp.jequel.literals.SqlKeyword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * A Tupel of expressions rendered seperated by delimeter
 * a column or select list
 * @since 18.10.2007 23:40:44
 */
public abstract class AbstractTupleExpression extends AbstractExpression<Collection> implements TupleExpression {
    private final Collection<Expression> expressions = new ArrayList<Expression>();
    private final Delimeter delim;

    protected AbstractTupleExpression(final Delimeter delim, final Collection<? extends Expression> expressions) {
        this.expressions.addAll(expressions);
        this.delim = delim;
    }

    protected AbstractTupleExpression(final Delimeter delim, final Expression... expressions) {
        this(delim, Arrays.asList(expressions));
    }

    public String toString() {
        return accept(EXPRESSION_FORMAT);
    }

    public <R> R accept(final ExpressionVisitor<R> expressionVisitor) {
        return expressionVisitor.visit(this);
    }

    protected void append(final Collection<? extends Expression> expressions) {
        if (expressions == null || expressions.isEmpty()) return;
        this.expressions.addAll(expressions);
    }

    protected void append(final Expression... expressions) {
        if (expressions == null || expressions.length == 0) return;
        append(Arrays.asList(expressions));
    }

    public Collection<Expression> getExpressions() {
        return expressions;
    }

    public boolean hasValues() {
        return !expressions.isEmpty();
    }

    public <K> void process(final ExpressionProcessor<K> expressionProcessor) {
        for (final Expression<?> expression : getExpressions()) {
            expressionProcessor.process(expression);
        }
    }

    public SqlKeyword getDelimeter() {
        return delim;
    }

    public boolean isAtomic() {
        return false;
    }
}
