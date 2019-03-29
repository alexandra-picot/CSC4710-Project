package edu.wsu;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/paper-list")
public class PaperList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBConnection dbConnection = new DBConnection();

        ArrayList<Map<String, String>> paperList = new ArrayList<>();

        try {
            Statement statement = dbConnection.createStatement();

            ResultSet res = statement.executeQuery( " SELECT * FROM papers");

            while (res.next()) {
                Map<String, String> tmp = new HashMap<>();
                tmp.put("paperid", String.valueOf(res.getInt("paperid")));
                tmp.put("title", res.getString("title"));
                tmp.put("abstract", res.getString("abstract"));
                paperList.add(tmp);
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        dbConnection.closeConnection();

        req.setAttribute("paperList", paperList);
        req.getRequestDispatcher("/paper-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBConnection dbConnection = new DBConnection();

        String searchInfo = req.getParameter("searchPaper");

        String firstName = req.getParameter("authorType");
//        System.out.println(firstName);
//        String[] values = req.getParameterValues("byFields");
//        if (values != null) {
//            for (String value: values) {
//                System.out.println(value);
//            }
//        }
//        String search
/*
* authorFirstName
* authorLastName
* authorAffiliation
* paperTitle
* paperDescription
*
*
* authorTypeSingle
* */
        ArrayList<Map<String, String>> paperList = new ArrayList<>();

        try {
            Statement statement = dbConnection.createStatement();

            ResultSet resPapers = statement.executeQuery(
                    "SELECT DISTINCT p.paperid, p.title, p.abstract " +
                            "FROM paper_authors pa " +
                            "INNER JOIN papers p on pa.paper_id = p.paperid " +
                            "INNER JOIN authors a on pa.author_id = a.email " +
                            "WHERE a.last_name LIKE '%" + searchInfo +"%' OR " +
                            "a.first_name LIKE '%" + searchInfo + "%' OR " +
                            "a.affiliation LIKE '%" + searchInfo + "%' OR " +
                            "p.title LIKE '%" + searchInfo + "%' OR " +
                            "p.abstract LIKE '%" + searchInfo + "%'"
            );

            while (resPapers.next()) {
                Map<String, String> tmp = new HashMap<>();
                tmp.put("paperid", String.valueOf(resPapers.getInt("paperid")));
                tmp.put("title", resPapers.getString("title"));
                tmp.put("abstract", resPapers.getString("abstract"));
                paperList.add(tmp);
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        dbConnection.closeConnection();

        req.setAttribute("paperList", paperList);
        req.getRequestDispatcher("/paper-list.jsp").forward(req, resp);
    }
}
