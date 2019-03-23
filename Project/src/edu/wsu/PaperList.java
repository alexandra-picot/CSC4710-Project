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
            Statement statement = dbConnection.getConnection().createStatement();

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
        RequestDispatcher view = req.getRequestDispatcher("/paper-list.jsp");
        view.forward(req, resp);
    }
}
