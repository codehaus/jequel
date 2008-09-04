package de.jexp.bricksandmortar.database;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author mhu@salt-solutions.de
 * @copyright (c) Salt Solutions GmbH 2006
 * @since 10.01.2008 12:04:19
 */
public class InformixDataSource extends DriverManagerDataSource {
    protected Connection getConnectionFromDriverManager(final String url, final Properties props) throws SQLException {
        final Connection connection = super.getConnectionFromDriverManager(url, props);
        return prepareConnection(connection);
    }

    protected Connection prepareConnection(final Connection connection) throws SQLException {
        final Statement statement = connection.createStatement();
        statement.execute("set isolation to dirty read");
        return connection;
    }
}
