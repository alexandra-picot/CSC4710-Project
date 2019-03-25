package edu.wsu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Statement;

@WebServlet("/savepaperreviewers")
public class SavePaperReviewers extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstReviewer = req.getParameter("firstReviewer");
        String secondReviewer = req.getParameter("secondReviewer");
        String thirdReviewer = req.getParameter("thirdReviewer");
        String paperId = req.getParameter("paperid");

        DBConnection dbConnection = new DBConnection();

        try {
            Statement statement = dbConnection.getConnection().createStatement();

            statement.executeUpdate("INSERT INTO reports (paper_id, pc_member_id) VALUES ('" + paperId + "', '" + firstReviewer + "'), " +
                    "('" + paperId + "', '" + secondReviewer + "'), " +
                    "('" + paperId + "', '" + thirdReviewer + "')");
        } catch (Exception e) {
            System.out.println(e);
        }

        dbConnection.closeConnection();

        req.getRequestDispatcher("/save-paper-reviewers.html").forward(req, resp);
    }
}
