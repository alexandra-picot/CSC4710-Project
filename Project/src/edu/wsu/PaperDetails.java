package edu.wsu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/paperdetails/*")
public class PaperDetails extends HttpServlet {

    private String SQL_DRIVER = "com.mysql.jdbc.Driver";
    private String DATABASE_URL = "jdbc:mysql://localhost:3306/sampledb";
    private String USER = "john";
    private String PASSWORD = "pass1234";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultSet pcmembers = null;
        ResultSet list = null;

        try {
            Class.forName(SQL_DRIVER);

            Connection dbConnection = DriverManager.getConnection(
                    DATABASE_URL,
                    USER,
                    PASSWORD);

            Statement statement = dbConnection.createStatement();

//            list = statement.executeQuery(" SELECT * FROM papers"
//            );

            pcmembers = statement.executeQuery(" SELECT * FROM pc_members"
            );

            PrintWriter writer = resp.getWriter();
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>Review Page</title>");
            writer.println("</head>");
            writer.println("<body bgcolor=white>");

            writer.println("<select>");
            while (pcmembers.next())
            {
                String email = pcmembers.getString("email");
                String name = pcmembers.getString("name");

                writer.println("<option value = ' " + email + "'> " + name + "</option>");


            }
            writer.println("</select>");



            writer.println("</body>");

            writer.println("</html>");
            dbConnection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}