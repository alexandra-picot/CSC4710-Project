<%--
  Created by IntelliJ IDEA.
  User: vincent
  Date: 2019-03-24
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Paper Details</title>
</head>
<body>
    <h1>Details of paper: ${paperDetails.title} </h1>

    <p>
        <strong>Description:</strong><br>
        ${paperDetails.description}
    </p>
    <p>
        <strong>Paper id:</strong> ${paperDetails.paperid}
    </p>

    <br><br>

    <form action="" id="reviewers" method="post">
        <h2>Paper reviewers:</h2>


        <label for="firstreviewer"><strong>First reviewer:</strong></label>
        <select form="reviewers" name="firstreviewer" id="firstreviewer">
            <option value=""></option>
            <%
                ArrayList<String> pcMembers = (ArrayList) request.getAttribute("pcMembers");

                for (String member: pcMembers) {
            %>
            <option value=<%= member %>><%= member %></option>
            <%
                }
            %>
        </select>
        <span class="error">${messages.firstreviewer}</span>

        <br><br>


        <label for="secondreviewer"><strong>Second reviewer:</strong></label>
        <select form="reviewers" name="secondreviewer" id="secondreviewer">
            <option value=""></option>
            <%
                for (String member: pcMembers) {
            %>
            <option value=<%= member %>><%= member %></option>
            <%
                }
            %>
        </select>
        <span class="error">${messages.secondreviewer}</span>

        <br><br>


        <label for="thirdreviewer"><strong>Third reviewer:</strong></label>
        <select form="reviewers" name="thirdreviewer" id="thirdreviewer">
            <option value=""></option>
            <%
                for (String member: pcMembers) {
            %>
            <option value=<%= member %>><%= member %></option>
            <%
                }
            %>
        </select>
        <span class="error">${messages.thirdreviewer}</span>

        <input type="hidden" name="paperid" value=${paperDetails.paperid}>

        <br><br>
        <button type="submit"><strong>Save paper reviewers</strong></button>
    </form>
</body>
</html>
