<%--
  Created by IntelliJ IDEA.
  User: vincent
  Date: 2019-04-17
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_site page_title="Add/Edit Paper Form">

    <jsp:body>

        <t:add_edit_paper_form action_link="paper/add-paper"
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

    </jsp:body>
</t:base_site>