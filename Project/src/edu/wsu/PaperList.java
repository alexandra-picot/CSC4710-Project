package edu.wsu;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

interface StringFunction {
    String func(HttpServletRequest req);
}

@WebServlet("/paper/paper-list")
public class PaperList extends HttpServlet {

    private DBConnection _dbConnection;

    private static final Map<String, String> fieldHtmlToSqlAuthor = new HashMap<>();
    static {
        fieldHtmlToSqlAuthor.put("email", "a.email");
        fieldHtmlToSqlAuthor.put("firstname", "a.first_name");
        fieldHtmlToSqlAuthor.put("lastname", "a.last_name");
        fieldHtmlToSqlAuthor.put("affiliation", "a.affiliation");
    }

    private static final Map<String, String> fieldHtmlToSqlReviewer = new HashMap<>();
    static {
        fieldHtmlToSqlReviewer.put("email", "pm.email");
        fieldHtmlToSqlReviewer.put("firstname", "pm.first_name");
        fieldHtmlToSqlReviewer.put("lastname", "pm.last_name");
    }

    private static final Map<String, String> fieldHtmlToSqlPaper = new HashMap<>();
    static {
        fieldHtmlToSqlPaper.put("id", "paperid");
        fieldHtmlToSqlPaper.put("title", "title");
        fieldHtmlToSqlPaper.put("description", "abstract");
    }

    private static final Map<String, StringFunction> searchFieldsToBuildMethod = new HashMap<>();
    static {
        searchFieldsToBuildMethod.put("paperStatus", (req) -> {
            String[] toSearch = req.getParameterValues("groupPaperStatus");

            if (toSearch == null || toSearch.length == 0) {
                return "";
            }

            ArrayList<String> toSearchAL = new ArrayList<>(Arrays.asList(toSearch));
            String sql = "";
            if (toSearchAL.contains("pending") && toSearchAL.contains("accepted") && toSearchAL.contains("rejected")) {
                sql = "SELECT paper_id FROM reports GROUP BY paper_id";
            } else if (toSearchAL.contains("pending") && toSearchAL.contains("accepted")) {
                sql = "SELECT r.paper_id FROM reports r, rejectedpaper rp WHERE r.paper_id != rp.paper_id GROUP BY r.paper_id";
            } else if (toSearchAL.contains("pending") && toSearchAL.contains("rejected")) {
                sql = "SELECT r.paper_id FROM reports r, acceptedpaper ap WHERE r.paper_id != ap.paper_id GROUP BY r.paper_id";
            } else if (toSearchAL.contains("rejected") && toSearchAL.contains("accepted")) {
                sql = "SELECT r.paper_id FROM reports r, acceptedpaper ap, rejectedpaper rp WHERE r.paper_id = ap.paper_id OR r.paper_id = rp.paper_id GROUP BY r.paper_id";
            } else if (toSearchAL.contains("pending")) {
                sql = "SELECT r.paper_id FROM reports r, acceptedpaper ap, rejectedpaper rp WHERE r.paper_id != ap.paper_id AND r.paper_id != rp.paper_id GROUP BY r.paper_id";
            } else if (toSearchAL.contains("accepted")) {
                sql = "SELECT paperid FROM papers INNER JOIN acceptedpaper ap ON paperid = ap.paper_id";
            } else if (toSearchAL.contains("rejected")) {
                sql = "SELECT paperid FROM papers INNER JOIN rejectedpaper rp ON paperid = rp.paper_id";
            }
            sql = "paperid IN (" + sql + ")";

            return sql;
        });
        searchFieldsToBuildMethod.put("authorFields", (req) -> {
            String[] exactSearch = req.getParameterValues("checkAuthorExactSearch");
            String sql;
            try {
                if (exactSearch != null && exactSearch.length == 1 && exactSearch[0].equals("exact")) {
                    sql = buildFieldSearchSql(req,
                            "authorToSearch",
                            "groupAuthorFields",
                            fieldHtmlToSqlAuthor, true);
                } else {
                    sql = buildFieldSearchSql(req,
                            "authorToSearch",
                            "groupAuthorFields",
                            fieldHtmlToSqlAuthor);
                }
            } catch (ValueException e) {
                System.out.println(e);
                return "";
            }

            sql = "paperid IN (SELECT DISTINCT pa.paper_id FROM paper_authors pa " +
                    "INNER JOIN authors a on pa.author_id = a.email " +
                    "WHERE " + sql + ")";
            return sql;
        });
        searchFieldsToBuildMethod.put("authorSpecial", (req) -> {
            String authorSpecial = req.getParameter("groupAuthorSpecial");
            String sql;
            if (authorSpecial.equals("single")) {
                sql = "paperid IN (SELECT DISTINCT pa.paper_id FROM paper_authors pa " +
                        "WHERE (" +
                        "SELECT COUNT(*) FROM paper_authors WHERE paper_id = pa.paper_id" +
                        ") = 1)";
            } else {
                String selectAuthorContribution = req.getParameter("selectAuthorContribution");
                sql = "paperid IN (SELECT paper_id FROM paper_authors " +
                        "WHERE contribution_significance = " + selectAuthorContribution + ")";
            }
            return sql;
        });
        searchFieldsToBuildMethod.put("reviewerFields", (req) -> {
            String[] exactSearch = req.getParameterValues("checkReviewerExactSearch");
            String sql;
            try {
                if (exactSearch != null && exactSearch.length == 1 && exactSearch[0].equals("exact")) {
                    sql = buildFieldSearchSql(req,
                            "reviewerToSearch",
                            "groupReviewerFields",
                            fieldHtmlToSqlReviewer, true);
                } else {
                    sql = buildFieldSearchSql(req,
                            "reviewerToSearch",
                            "groupReviewerFields",
                            fieldHtmlToSqlReviewer);
                }
            } catch (ValueException e) {
                    System.out.println(e);
                    return "";
                }
            sql = "paperid IN (SELECT DISTINCT rep.paper_id FROM reports rep " +
                    "INNER JOIN pc_members pm on rep.pc_member_id = pm.email " +
                    "WHERE " + sql + ")";
            return sql;
        });
        searchFieldsToBuildMethod.put("paperFields", (req) -> {
            String sql;
            try {
                sql = buildFieldSearchSql(req,
                        "paperToSearch",
                        "groupPaperFields",
                        fieldHtmlToSqlPaper);
            } catch (ValueException e) {
                System.out.println(e);
                return "";
            }
            sql = "(" + sql + ")";
            return sql;
        });
    }

