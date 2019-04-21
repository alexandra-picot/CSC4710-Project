<%--
  Created by IntelliJ IDEA.
  User: vincent
  Date: 2019-04-19
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base_site page_title="Add/Edit Paper Form">

    <jsp:body>
        <c:choose>
            <c:when test="${empty paperDetails}">
                <h1>Paper not found!</h1>
                <p>
                    Sorry, the paper asked couldn't be found.
                </p>
            </c:when>
            <c:otherwise>

                <t:add_edit_paper_form action_link="paper/edit-paper"
                                       title="${paperDetails.title}"
                                       description="${paperDetails.description}"
                                       author_list="${paperAuthors}"
                                       pc_member_list="${pcMembers}"
                                       first_reviewer="${paperReviewers[0]}"
                                       second_reviewer="${paperReviewers[1]}"
                                       third_reviewer="${paperReviewers[2]}"
                                       paper_id="${paperDetails.paperid}"
                                       errors="${errors}">

                </t:add_edit_paper_form>
            </c:otherwise>
        </c:choose>
    </jsp:body>
</t:base_site>