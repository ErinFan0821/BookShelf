package com.erin.book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

    String driver;
    String password;
    String url;
    String user;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection connection = getConnection();
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

    public void init() throws ServletException {
        driver = getInitParameter("DRIVER");
        password = getInitParameter("PASSWORD");
        url = getInitParameter("URL");
        user = getInitParameter("USER");
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
