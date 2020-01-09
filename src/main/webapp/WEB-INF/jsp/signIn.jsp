<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
    <form action="controller" method="post">
		<input type="hidden" name="command" value="authorization" />
		Login:
		<input type="text" name="login" value="" /><br/>
		Password:
		<input type="password" name="password" value=""/><br/>
		<input type="submit" value="sing in" /><br/>
	</form>

	<c:if test="${not empty param.errorMessage }">
		<c:out value="${param.errorMessage}"></c:out>
	</c:if>
	

	<br/>
	<form action="controller" method="post">
		<input type="hidden" name="command" value="go_to_registration_page" />
		<input type="submit" value="registration" /><br/>
	</form>
</body>
</html>