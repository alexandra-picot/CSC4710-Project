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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBConnection dbConnection = new DBConnection();

        Map<String, String> paperDetails = new HashMap<>();
        ArrayList<String> pcMembers = new ArrayList<>();

        String[] pathInfo = req.getPathInfo().split("/");
        String paperId = pathInfo[1];

        ResultSet list = null;

        try {
            Statement statementPcMembers = dbConnection.getConnection().createStatement();
            ResultSet rsPcMembers = statementPcMembers.executeQuery(" SELECT * FROM pc_members");

            while (rsPcMembers.next()) {
                pcMembers.add(rsPcMembers.getString("email"));
            }
//
//
//            Statement statementPaper = dbConnection.getConnection().createStatement();
//            ResultSet rsPaper = statementPaper.executeQuery("SELECT * FROM papers WHERE paperid = $(paperId)");
//
//            if (rsPaper.first()) {
//                paperDetails.put("paperid", String.valueOf(rsPaper.getInt("paperid")));
//                paperDetails.put("title", rsPaper.getString("title"));
//                paperDetails.put("abstract", rsPaper.getString("abstract"));
//                paperDetails.put("pdf", rsPaper.getString("pdf"));
//
//            }
        } catch (Exception e) {
            System.out.println(e);
        }

        dbConnection.closeConnection();

        req.setAttribute("paperDetails", paperDetails);
        req.setAttribute("pcMembers", pcMembers);
        RequestDispatcher view = req.getRequestDispatcher("/paper-details.jsp");
        view.forward(req, resp);
    }
}