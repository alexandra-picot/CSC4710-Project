package edu.wsu;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLRequestsUtils {

    static public Map<String, String> getPaperDetails(String paperId, DBConnection dbConnection) {
        Map<String, String> paperDetails = new HashMap<>();

        try {
            Statement statementPaper =dbConnection.createStatement();
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
        return paperDetails;
    }

    static private ArrayList<Map<String, String>> getPersonListMap(String request, DBConnection dbConnection) {
        ArrayList<Map<String, String>> personList = new ArrayList<>();

        try {
            Statement statementPaperAuthors = dbConnection.createStatement();
            ResultSet rsPaperAuthors = statementPaperAuthors.executeQuery(request);

            while (rsPaperAuthors.next()) {
                Map<String, String> tmp = new HashMap<>();

                tmp.put(Person.PERSON_EMAIL_KEY, rsPaperAuthors.getString("email"));
                tmp.put(Person.PERSON_FIRSTNAME_KEY, rsPaperAuthors.getString("first_name"));
                tmp.put(Person.PERSON_LASTNAME_KEY, rsPaperAuthors.getString("last_name"));
                personList.add(tmp);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return personList;
    }

    static private ArrayList<Person> getPersonList(String request, DBConnection dbConnection, PersonType type) {
        ArrayList<Person> personList = new ArrayList<>();

        try {
            Statement statementPaperAuthors = dbConnection.createStatement();
            ResultSet rsPaperAuthors = statementPaperAuthors.executeQuery(request);

            while (rsPaperAuthors.next()) {
                Person person = PersonFactory.getPerson(
                        rsPaperAuthors.getString("email"),
                        rsPaperAuthors.getString("first_name"),
                        rsPaperAuthors.getString("last_name"),
                        type
                );
                personList.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return personList;
    }

    static private ArrayList<Author> convertListOfPersonToAuthor(ArrayList<Person> list) {
        ArrayList<Author> res = new ArrayList<>();
        for (Person person: list) {
            res.add((Author) person);
        }
        return res;
    }

    static public ArrayList<Author> getListOfAuthorsForPapers(String paperId, DBConnection dbConnection) {
        String  request = "SELECT a.email, a.first_name, a.last_name FROM paper_authors pa " +
                "JOIN authors a ON pa.author_id = a.email WHERE pa.paper_id = " + paperId +
                " ORDER BY pa.contribution_significance ASC";
        return convertListOfPersonToAuthor(getPersonList(request, dbConnection, PersonType.AUTHOR));
    }

    static public ArrayList<Person> getPcMembersList(DBConnection dbConnection) {
        String request = "SELECT * FROM pc_members ORDER BY first_name, last_name";
        return getPersonList(request, dbConnection, PersonType.PC_MEMBER);
    }

    static public ArrayList<Map<String, String>> getListOfAuthorsForPapersMap(String paperId, DBConnection dbConnection) {
        String  request = "SELECT a.email, a.first_name, a.last_name FROM paper_authors pa " +
                "JOIN authors a ON pa.author_id = a.email WHERE pa.paper_id = " + paperId +
                " ORDER BY pa.contribution_significance ASC";
        return getPersonListMap(request, dbConnection);
    }

    static public ArrayList<Map<String, String>> getPcMembersListMap(DBConnection dbConnection) {
        String request = "SELECT * FROM pc_members ORDER BY first_name, last_name";
        return getPersonListMap(request, dbConnection);
    }

    static public ArrayList<Author> getAuthorsList(DBConnection dbConnection) {
        String request = "SELECT *  FROM authors";
        return convertListOfPersonToAuthor(getPersonList(request, dbConnection, PersonType.AUTHOR));
    }

    static public ArrayList<String> getPaperReviewers(String paperId, DBConnection dbConnection) {
        ArrayList<String> paperReviewers = new ArrayList<>();

        try {
            Statement statementPaperReviewers = dbConnection.createStatement();
            ResultSet rsPaperReviewers = statementPaperReviewers.executeQuery("SELECT pc_member_id FROM reports WHERE paper_id = " + paperId);

            while (rsPaperReviewers.next()) {
                paperReviewers.add(rsPaperReviewers.getString("pc_member_id"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return paperReviewers;
    }

    static public void updateAuthor(Author author, DBConnection dbConnection) {
        try {
            String sql = "UPDATE authors SET first_name='" + author.firstName + "', last_name='" + author.lastName + "' WHERE email LIKE '" + author.email + "'";
            dbConnection.createStatement().executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static public void updateContribution(String paperId, String authorId, int contribution, DBConnection dbConnection) {
        try {
            String sql = "UPDATE paper_authors SET contribution_significance=" + contribution + " WHERE author_id LIKE '" + authorId + "' AND paper_id=" + paperId;
            dbConnection.createStatement().executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
