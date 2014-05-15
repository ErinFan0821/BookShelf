package com.erin.book;

import org.apache.commons.dbcp.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by techops on 4/24/14.
 */
public class BookShelfServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int isbn = Integer.parseInt(request.getParameter("isbn"));
        String name = request.getParameter("name");
        Double price = Double.parseDouble(request.getParameter("price"));
        String author = request.getParameter("author");

        try {
            Class.forName("com.mysql.jdbc.Driver");

            String query = " insert into Book (isbn, name, price, author)"
                    + " values (?, ?, ?, ?)";

            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl("jdbc:mysql://localhost:3306/BookShelf");
            basicDataSource.setUsername("root");
            basicDataSource.setPassword("root");
            Connection connection = basicDataSource.getConnection();

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, isbn);
            preparedStmt.setString(2, name);
            preparedStmt.setDouble(3, price);
            preparedStmt.setString(4, author);
            preparedStmt.execute();

            preparedStmt = connection.prepareStatement("select * from Book");
            ResultSet resultSet = preparedStmt.executeQuery();
            List<BookItem> books = new ArrayList<>();
            while (resultSet.next()) {
                BookItem bookItem = new BookItem();
                bookItem.setName(resultSet.getString("name"));
                bookItem.setIsbn(resultSet.getInt("isbn"));
                bookItem.setPrice(resultSet.getDouble("price"));
                bookItem.setAuthor(resultSet.getString("author"));
                books.add(bookItem);
            }
            request.setAttribute("books", books);
            request.getRequestDispatcher("/WEB-INF/pages/showBooks.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("Connect Failed: " + e.getMessage());
        }

}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
    }
}
