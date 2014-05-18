package com.erin.book;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection connection = null;

    public Database(String driver, String url, String user, String password) {
        try {
            Class.forName(driver);
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(url);
            basicDataSource.setUsername(user);
            basicDataSource.setPassword(password);
            connection = basicDataSource.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertData(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        return  statement.execute(sql);
    }

    public ResultSet selectData(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }
}
