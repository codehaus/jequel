package de.jexp.jequel.jdbctest;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mh14 @ jexp.de
 * @since 03.11.2007 08:12:07 (c) 2007 jexp.de
 */
public class TestDataSource implements DataSource {
    private static final PrintWriter PRINT_WRITER = new PrintWriter(System.err);
    private ResultSet resultSet;
    private int changeCount;

    public TestDataSource(final ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public TestDataSource(final int changeCount) {
        this.changeCount = changeCount;
    }

    public Connection getConnection() throws SQLException {
        return createConnection();
    }

    protected TestConnection createConnection() {
        return new TestConnection(resultSet, changeCount);
    }

    public Connection getConnection(final String s, final String s1) throws SQLException {
        return createConnection();
    }

    public PrintWriter getLogWriter() throws SQLException {
        return PRINT_WRITER;
    }

    public void setLogWriter(final PrintWriter printWriter) throws SQLException {
    }

    public void setLoginTimeout(final int i) throws SQLException {
    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }
}
