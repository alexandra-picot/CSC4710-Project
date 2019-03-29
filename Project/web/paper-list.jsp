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
        <script src="${pageContext.request.contextPath}/js/PaperListFilterHandling.js"></script>
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
                <%--
                Header bar with title and search bar
                 --%>
                <div class="container-fluid bg-dark">
                    <div class="row align-items-center">
                        <div class="col">
                            <h2 align="left" class="ml-3 text-white">List of papers</h2>

                        </div>
                        <div class="col d-flex flex-row-reverse mt-3 align-items-start">
                            <button class="mr-2 btn btn-light order-1" onclick="showFilters()">Filters</button>
                            <form id="searchPaperForm" name="searchPaperForm" class="form-inline order-0" action="" method="post" onsubmit="return validateCoAuthor();">
                                <input name="searchPaper" id="searchPaper" type="search" class="form-control mr-2" placeholder="search...">
                                <button type="submit" class="btn btn-light">Search</button>
                            </form>
                        </div>
                    </div>
                </div>

                <%--
                Hidden filter block that appear when user clicks on the filter button
                --%>
                <div id="filters" class="container-fluid bg-secondary py-3" style="display: none">
                    <div id="divFilteringType" class="form-inline">
                        <div class="form-check form-check-inline text-white">
                            <strong>Choose a type of filter:</strong>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="rdFieldSearch" name="FilteringType" onclick="showFieldSearch()">
                            <label class="form-check-label" for="rdFieldSearch">Search by fields</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" id="rdAuthorTypeSearch" name="FilteringType" onclick="showAuthorTypeSearch()">
                            <label class="form-check-label" for="rdAuthorTypeSearch">Search by author's type</label>
                        </div>
                    </div>
                    <%--
                    Div that is display is the user select the radioButton 'Search by fields'
                    --%>
                    <div id="divFieldSearch" class="mt-3" style="display: none">
                        <div class="form-inline">
                            <div class="form-check form-check-inline">
                                <strong>Fields to search in:</strong>
                            </div>
                            <div class="form-check form-check-inline">
                                <input form="searchPaperForm" class="form-check-input" type="checkbox" name="byFields" id="authorFirstName" value="authorFirstName">
                                <label class="form-check-label" for="authorFirstName">Authors' first name</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input form="searchPaperForm" class="form-check-input" type="checkbox" name="byFields" id="authorLastName" value="authorLastName">
                                <label class="form-check-label" for="authorLastName">Authors' last name</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input form="searchPaperForm" class="form-check-input" type="checkbox" name="byFields" id="authorAffiliation" value="authorAffiliation">
                                <label class="form-check-label" for="authorAffiliation">Authors' affiliations</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input form="searchPaperForm" class="form-check-input" type="checkbox" name="byFields" id="paperTitle" value="paperTitle">
                                <label class="form-check-label" for="paperTitle">Papers' title</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input form="searchPaperForm" class="form-check-input" type="checkbox" name="byFields" id="paperDescription" value="paperDescription">
                                <label class="form-check-label" for="paperDescription">Papers' description</label>
                            </div>
                        </div>
                    </div>
                    <%--
                    Div that is display is the user select the radioButton 'Search by author's type'
                    --%>
                    <div id="divAuthorTypeSearch" class="mt-3" style="display: none;">
                        <div class="form-inline">
                            <div class="form-check-inline">
                                <strong>Author options:</strong>
                            </div>
                            <div class="form-check form-check-inline">
                                <input form="searchPaperForm" class="form-check-input" type="radio" name="authorType" id="authorTypeSingle" onclick="hideCoAuthorInputs()" value="Single">
                                <label class="form-check-label" for="authorTypeSingle">Single author</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input form="searchPaperForm" class="form-check-input" type="radio" name="authorType" id="authorTypeFirst" onclick="hideCoAuthorInputs()" value="First">
                                <label class="form-check-label" for="authorTypeFirst">First author</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input form="searchPaperForm" class="form-check-input" type="radio" name="authorType" id="authorTypeCoAuthor" onclick="showCoAuthorInputs()" value="CoAuthor">
                                <label class="form-check-label" for="authorTypeCoAuthor">Co-authored</label>
                            </div>
                            <div id="coAuthor" style="display: none">
                                <div class="form-inline">
                                    <label class="ml-2 mr-2" for="firstAuthor">First author:</label>
                                    <select id="firstAuthor" form="searchPaperForm" class="form-control mr-4 w-25">
                                        <option value="test" selected>test.test@gmail.com</option>
                                        <option value="jp">jean-pierre.foucault@wayne.edu</option>
                                    </select>

                                    <label class="mr-2" for="secondAuthor">Second author:</label>
                                    <select id="secondAuthor" form="searchPaperForm" class="form-control mr-4 w-25">
                                        <option value="test" selected>test.test@gmail.com</option>
                                        <option value="jp">jean-pierre.foucault@wayne.edu</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <table class="table table-striped mt-3">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Title</th>
                        <th scope="col">Description</th>
                    </tr>
                    </thead>
                    <c:forEach items="${paperList}" var="paper">
                        <tr>
                            <th scope="row">${paper['paperid']}</th>
                            <td><a href="${pageContext.request.contextPath}/paperdetails/${paper['paperid']}">${paper['title']}</a></td>
                            <td>${paper['abstract']}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:body>

</t:base_site>
