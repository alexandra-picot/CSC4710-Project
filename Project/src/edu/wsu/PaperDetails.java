package edu.wsu;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/paperdetails/*")
public class PaperDetails extends HttpServlet {

    private String SQL_DRIVER = "com.mysql.jdbc.Driver";
    private String DATABASE_URL = "jdbc:mysql://localhost:3306/sampledb";
    private String USER = "john";
    private String PASSWORD = "pass1234";

    private ArrayList<String> getPcMembersList() {
        DBConnection dbConnection = new DBConnection();

        ArrayList<String> pcMembers = new ArrayList<>();

        try {
            Statement statementPcMembers = dbConnection.getConnection().createStatement();
            ResultSet rsPcMembers = statementPcMembers.executeQuery(" SELECT * FROM pc_members");

            while (rsPcMembers.next()) {
                pcMembers.add(rsPcMembers.getString("email"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        dbConnection.closeConnection();
        return pcMembers;
    }

    private Map<String, String> getPaperDetails(String paperId) {
        DBConnection dbConnection = new DBConnection();

        Map<String, String> paperDetails = new HashMap<>();

        try {
            Statement statementPaper = dbConnection.getConnection().createStatement();
            ResultSet rsPaper = statementPaper.executeQuery("SELECT * FROM papers WHERE paperid = " + paperId);

            if (rsPaper.first()) {
                paperDetails.put("paperid", String.valueOf(rsPaper.getInt("paperid")));
                paperDetails.put("title", rsPaper.getString("title"));
                paperDetails.put("description", rsPaper.getString("abstract"));
                paperDetails.put("pdf", rsPaper.getString("pdf"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        dbConnection.closeConnection();
        return paperDetails;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> paperDetails = new HashMap<>();
        ArrayList<String> pcMembers = new ArrayList<>();

        String[] pathInfo = req.getPathInfo().split("/");
        String paperId = pathInfo[1];

        pcMembers = getPcMembersList();
        paperDetails = getPaperDetails(paperId);

        req.setAttribute("paperDetails", paperDetails);
        req.setAttribute("pcMembers", pcMembers);
        RequestDispatcher view = req.getRequestDispatcher("/paper-details.jsp");
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstReviewer = (String) req.getParameter("firstreviewer");
        String secondReviewer = (String) req.getParameter("secondreviewer");
        String thirdReviewer = (String) req.getParameter("thirdreviewer");
        String paperId = (String) req.getParameter("paperid");

        Boolean error = false;

        Map<String, String> errors = new HashMap<String, String>();
        req.setAttribute("messages", errors);
        if (firstReviewer == null || firstReviewer.isEmpty()) {
            errors.put("firstreviewer", "Please select a value!");
            error = true;
        }
        if (secondReviewer == null || secondReviewer.isEmpty()) {
            errors.put("secondreviewer", "Please select a value!");
            error = true;
        }
        if (thirdReviewer == null || thirdReviewer.isEmpty()) {
            errors.put("thirdreviewer", "Please select a value!");
            error = true;
        }
        if (error || paperId == null || paperId.isEmpty()) {
            req.setAttribute("paperDetails", getPaperDetails(paperId));
            req.setAttribute("pcMembers", getPcMembersList());
            RequestDispatcher view = req.getRequestDispatcher("/paper-details.jsp");
            view.forward(req, resp);
        }
        else {
            error = false;
            if (firstReviewer.equals(secondReviewer) || firstReviewer.equals(thirdReviewer)) {
                errors.put("firstreviewer", "You cannot assign twice the same reviewer to a paper!");
                error = true;
            }
            if (secondReviewer.equals(thirdReviewer)) {
                errors.put("secondreviewer", "You cannot assign twice the same reviewer to a paper!");
                error = true;
            }
            if (error) {
                req.setAttribute("paperDetails", getPaperDetails(paperId));
                req.setAttribute("pcMembers", getPcMembersList());
                RequestDispatcher view = req.getRequestDispatcher("/paper-details.jsp");
                view.forward(req, resp);
            } else {
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
                resp.sendRedirect(req.getContextPath() + "/paper-saved.html");
            }
        }
    }
}