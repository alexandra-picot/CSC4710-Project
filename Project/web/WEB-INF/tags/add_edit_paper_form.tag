<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: vincent
  Date: 2019-04-19
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@tag description="base site tag" pageEncoding="UTF-8"%>
<%@attribute name="action_link" required="true" type="java.lang.String"%>
<%@attribute name="title" type="java.lang.String"%>
<%@attribute name="description" type="java.lang.String"%>
<%@attribute name="author_list" type="java.util.ArrayList"%>
<%@attribute name="pc_member_list" type="java.util.ArrayList" required="true"%>
<%@attribute name="first_reviewer"%>
<%@attribute name="second_reviewer"%>
<%@attribute name="third_reviewer"%>
<%@attribute name="paper_id"%>
<%@attribute name="errors"%>

<c:if test="${not empty errors}">
    <div class="container-fluid mb-5">
        <c:forEach items="${errors}" var="error">
            <div class="alert alert-warning" role="alert">
                    ${error}
            </div>
        </c:forEach>
    </div>
</c:if>

<form action="${pageContext.request.contextPath}/${action_link}" method="post">
    <div id="paperDetailsDiv" class="container-fluid mb-5">
        <div class="bg-dark py-2 pl-2">
            <h4 class="text-light">Paper's details</h4>
        </div>
        <div class="form-group mt-2">
            <label for="paperTitle"><strong>Title</strong></label>
            <input type="text" id="paperTitle" name="paperTitle" placeholder="Enter paper's title" class="form-control" value="${title}">
            <%--<small id="titleHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>--%>
        </div>
        <div class="form-group">
            <label for="paperDescription"><strong>Description</strong></label>
            <textarea id="paperDescription" name="paperDescription" placeholder="Enter paper's description" class="form-control">${description}</textarea>
            <%--<small id="titleHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>--%>
        </div>

    </div>

    <div id="paperAuthorsDiv" class="container-fluid mb-5">
        <div class="bg-dark py-2 pl-2">
            <h4 class="text-light">Paper's authors</h4>
        </div>

        <%--
        First author inputs
        --%>
        <h5 class="mt-2">First author</h5>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="emailFirstAuthor">Email</label>
                <input id="emailFirstAuthor" name="emailFirstAuthor" type="email" class="form-control"  placeholder="Email" value="${author_list[0].email}">
            </div>
            <div class="form-group col-md-4">
                <label for="firstNameFirstAuthor">First name</label>
                <input id="firstNameFirstAuthor" name="firstNameFirstAuthor" type="text" class="form-control"  placeholder="First name" value="${author_list[0].firstName}">
            </div>
            <div class="form-group col-md-4">
                <label for="lastNameFirstAuthor">Last name</label>
                <input id="lastNameFirstAuthor" name="lastNameFirstAuthor" type="text" class="form-control"  placeholder="Last name" value="${author_list[0].lastName}">
            </div>
        </div>

        <%--
        Second author inputs
        --%>
        <h5 class="mt-2">Second author</h5>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="emailSecondAuthor">Email</label>
                <input id="emailSecondAuthor" name="emailSecondAuthor" type="email" class="form-control"  placeholder="Email" value="${author_list[1].email}">
            </div>
            <div class="form-group col-md-4">
                <label for="firstNameSecondAuthor">First name</label>
                <input id="firstNameSecondAuthor" name="firstNameSecondAuthor" type="text" class="form-control"  placeholder="First name" value="${author_list[1].firstName}">
            </div>
            <div class="form-group col-md-4">
                <label for="lastNameSecondAuthor">Last name</label>
                <input id="lastNameSecondAuthor" name="lastNameSecondAuthor" type="text" class="form-control"  placeholder="Last name" value="${author_list[1].lastName}">
            </div>
        </div>

        <%--
        Third author inputs
        --%>
        <h5 class="mt-2">Third author</h5>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="emailThirdAuthor">Email</label>
                <input id="emailThirdAuthor" name="emailThirdAuthor" type="email" class="form-control"  placeholder="Email" value="${author_list[2].email}">
            </div>
            <div class="form-group col-md-4">
                <label for="firstNameThirdAuthor">First name</label>
                <input id="firstNameThirdAuthor" name="firstNameThirdAuthor" type="text" class="form-control"  placeholder="First name" value="${author_list[2].firstName}">
            </div>
            <div class="form-group col-md-4">
                <label for="lastNameThirdAuthor">Last name</label>
                <input id="lastNameThirdAuthor" name="lastNameThirdAuthor" type="text" class="form-control"  placeholder="Last name" value="${author_list[2].lastName}">
            </div>
        </div>

        <%--
        Fourth author inputs
        --%>
        <h5 class="mt-2">Fourth author</h5>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="emailFourthAuthor">Email</label>
                <input id="emailFourthAuthor" name="emailFourthAuthor" type="email" class="form-control"  placeholder="Email" value="${author_list[3].email}">
            </div>
            <div class="form-group col-md-4">
                <label for="firstNameFourthAuthor">First name</label>
                <input id="firstNameFourthAuthor" name="firstNameFourthAuthor" type="text" class="form-control"  placeholder="First name" value="${author_list[3].firstName}">
            </div>
            <div class="form-group col-md-4">
                <label for="lastNameFourthAuthor">Last name</label>
                <input id="lastNameFourthAuthor" name="lastNameFourthAuthor" type="text" class="form-control"  placeholder="Last name" value="${author_list[3].lastName}">
            </div>
        </div>

        <%--
        Fifth author inputs
        --%>
        <h5 class="mt-2">Fifth author</h5>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="emailFifthAuthor">Email</label>
                <input id="emailFifthAuthor" name="emailFifthAuthor" type="email" class="form-control"  placeholder="Email" value="${author_list[4].email}">
            </div>
            <div class="form-group col-md-4">
                <label for="firstNameFifthAuthor">First name</label>
                <input id="firstNameFifthAuthor" name="firstNameFifthAuthor" type="text" class="form-control"  placeholder="First name" value="${author_list[4].firstName}">
            </div>
            <div class="form-group col-md-4">
                <label for="lastNameFifthAuthor">Last name</label>
                <input id="lastNameFifthAuthor" name="lastNameFifthAuthor" type="text" class="form-control"  placeholder="Last name" value="${author_list[4].lastName}">
            </div>
        </div>
    </div>

    <div id="paperReviewersDiv" class="container-fluid mb-5">
        <div class="bg-dark py-2 pl-2">
            <h4 class="text-light">Paper's reviewers</h4>
        </div>
        <div class="form-group mt-2">
            <label for="paperFirstReviewer"><strong>First reviewer</strong></label>
            <select id="paperFirstReviewer" name="paperFirstReviewer" class="form-control w-25">
                <option value="" selected></option>
                <c:forEach items="${pc_member_list}" var="member">
                    <c:choose>
                        <c:when test="${member.email == first_reviewer}">
                            <option value="${member.email}" selected>${member.firstName} ${member.lastName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${member.email}">${member.firstName} ${member.lastName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="paperSecondReviewer"><strong>Second reviewer</strong></label>
            <select id="paperSecondReviewer" name="paperSecondReviewer" class="form-control w-25">
                <option value=""></option>
                <c:forEach items="${pc_member_list}" var="member">
                    <c:choose>
                        <c:when test="${member.email == second_reviewer}">
                            <option value="${member.email}" selected>${member.firstName} ${member.lastName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${member.email}">${member.firstName} ${member.lastName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="paperThirdReviewer"><strong>Third reviewer</strong></label>
            <select id="paperThirdReviewer" name="paperThirdReviewer" class="form-control w-25">
                <option value=""></option>
                <c:forEach items="${pc_member_list}" var="member">
                    <c:choose>
                        <c:when test="${member.email == third_reviewer}">
                            <option value="${member.email}" selected>${member.firstName} ${member.lastName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${member.email}">${member.firstName} ${member.lastName}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
    </div>

    <input type="hidden" id="paperid" name="paperid" value=${paper_id}>

    <div id="paperSaveDiv" class="container-fluid">
        <button type="submit" class="btn btn-primary">Save</button>
    </div>

</form>
