package de.jexp.bricksandmortar.database;

import com.ibatis.sqlmap.client.SqlMapClient;
import de.jexp.bricksandmortar.WorkflowStepTest;
import de.jexp.bricksandmortar.WorkflowStepTestUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.util.Collections;
import java.util.List;

public class IbatisQueryStepTest extends WorkflowStepTest<IbatisQueryStep> {
    public void testQueryStepInitTemplate() {
        assertNotNull(step.getSqlMapClientTemplate());
    }


    public void testQueryStepSelect() {
        step.setQuery("testSelect");
        runAndCheckResult();
    }

    public void testQueryStepSelectParam() {
        step.setQuery("testSelectParam");
        step.setQueryParams(Collections.singletonMap("name", WorkflowStepTestUtils.TEST_ARTICLE));
        runAndCheckResult();
    }

    private SqlMapClient createSqlMapClientTemplate() {
        final SqlMapClientFactoryBean sqlMapClientFactoryBean = new SqlMapClientFactoryBean();
        sqlMapClientFactoryBean.setDataSource(getHsqlDataSource());
        sqlMapClientFactoryBean.setConfigLocation(new ClassPathResource("de/jexp/bricksandmortar/spring/test.config.xml"));
        try {
            sqlMapClientFactoryBean.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (SqlMapClient) sqlMapClientFactoryBean.getObject();
    }

    public void testSqlClientTemplate() {
        final SqlMapClientTemplate client = new SqlMapClientTemplate(createSqlMapClientTemplate());
        final List result = client.queryForList("testSelect");
        WorkflowStepTestUtils.checkSelectList(result);
    }

    protected void setUp() {
        step = new IbatisQueryStep();
        step.setBeanName("testQueryStep");
        step.setSqlMapClient(createSqlMapClientTemplate());
    }
}
