import org.apache.commons.dbcp.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            final ResultSet resultSet = preparedStmt.executeQuery();
            PrintWriter writer = response.getWriter();
            while (resultSet.next()) {
                isbn = resultSet.getInt("isbn");
                name = resultSet.getString("name");
                price = resultSet.getDouble("price");
                author = resultSet.getString("author");
                System.out.println(isbn + ";" + name + ";" + price + ";" + author);
                writer.println("<h1>" + "BookShelf" + "</h1>");
                writer.println("ISBN:" + isbn);
                writer.println("BookName:" + name);
                writer.println("Price:" + price);
                writer.println("Author:" + author);

            }

        } catch (Exception e) {
            System.out.println("Connect Failed: " + e.getMessage());
        }

}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        writer.println("<h1>" + "BookShelf" + "</h1>");
        writer.println("<form action=\"add\" method=\"post\">"+
                "ISBN: <input type=\"text\" name=\"isbn\" />\n" +
                "Name: <input type=\"text\" name=\"name\" />\n" +
                "Price: <input type=\"text\" name=\"price\" />\n"+
                "author: <input type=\"text\" name=\"author\" />\n"+
                "<input type=\"submit\" value=\"Submit\" />\n"+
                "</form>"
        );
    }
}
