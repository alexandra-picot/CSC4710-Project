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

interface StringFunction {
    String func(HttpServletRequest req);
}

@WebServlet("/paper-list")
public class PaperList extends HttpServlet {

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
        //TODO: Link the accepted/rejected paper! Create SQL Views
        searchFieldsToBuildMethod.put("authorFields", (req) -> {
            String[] exactSearch = req.getParameterValues("checkAuthorExactSearch");
            String sql;
            if (exactSearch.length == 1 && exactSearch[0].equals("exact")) {
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


             sql = "paperid IN (SELECT DISTINCT pa.paper_id FROM paper_authors pa " +
                    "INNER JOIN authors a on pa.author_id = a.email " +
                    "WHERE " + sql + ")";
            System.out.println(sql);
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
            System.out.println(sql);
            return sql;
        });
        searchFieldsToBuildMethod.put("reviewerFields", (req) -> {
            String[] exactSearch = req.getParameterValues("checkReviewerExactSearch");
            String sql;
            if (exactSearch.length == 1 && exactSearch[0].equals("exact")) {
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
            sql = "paperid IN (SELECT DISTINCT rep.paper_id FROM reports rep " +
                    "INNER JOIN pc_members pm on rep.pc_member_id = pm.email " +
                    "WHERE " + sql + ")";
            System.out.println(sql);
            return sql;
        });
        searchFieldsToBuildMethod.put("paperFields", (req) -> {
            String sql = buildFieldSearchSql(req,
                    "paperToSearch",
                    "groupPaperFields",
                    fieldHtmlToSqlPaper);

            sql = "(" + sql + ")";
            System.out.println(sql);
            return sql;
        });
    }

    private static String buildFieldSearchSql(HttpServletRequest req, String toSearchName, String groupFieldsName, Map<String, String> htmlToSql) {
        return buildFieldSearchSql(req, toSearchName, groupFieldsName, htmlToSql, false);
    }

    private static String buildFieldSearchSql(HttpServletRequest req, String toSearchName, String groupFieldsName, Map<String, String> htmlToSql, Boolean exact) {
        String searchInfo = req.getParameter(toSearchName);
        ArrayList<String> columnToSearch = new ArrayList<>();
        String[] fieldsToSearch = req.getParameterValues(groupFieldsName);
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

    private ArrayList<Map<String, String>> getPaperList(String sql) {
        DBConnection dbConnection = new DBConnection();

        ArrayList<Map<String, String>> paperList = new ArrayList<>();

        try {
            Statement statement = dbConnection.createStatement();

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

        dbConnection.closeConnection();

        return paperList;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Map<String, String>> paperList = getPaperList("SELECT * FROM papers");

        req.setAttribute("paperList", paperList);
        req.getRequestDispatcher("/paper-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Map<String, String>> paperList;


        String standardSearch = req.getParameter("standardSearch");

        System.out.println(standardSearch);

        if (standardSearch != null) {
            String searchInfo = req.getParameter("searchPaper");

            paperList = getPaperList("SELECT DISTINCT paperid, title, abstract " +
                    "FROM papers " +
                    "WHERE title LIKE '%" + searchInfo + "%' OR " +
                    "abstract LIKE '%" + searchInfo + "%'");


        } else {
            String searchType = req.getParameter("searchType");
            System.out.println(searchType);
            if (searchType.equals("fieldSearch")) {
                String[] fieldsToSearch = req.getParameterValues("toSearchGroup");

                ArrayList<String> conditionsToJoin = new ArrayList<>();
                for (String field: fieldsToSearch) {
                    System.out.println(field);
                    conditionsToJoin.add(searchFieldsToBuildMethod.get(field).func(req));
                }

                String sql = String.join(" AND ", conditionsToJoin);

                sql = "SELECT paperid, title, abstract FROM papers WHERE " + sql;
                paperList = getPaperList(sql);
            } else {
                paperList = null;
            }
        }
        req.setAttribute("paperList", paperList);
        req.getRequestDispatcher("/paper-list.jsp").forward(req, resp);
//        String filteringType = req.getParameter("filteringType");
//
//        String sqlFilter = "";
//        switch (filteringType) {
//            case "byFieldsSearch":
//                S
//
//                sqlFilter = sqlFilter.join(" OR ")
//                break;
//            case "authorTypeSearch":
//                break;
//
//            default:
//
//        }
//        String authorType = req.getParameter("authorType");


    }
}
