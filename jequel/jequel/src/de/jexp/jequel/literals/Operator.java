package de.jexp.jequel.literals;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 00:47:09
 */
public enum Operator implements SqlKeyword {
    LE("<="),
    GE(">="),
    NE("!="),
    LT("<"),
    GT(">"),
    EQ("="),
    LIKE,
    AND,
    OR,
    IS_NOT,
    IS,
    IN,
    BETWEEN,
    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    BY("/");

    Operator() {
        this(null);
    }

    private final String sqlOperator;

    Operator(final String sqlOperator) {
        this.sqlOperator = sqlOperator;
    }

    public String getSqlKeyword() {
        return sqlOperator;
    }
}
