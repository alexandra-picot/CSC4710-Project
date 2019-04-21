package edu.wsu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Statement;

@WebServlet("/paper/delete-paper/*")
public class DeletePaper extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] pathInfo = req.getPathInfo().split("/");
        String paperId = pathInfo[1];

        System.out.println(paperId);
        DBConnection dbConnection = new DBConnection();
        try {
            Statement statement = dbConnection.createStatement();

            statement.execute("DELETE FROM papers WHERE paperid = " + paperId);
        } catch (Exception e) {
            System.out.println(e);
        }
        dbConnection.closeConnection();
        req.getRequestDispatcher("/paper/paper-list").forward(req, resp);
    }
}
