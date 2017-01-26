<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="/WEB-INF/views/client/jspf/head.jspf" %>
    <title><fmt:message key="general.sign.in"/></title>
</head>
<body>
<%@ include file="/WEB-INF/views/client/jspf/header.jspf" %>

<div class="login-page">
    <div class="container">
        <div class="account_grid">
            <div class="col-md-6 login-left wow fadeInLeft" data-wow-delay="0.4s">
                <h3><fmt:message key="login.new_customer"/></h3>
                <p><fmt:message key="login.new_customer.label"/></p>
                <a class="acount-btn" href="/hotel?command=forward&page=createAccount"><fmt:message
                        key="login.create_account"/></a>
            </div>
            <div class="col-md-6 login-right wow fadeInRight" data-wow-delay="0.4s">
                <c:if test="${not empty requestScope.signUpSuccess}">
                    <div class="grid_3 grid_5">
                        <div class="alert alert-success" role="alert">
                            <strong><fmt:message key="general.thank_you"/></strong> <fmt:message
                                key="message.successful.sign_up"/>
                        </div>
                    </div>
                </c:if>
                <h3><fmt:message key="login.registered_customers"/></h3>
                <p><fmt:message key="login.registered_customers.label"/></p>
                <form name="loginForm" method="POST" action="/hotel" autocomplete="on">
                    <input type="hidden" name="command" value="login"/>
                    <div>
                        <span><fmt:message key="general.email"/><label>*</label></span>
                        <input type="text" name="userEmail" required="required">
                    </div>
                    <div>
                        <span><fmt:message key="general.password"/><label>*</label></span>
                        <input name="userPassword" required="required" type="password">
                    </div>
                    <!--<a class="forgot" href="#">Forgot Your Password?</a>-->
                    <input type="submit" value=<fmt:message key="general.sign.in"/>>
                </form>
                <c:if test="${not empty requestScope.invalidLoginError}">
                    <div class="grid_3 grid_5">
                        <div class="alert alert-danger" role="alert">
                            <strong><fmt:message key="login.oh_snap"/></strong><fmt:message key="errors.invalid.username_or_pwd"/>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<!-- footer -->
<%@ include file="/WEB-INF/views/client/jspf/footer.jspf" %>
<!-- footer -->
</body>
</html>