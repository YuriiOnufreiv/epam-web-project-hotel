<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE HTML>
<html>
<head>
    <title>Sign In</title>
    <%@ include file="jspf/styles.jspf" %>
    <%@ include file="jspf/scripts.jspf" %>
</head>
<body>
<!-- banner -->
<!--<div class="banner1">-->
<%@ include file="jspf/header.jspf" %>
<!--</div>-->
<!-- banner -->

<div class="login-page">
    <div class="container">
        <div class="account_grid">
            <div class="col-md-6 login-left wow fadeInLeft" data-wow-delay="0.4s">
                <h3>NEW CUSTOMERS</h3>
                <p>By creating an account with our store, you will be able to move through the checkout process faster,
                    store multiple shipping addresses, view and track your orders in your account and more.</p>
                <a class="acount-btn" href="/jsp/register.jsp">Create an Account</a>
            </div>
            <div class="col-md-6 login-right wow fadeInRight" data-wow-delay="0.4s">
                <h3>REGISTERED CUSTOMERS</h3>
                <p>If you have an account with us, please log in.</p>
                <form name="loginForm" method="POST" action="/hotel/login" autocomplete="on">
                    <input type="hidden" name="command" value="login"/>
                    <div>
                        <span>Email Address<label>*</label></span>
                        <input type="text" name="email" required="required">
                    </div>
                    <div>
                        <span>Password<label>*</label></span>
                        <input name="password" required="required" type="password">
                    </div>
                    <!--<a class="forgot" href="#">Forgot Your Password?</a>-->
                    <input type="submit" value="Login">
                </form>
                <c:if test="${not empty requestScope.errorMessage}">
                    <div class="grid_3 grid_5">
                        <div class="alert alert-danger" role="alert">
                            <strong>Oh snap!</strong> Invalid username or password
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<!-- footer -->
<%@ include file="jspf/footer.jspf" %>
<!-- footer -->
</body>
</html>