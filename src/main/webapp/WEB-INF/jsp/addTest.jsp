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
<jsp:useBean id="now" class="java.util.Date"/>
<!doctype html>

<html lang="${lang}">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Fonts -->
    <link rel="dns-prefetch" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css?family=Raleway:300,400,600" rel="stylesheet" type="text/css">

    <link rel="stylesheet" href="${contextPath}/styles/css/style.css">

    <!-- Bootstrap CSS -->
    <%--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">--%>
    <title>Add test</title>
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
                        <a class="nav-link text-primary" href="#" onclick="parentNode.submit();"><fmt:message bundle="${lang}" key="local.lnk.text.ru"/></a>
                    </form>
                </li>
                <li class="nav-item">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="localization" />
                        <input	type="hidden" name="language" value="en" />
                        <a class="nav-link text-primary" href="#" onclick="parentNode.submit();"><fmt:message bundle="${lang}" key="local.lnk.text.en"/></a>
                    </form>
                </li>
            </ul>
        </div>
        <a class="navbar-brand" href="controller?command=go_to_test_page"><fmt:message key="entry.label.testsystem" bundle="${lang}"/></a>
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

<main class="login-form">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header"><fmt:message key="test.label.add" bundle="${lang}" /></div>
                    <div class="card-body">
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="add_test" />
                            <div class="form-group row">
                                <label for="text" class="col-md-4 col-form-label text-md-right"><fmt:message key="test.label.text" bundle="${lang}" /></label>
                                <div class="col-md-6">
                                    <input type="text" id="text" class="form-control" name="text" required autofocus>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="time" class="col-md-4 col-form-label text-md-right"><fmt:message key="test.label.time" bundle="${lang}" /></label>
                                <div class="col-md-6">
                                    <input type="text" id="time" class="form-control" name="time" pattern="([01]?[0-9]{1}|2[0-3]{1}):[0-5]{1}[0-9]{1}" value = "01:00" required>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="status" class="col-md-4 col-form-label text-md-right"><fmt:message key="test.label.status" bundle="${lang}" /></label>
                                <div class="col-md-6">
                                    <input type="text" id="status" class="form-control" name="status" required>
                                </div>
                            </div>

                            <div class="col-md-6 offset-md-4">
                                <fmt:message key="test.label.add" bundle="${lang}" var="buttonValue" />
                                <input type="submit" class="btn btn-primary" value="${buttonValue}">
                            </div>
                            <div class="col-md-6 text-danger">
                                <c:if test="${not empty param.errorMessage }">
                                    <c:out value="${param.errorMessage}"></c:out>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>
