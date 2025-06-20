<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
<c:if test="${not empty error}">
    <p class="error"> ${error} </p>
</c:if>

<form:form action="/login" method="post" cssClass="form" modelAttribute="loginForm">
    <form:label path="login" cssClass="label">Логин</form:label>
    <form:input path="login" cssClass="input-field" required="true" />

    <form:label path="password" cssClass="label">Пароль</form:label>
    <form:password path="password" cssClass="input-field" required="true" />

    <button type="submit" class="form__button">Войти</button>
</form:form>
</body>
</html>