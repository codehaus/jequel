package de.jexp.jequel.execute;

import de.jexp.jequel.generator.GeneratorTestUtils;

public class HsqlStatementTest extends AbstractStatementTest {
    protected void setUp() throws Exception {
        dataSource = GeneratorTestUtils.createAndSetupHsqlDatasource();
        executableStatement = articleSql.executeOn(dataSource);
    }

    protected void tearDown() throws Exception {
        GeneratorTestUtils.closeDatabase();
    }
}
