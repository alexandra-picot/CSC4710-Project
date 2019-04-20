package edu.wsu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

@WebServlet("/add-paper")
public class AddPaper extends AddEditPaperCommon {

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
    void forwardError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-paper.jsp").forward(req, resp);
    }

    @Override
    void handlePaperDetails(Map<String, String> paperDetails) throws SQLException {
        String sql = "INSERT INTO papers (title, abstract, pdf) VALUES (" +
                "'" + paperDetails.get("title") + "', '" + paperDetails.get("description") + "', 'home/papers/paper')";
        PreparedStatement statement = _dbConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.executeUpdate();
        try {
            ResultSet rsGeneratedKeys = statement.getGeneratedKeys();

            if (rsGeneratedKeys.first()) {
                paperDetails.put("paperid", String.valueOf(rsGeneratedKeys.getLong(1)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    void handleAuthors(ArrayList<Author> paperAuthors,
                       ArrayList<String> paperAuthorsEmails,
                       String paperId) throws SQLException {
        ArrayList<Author> authorsList = SQLRequestsUtils.getAuthorsList(_dbConnection);
        ArrayList<String> authorsEmails;

        ArrayList<PaperAuthor> toAddAuthorsDoesntExists = new ArrayList<>();
        ArrayList<PaperAuthor> toAddAuthorsExistsUpdate = new ArrayList<>();
        ArrayList<PaperAuthor> toAddAuthorsExists = new ArrayList<>();

        authorsEmails = extractAuthorsEmail(authorsList);

        int contribution = 1;
        for (Author author: paperAuthors) {
            PaperAuthor pa = new PaperAuthor(author, paperId, contribution);
            if (authorsList.contains(author)) {
                toAddAuthorsExists.add(pa);
            } else if (authorsEmails.contains(author.email)) {
                toAddAuthorsExistsUpdate.add(pa);
            } else {
                toAddAuthorsDoesntExists.add(pa);
            }
            contribution++;
        }

        createNewAuthors(toAddAuthorsDoesntExists);

        updateAuthorList(toAddAuthorsExistsUpdate);

        linkAuthorToPaper(toAddAuthorsDoesntExists);
        linkAuthorToPaper(toAddAuthorsExists);
        linkAuthorToPaper(toAddAuthorsExistsUpdate);
    }

    @Override
    void handleReviewers(ArrayList<String> paperReviewer,
                         String paperId) throws SQLException {
        createReportsForPaper(paperReviewer, paperId);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

        req.getRequestDispatcher("/successful-paper-add.jsp").forward(req, resp);
    }
}
