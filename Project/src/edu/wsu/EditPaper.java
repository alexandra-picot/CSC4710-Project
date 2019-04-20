package edu.wsu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/edit-paper/*")
public class EditPaper extends HttpServlet {

    private DBConnection _dbConnection;

    private static ArrayList<String> letterEnumeration = new ArrayList<>();
    static {
        letterEnumeration.add("First");
        letterEnumeration.add("Second");
        letterEnumeration.add("Third");
        letterEnumeration.add("Fourth");
        letterEnumeration.add("Fifth");
    }

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

        req.getRequestDispatcher("/edit-paper.jsp").forward(req, resp);
    }

    private void handleReviewers(ArrayList<String> paperReviewer, String paperId, Statement statement) throws SQLException {
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
            statement.executeUpdate(sql);
        }

        if (!(paperReviewer.isEmpty())) {
            ArrayList<String> lst = new ArrayList<>();
            for (String reviewer: paperReviewer) {
                lst.add("('" + paperId + "', '" + reviewer + "')");
            }
            String sql = String.join(", ", lst);
            sql = "INSERT INTO reports (paper_id, pc_member_id) VALUES " + sql;
            statement.executeUpdate(sql);
        }
    }

    private void updateAuthorList(ArrayList<PaperAuthor> authors) {
        for (PaperAuthor pa: authors) {
            SQLRequestsUtils.updateAuthor(pa.author, _dbConnection);
        }
    }

    private void updateAuthorContribution(ArrayList<PaperAuthor> authors) {
        for (PaperAuthor pa: authors) {
            SQLRequestsUtils.updateContribution(pa.paperId, pa.author.email, pa.contribution, _dbConnection);
        }
    }

    private void linkAuthorToPaper(ArrayList<PaperAuthor> authors) throws SQLException {
        if (authors.isEmpty()) {
            return;
        }
        ArrayList<String> list = new ArrayList<>();
        for (PaperAuthor pa: authors) {
                list.add("('" + pa.paperId + "', '" + pa.author.email + "', '" + pa.contribution + "')");
        }
        String sql = String.join(", ", list);
        sql = "INSERT INTO paper_authors (paper_id, author_id, contribution_significance) VALUES " + sql;
        System.out.println(sql);
        _dbConnection.createStatement().executeUpdate(sql);
    }

    private void handleAuthors(ArrayList<Author> paperAuthors,
                               ArrayList<String> paperAuthorsEmails,
                               String paperId,
                               Statement statement) throws SQLException {

        ArrayList<Author> currentPaperAuthors = SQLRequestsUtils.getListOfAuthorsForPapers(paperId, _dbConnection);
        ArrayList<String> currentPaperAuthorsEmails = new ArrayList<>();
        ArrayList<Author> authorsList = SQLRequestsUtils.getAuthorsList(_dbConnection);
        ArrayList<String> authorsEmails = new ArrayList<>();

        ArrayList<PaperAuthor> noChangeAuthors = new ArrayList<>();
        ArrayList<PaperAuthor> toUpdateAuthors = new ArrayList<>();
        ArrayList<PaperAuthor> toAddAuthorsDoesntExists = new ArrayList<>();
        ArrayList<PaperAuthor> toAddAuthorsExistsUpdate = new ArrayList<>();
        ArrayList<PaperAuthor> toAddAuthorsExists = new ArrayList<>();
        ArrayList<Author> toDeleteAuthors = new ArrayList<>();

        for (Author author: currentPaperAuthors) {
            currentPaperAuthorsEmails.add(author.email);
        }

        for (Author author: authorsList) {
            authorsEmails.add(author.email);
        }

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
            statement.executeUpdate(sql);
        }

        if (!(toAddAuthorsDoesntExists.isEmpty())) {
            ArrayList<String> lst_add_author = new ArrayList<>();
            for (PaperAuthor pa: toAddAuthorsDoesntExists) {
                lst_add_author.add("('" + pa.author.email + "', '" + pa.author.firstName + "', '" + pa.author.lastName + "', 'Computer Science')");
            }
            String sql = String.join(", ", lst_add_author);
            sql = "INSERT INTO authors (email, first_name, last_name, affiliation) VALUES " + sql;
            statement.executeUpdate(sql);
        }

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
        ArrayList<String> errors = new ArrayList<>();
        ArrayList<Author> paperAuthors = new ArrayList<>();
        ArrayList<String> paperAuthorsEmails = new ArrayList<>();
        ArrayList<Map<String, String>> paperAuthorsMap = new ArrayList<>();

        Map<String, String> paperDetails = new HashMap<>();
        ArrayList<String> paperReviewers = new ArrayList<>();
        String paperId;

        paperId = req.getParameter("paperid").trim();
        paperDetails.put("paperid", paperId);
        paperDetails.put("title", req.getParameter("paperTitle").trim());
        paperDetails.put("description", req.getParameter("paperDescription").trim());

        if (paperDetails.get("title").isEmpty()) {
            errors.add("Error: You cannot have a paper without titles.");
        }
        if (paperDetails.get("description").isEmpty()) {
            errors.add("Error: You cannot have a paper without description.");
        }

        for (String number: letterEnumeration) {
            Map<String, String> tmp = new HashMap<>();

            String email = req.getParameter("email" + number + "Author").trim();
            String firstName = req.getParameter("firstName" + number + "Author").trim();
            String lastName = req.getParameter("lastName" + number + "Author").trim();
            tmp.put(Person.PERSON_EMAIL_KEY, email);
            tmp.put(Person.PERSON_FIRSTNAME_KEY, firstName);
            tmp.put(Person.PERSON_LASTNAME_KEY, lastName);
            paperAuthorsMap.add(tmp);
            if (email.isEmpty() && firstName.isEmpty() && lastName.isEmpty()) {
                continue;
            }
            if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                errors.add("Error: Please make sure that you fill every information on the " + number.toLowerCase() + " author.");
                continue;
            }
            if (paperAuthorsEmails.contains(email)) {
                errors.add("Error: The email in " + number.toLowerCase() + " author is already used in a previous author.");
                continue;
            }
            paperAuthorsEmails.add(email);
            Author author = new Author(
                    email,
                    firstName,
                    lastName
            );
            paperAuthors.add(author);
        }

        if (paperAuthors.isEmpty()) {
            errors.add("Error: You cannot have a paper without at least one author.");
        }

        for (int i = 0; i < 3; i++) {
            String tmp = req.getParameter("paper" + letterEnumeration.get(i) + "Reviewer").trim();
            if (tmp.isEmpty()) {
                continue;
            }
            if (paperReviewers.contains(tmp)) {
                errors.add("Error: The " + letterEnumeration.get(i).toLowerCase() + " reviewer is already assigned in a previous reviewer.");
            }
            paperReviewers.add(tmp);
        }

        _dbConnection = new DBConnection();

        if (!(errors.isEmpty())) {
            req.setAttribute("paperDetails", paperDetails);
            req.setAttribute("paperAuthors", paperAuthorsMap);
            req.setAttribute("pcMembers", SQLRequestsUtils.getPcMembersListMap(_dbConnection));
            req.setAttribute("paperReviewers", paperReviewers);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/edit-paper.jsp").forward(req, resp);
            return;
        }

        try {
            Statement statement = _dbConnection.createStatement();

            String sql = "UPDATE papers " +
                    "SET title = '" + paperDetails.get("title") + "', abstract = '" + paperDetails.get("description") +
                    "' WHERE paperid = " + paperId;
            statement.executeUpdate(sql);

            handleAuthors(paperAuthors, paperAuthorsEmails, paperId, statement);

            handleReviewers(paperReviewers, paperId, statement);

        } catch (Exception e) {
            System.out.println(e);
            _dbConnection.closeConnection();
            req.getRequestDispatcher("/paper-list").forward(req, resp);
            return;
        }

        _dbConnection.closeConnection();

        req.setAttribute("paperTitle", paperDetails.get("title"));
        req.getRequestDispatcher("/successful-paper-edit.jsp").forward(req, resp);
    }
}
