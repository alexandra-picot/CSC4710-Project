<%--
  Created by IntelliJ IDEA.
  User: Mrinnal M
  Date: 4/22/2019
  Time: 1:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CSC 4710 Project</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<banner id="banner">
    <h1>CSC 4710 Project</h1>
</banner>
    <content id="content">
            <%@ page import="java.sql.*" %>
            <p>Update, Delete, & Add Review Reports: </p>
            <p>Format start date as YYYY-MM-DD (ex: 1998-09-28).</p>
            <p>Format recommendation as 'r' for reject, 'a' for accept.</p>
            <p>Paper ID and member ID must be valid within database.</p>


            <table align="center" border="2">
                <tr>
                    <td>Report ID</td>
                    <td>Start Date</td>
                    <td>Comment</td>
                    <td>Recommendation</td>
                    <td>Paper ID</td>
                    <td>Member ID</td>
                    <td>Actions:</td>
                </tr>
                <%
                    try
                    {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/sampledb";

                        Connection conn = DriverManager.getConnection(url,"root","pass1234");

                        Statement stmt = conn.createStatement();

                        ResultSet rs;

                        rs = stmt.executeQuery("SELECT * FROM review");

                        while ( rs.next() ) { %>
                <form action="${pageContext.request.contextPath}/updateReview" method="post">
                    <tr><td><input type="text" name="reportid" readonly="readonly" value="<%=rs.getString("reportid") %>"></td>

                        <td><input type="text" name="sdate" value="<%=rs.getString("sdate") %>"></td>

                        <td><input type="text" name="comm" value="<%=rs.getString("comm") %>"></td>

                        <td><input type="text" name="recommendation" value="<%=rs.getString("recommendation") %>"></td>

                        <td><input type="text" name="paperid" readonly="readonly" value="<%=rs.getString("paperid") %>"></td>


                        <td><input type="text" name="memberid" readonly="readonly" value="<%=rs.getString("memberid") %>"></td>

                        <td><input type="submit" name="update" value="Update"/>

                            <input type="submit" name="delete" value="Delete"/></td></tr>

                </form>
                <% } %>
            </table>
            <br>

            <form action="${pageContext.request.contextPath}/updateReview" method="post"><tr>

                <td><input type="text" name="sdateX" placeholder="Start Date"></td>

                <td><input type="text" name="commX" placeholder="Comm"></td>

                <td><input type="text" name="recommendationX" placeholder="Recommendation"></td>

                <td><input type="text" name="paperidX" placeholder="Paper ID"></td>

                <td><input type="text" name="memberidX" placeholder="Member ID"></td>

                <td><input type="submit" name="addnew" value="Add New Review"/>

                </td></tr>

                <%
                        rs.close();
                        stmt.close();
                        conn.close();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                %>
            </form>

            <form action="${pageContext.request.contextPath}/index.jsp#afterInit">
                <input type="submit" value="Return to Homepage">
            </form>

    </content>

  </body>

</html>
