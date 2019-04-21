package edu.wsu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AddEditPaperCommon extends HttpServlet {

    DBConnection _dbConnection;

    static ArrayList<String> letterEnumeration = new ArrayList<>();

    static {
        letterEnumeration.add("First");
        letterEnumeration.add("Second");
        letterEnumeration.add("Third");
        letterEnumeration.add("Fourth");
        letterEnumeration.add("Fifth");
    }

    abstract void forwardError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    abstract void handlePaperDetails(Map<String, String> paperDetails) throws SQLException;

    abstract void handleReviewers(ArrayList<String> paperReviewer,
                                  String paperId) throws SQLException;

    abstract void handleAuthors(ArrayList<Author> paperAuthors,
                               ArrayList<String> paperAuthorsEmails,
                               String paperId) throws SQLException;

    ArrayList<String> extractAuthorsEmail(ArrayList<Author> authors) {
        ArrayList<String> res = new ArrayList<>();
        for (Author author: authors) {
            res.add(author.email);
        }
        return res;
    }

    void updateAuthorList(ArrayList<PaperAuthor> authors) {
        for (PaperAuthor pa: authors) {
            SQLRequestsUtils.updateAuthor(pa.author, _dbConnection);
        }
    }

    void createNewAuthors(ArrayList<PaperAuthor> authors) throws SQLException {
        if (authors.isEmpty()) {
            return;
        }
        ArrayList<String> list = new ArrayList<>();
        for (PaperAuthor pa: authors) {
            list.add("('" + pa.author.email + "', '" + pa.author.firstName + "', '" + pa.author.lastName + "', 'Computer Science')");
        }
        String sql = String.join(", ", list);
        sql = "INSERT INTO authors (email, first_name, last_name, affiliation) VALUES " + sql;
        _dbConnection.createStatement().executeUpdate(sql);
    }

    void linkAuthorToPaper(ArrayList<PaperAuthor> authors) throws SQLException {
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

    void createReportsForPaper(ArrayList<String> paperReviewer,
                               String paperId) throws SQLException {
        if (paperReviewer.isEmpty()) {
            return;
        }
        ArrayList<String> list = new ArrayList<>();
        for (String reviewer: paperReviewer) {
            list.add("('" + paperId + "', '" + reviewer + "')");
        }
        String sql = String.join(", ", list);
        sql = "INSERT INTO reports (paper_id, pc_member_id) VALUES " + sql;
        _dbConnection.createStatement().executeUpdate(sql);
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
            errors.add("Error: You cannot have a paper without title.");
        }
        if (paperDetails.get("description").isEmpty()) {
            errors.add("Error: You cannot have a paper without description.");
        }

        for (String number: letterEnumeration) {
            Map<String, String> tmp = new HashMap<>();

            String email = req.getParameter("email" + number + "Author").trim().toLowerCase();
            String firstName = req.getParameter("firstName" + number + "Author").trim().toLowerCase();
            String lastName = req.getParameter("lastName" + number + "Author").trim().toLowerCase();
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
            forwardError(req, resp);
            return;
        }

        try {
            handlePaperDetails(paperDetails);
            paperId = paperDetails.get("paperid");
            handleAuthors(paperAuthors, paperAuthorsEmails, paperId);
            handleReviewers(paperReviewers, paperId);
        } catch (Exception e) {
            System.out.println(e);
            _dbConnection.closeConnection();
            req.getRequestDispatcher("/paper-list").forward(req, resp);
            return;
        }

        _dbConnection.closeConnection();

        req.setAttribute("paperTitle", paperDetails.get("title"));
    }
}
