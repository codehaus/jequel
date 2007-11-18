package de.jexp.jequel.literals;

/**
 * @author mh14 @ jexp.de
 * @since 24.10.2007 08:10:22 (c) 2007 jexp.de
 */
public enum SelectKeyword implements SqlKeyword {
    SELECT,
    FROM,
    WHERE,
    ORDER_BY,
    HAVING,
    GROUP_BY;

    public String getSqlKeyword() {
        return null;
    }
}
