<%--
  Created by IntelliJ IDEA.
  User: Mrinnal M
  Date: 4/22/2019
  Time: 12:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>4710 Project</title>
    <link rel="stylesheet" href="style.css">
  </head>
  <body>

  <banner id="banner">

      <h1>CSC 4710 Project</h1>

  </banner>

  <content id="content">

      <h1> Members who have no papers assigned to them for review </h1>

      <form>

          <%@ page import="java.sql.*" %>
          <%
              try {
                  String url = "jdbc:mysql://localhost:3306/sampledb";
                  Connection conn = DriverManager.getConnection(url,"root","pass1234");
                  Statement stmt = conn.createStatement();
                  ResultSet rs;
                  rs = stmt.executeQuery("SELECT name, memberid FROM pcmember WHERE memberid IN (SELECT memberid FROM pcmember WHERE memberid NOT IN (SELECT memberid FROM REVIEW));");
                  while ( rs.next() ) { %>

          <p>Member name: <input type= "text" value =<%=rs.getString("name") %> readonly>Member ID:<input type= "text" value =<%=rs.getString("memberid") %> readonly></p>
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


  </body>
</html>
