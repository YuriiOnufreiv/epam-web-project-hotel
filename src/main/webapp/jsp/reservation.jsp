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
    <title>Reservation</title>
    <%@ include file="jspf/styles.jspf" %>
    <%@ include file="jspf/scripts.jspf" %>
</head>
<body>
<%@ include file="jspf/header.jspf" %>
<div class="main-1">
    <div class="container">
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <div class="account_grid">
                    <div class="col-md-6 login-left wow fadeInLeft big-padding" data-wow-delay="0.4s">
                        <h3>Ooops :(</h3>
                        <p>To make reservation You should be signed in firstly.</p>
                        <a class="acount-btn" href="/jsp/signin.jsp">Sign In</a>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="register">
                    <c:if test="${not empty requestScope.successfulReserve}">
                        <div class="grid_3 grid_5">
                            <div class="alert alert-success" role="alert">
                                <strong>Thank You!</strong> Our administrator will process Your request as soon as
                                possible.
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.invalidDates}">
                        <div class="grid_3 grid_5">
                            <div class="alert alert-danger" role="alert">
                                <strong>Ooops!</strong> Check-in date mast be before the check-out date.
                            </div>
                        </div>
                    </c:if>
                    <form name="registerForm" method="POST" action="/hotel/reservation" autocomplete="on">
                        <input type="hidden" name="command" value="reservation"/>
                        <div class="register-top-grid">
                            <h3>PERSONAL INFORMATION</h3>
                            <div class="wow fadeInLeft" data-wow-delay="0.4s">
                                <span>First Name<label>*</label></span>
                                <input name="first_name" required="required" type="text"
                                       value="${sessionScope.user.name}"
                                       readonly/>
                            </div>
                            <div class="wow fadeInRight" data-wow-delay="0.4s">
                                <span>Last Name<label>*</label></span>
                                <input name="last_name" required="required" type="text"
                                       value="${sessionScope.user.surname}"
                                       readonly/>
                            </div>
                            <div class="wow fadeInLeft" data-wow-delay="0.4s">
                                <span>Email Address<label>*</label></span>
                                <input name="email" required="required" type="email" value="${sessionScope.user.email}"
                                       readonly/>
                            </div>
                            <div class="wow fadeInRight" data-wow-delay="0.4s">
                                <span>Phone Number<label>*</label></span>
                                <input name="telephone" required="required" type="tel"
                                       value="${sessionScope.user.telephone}"
                                       readonly/>
                            </div>
                        </div>
                        <div class="register-bottom-grid">
                            <h3>REQUEST INFORMATION</h3>
                            <div class="wow fadeInLeft" data-wow-delay="0.4s">
                                <span>Total Persons<label>*</label></span>
                                <input name="total_persons" value="${requestScope.total_persons}" required="required"
                                       type="number" min="1" max="5"/>
                            </div>
                            <div class="wow fadeInRight" data-wow-delay="0.4s">
                                <span>Room Type<label>*</label></span>
                                <select name="room_type" onchange="change_country(this.value)"
                                        class="frm-field required">
                                    <option value="first">First</option>
                                    <option value="second">Second</option>
                                </select>
                            </div>
                            <div class="wow fadeInLeft" data-wow-delay="0.4s">
                                <span>Check In<label>*</label></span>
                                <input name="check_in_date" class="date" id="datepicker" type="text"
                                       value="Press to select date..."
                                       onfocus="this.value = '';"
                                       onblur="if (this.value == '') {this.value = 'Check In';}">

                            </div>
                            <div class="wow fadeInRight" data-wow-delay="0.4s">
                                <span>Check Out<label>*</label></span>
                                <input name="check_out_date" class="date" id="datepicker1" type="text"
                                       value="Press to select date..."
                                       onfocus="this.value = '';"
                                       onblur="if (this.value == '') {this.value = 'Check In';}">
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="register-but">
                            <form>
                                <input type="submit" value="submit">
                                <div class="clearfix"></div>
                            </form>
                        </div>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@ include file="jspf/footer.jspf" %>
</body>
</html>
