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
 * Created by techops on 5/16/14.
 */
public class BookShelfShowBooksServlet extends HttpServlet {
    public static final String NAME = "name";
    public static final String ISBN = "isbn";
    public static final String PRICE = "price";
    public static final String AUTHOR = "author";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl("jdbc:mysql://localhost:3306/BookShelf");
            basicDataSource.setUsername("root");
            basicDataSource.setPassword("root");
            Connection connection = basicDataSource.getConnection();

            PreparedStatement preparedStmt = connection.prepareStatement("select * from Book");
            ResultSet resultSet = preparedStmt.executeQuery();
            List<BookItem> books = new ArrayList<>();
            while (resultSet.next()) {
                BookItem bookItem = new BookItem();
                bookItem.setName(resultSet.getString(NAME));
                bookItem.setIsbn(resultSet.getInt(ISBN));
                bookItem.setPrice(resultSet.getDouble(PRICE));
                bookItem.setAuthor(resultSet.getString(AUTHOR));
                books.add(bookItem);
            }
            request.setAttribute("books", books);
            request.getRequestDispatcher("/WEB-INF/pages/showBooks.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("Connect Failed: " + e.getMessage());
        }

    }
}
