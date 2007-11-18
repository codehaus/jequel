package de.jexp.jequel;

import de.jexp.jequel.literals.SqlKeyword;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 23:42:58
 */
public enum Delimeter implements SqlKeyword {
    COMMA(", "), EMPTY(""), POINT("."), SPACE(" "), SHARP("#");

    private final String delimString;

    Delimeter(final String delimString) {
        this.delimString = delimString;
    }

    public String toString() {
        return delimString;
    }

    public String getSqlKeyword() {
        return delimString;
    }
}
