package de.jexp.bricksandmortar.database;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

import de.jexp.bricksandmortar.StepResult;
import de.jexp.bricksandmortar.database.SqlStep;

/**
 * Created by mh14 on 05.07.2007 21:31:22
 */
public abstract class NamedParameterStep<T extends StepResult<?>> extends SqlStep<T> {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    protected NamedParameterJdbcTemplate getNamedJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    public DataSource getDataSource() {
        return dataSource;
    }

    protected boolean validConfig() {
        if (getNamedJdbcTemplate() == null)
            throw new IllegalStateException(this + " missing jdbc template");
        return true;
    }
}