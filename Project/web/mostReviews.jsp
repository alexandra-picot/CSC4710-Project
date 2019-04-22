<%--
  Created by IntelliJ IDEA.
  User: Mrinnal M
  Date: 4/22/2019
  Time: 12:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>

    <title>CSC 4710 Project</title>
    <link rel="stylesheet" href="style.css">

</head>

<jsp:body>

<banner id="banner">

    <h1>CSC 4710 Project</h1>

</banner>

<content id="content">

    <h1>PC Members who have done the most reviews</h1>
    <form>

        <%@ page import="java.sql.*" %>
        <%
            try {
                String url = "jdbc:mysql://localhost:3306/sampledb";
                Connection conn = DriverManager.getConnection(url,"root","pass1234");
                Statement stmt = conn.createStatement();
                ResultSet rs;
                rs = stmt.executeQuery("SELECT name, memberid FROM PCmember WHERE memberid IN (SELECT memberid FROM review r GROUP BY r.memberid HAVING COUNT(*) = (SELECT MAX(num) FROM (SELECT memberid, COUNT(*) as num FROM review r2 GROUP BY r2.memberid) as r3));");
                while ( rs.next() ) { %>

        <p>Member name:<input type= "text" value =<%=rs.getString("name") %> readonly> Member ID:<input type= "text" value =<%=rs.getString("memberid") %> readonly></p>

        <%   }
            conn.close();
        } catch (Exception e)
        {
            System.err.println("Got an exception! ");

            System.err.println(e.getMessage());
        }
        %>

    </form>
    <form action="${pageContext.request.contextPath}/index.jsp#afterInit">

        <input type="submit" value="Return to Homepage">
    </form>

</content>
</jsp:body>
</html>
