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
    <title>Process request</title>
</head>
<body>

<div class="wrapper">
    <%@ include file="/WEB-INF/views/admin/jspf/sidebar.jspf" %>
    <div class="main-panel">
        <%@ include file="/WEB-INF/views/admin/jspf/header.jspf" %>
        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-8">
                        <div class="card">
                            <c:if test="${requestScope.createBillError}">
                                <div class="alert alert-danger">
                                    <span><b> Error - </b>Unable to create bill. Try again</span>
                                </div>
                            </c:if>
                            <div class="header">
                                <h4 class="title">Request #${requestScope.bookRequest.id}</h4>
                                <hr>
                            </div>
                            <div class="content">
                                <div class="typo-line">
                                    <h5><p class="category">Request Date</p><fmt:formatDate pattern="MM/dd/yyyy hh:mm"
                                                                                            value="${requestScope.bookRequest.creationDate}"/>
                                    </h5>
                                </div>
                                <div class="typo-line">
                                    <h5><p class="category">Persons</p>${requestScope.bookRequest.persons}</h5>
                                </div>
                                <div class="typo-line">
                                    <h5><p class="category">Room
                                        type</p>${requestScope.idRoomTypeMap[requestScope.bookRequest.roomTypeId]}
                                    </h5>
                                </div>
                                <div class="typo-line">
                                    <h5><p class="category">Check In</p>${requestScope.bookRequest.checkIn}</h5>
                                </div>
                                <div class="typo-line">
                                    <h5><p class="category">Check Out</p>${requestScope.bookRequest.checkOut}</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card card-user">
                            <div class="image">
                                <img src="https://ununsplash.imgix.net/photo-1431578500526-4d9613015464?fit=crop&fm=jpg&h=300&q=75&w=400"
                                     alt="..."/>
                            </div>
                            <div class="content">
                                <div class="author">
                                    <a>
                                        <img class="avatar border-gray" src="/resources/admin/img/faces/face-0.jpg"
                                             alt="..."/>
                                        <h4 class="title">${requestScope.user.name} ${requestScope.user.surname}<br/>
                                            <hr>
                                            <small><b>Email:</b> ${requestScope.user.email}</small>
                                            <br/>
                                            <small><b>Phone:</b> ${requestScope.user.phoneNum}</small>
                                            <br/>
                                            <hr>
                                        </h4>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-4">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Exact Room</h4>
                            </div>
                            <div class="content">
                                <c:if test="${not empty requestScope.exactRoom}">
                                    <div class="typo-line">
                                        <h5><p class="category">#</p>${requestScope.exactRoom.number}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">
                                            Type</p>${requestScope.idRoomTypeMap[requestScope.exactRoom.roomTypeId]}
                                        </h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Price</p>$${requestScope.exactRoom.price}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Persons</p>${requestScope.exactRoom.maxPerson}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Description</p>${requestScope.exactRoom.description}
                                        </h5>
                                    </div>
                                    <a href="/hotel?command=createBill&bookRequestId=${bookRequest.id}&roomId=${requestScope.exactRoom.id}&roomTypeId=${requestScope.exactRoom.roomTypeId}"
                                       class="btn btn-info btn-fill">Create Bill</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Alternative Room</h4>
                            </div>
                            <div class="content">
                                <c:if test="${not empty requestScope.alternativeRoom}">
                                    <c:set var="alternativeType" scope="page"
                                           value="${requestScope.idRoomTypeMap[requestScope.alternativeRoom.roomTypeId]}"/>
                                    <div class="typo-line">
                                        <h5><p class="category">#</p>${requestScope.alternativeRoom.number}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Type</p>${alternativeType.type}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Price</p>$${alternativeType.price}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Persons</p>${alternativeType.maxPerson}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Description</p>${alternativeType.description}</h5>
                                    </div>
                                    <a href="/hotel?command=createBill&bookRequestId=${bookRequest.id}&roomId=${requestScope.alternativeRoom.id}&roomTypeId=${requestScope.alternativeRoom.roomTypeId}"
                                       class="btn btn-info btn-fill">Create Bill</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </div>
        <%@ include file="/WEB-INF/views/admin/jspf/footer.jspf" %>
    </div>
</div>
</body>
</html>
