<%--
  Created by IntelliJ IDEA.
  User: yurii
  Date: 1/1/17
  Time: 3:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/client/jspf/head.jspf" %>
    <title><fmt:message key="header.reservation"/></title>
</head>
<body>
<%@ include file="/WEB-INF/views/client/jspf/header.jspf" %>
<div class="main-1">
    <div class="container">
        <div class="account_grid">
            <div class="col-md-6 login-left wow fadeInLeft big-padding" data-wow-delay="0.4s">
                <h3><fmt:message key="reservation.oops"/></h3>
                <p><fmt:message key="reservation.sign_in_message"/></p>
                <a class="acount-btn" href="/hotel?command=forward&page=login"><fmt:message key="general.sign.in"/></a>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/client/jspf/footer.jspf" %>
</body>
</html>
