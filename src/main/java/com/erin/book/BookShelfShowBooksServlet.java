package com.erin.book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookShelfShowBooksServlet extends HttpServlet {
    public static final String NAME = "name";
    public static final String ISBN = "isbn";
    public static final String PRICE = "price";
    public static final String AUTHOR = "author";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Database database = (Database) getServletContext().getAttribute("database");

            final String sql = "select * from Book";
            ResultSet resultSet = database.selectData(sql);
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
