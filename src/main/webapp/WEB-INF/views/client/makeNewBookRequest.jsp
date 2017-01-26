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
        <div class="register">
            <c:if test="${not empty requestScope.newBookRequestSuccess}">
                <div class="grid_3 grid_5">
                    <div class="alert alert-success" role="alert">
                        <strong><fmt:message key="general.thank_you"/></strong><fmt:message
                            key="message.successful.reserve"/>
                    </div>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.invalidBookRequestDatesError}">
                <div class="grid_3 grid_5">
                    <div class="alert alert-danger" role="alert">
                        <strong><fmt:message key="reservation.oops"/> </strong><fmt:message
                            key="error.invalid.date"/>
                    </div>
                </div>
            </c:if>
            <form name="registerForm" method="POST" action="/hotel" autocomplete="on">
                <input type="hidden" name="command" value="makeNewBooking"/>
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
                        <input name="bookRequestPersons" value="${requestScope.bookRequestPersons}" required="required"
                               type="number" min="1" max="5"/>
                    </div>
                    <div class="wow fadeInRight" data-wow-delay="0.4s">
                        <span><fmt:message key="bookRequest.room_type"/></span>
                        <select name="roomType" class="frm-field required">
                            <c:forEach items="${sessionScope.idTypeTitleMap}" var="entry">
                                <option value="${entry.key}" ${entry.key == requestScope.roomType ? 'selected' : ''}>
                                    <c:out value="${entry.value}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="wow fadeInLeft" data-wow-delay="0.4s">
                        <span><fmt:message key="bookRequest.checkin"/></span>
                        <input name="checkInDate" class="date" id="datepicker" type="text"
                               value="Press to select date..."
                               onfocus="this.value = '';"
                               onblur="if (this.value == '') {this.value = 'Check In';}">

                    </div>
                    <div class="wow fadeInRight" data-wow-delay="0.4s">
                        <span><fmt:message key="bookRequest.checkout"/></span>
                        <input name="checkOutDate" class="date" id="datepicker1" type="text"
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
    </div>
</div>
<%@ include file="/WEB-INF/views/client/jspf/footer.jspf" %>
</body>
</html>
