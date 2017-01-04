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
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <title><fmt:message key="header.reservation"/></title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="main-1">
    <div class="container">
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <div class="account_grid">
                    <div class="col-md-6 login-left wow fadeInLeft big-padding" data-wow-delay="0.4s">
                        <h3><fmt:message key="reservation.oops"/></h3>
                        <p><fmt:message key="reservation.sign_in_message"/></p>
                        <a class="acount-btn" href="/jsp/performLogin.jsp"><fmt:message key="general.sign.in"/></a>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="register">
                    <c:if test="${not empty requestScope.successfulReserve}">
                        <div class="grid_3 grid_5">
                            <div class="alert alert-success" role="alert">
                                <strong><fmt:message key="general.thank_you"/></strong><fmt:message key="message.successful.reserve"/>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.invalidDates}">
                        <div class="grid_3 grid_5">
                            <div class="alert alert-danger" role="alert">
                                <strong><fmt:message key="reservation.oops"/> </strong><fmt:message key="error.invalid.checkin_date"/>
                            </div>
                        </div>
                    </c:if>
                    <form name="registerForm" method="POST" action="/hotel/reservation" autocomplete="on">
                        <input type="hidden" name="command" value="reservation"/>
                        <div class="register-top-grid">
                            <h3><fmt:message key="register.personal_info"/></h3>
                            <div class="wow fadeInLeft" data-wow-delay="0.4s">
                                <span><fmt:message key="general.first_name"/></span>
                                <input name="first_name" required="required" type="text"
                                       value="${sessionScope.user.name}"
                                       readonly/>
                            </div>
                            <div class="wow fadeInRight" data-wow-delay="0.4s">
                                <span><fmt:message key="general.last_name"/></span>
                                <input name="last_name" required="required" type="text"
                                       value="${sessionScope.user.surname}"
                                       readonly/>
                            </div>
                            <div class="wow fadeInLeft" data-wow-delay="0.4s">
                                <span><fmt:message key="general.email"/></span>
                                <input name="email" required="required" type="email" value="${sessionScope.user.email}"
                                       readonly/>
                            </div>
                            <div class="wow fadeInRight" data-wow-delay="0.4s">
                                <span><fmt:message key="general.phone_number"/></span>
                                <input name="phoneNum" required="required" type="tel"
                                       value="${sessionScope.user.phoneNum}"
                                       readonly/>
                            </div>
                        </div>
                        <div class="register-bottom-grid">
                            <h3><fmt:message key="reservation.request_info"/></h3>
                            <div class="wow fadeInLeft" data-wow-delay="0.4s">
                                <span><fmt:message key="bookRequest.total_persons"/></span>
                                <input name="total_persons" value="${requestScope.total_persons}" required="required"
                                       type="number" min="1" max="5"/>
                            </div>
                            <div class="wow fadeInRight" data-wow-delay="0.4s">
                                <span><fmt:message key="bookRequest.room_type"/></span>
                                <select name="room_type" onchange="change_country(this.value)"
                                        class="frm-field required">
                                    <option value="first">First</option>
                                    <option value="second">Second</option>
                                </select>
                            </div>
                            <div class="wow fadeInLeft" data-wow-delay="0.4s">
                                <span><fmt:message key="bookRequest.checkin"/></span>
                                <input name="check_in_date" class="date" id="datepicker" type="text"
                                       value="Press to select date..."
                                       onfocus="this.value = '';"
                                       onblur="if (this.value == '') {this.value = 'Check In';}">

                            </div>
                            <div class="wow fadeInRight" data-wow-delay="0.4s">
                                <span><fmt:message key="bookRequest.checkout"/></span>
                                <input name="check_out_date" class="date" id="datepicker1" type="text"
                                       value="Press to select date..."
                                       onfocus="this.value = '';"
                                       onblur="if (this.value == '') {this.value = 'Check In';}">
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="register-but">
                            <form>
                                <input type="submit" value=<fmt:message key="reservation.submit"/>>
                                <div class="clearfix"></div>
                            </form>
                        </div>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>