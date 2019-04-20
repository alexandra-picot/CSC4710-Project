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

        <t:add_edit_paper_form action_link="add-paper"
                               pc_member_list="${pcMembers}"
                               errors="${errors}">

        </t:add_edit_paper_form>

    </jsp:body>
</t:base_site>