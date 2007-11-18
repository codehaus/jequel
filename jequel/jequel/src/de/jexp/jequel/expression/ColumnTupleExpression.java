package de.jexp.jequel.expression;

import de.jexp.jequel.Delimeter;

import java.util.Collection;

/**
 * @author mh14 @ jexp.de
 * @since 21.10.2007 22:35:08 (c) 2007 jexp.de
 *        dynamic set of expressions, instance of Collection
 */
public class ColumnTupleExpression extends AbstractTupleExpression {
    public ColumnTupleExpression(final Delimeter delim, final Collection<? extends Expression<?>> expressions) {
        super(delim, expressions);
    }

    public ColumnTupleExpression(final Delimeter delim, final Expression<?>... expressions) {
        super(delim, expressions);
    }

    public void append(final Collection<? extends Expression> expressions) {
        super.append(expressions);
    }

    public void append(final Expression... expressions) {
        super.append(expressions);
    }
}