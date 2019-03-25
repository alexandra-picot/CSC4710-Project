<%--
Created by IntelliJ IDEA.
User: vincent
Date: 2019-03-23
Time: 13:08
To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Paper List</title>
    <link rel="stylesheet" type="text/css" href="Stylesheet/paperlist.css">
</head>
<body>
<h1>List of papers:</h1>
<p>
    <a href="index.html">Go back to home</a>
</p>
    <%
        ArrayList<Map<String, String>> list = (ArrayList) request.getAttribute("paperList");
        if (list.isEmpty()) {
            %>
            <h1>No papers to show</h1>
            <p>
                Be sure to click on the 'Initialize Database' button on the home page
            </p>
            <%
        } else {
            %>
            <table>
                <%
                    for (Map paper: list) {
                %>
                <tr>
                    <th><a href="${pageContext.request.contextPath}/paperdetails/<%= paper.get("paperid") %>"><%= paper.get("title") %></a> </th>
                    <th><%= paper.get("abstract") %></th>
                </tr>
                <%
                    }
                %>
            </table>
        <%
        }
        %>
<p>
    <a href="index.html">Go back to home</a>
</p>
</body>
</html>