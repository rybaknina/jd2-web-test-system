<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error page</title>
</head>
<body>
<c:if test="${not empty param.errorMessage }">
    <c:out value="${param.errorMessage}"></c:out>
</c:if>
</body>
</html>