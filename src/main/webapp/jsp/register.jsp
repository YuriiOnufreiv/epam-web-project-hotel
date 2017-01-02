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
    <title>Register</title>
    <%@ include file="jspf/styles.jspf" %>
    <%@ include file="jspf/scripts.jspf" %>
</head>
<body>
<%@ include file="jspf/header.jspf" %>
<div class="main-1">
    <div class="container">
        <div class="register">
            <form name="registerForm" method="POST" action="/hotel/register" autocomplete="on">
                <input type="hidden" name="command" value="register"/>
                <div class="register-top-grid">
                    <h3>PERSONAL INFORMATION</h3>
                    <div class="wow fadeInLeft" data-wow-delay="0.4s">
                        <span>First Name<label>*</label></span>
                        <input input name="first_name" required="required" type="text"
                               value="${requestScope.first_name}" placeholder="Taras">
                    </div>
                    <div class="wow fadeInRight" data-wow-delay="0.4s">
                        <span>Last Name<label>*</label></span>
                        <input name="last_name" required="required" type="text" value="${requestScope.last_name}"
                               placeholder="Shevchenko"/>
                    </div>
                    <div class="wow fadeInLeft" data-wow-delay="0.4s">
                        <span>Email Address<label>*</label></span>
                        <input name="email" required="required" type="email" value="${requestScope.email}"
                               placeholder="shevchenko@mail.com"/>
                    </div>
                    <div class="wow fadeInRight" data-wow-delay="0.4s">
                        <span>Phone Number<label>*</label></span>
                        <input name="telephone" required="required" type="tel" value="${requestScope.telephone}"
                               maxlength="12" placeholder="eg. 380977767760"/>
                    </div>
                </div>
                <div class="register-bottom-grid">
                    <h3>LOGIN INFORMATION</h3>
                    <div class="wow fadeInLeft" data-wow-delay="0.4s">
                        <span>Password<label>*</label></span>
                        <input name="password" required="required" type="password" placeholder="eg. X8df!90EO"/>
                    </div>
                </div>
                <div class="clearfix"></div>
                <c:if test="${not empty requestScope.emailError or not empty requestScope.phoneError
                                or not empty requestScope.passwordError}">
                    <div class="grid_3 grid_5">
                        <div class="alert alert-danger" role="alert">
                            <ul style="padding-left: 25px;">
                            <c:if test="${not empty requestScope.emailError}">
                                <li><strong>User with such email already exists</strong></li>
                            </c:if>
                            <c:if test="${not empty requestScope.phoneError}">
                                <li><strong>Invalid phone number</strong></li>
                            </c:if>
                            <c:if test="${not empty requestScope.passwordError}">
                                <li><strong>Password doesn't meet requirements</strong></li>
                            </c:if>
                            </ul>
                        </div>
                    </div>
                </c:if>
                <div class="register-but">
                    <form>
                        <input type="submit" value="submit">
                        <div class="clearfix"></div>
                    </form>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- footer -->
<%@ include file="jspf/footer.jspf" %>
<!-- footer -->
</body>
</html>