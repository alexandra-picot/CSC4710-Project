package edu.wsu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet("/add-paper")
public class AddPaper extends HttpServlet {

    private DBConnection _dbConnection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Map<String, String>> pcMembers;

        _dbConnection = new DBConnection();

        pcMembers = SQLRequestsUtils.getPcMembersListMap(_dbConnection);

        _dbConnection.closeConnection();

        req.setAttribute("pcMembers", pcMembers);

        req.getRequestDispatcher("/add-paper.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.getRequestDispatcher("/successful-paper-add.jsp").forward(req, resp);
    }
}
