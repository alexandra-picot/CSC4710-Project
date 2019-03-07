package edu.wsu;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/createdb")
public class CreateDb extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>You've created the database and filled it with data</title>");
        writer.println("</head>");
        writer.println("<body bgcolor=white>");
        writer.println("<h1>You've created the database and filled it with data</h1>");
        writer.println("<p>");
        writer.println("<a href=\"index.html\">Go back to home</a>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
