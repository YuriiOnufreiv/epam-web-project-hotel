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
    <title>Add New Room Type</title>
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
                            <c:if test="${requestScope.addRoomTypeSuccess}">
                                <div class="alert alert-success">
                                    <span><b> Success - </b>New room type was added</span>
                                </div>
                            </c:if>
                            <c:if test="${requestScope.addRoomTypeError}">
                                <div class="alert alert-danger">
                                    <span><fmt:message key="error.new.room.type"/></span>
                                </div>
                            </c:if>
                            <c:if test="${requestScope.invalidRoomTypeError}">
                                <div class="alert alert-danger">
                                    <span><b> Error - </b>Type '${requestScope.roomType}' already exists</span>
                                </div>
                            </c:if>
                            <div class="header">
                                <h4 class="title">Room Type Info</h4>
                            </div>
                            <div class="content">
                                <form action="/hotel?command=addNewRoomType" method="post">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Type</label>
                                                <input name="roomType" type="text" class="form-control" min="0"
                                                       max="999">
                                            </div>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-info btn-fill pull-right">Add new Room Type
                                    </button>
                                    <div class="clearfix"></div>
                                </form>
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
