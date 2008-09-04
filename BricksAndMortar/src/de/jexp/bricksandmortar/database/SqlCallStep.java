package de.jexp.bricksandmortar.database;

import de.jexp.bricksandmortar.results.ListStepResult;

import java.util.Map;
import java.util.Collections;

import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;

import javax.sql.DataSource;

/**
 * @author mhu@salt-solutions.de
 * @copyright (c) Salt Solutions GmbH 2006
 * @since 03.12.2007 10:30:13
 */
public class SqlCallStep extends SqlStep<ListStepResult> {
    private DataSource dataSource;
    private Map<String,String> paramTypes;
    protected ListStepResult execute(final Map row, final int count) {
        final StoredProcedure storedProcedure=new StoredProcedure(dataSource,getQuery()) {};
        declareParameters(row, storedProcedure);
        final Map params = row!=null ? row : Collections.emptyMap();
        final Map procedureResult = storedProcedure.execute(params);
        return new ListStepResult(createResultName(count), Collections.<Map<String, ?>>singletonList(procedureResult));
    }

    protected void declareParameters(final Map row, final StoredProcedure storedProcedure) {
        final Map<String, String> paramTypes = getParamTypes();
        if (paramTypes==null) return;
        for (final String paramName : paramTypes.keySet()) {
            final SqlParameter sqlParameter = createParameter(paramName, row);
            storedProcedure.declareParameter(sqlParameter);
        }
    }

    private SqlParameter createParameter(final String paramName, final Map row) {
        final SqlParameter sqlParameter;
        if (row!=null && row.containsKey(paramName)) {
            sqlParameter = new SqlParameter(paramName, typeForName(paramTypes.get(paramName)));
        } else {
            sqlParameter = new SqlOutParameter(paramName, typeForName(paramTypes.get(paramName)));
        }
        return sqlParameter;
    }

    private int typeForName(final String type) {
        return SqlTypeUtils.getType(type);
    }

    protected boolean validConfig() {
        if (dataSource == null)
            throw new IllegalStateException(this + " missing DataSource");
        return true;
    }


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, String> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(final Map<String, String> paramTypes) {
        this.paramTypes = paramTypes;
    }
}
