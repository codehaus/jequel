package de.jexp.bricksandmortar.database;

import org.springframework.jdbc.core.ColumnMapRowMapper;

public class UpcaseColumnMapRowMapper extends ColumnMapRowMapper {
    protected String getColumnKey(final String columnKey) {
        return super.getColumnKey(columnKey).toUpperCase();
    }
}
