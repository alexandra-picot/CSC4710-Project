package edu.wsu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet("/paper/edit-paper/*")
public class EditPaper extends AddEditPaperCommon {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> paperDetails;
        ArrayList<Map<String, String>> paperAuthors;
        ArrayList<Map<String, String>> pcMembers;
        ArrayList<String> paperReviewers;

        String[] pathInfo = req.getPathInfo().split("/");
        String paperId = pathInfo[1];

        _dbConnection = new DBConnection();

        paperDetails = SQLRequestsUtils.getPaperDetails(paperId, _dbConnection);
        paperAuthors = SQLRequestsUtils.getListOfAuthorsForPapersMap(paperId, _dbConnection);
        pcMembers = SQLRequestsUtils.getPcMembersListMap(_dbConnection);
        paperReviewers = SQLRequestsUtils.getPaperReviewers(paperId, _dbConnection);

        _dbConnection.closeConnection();

        req.setAttribute("paperDetails", paperDetails);
        req.setAttribute("paperAuthors", paperAuthors);
        req.setAttribute("pcMembers", pcMembers);
        req.setAttribute("paperReviewers", paperReviewers);

        req.getRequestDispatcher("/paper/edit-paper.jsp").forward(req, resp);
    }

    @Override
    void forwardError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/paper/edit-paper.jsp").forward(req, resp);
    }

    @Override
    void handlePaperDetails(Map<String, String> paperDetails) throws SQLException {
        String sql = "UPDATE papers " +
                "SET title = ?, abstract = ? WHERE paperid = ?";
        PreparedStatement statement = _dbConnection.getConnection().prepareStatement(sql);
        statement.setString(1, paperDetails.get("title"));
        statement.setString(2, paperDetails.get("description"));
        statement.setString(3, paperDetails.get("paperid"));

        statement.executeUpdate();
    }

    @Override
    void handleReviewers(ArrayList<String> paperReviewer, String paperId) throws SQLException {
        ArrayList<String> currentPaperReviewers = SQLRequestsUtils.getPaperReviewers(paperId, _dbConnection);
        ArrayList<String> similarReviewer = new ArrayList<>();

        for (String reviewer: currentPaperReviewers) {
            if (paperReviewer.contains(reviewer)) {
                similarReviewer.add(reviewer);
            }
        }

        for (String reviewer: similarReviewer) {
            paperReviewer.remove(reviewer);
            currentPaperReviewers.remove(reviewer);
        }

        if (!(currentPaperReviewers.isEmpty())) {
            ArrayList<String> lst = new ArrayList<>();
            for (String reviewer: currentPaperReviewers) {
                lst.add("pc_member_id LIKE '" + reviewer + "'");
            }
            String sql = String.join(" OR ", lst);

            sql = "DELETE FROM reports WHERE paper_id = " + paperId + " AND (" + sql + ")";
            System.out.println(sql);
            _dbConnection.createStatement().executeUpdate(sql);
        }

        createReportsForPaper(paperReviewer, paperId);
    }

    void updateAuthorContribution(ArrayList<PaperAuthor> authors) {
        for (PaperAuthor pa: authors) {
            SQLRequestsUtils.updateContribution(pa.paperId, pa.author.email, pa.contribution, _dbConnection);
        }
    }

    @Override
    void handleAuthors(ArrayList<Author> paperAuthors,
                       ArrayList<String> paperAuthorsEmails,
                       String paperId) throws SQLException {

        ArrayList<Author> currentPaperAuthors = SQLRequestsUtils.getListOfAuthorsForPapers(paperId, _dbConnection);
        ArrayList<String> currentPaperAuthorsEmails;
        ArrayList<Author> authorsList = SQLRequestsUtils.getAuthorsList(_dbConnection);
        ArrayList<String> authorsEmails;

        ArrayList<PaperAuthor> noChangeAuthors = new ArrayList<>();
        ArrayList<PaperAuthor> toUpdateAuthors = new ArrayList<>();
        ArrayList<PaperAuthor> toAddAuthorsDoesntExists = new ArrayList<>();
        ArrayList<PaperAuthor> toAddAuthorsExistsUpdate = new ArrayList<>();
        ArrayList<PaperAuthor> toAddAuthorsExists = new ArrayList<>();
        ArrayList<Author> toDeleteAuthors = new ArrayList<>();

        currentPaperAuthorsEmails = extractAuthorsEmail(currentPaperAuthors);

        authorsEmails = extractAuthorsEmail(authorsList);


        int contribution = 1;
        for (Author author: paperAuthors) {
            PaperAuthor pa = new PaperAuthor(author, paperId, contribution);
            if (currentPaperAuthors.contains(author)) {
                noChangeAuthors.add(pa);
            } else if (currentPaperAuthorsEmails.contains(author.email)) {
                toUpdateAuthors.add(pa);
            } else {
                if (authorsList.contains(author)) {
                    toAddAuthorsExists.add(pa);
                } else if (authorsEmails.contains(author.email)) {
                    toAddAuthorsExistsUpdate.add(pa);
                } else {
                    toAddAuthorsDoesntExists.add(pa);
                }
            }
            contribution++;
        }

        for (Author author: currentPaperAuthors) {
            if (!(paperAuthorsEmails.contains(author.email))) {
                toDeleteAuthors.add(author);
            }
        }

        if (!(toDeleteAuthors.isEmpty())) {
            ArrayList<String> lst = new ArrayList<>();
            for (Author author: toDeleteAuthors) {
                lst.add("author_id LIKE '" + author.email + "'");
            }
            String sql = String.join(" OR ", lst);

            sql = "DELETE FROM paper_authors WHERE paper_id = " + paperId + " AND (" + sql + ")";
            _dbConnection.createStatement().executeUpdate(sql);
        }

        createNewAuthors(toAddAuthorsDoesntExists);

        updateAuthorList(toAddAuthorsExistsUpdate);
        updateAuthorList(toUpdateAuthors);

        updateAuthorContribution(toUpdateAuthors);
        updateAuthorContribution(noChangeAuthors);

        linkAuthorToPaper(toAddAuthorsDoesntExists);
        linkAuthorToPaper(toAddAuthorsExists);
        linkAuthorToPaper(toAddAuthorsExistsUpdate);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

        req.getRequestDispatcher("/paper/successful-paper-edit.jsp").forward(req, resp);
    }
}
