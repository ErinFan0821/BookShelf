package com.erin.book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class BookShelfServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Database database = (Database) getServletContext().getAttribute("database");

        int isbn = Integer.parseInt(request.getParameter("isbn"));
        String name = request.getParameter("name");
        Double price = Double.parseDouble(request.getParameter("price"));
        String author = request.getParameter("author");

        String query = " insert into Book (isbn, name, price, author)"
                + " values(" + isbn + ", '" + name + "', " + price + ", '" + author + "')";

        System.out.println(query);

        try {
            database.insertData(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/web/showBook");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
    }
}