    private static String buildFieldSearchSql(HttpServletRequest req,
                                              String toSearchName,
                                              String groupFieldsName,
                                              Map<String, String> htmlToSql) throws ValueException {
        return buildFieldSearchSql(req, toSearchName, groupFieldsName, htmlToSql, false);
    }

    private static String buildFieldSearchSql(HttpServletRequest req,
                                              String toSearchName,
                                              String groupFieldsName,
                                              Map<String, String> htmlToSql,
                                              Boolean exact) throws ValueException {
        String searchInfo = req.getParameter(toSearchName).trim().toLowerCase()
                ;
        ArrayList<String> columnToSearch = new ArrayList<>();
        String[] fieldsToSearch = req.getParameterValues(groupFieldsName);
        if (fieldsToSearch == null || fieldsToSearch.length == 0) {
            throw new ValueException("No argument selected.");
        }
        if (searchInfo.isEmpty()) {
            throw new ValueException("Nothing to search");
        }
        String preCompString;
        String postCompString;
        if (exact) {
            preCompString = "'";
            postCompString = "'";
        } else {
            preCompString = "'%";
            postCompString = "%'";
        }
        for (String field: fieldsToSearch) {
            columnToSearch.add(htmlToSql.get(field) + " LIKE " + preCompString + searchInfo + postCompString);
        }
        return String.join(" OR ", columnToSearch);
    }

