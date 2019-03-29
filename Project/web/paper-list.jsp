<%--
Created by IntelliJ IDEA.
User: vincent
Date: 2019-03-23
Time: 13:08
To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base_site>

    <jsp:attribute name="page_title">
        Paper List
    </jsp:attribute>

    <jsp:attribute name="head_links">
        <link rel="stylesheet" type="text/css" href="Stylesheet/paperlist.css">
    </jsp:attribute>

    <jsp:body>
        <c:choose>
            <c:when test="${empty paperList}">
                <h1>No papers to show!</h1>
                <p>
                    Be sure to click on the 'Initialize Database' button on the home page. <br/>
                    Or click <a href="${pageContext.request.contextPath}/initialize-db">here</a> to initialize the database.
                </p>
            </c:when>
            <c:otherwise>
                <h1>List of papers:</h1>
                <table>
                    <c:forEach items="${paperList}" var="paper">
                        <tr>
                            <th><a href="${pageContext.request.contextPath}/paperdetails/${paper['paperid']}">${paper['title']}</a></th>
                            <th>${paper['abstract']}</th>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:body>

</t:base_site>
