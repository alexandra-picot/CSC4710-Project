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
        <%--
        Header bar with title and search bar
        --%>
        <div class="container-fluid bg-dark">
            <div class="row align-items-center">
                <div class="col">
                    <h2 align="left" class="ml-3 text-white"><a href="${pageContext.request.contextPath}/paper-list" class="text-white">List of papers</a></h2>

                </div>
                <div class="col d-flex flex-row-reverse mt-3 align-items-start">
                    <button class="mr-2 btn btn-light order-1" onclick="showAdvancedSearch()">Advanced Search</button>
                    <form id="formSearchPaper" name="formSearchPaper" class="form-inline order-0" action="" method="post" onsubmit="return validateCoAuthor();">
                        <input id="searchPaper" name="searchPaper" type="search" class="form-control mr-2" placeholder="search...">
                        <input id="standardSearch" name="standardSearch" type="hidden" value="standardSearch">
                        <button type="submit" class="btn btn-light">Search</button>
                    </form>
                </div>
            </div>
        </div>

        <c:choose>
            <c:when test="${empty paperList}">
                <h1>No papers to show!</h1>
                <p>
                    If you just made a search, it means that no papers correspond to what you looked for.<br/><br/>

                    If you just arrived on this page, it either means that there is no papers at all or that an error occured.<br/>
                    In any case, be sure to click on the '<strong>Initialize Database</strong>' button on the home page. <br/>
                    Or click <a href="${pageContext.request.contextPath}/initialize-db">here</a> to initialize the database.
                </p>
            </c:when>
            <c:otherwise>
                <%--
                Hidden filter block that appear when user clicks on the filter button
                --%>
                <%--style="display: none"--%>
                <div id="divAdvancedSearch" class="container-fluid bg-secondary py-3" style="display: none">
                    <h2>Advanced search:</h2>
                    <form id="formAdvancedPaperSearch" action="" method="post" class="form">
                        <div id="divSearchType" class="form-inline">
                            <div class="form-check form-check-inline text-white">
                                <strong>Type of search:</strong>
                            </div>

                            <div class="form-check form-check-inline">
                                <input id="rdFieldSearch" type="radio" name="searchType" value="fieldSearch" class="form-check-input" onclick="showFieldSearch()" checked>
                                <label for="rdFieldSearch" class="form-check-label">Search by fields</label>
                            </div>

                            <div class="form-check form-check-inline">
                                <input id="rdSpecialSearch" type="radio" name="searchType" value="specialSearch" class="form-check-input"
                                       onclick="showSpecialSearch()">
                                <label for="rdSpecialSearch" class="form-check-label">Special search</label>
                            </div>
                        </div>

                        <%--
                        Div that is display is the user select the radioButton 'Search by fields'
                        --%>
                        <div id="divFieldSearch" class="mt-3">
                            <div class="form-inline mb-3">
                                <div class="form-check form-check-inline">
                                    <input id="paperStatus" type="checkbox" name="toSearchGroup" value="paperStatus" class="form-check-input" onclick="showPaperStatus()">
                                    <label for="paperStatus" class="form-check-label text-white"><strong>Papers' status</strong></label>
                                </div>

                                <div id="divPaperStatus" style="display: none;">
                                    <div class="form-inline" >
                                        <div class="form-check form-check-inline">
                                            <input id="pendingPaper" type="checkbox" name="groupPaperStatus" value="pending" class="form-check-input">
                                            <label for="pendingPaper" class="form-check-label">Pending</label>
                                        </div>

                                        <div class="form-check form-check-inline">
                                            <input id="acceptedPaper" type="checkbox" name="groupPaperStatus"  value="accepted" class="form-check-input">
                                            <label for="acceptedPaper" class="form-check-label">Accepted</label>
                                        </div>

                                        <div class="form-check form-check-inline">
                                            <input id="rejectedPaper" type="checkbox" name="groupPaperStatus" value="rejected" class="form-check-input">
                                            <label for="rejectedPaper" class="form-check-label">Rejected</label>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="form-inline mb-3">
                                <div class="form-check form-check-inline">
                                    <input id="authorFields" type="checkbox" name="toSearchGroup" value="authorFields" class="form-check-input" onclick="showAuthorFields()">
                                    <label for="authorFields" class="form-check-label text-white"><strong>Authors' fields</strong></label>
                                </div>

                                <div id="divAuthorFields" style="display: none">
                                    <div class="form-inline">
                                        <div class="form-check form-check-inline">
                                            <input id="authorEmail" type="checkbox" name="groupAuthorFields" value="email" class="form-check-input">
                                            <label for="authorEmail" class="form-check-label">Email</label>
                                        </div>

                                        <div class="form-check form-check-inline">
                                            <input id="authorFirstName" type="checkbox" name="groupAuthorFields" value="firstname" class="form-check-input">
                                            <label for="authorFirstName" class="form-check-label">First name</label>
                                        </div>

                                        <div class="form-check form-check-inline">
                                            <input id="authorLastName" type="checkbox" name="groupAuthorFields"  value="lastname" class="form-check-input">
                                            <label for="authorLastName" class="form-check-label">Last name</label>
                                        </div>

                                        <div class="form-check form-check-inline">
                                            <input id="authorAffiliation" type="checkbox" name="groupAuthorFields" value="affiliation" class="form-check-input">
                                            <label for="authorAffiliation" class="form-check-label">Affiliations</label>
                                        </div>

                                        <div class="form-inline ml-3 ">
                                            <label for="authorToSearch"><strong>Search for:</strong></label>
                                            <input id="authorToSearch" name="authorToSearch" type="search" class="form-control ml-1" placeholder="Search in authors...">
                                        </div>
                                        <div class="form-check form-check-inline ml-2">
                                            <input id="authorExactSearch" type="checkbox" name="checkAuthorExactSearch" value="exact" class="form-check-input">
                                            <label for="authorExactSearch" class="form-check-label">Search exactly</label>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="form-inline mb-3">

                                <div class="form-check form-check-inline">
                                    <input id="authorSpecial" type="checkbox" name="toSearchGroup" value="authorSpecial" class="form-check-input" onclick="showAuthorSpecial()">
                                    <label for="authorSpecial" class="form-check-label text-white"><strong>Authors Special Options</strong></label>
                                </div>

                                <div id="divAuthorSpecial" style="display: none">
                                    <div class="form-inline">
                                        <div class="form-check form-check-inline">
                                            <input id="singleAuthor" type="radio" name="groupAuthorSpecial" value="single" class="form-check-input" checked onclick="showAuthorContributionSelect()">
                                            <label for="singleAuthor" class="form-check-label">Single author</label>
                                        </div>

                                        <div class="form-check form-check-inline">
                                            <input id="authorContribution" type="radio" name="groupAuthorSpecial"  value="contribution" class="form-check-input" onclick="showAuthorContributionSelect()">
                                            <label for="authorContribution" class="form-check-label">Contribution importance</label>
                                        </div>
                                        <div class="form-inline">
                                            <%--
                                            TODO: show select list based on the max contribution in the DB
                                            --%>
                                            <select id="selectAuthorContribution" name="selectAuthorContribution" class="form-control" disabled>
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="form-inline mb-3">
                                <div class="form-check form-check-inline">
                                    <input id="reviewerFields" type="checkbox" name="toSearchGroup" value="reviewerFields" class="form-check-input" onclick="showReviewerFields()">
                                    <label for="reviewerFields" class="form-check-label text-white"><strong>Reviewers' fields</strong></label>
                                </div>

                                <div id="divReviewerFields" style="display: none">
                                    <div class="form-inline">
                                        <div class="form-check form-check-inline">
                                            <input id="reviewerEmail" type="checkbox" name="groupReviewerFields" value="email" class="form-check-input">
                                            <label for="reviewerEmail" class="form-check-label">Email</label>
                                        </div>

                                        <div class="form-check form-check-inline">
                                            <input id="reviewerFirstName" type="checkbox" name="groupReviewerFields" value="firstname" class="form-check-input">
                                            <label for="reviewerFirstName" class="form-check-label">First name</label>
                                        </div>

                                        <div class="form-check form-check-inline">
                                            <input id="reviewerLastName" type="checkbox" name="groupReviewerFields"  value="lastname" class="form-check-input">
                                            <label for="reviewerLastName" class="form-check-label">Last name</label>
                                        </div>

                                        <div class="form-inline ml-3 ">
                                            <label for="reviewerToSearch"><strong>Search for:</strong></label>
                                            <input id="reviewerToSearch" name="reviewerToSearch" type="search" class="form-control ml-1"  placeholder="Search in reviewers...">
                                        </div>
                                        <div class="form-check form-check-inline ml-2">
                                            <input id="reviewerExactSearch" type="checkbox" name="checkReviewerExactSearch" value="exact" class="form-check-input">
                                            <label for="reviewerExactSearch" class="form-check-label">Search exactly</label>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="form-inline">

                                <div class="form-check form-check-inline">
                                    <input id="paperFields" type="checkbox" name="toSearchGroup" value="paperFields" class="form-check-input" onclick="showPaperFields()">
                                    <label for="paperFields" class="form-check-label text-white"><strong>Papers' fields</strong></label>
                                </div>

                                <div id="divPaperFields" style="display: none">
                                    <div class="form-inline">
                                        <div class="form-check form-check-inline">
                                            <input id="paperId" type="checkbox" name="groupPaperFields" value="id" class="form-check-input">
                                            <label for="paperId" class="form-check-label">Id</label>
                                        </div>

                                        <div class="form-check form-check-inline">
                                            <input id="paperTitle" type="checkbox" name="groupPaperFields" value="title" class="form-check-input">
                                            <label for="paperTitle" class="form-check-label">Title</label>
                                        </div>

                                        <div class="form-check form-check-inline">
                                            <input id="paperDescription" type="checkbox" name="groupPaperFields" value="description" class="form-check-input">
                                            <label for="paperDescription" class="form-check-label">Description</label>
                                        </div>

                                        <div class="form-inline ml-3 ">
                                            <label for="paperToSearch"><strong>Search for:</strong></label>
                                            <input id="paperToSearch" name="paperToSearch" type="search" class="form-control ml-1" placeholder="Search in papers...">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div>
                            <button type="submit" class="btn btn-light mt-5">Search</button>
                        </div>

                    </form>
                    <%--<div id="divFilteringType" class="form-inline">--%>
                        <%--<div class="form-check form-check-inline text-white">--%>
                            <%--<strong>Choose a type of filter:</strong>--%>
                        <%--</div>--%>
                        <%--<div class="form-check form-check-inline">--%>
                            <%--<input class="form-check-input" type="radio" id="rdFieldSearch" name="filteringType" value="byFieldsSearch" onclick="showFieldSearch()">--%>
                            <%--<label class="form-check-label" for="rdFieldSearch">Search by fields</label>--%>
                        <%--</div>--%>
                        <%--<div class="form-check form-check-inline">--%>
                            <%--<input class="form-check-input" type="radio" id="rdAuthorTypeSearch" name="filteringType" value="authorTypeSearch" onclick="showAuthorTypeSearch()">--%>
                            <%--<label class="form-check-label" for="rdAuthorTypeSearch">Search by author's type</label>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--&lt;%&ndash;--%>
                    <%--Div that is display is the user select the radioButton 'Search by fields'--%>
                    <%--&ndash;%&gt;--%>
                    <%--<div id="divFieldSearch" class="mt-3" style="display: none">--%>
                        <%--<div class="form-inline">--%>
                            <%--<div class="form-check form-check-inline">--%>
                                <%--<strong>Fields to search in:</strong>--%>
                            <%--</div>--%>
                            <%--<div class="form-check form-check-inline">--%>
                                <%--<input  class="form-check-input" type="checkbox" name="byFields" id="authorFirstName" value="authorFirstName">--%>
                                <%--<label class="form-check-label" for="authorFirstName">Authors' first name</label>--%>
                            <%--</div>--%>
                            <%--<div class="form-check form-check-inline">--%>
                                <%--<input  class="form-check-input" type="checkbox" name="byFields" id="authorLastName" value="authorLastName">--%>
                                <%--<label class="form-check-label" for="authorLastName">Authors' last name</label>--%>
                            <%--</div>--%>
                            <%--<div class="form-check form-check-inline">--%>
                                <%--<input  class="form-check-input" type="checkbox" name="byFields" id="authorAffiliation" value="authorAffiliation">--%>
                                <%--<label class="form-check-label" for="authorAffiliation">Authors' affiliations</label>--%>
                            <%--</div>--%>
                            <%--<div class="form-check form-check-inline">--%>
                                <%--<input  class="form-check-input" type="checkbox" name="byFields" id="paperTitle" value="paperTitle">--%>
                                <%--<label class="form-check-label" for="paperTitle">Papers' title</label>--%>
                            <%--</div>--%>
                            <%--<div class="form-check form-check-inline">--%>
                                <%--<input  class="form-check-input" type="checkbox" name="byFields" id="paperDescription" value="paperDescription">--%>
                                <%--<label class="form-check-label" for="paperDescription">Papers' description</label>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--&lt;%&ndash;--%>
                    <%--Div that is display is the user select the radioButton 'Search by author's type'--%>
                    <%--&ndash;%&gt;--%>
                    <%--<div id="divAuthorTypeSearch" class="mt-3" style="display: none;">--%>
                        <%--<div class="form-inline">--%>
                            <%--<div class="form-check-inline">--%>
                                <%--<strong>Author options:</strong>--%>
                            <%--</div>--%>
                            <%--<div class="form-check form-check-inline">--%>
                                <%--<input  class="form-check-input" type="radio" name="authorType" id="authorTypeSingle" onclick="hideCoAuthorInputs()" value="Single">--%>
                                <%--<label class="form-check-label" for="authorTypeSingle">Single author</label>--%>
                            <%--</div>--%>
                            <%--<div class="form-check form-check-inline">--%>
                                <%--<input  class="form-check-input" type="radio" name="authorType" id="authorTypeFirst" onclick="hideCoAuthorInputs()" value="First">--%>
                                <%--<label class="form-check-label" for="authorTypeFirst">First author</label>--%>
                            <%--</div>--%>
                            <%--<div class="form-check form-check-inline">--%>
                                <%--<input  class="form-check-input" type="radio" name="authorType" id="authorTypeCoAuthor" onclick="showCoAuthorInputs()" value="CoAuthor">--%>
                                <%--<label class="form-check-label" for="authorTypeCoAuthor">Co-authored</label>--%>
                            <%--</div>--%>
                            <%--<div id="coAuthor" style="display: none">--%>
                                <%--<div class="form-inline">--%>
                                    <%--<label class="ml-2 mr-2" for="firstAuthor">First author:</label>--%>
                                    <%--<select id="firstAuthor"  class="form-control mr-4 w-25">--%>
                                        <%--<option value="test" selected>test.test@gmail.com</option>--%>
                                        <%--<option value="jp">jean-pierre.foucault@wayne.edu</option>--%>
                                    <%--</select>--%>

                                    <%--<label class="mr-2" for="secondAuthor">Second author:</label>--%>
                                    <%--<select id="secondAuthor"  class="form-control mr-4 w-25">--%>
                                        <%--<option value="test" selected>test.test@gmail.com</option>--%>
                                        <%--<option value="jp">jean-pierre.foucault@wayne.edu</option>--%>
                                    <%--</select>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
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
