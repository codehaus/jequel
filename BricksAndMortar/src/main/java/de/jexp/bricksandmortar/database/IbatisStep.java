package de.jexp.bricksandmortar.database;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import de.jexp.bricksandmortar.StepResult;
import de.jexp.bricksandmortar.database.SqlStep;


public abstract class IbatisStep<T extends StepResult<?>> extends SqlStep<T> {
    private SqlMapClientTemplate sqlMapClientTemplate;

    public void setSqlMapClient(final SqlMapClient sqlMapClient) {
        this.sqlMapClientTemplate = new SqlMapClientTemplate(sqlMapClient);
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClientTemplate.getSqlMapClient();
    }

    protected SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMapClientTemplate;
    }
    protected boolean validConfig() {
        if (getSqlMapClientTemplate() == null)
            throw new IllegalStateException(getClass().getSimpleName() + ":" + getName() + " missing ibatis config.");
        return true;
    }
}
