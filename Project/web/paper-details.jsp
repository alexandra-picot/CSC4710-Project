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
    <link href="${pageContext.request.contextPath}/Stylesheet/paperdetails.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/js/PaperDetailsFormValidation.js"></script>
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

    <form name="assignReviewers" action="${pageContext.request.contextPath}/savepaperreviewers" method="post" id="assignReviewers" onsubmit="return validateForm();">
        <h2>Paper reviewers:</h2>


        <label for="firstReviewer"><strong>First reviewer:</strong></label>
        <select form="assignReviewers" name="firstReviewer" id="firstReviewer">
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
        <span id="firstReviewerError" class="error">${messages.firstreviewer}</span>

        <br><br>


        <label for="secondReviewer"><strong>Second reviewer:</strong></label>
        <select form="assignReviewers" name="secondReviewer" id="secondReviewer">
            <option value=""></option>
            <%
                for (String member: pcMembers) {
            %>
            <option value=<%= member %>><%= member %></option>
            <%
                }
            %>
        </select>
        <span id="secondReviewerError" class="error">${messages.secondreviewer}</span>

        <br><br>


        <label for="thirdReviewer"><strong>Third reviewer:</strong></label>
        <select form="assignReviewers" name="thirdReviewer" id="thirdReviewer">
            <option value=""></option>
            <%
                for (String member: pcMembers) {
            %>
            <option value=<%= member %>><%= member %></option>
            <%
                }
            %>
        </select>
        <span id="thirdReviewerError" class="error">${messages.thirdreviewer}</span>

        <input type="hidden" name="paperid" value=${paperDetails.paperid}>

        <br><br>
        <button type="submit"><strong>Save paper reviewers</strong></button>
    </form>
</body>
</html>
