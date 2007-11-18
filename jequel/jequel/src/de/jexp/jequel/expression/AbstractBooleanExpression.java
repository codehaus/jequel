package de.jexp.jequel.expression;

import de.jexp.jequel.literals.Operator;

/**
 * @author mh14 @ jexp.de
 * @since 22.10.2007 00:41:37 (c) 2007 jexp.de
 */
public abstract class AbstractBooleanExpression extends AbstractExpression<Boolean> implements BooleanExpression {
    public BooleanExpression and(final BooleanExpression expression) {
        return createBinaryBooleanExpression(Operator.AND, expression);
    }

    public BooleanExpression or(final BooleanExpression expression) {
        return createBinaryBooleanExpression(Operator.OR, expression);
    }

    public BooleanExpression OR(final BooleanExpression expression) {
        return new BooleanBinaryExpression(this, Operator.OR, expression) {
            public boolean isParenthesed() {
                return true;
            }
        };
    }

    public BooleanExpressionAlias as(final String alias) {
        return new BooleanExpressionAlias(alias);
    }

    private class BooleanExpressionAlias extends DefaultExpressionAlias<Boolean, BooleanExpression> implements BooleanExpression {
        public BooleanExpressionAlias(final String alias) {
            super(AbstractBooleanExpression.this, alias);
        }

        public BooleanExpression and(final BooleanExpression expression) {
            return getAliased().and(expression);
        }

        public BooleanExpression or(final BooleanExpression expression) {
            return getAliased().or(expression);
        }

        public BooleanExpression OR(final BooleanExpression expression) {
            return getAliased().OR(expression);
        }
    }
}
