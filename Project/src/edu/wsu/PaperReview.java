package edu.wsu;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet("/paperlist")
public class PaperReview extends HttpServlet {

    private String SQL_DRIVER = "com.mysql.jdbc.Driver";
    private String DATABASE_URL = "jdbc:mysql://localhost:3306/sampledb";
    private String USER = "john";
    private String PASSWORD = "pass1234";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultSet list = null;

        try{
            Class.forName(SQL_DRIVER);

            Connection dbConnection = DriverManager.getConnection(
                    DATABASE_URL,
                    USER,
                    PASSWORD);

            Statement statement = dbConnection.createStatement();

            list = statement.executeQuery( " SELECT *FROM papers"
            );

            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>Sample Application Servlet Page</title>");
            writer.println("</head>");
            writer.println("<body bgcolor=white>");

            if (list == null)
            {
                writer.println("No Paper to show");
            }

            else
            {
                writer.println("<table style='border: 1px solid black'>");
                while (list.next())
                {
                    int paperId = list.getInt("paperid");
                    String header = list.getString("title");
                    String details = list.getString("abstract");
                    String source = list.getString("pdf");
                    writer.println("<tr style='border: 1px solid black'>");
                    writer.println("<th style='border: 1px solid black'> " + paperId + "</th>");
                    writer.println("<th style='border: 1px solid black'> " + header +"</th>");
                    writer.println("<th style='border: 1px solid black'> " + details + "</th>");
                    writer.println("<th style='border: 1px solid black'> " + source +"</th>");
                    writer.println("</tr>");

                }
                writer.println("</table>");
            }

            writer.println("</body>");

            writer.println("</html>");
            dbConnection.close();
        } catch(Exception e) {
            System.out.println(e);
        }

        /*
        writer.println("<table border=\"0\">");
        writer.println("<tr>");
        writer.println("<td>");
        writer.println("<img src=\"images/tomcat.gif\">");
        writer.println("</td>");
        writer.println("<td>");
        writer.println("<h1>Sample Application Servlet</h1>");
        writer.println("This is the output of a servlet that is part of");
        writer.println("the Hello, World application.");
        writer.println("</td>");
        writer.println("</tr>");
        writer.println("</table>");
        */


    }
}
