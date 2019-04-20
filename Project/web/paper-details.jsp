<%--
  Created by IntelliJ IDEA.
  User: vincent
  Date: 2019-03-24
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base_site page_title="Paper Details">

    <jsp:attribute name="head_links">
        <link href="${pageContext.request.contextPath}/Stylesheet/paperdetails.css" rel="stylesheet" type="text/css">
        <script src="${pageContext.request.contextPath}/js/PaperDetailsFormValidation.js"></script>
    </jsp:attribute>

    <jsp:body>
        <c:choose>
            <c:when test="${empty paperDetails}">
                <h1>Paper not found!</h1>
                <p>
                    Sorry, the paper asked couldn't be found.
                </p>
            </c:when>
            <c:otherwise>
                <h1>${paperDetails.title}</h1>

                <p>
                    <strong>Description:</strong><br/>
                        ${paperDetails.description}
                </p>
                <p>
                    <strong>Paper id:</strong> ${paperDetails.paperid}
                </p>

                <c:if test="${not empty paperAuthors}">
                    <strong>List of authors by contribution significance:</strong><br/>
                    <ul>
                        <c:forEach items="${paperAuthors}" var="author">
                            <li>${author}</li>
                        </c:forEach>
                    </ul>
                </c:if>

                <br/>

                <c:choose>
                    <c:when test="${empty paperReviewers}">
                        <form name="assignReviewers" action="${pageContext.request.contextPath}/savepaperreviewers" method="post" id="assignReviewers" onsubmit="return validateForm();">
                            <h2>Paper reviewers:</h2>

                            <div class="form-group">
                                <label for="firstReviewer"><strong>First reviewer:</strong></label>
                                <select form="assignReviewers" name="firstReviewer" id="firstReviewer" class="form-control w-25">
                                    <option value=""></option>
                                    <c:forEach items="${pcMembers}" var="member">
                                        <option value=${member}>${member}</option>
                                    </c:forEach>
                                </select>
                                <span id="firstReviewerError" class="error">${messages.firstreviewer}</span>
                            </div>

                            <div class="form-group">
                                <label for="secondReviewer"><strong>Second reviewer:</strong></label>
                                <select form="assignReviewers" name="secondReviewer" id="secondReviewer" class="form-control w-25">
                                    <option value=""></option>
                                    <c:forEach items="${pcMembers}" var="member">
                                        <option value=${member}>${member}</option>
                                    </c:forEach>
                                </select>
                                <span id="secondReviewerError" class="error">${messages.secondreviewer}</span>
                            </div>

                            <div class="form-group">
                                <label for="thirdReviewer"><strong>Third reviewer:</strong></label>
                                <select form="assignReviewers" name="thirdReviewer" id="thirdReviewer" class="form-control w-25">
                                    <option value=""></option>
                                    <c:forEach items="${pcMembers}" var="member">
                                        <option value=${member}>${member}</option>
                                    </c:forEach>
                                </select>
                                <span id="thirdReviewerError" class="error">${messages.thirdreviewer}</span>
                            </div>

                            <input type="hidden" name="paperid" value=${paperDetails.paperid}>

                            <button type="submit" class="btn btn-success"><strong>Save paper reviewers</strong></button>
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
            </c:otherwise>
        </c:choose>
        <form action="${pageContext.request.contextPath}/paper-list">
            <button type="submit" class="btn btn-primary">Back to paper list</button>
        </form>
    </jsp:body>

</t:base_site>