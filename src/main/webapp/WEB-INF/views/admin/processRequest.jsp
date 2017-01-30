<%--
  Created by IntelliJ IDEA.
  User: yurii
  Date: 1/1/17
  Time: 3:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="dateTag" uri="/WEB-INF/views/tld/dateTag.tld" %>
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
                                    <h5><p class="category">Request Date</p>
                                        <dateTag:date date="${requestScope.bookRequest.creationDate}"
                                                      locale="${sessionScope.language}" showTime="true"/>
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
                                    <h5>
                                        <p class="category">Check In</p>
                                        <dateTag:date date="${requestScope.bookRequest.checkIn}"
                                                      locale="${sessionScope.language}"/>
                                    </h5>
                                </div>
                                <div class="typo-line">
                                    <h5>
                                        <p class="category">Check Out</p>
                                        <dateTag:date date="${requestScope.bookRequest.checkOut}"
                                                      locale="${sessionScope.language}"/>
                                    </h5>
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
                                <h4 class="title">Exact Type Room</h4>
                                <hr>
                            </div>
                            <div class="content">
                                <c:if test="${not empty requestScope.exactTypeRoom}">
                                    <div class="typo-line">
                                        <h5><p class="category">#</p>${requestScope.exactTypeRoom.number}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">
                                            Type</p>${requestScope.idRoomTypeMap[requestScope.exactTypeRoom.roomTypeId]}
                                        </h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Price</p>$${requestScope.exactTypeRoom.price}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Persons</p>${requestScope.exactTypeRoom.maxPerson}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Description</p>${requestScope.exactTypeRoom.description}
                                        </h5>
                                    </div>
                                    <hr>
                                    <a href="/hotel?command=createBill&bookRequestId=${bookRequest.id}&roomId=${requestScope.exactTypeRoom.id}&roomTypeId=${requestScope.exactTypeRoom.roomTypeId}"
                                       class="btn btn-info btn-fill">Create Bill</a>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Favourite Type Room</h4>
                                <hr>
                            </div>
                            <div class="content">
                                <c:if test="${not empty requestScope.favouriteTypeRoom}">
                                    <div class="typo-line">
                                        <h5><p class="category">#</p>${requestScope.favouriteTypeRoom.number}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">
                                            Type</p>${requestScope.idRoomTypeMap[requestScope.favouriteTypeRoom.roomTypeId]}
                                        </h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Price</p>$${requestScope.favouriteTypeRoom.price}
                                        </h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">
                                            Persons</p>${requestScope.favouriteTypeRoom.maxPerson}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">
                                            Description</p>${requestScope.favouriteTypeRoom.description}
                                        </h5>
                                    </div>
                                    <hr>
                                    <a href="/hotel?command=createBill&bookRequestId=${bookRequest.id}&roomId=${requestScope.favouriteTypeRoom.id}&roomTypeId=${requestScope.favouriteTypeRoom.roomTypeId}"
                                       class="btn btn-info btn-fill">Create Bill</a>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Any Type Room</h4>
                                <hr>
                            </div>
                            <div class="content">
                                <c:if test="${not empty requestScope.anyTypeRoom}">
                                    <div class="typo-line">
                                        <h5><p class="category">#</p>${requestScope.anyTypeRoom.number}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">
                                            Type</p>${requestScope.idRoomTypeMap[requestScope.anyTypeRoom.roomTypeId]}
                                        </h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Price</p>$${requestScope.anyTypeRoom.price}</h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">Persons</p>${requestScope.anyTypeRoom.maxPerson}
                                        </h5>
                                    </div>
                                    <div class="typo-line">
                                        <h5><p class="category">
                                            Description</p>${requestScope.anyTypeRoom.description}
                                        </h5>
                                    </div>
                                    <hr>
                                    <a href="/hotel?command=createBill&bookRequestId=${bookRequest.id}&roomId=${requestScope.anyTypeRoom.id}&roomTypeId=${requestScope.anyTypeRoom.roomTypeId}"
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
