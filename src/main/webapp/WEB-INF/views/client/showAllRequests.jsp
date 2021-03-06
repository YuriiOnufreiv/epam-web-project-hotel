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
<html>
<head>
    <%@ include file="/WEB-INF/views/client/jspf/head.jspf" %>
    <title><fmt:message key="header.bookRequests"/></title>
</head>
<body>
<%@ include file="/WEB-INF/views/client/jspf/header.jspf" %>
<div class="main-1">
    <div class="container big-padding">
        <h3 class="hdg"><fmt:message key="bookRequests.your_requests"/></h3>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th><fmt:message key="bookRequest.creationDate"/></th>
                <th><fmt:message key="bookRequest.checkin"/></th>
                <th><fmt:message key="bookRequest.checkout"/></th>
                <th><fmt:message key="bookRequest.room_type"/></th>
                <th><fmt:message key="bookRequest.total_persons"/></th>
                <th><fmt:message key="bookRequest.bill"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="bookRequest" items="${requestScope.bookRequestList}">
                <tr>
                    <td><c:out value="${bookRequest.id}"/></td>
                    <td><dateTag:date date="${bookRequest.creationDate}" locale="${sessionScope.language}"
                                      showTime="true"/></td>
                    <td><dateTag:date date="${bookRequest.checkIn}" locale="${sessionScope.language}"/></td>
                    <td><dateTag:date date="${bookRequest.checkOut}" locale="${sessionScope.language}"/></td>
                    <td><c:out value="${requestScope.idRoomTypeMap[bookRequest.roomTypeId]}"/></td>
                    <td><c:out value="${bookRequest.persons}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${bookRequest.processed}">
                                <a class="acount-btn" href="/hotel?command=showBillInfo&bookRequestId=${bookRequest.id}"><fmt:message key="general.see"/></a>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="bookRequest.notProcessed"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/WEB-INF/views/client/jspf/footer.jspf" %>
</body>
</html>
