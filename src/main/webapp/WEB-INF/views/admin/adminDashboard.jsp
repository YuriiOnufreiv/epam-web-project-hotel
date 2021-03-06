<%--
  Created by IntelliJ IDEA.
  User: yurii
  Date: 1/1/17
  Time: 3:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/views/tld/autoPageRefresh.tld" prefix="autoPageRefresh" %>
<%@ taglib prefix="dateTag" uri="/WEB-INF/views/tld/dateTag.tld" %>
<!DOCTYPE HTML>
<!doctype html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/admin/jspf/head.jspf" %>
    <meta charset="utf-8"/>
    <title>Admin dashboard</title>
    <autoPageRefresh:autoRefresh interval="15"/>
</head>
<body>

<div class="wrapper">
    <%@ include file="/WEB-INF/views/admin/jspf/sidebar.jspf" %>
    <div class="main-panel">
        <%@ include file="/WEB-INF/views/admin/jspf/header.jspf" %>
        <c:if test="${requestScope.newRoomsAvailable}">
            <script>
                demo.showNotification("New room(-s) became available")
            </script>
        </c:if>
        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="header">
                                <h4 class="title">Book Requests</h4>
                                <p class="category">This table contains new book requests</p>
                            </div>
                            <div class="content table-responsive table-full-width">
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th><fmt:message key="bookRequest.creationDate"/></th>
                                        <th>Process</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="bookRequest" items="${requestScope.notProcessedBookRequestList}">
                                        <tr>
                                            <td><c:out value="${bookRequest.id}"/></td>
                                            <td><dateTag:date date="${bookRequest.creationDate}"
                                                              locale="${sessionScope.language}" showTime="true"/></td>
                                            <td>
                                                <a href="/hotel?command=processBookRequest&bookRequestId=${bookRequest.id}"
                                                   class="btn btn-info btn-fill">Process</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
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
