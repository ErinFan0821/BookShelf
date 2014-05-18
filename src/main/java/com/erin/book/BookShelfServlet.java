package com.erin.book;

import org.apache.commons.dbcp.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookShelfServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = getConnection();
        int isbn = Integer.parseInt(request.getParameter("isbn"));
        String name = request.getParameter("name");
        Double price = Double.parseDouble(request.getParameter("price"));
        String author = request.getParameter("author");


        String query = " insert into Book (isbn, name, price, author)"
                + " values (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, isbn);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, author);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/BookShelf/showBook");
    }

    private Connection getConnection() {
            Connection connection = null;
            try {
                String driver = getInitParameter("DRIVER");
                String password = getInitParameter("PASSWORD");
                String url = getInitParameter("URL");
                String user = getInitParameter("USER");

                Class.forName(driver);

                BasicDataSource basicDataSource = new BasicDataSource();
                basicDataSource.setUrl(url);
                basicDataSource.setUsername(user);
                basicDataSource.setPassword(password);
                connection = basicDataSource.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return connection;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
    }
}
