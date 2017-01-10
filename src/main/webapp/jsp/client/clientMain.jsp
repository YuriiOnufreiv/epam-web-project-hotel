<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/client/head.jspf" %>
    <title><fmt:message key="index.page.title"/></title>
</head>
<body>
<!-- banner -->
<div class="banner">
    <%@ include file="/WEB-INF/jspf/client/header.jspf" %>
    <div class="banner-info">
        <div class="container"></div>
    </div>
</div>
<!-- footer -->
<%@ include file="/WEB-INF/jspf/client/footer.jspf" %>
<!-- footer -->
</body>
</html>