<%--
  Created by IntelliJ IDEA.
  User: yurii
  Date: 1/1/17
  Time: 3:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<!doctype html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/admin/jspf/head.jspf" %>
    <meta charset="utf-8"/>
    <title>Bill Info</title>
</head>
<body>

<div class="wrapper">
    <%@ include file="/WEB-INF/views/admin/jspf/sidebar.jspf" %>
    <div class="main-panel">
        <%@ include file="/WEB-INF/views/admin/jspf/header.jspf" %>
        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Bill for Request #${requestScope.bookRequestId}</h4>
                                <hr>
                            </div>
                            <div class="content">
                                <div class="typo-line">
                                    <h5><p class="category">Request Date</p><fmt:formatDate pattern="MM/dd/yyyy hh:mm"
                                                                                            value="${requestScope.bookRequestCreationDate}"/>
                                    </h5>
                                </div>
                                <div class="typo-line">
                                    <h5><p class="category">Persons</p>${requestScope.bookRequestPersons}</h5>
                                </div>
                                <div class="typo-line">
                                    <h5><p class="category">Room type</p>${requestScope.roomType}</h5>
                                </div>
                                <div class="typo-line">
                                    <h5><p class="category">Check In</p>${requestScope.checkInDate}</h5>
                                </div>
                                <div class="typo-line">
                                    <h5><p class="category">Check Out</p>${requestScope.checkOutDate}</h5>
                                </div>
                                <hr>
                                <div class="typo-line">
                                    <h5><p class="category"><b>Total Price</b></p><b>$${requestScope.billTotalPrice}</b>
                                    </h5>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%--<div class="col-md-4">--%>
                        <%--<div class="card card-user">--%>
                            <%--<div class="image">--%>
                                <%--<img src="https://ununsplash.imgix.net/photo-1431578500526-4d9613015464?fit=crop&fm=jpg&h=300&q=75&w=400"--%>
                                     <%--alt="..."/>--%>
                            <%--</div>--%>
                            <%--<div class="content">--%>
                                <%--<div class="author">--%>
                                    <%--<a>--%>
                                        <%--<img class="avatar border-gray" src="/resources/assets/img/faces/face-0.jpg"--%>
                                             <%--alt="..."/>--%>
                                        <%--<h4 class="title">${requestScope.user.name} ${requestScope.user.surname}<br/>--%>
                                            <%--<hr>--%>
                                            <%--<small><b>Email:</b> ${requestScope.user.email}</small>--%>
                                            <%--<br/>--%>
                                            <%--<small><b>Phone:</b> ${requestScope.user.phoneNum}</small>--%>
                                            <%--<br/>--%>
                                            <%--<hr>--%>
                                        <%--</h4>--%>
                                    <%--</a>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                </div>
            </div>
            <a href="/hotel?command=showAdminDashboard" class="btn btn-info btn-fill">Back to new requests</a>
        </div>
        <%@ include file="/WEB-INF/views/admin/jspf/footer.jspf" %>
    </div>
</div>
</body>
</html>