package de.jexp.jequel.jdbctest;

import java.sql.*;
import java.util.Map;

/**
 * @author mh14 @ jexp.de
 * @since 03.11.2007 08:11:19 (c) 2007 jexp.de
 */
public class TestConnection implements Connection {
    private ResultSet resultSet;
    private int changeCount;

    public TestConnection(final ResultSet resultSet, final int changeCount) {
        this.resultSet = resultSet;
        this.changeCount = changeCount;
    }

    public Statement createStatement() throws SQLException {
        return new TestStatement(this, resultSet, changeCount);
    }

    public PreparedStatement prepareStatement(final String s) throws SQLException {
        return null;
    }

    public CallableStatement prepareCall(final String s) throws SQLException {
        return null;
    }

    public String nativeSQL(final String s) throws SQLException {
        return null;
    }

    public void setAutoCommit(final boolean b) throws SQLException {
    }

    public boolean getAutoCommit() throws SQLException {
        return true;
    }

    public void commit() throws SQLException {
    }

    public void rollback() throws SQLException {
    }

    public void close() throws SQLException {
    }

    public boolean isClosed() throws SQLException {
        return false;
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }

    public void setReadOnly(final boolean b) throws SQLException {
    }

    public boolean isReadOnly() throws SQLException {
        return false;
    }

    public void setCatalog(final String s) throws SQLException {
    }

    public String getCatalog() throws SQLException {
        return null;
    }

    public void setTransactionIsolation(final int i) throws SQLException {
    }

    public int getTransactionIsolation() throws SQLException {
        return 0;
    }

    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    public void clearWarnings() throws SQLException {
    }

    public Statement createStatement(final int i, final int i1) throws SQLException {
        return null;
    }

    public PreparedStatement prepareStatement(final String s, final int i, final int i1) throws SQLException {
        return null;
    }

    public CallableStatement prepareCall(final String s, final int i, final int i1) throws SQLException {
        return null;
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;
    }

    public void setTypeMap(final Map<String, Class<?>> stringClassMap) throws SQLException {
    }

    public void setHoldability(final int i) throws SQLException {
    }

    public int getHoldability() throws SQLException {
        return 0;
    }

    public Savepoint setSavepoint() throws SQLException {
        return null;
    }

    public Savepoint setSavepoint(final String s) throws SQLException {
        return null;
    }

    public void rollback(final Savepoint savepoint) throws SQLException {
    }

    public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
    }

    public Statement createStatement(final int i, final int i1, final int i2) throws SQLException {
        return null;
    }

    public PreparedStatement prepareStatement(final String s, final int i, final int i1, final int i2) throws SQLException {
        return null;
    }

    public CallableStatement prepareCall(final String s, final int i, final int i1, final int i2) throws SQLException {
        return null;
    }

    public PreparedStatement prepareStatement(final String s, final int i) throws SQLException {
        return null;
    }

    public PreparedStatement prepareStatement(final String s, final int[] ints) throws SQLException {
        return null;
    }

    public PreparedStatement prepareStatement(final String s, final String[] strings) throws SQLException {
        return null;
    }
}
