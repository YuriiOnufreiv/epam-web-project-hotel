<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="/WEB-INF/views/client/jspf/head.jspf" %>
    <title><fmt:message key="general.register"/></title>
</head>
<body>
<%@ include file="/WEB-INF/views/client/jspf/header.jspf" %>
<div class="main-1">
    <div class="container">
        <div class="register">
            <form name="registerForm" method="POST" action="/hotel" autocomplete="on">
                <input type="hidden" name="command" value="register"/>
                <div class="register-top-grid">
                    <h3><fmt:message key="register.personal_info"/></h3>
                    <div class="wow fadeInLeft" data-wow-delay="0.4s">
                        <span><fmt:message key="general.first_name"/></span>
                        <input name="userFirstName" required="required" type="text"
                               value="${requestScope.userFirstName}" placeholder="Taras">
                    </div>
                    <div class="wow fadeInRight" data-wow-delay="0.4s">
                        <span><fmt:message key="general.last_name"/></span>
                        <input name="userLastName" required="required" type="text" value="${requestScope.userLastName}"
                               placeholder="Shevchenko"/>
                    </div>
                    <div class="wow fadeInLeft" data-wow-delay="0.4s">
                        <span><fmt:message key="general.email"/></span>
                        <input name="userEmail" required="required" type="email" value="${requestScope.userEmail}"
                               placeholder="shevchenko@mail.com"/>
                    </div>
                    <div class="wow fadeInRight" data-wow-delay="0.4s">
                        <span><fmt:message key="general.phone_number"/></span>
                        <input name="userTelephoneNumber" required="required" type="tel"
                               value="${requestScope.userTelephoneNumber}"
                               maxlength="12" placeholder="eg. 380977767760"/>
                    </div>
                </div>
                <div class="register-bottom-grid">
                    <h3><fmt:message key="register.login_info"/></h3>
                    <div class="wow fadeInLeft" data-wow-delay="0.4s">
                        <span><fmt:message key="general.password"/></span>
                        <input name="userPassword" required="required" type="password" placeholder="eg. X8df!90EO"/>
                    </div>
                </div>
                <div class="clearfix"></div>
                <c:if test="${not empty requestScope.signUpErrors}">
                    <div class="grid_3 grid_5">
                        <div class="alert alert-danger" role="alert">
                            <ul class="pad-left-25">
                                <c:forTokens items="${requestScope.signUpErrors}" var="error" delims="|">
                                    <li><strong><fmt:message key="${error}"/></strong></li>
                                </c:forTokens>
                            </ul>
                        </div>
                    </div>
                </c:if>
                <div class="register-but">
                    <form>
                        <input type="submit" value=<fmt:message key="general.register"/>>
                        <div class="clearfix"></div>
                    </form>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- footer -->
<%@ include file="/WEB-INF/views/client/jspf/footer.jspf" %>
<!-- footer -->
</body>
</html>