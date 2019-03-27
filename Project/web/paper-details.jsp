<%--
  Created by IntelliJ IDEA.
  User: vincent
  Date: 2019-03-24
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base_site>

    <jsp:attribute name="page_title">
        About
    </jsp:attribute>

    <jsp:attribute name="head_links">
        <link href="${pageContext.request.contextPath}/Stylesheet/paperdetails.css" rel="stylesheet" type="text/css">
        <script src="${pageContext.request.contextPath}/js/PaperDetailsFormValidation.js"></script>
    </jsp:attribute>

    <jsp:body>
        <h1>Details of paper: ${paperDetails.title} </h1>

        <p>
            <strong>Description:</strong><br/>
            ${paperDetails.description}
        </p>
        <p>
            <strong>Paper id:</strong> ${paperDetails.paperid}
        </p>

        <br/><br/>

        <c:choose>
            <c:when test="${empty paperReviewers}">
                <form name="assignReviewers" action="${pageContext.request.contextPath}/savepaperreviewers" method="post" id="assignReviewers" onsubmit="return validateForm();">
                    <h2>Paper reviewers:</h2>


                    <label for="firstReviewer"><strong>First reviewer:</strong></label>
                    <select form="assignReviewers" name="firstReviewer" id="firstReviewer">
                        <option value=""></option>
                        <c:forEach items="${pcMembers}" var="member">
                            <option value=${member}>${member}</option>
                        </c:forEach>
                    </select>
                    <span id="firstReviewerError" class="error">${messages.firstreviewer}</span>

                    <br/><br/>

                    <label for="secondReviewer"><strong>Second reviewer:</strong></label>
                    <select form="assignReviewers" name="secondReviewer" id="secondReviewer">
                        <option value=""></option>
                        <c:forEach items="${pcMembers}" var="member">
                            <option value=${member}>${member}</option>
                        </c:forEach>
                    </select>
                    <span id="secondReviewerError" class="error">${messages.secondreviewer}</span>

                    <br/><br/>


                    <label for="thirdReviewer"><strong>Third reviewer:</strong></label>
                    <select form="assignReviewers" name="thirdReviewer" id="thirdReviewer">
                        <option value=""></option>
                        <c:forEach items="${pcMembers}" var="member">
                            <option value=${member}>${member}</option>
                        </c:forEach>
                    </select>
                    <span id="thirdReviewerError" class="error">${messages.thirdreviewer}</span>

                    <input type="hidden" name="paperid" value=${paperDetails.paperid}>

                    <br/><br/>
                    <button type="submit"><strong>Save paper reviewers</strong></button>
                </form>
            </c:when>
            <c:otherwise>
                <h3>Reviewers list:</h3>
                <ul>
                    <c:forEach items="${paperReviewers}" var="reviewer">
                        <li>${reviewer}</li>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>
    </jsp:body>

</t:base_site>