    private ArrayList<String> getEmailList(String table) {
        ArrayList<String> list = new ArrayList<>();

        try {
            Statement statement = _dbConnection.createStatement();

            ResultSet res = statement.executeQuery("SELECT email FROM " + table);

            while (res.next()) {
                list.add(res.getString("email"));
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return list;
    }

    private ArrayList<String> getAuthorsList() {
        return getEmailList("authors");
    }

    private ArrayList<String> getPCMembersList() {
        return getEmailList("pc_members");
    }

    private ArrayList<Map<String, String>> getPaperList(String sql) {
        ArrayList<Map<String, String>> paperList = new ArrayList<>();

        try {
            Statement statement = _dbConnection.createStatement();

            ResultSet res = statement.executeQuery(sql);

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

        return paperList;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        _dbConnection = new DBConnection();
        ArrayList<Map<String, String>> paperList = getPaperList("SELECT * FROM papers");
        ArrayList<String> authorsList = getAuthorsList();
        ArrayList<String> pcMembersList = getPCMembersList();
        _dbConnection.closeConnection();

        req.setAttribute("paperList", paperList);
        req.setAttribute("authorsList", authorsList);
        req.setAttribute("pcMembersList", pcMembersList);
        req.getRequestDispatcher("/paper/paper-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        _dbConnection = new DBConnection();

        ArrayList<Map<String, String>> paperList;
        ArrayList<String> authorsList = getAuthorsList();
        ArrayList<String> pcMembersList = getPCMembersList();

        String standardSearch = req.getParameter("standardSearch");

        if (standardSearch != null) {
            String searchInfo = req.getParameter("searchPaper");

            paperList = getPaperList("SELECT DISTINCT paperid, title, abstract " +
                    "FROM papers " +
                    "WHERE title LIKE '%" + searchInfo + "%' OR " +
                    "abstract LIKE '%" + searchInfo + "%'");


        } else {
            String searchType = req.getParameter("searchType");
            if (searchType.equals("fieldSearch")) {
                String[] fieldsToSearch = req.getParameterValues("toSearchGroup");
                String sql;

                if (fieldsToSearch == null || fieldsToSearch.length == 0) {
                    sql = "SELECT * FROM papers";
                } else {
                    ArrayList<String> conditionsToJoin = new ArrayList<>();
                    for (String field: fieldsToSearch) {
                        String tmp = searchFieldsToBuildMethod.get(field).func(req);
                        if (tmp.isEmpty()) {
                            continue;
                        }
                        conditionsToJoin.add(tmp);
                    }


                    if (conditionsToJoin.isEmpty()) {
                        sql = "SELECT * FROM papers";
                    } else {
                        sql = String.join(" AND ", conditionsToJoin);
                        sql = "SELECT paperid, title, abstract FROM papers WHERE " + sql;
                    }
                }
                paperList = getPaperList(sql);
            } else {
                String specialSearch = req.getParameter("groupSpecialSearch");
                if (specialSearch.equals("coAuthor")) {
                    String firstAuthor = req.getParameter("firstAuthor");
                    String secondAuthor = req.getParameter("secondAuthor");

                    String sql = "SELECT p.paperid, p.title, p.abstract FROM paper_authors pa INNER JOIN papers p ON pa.paper_id = p.paperid " +
                            "WHERE pa.author_id IN ('" + firstAuthor + "', '" + secondAuthor + "'" +
                            ") GROUP BY pa.paper_id HAVING COUNT(*) = 2 AND MAX(pa.contribution_significance) = 2";
                    paperList = getPaperList(sql);

                } else {
                    String firstReviewer = req.getParameter("firstReviewer");
                    String secondReviewer = req.getParameter("secondReviewer");

                    String sql = "SELECT p.paperid, p.title, p.abstract FROM reports r INNER JOIN papers p ON r.paper_id = p.paperid " +
                            "WHERE pc_member_id IN ('" + firstReviewer + "', '" + secondReviewer + "') AND recommendation = 'R' GROUP BY paper_id HAVING COUNT(*) = 2";
                    paperList = getPaperList(sql);
                }
            }
        }

        _dbConnection.closeConnection();

        req.setAttribute("paperList", paperList);
        req.setAttribute("authorsList", authorsList);
        req.setAttribute("pcMembersList", pcMembersList);
        req.getRequestDispatcher("/paper/paper-list.jsp").forward(req, resp);
    }
}
