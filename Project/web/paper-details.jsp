<%--
  Created by IntelliJ IDEA.
  User: vincent
  Date: 2019-03-24
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Paper Details</title>
</head>
<body>
    <% Map<String, String> paperDetails = (Map) request.getAttribute("paperDetails"); %>
    <h1>Details of paper: <%= paperDetails.get("title") %> </h1>


</body>
</html>
