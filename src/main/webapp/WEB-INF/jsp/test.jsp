<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="resources.local" var="lang" />

<!doctype html>

<html lang="${lang}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Fonts -->
    <link rel="dns-prefetch" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css?family=Raleway:300,400,600" rel="stylesheet" type="text/css">

    <link rel="stylesheet" href="${contextPath}/styles/css/style.css">


    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <title>Tests</title>
    <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;margin:  80px}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
        .error {
            margin:  100px;
            color: red;}
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light navbar-laravel">

    <div class="container">
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="localization" />
                        <input type="hidden" name="language" value="ru" />
                        <a class="nav-link text-primary" href="#" onclick="parentNode.submit();"><fmt:message bundle="${lang}" key="local.lnk.name.ru"/></a>
                    </form>
                </li>
                <li class="nav-item">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="localization" />
                        <input	type="hidden" name="language" value="en" />
                        <a class="nav-link text-primary" href="#" onclick="parentNode.submit();"><fmt:message bundle="${lang}" key="local.lnk.name.en"/></a>
                    </form>
                </li>
            </ul>
        </div>
        <form action="controller" method="post">
            <input type="hidden" name="command" value="go_to_test_page" />
            <a class="navbar-brand" href="#" onclick="parentNode.submit();"><fmt:message bundle="${lang}" key="entry.label.testsystem"/></a>
        </form>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="sign_out" />
                        <a class="nav-link" href="#" onclick="parentNode.submit();"><fmt:message bundle="${lang}" key="entry.label.home" /></a>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <a class="navbar-brand" href="controller?command=go_to_add_test_page"><fmt:message key="test.label.add" bundle="${lang}"/></a>
</div>
<table class="tg">
    <thead>
    <tr>
        <th>#</th>
        <th><fmt:message key="test.label.name" bundle="${lang}" /></th>
        <th><fmt:message key="test.label.time" bundle="${lang}" /></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:set var="i" value="1" />
    <c:forEach items="${listTests}" var="test">
        <tr>
            <td>${test.id}</td>
            <td width="120">${test.name}</td>
            <td width="120">${test.time}</td>
            <td width="120">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="start_test" />
                    <input type="hidden" name="test" value="${test}" />
                    <fmt:message key="local.lnk.starttest" bundle="${lang}" var="buttonValue" />
                    <input type="submit" class="btn btn-primary" value="${buttonValue}">
                </form>
            </td>
            <%--<td><a href="<c:url value='/start/${u.id}' />" ><fmt:message bundle="${lang}" key="local.lnk.starttest" /></a></td>--%>
        </tr>
        <c:set var="i" value="${i+1}" />
    </c:forEach>
    </tbody>
</table>
<div class="error">
    <c:if test="${not empty param.errorMessage }">
        <c:out value="${param.errorMessage}"></c:out>
    </c:if>
</div>
</body>
</html>
