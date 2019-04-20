<%--
  Created by IntelliJ IDEA.
  User: vincent
  Date: 2019-03-27
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base_site page_title="CSC4710 Project Index">

    <jsp:body>
        <h2>Important note:</h2>
        <p>
            Before using anything else on this application, you need to use the '<strong>Initialize Database</strong>' button below.</br>
            If you don't use this button, all the other pages will show an error message.
        </p>
        <form action="initialize-db">
            <button type="submit" class="btn btn-primary">Initialize Database</button>
        </form>
    </jsp:body>
</t:base_site>