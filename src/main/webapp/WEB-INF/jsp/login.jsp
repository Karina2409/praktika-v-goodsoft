<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<form action="login.jhtml" method="post" class="form">
    <label for="login" class="label">Login</label>
    <input type="text" id="login" name="login" class="input-field" required/>

    <label for="password" class="label">Password</label>
    <input type="text" id="password" name="password" class="input-field" required>

    <input type="submit" value="Log On" class="form__button"/>
</form>
</body>
</html>