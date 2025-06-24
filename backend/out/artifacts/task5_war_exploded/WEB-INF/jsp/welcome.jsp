<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:myhtml title="Welcome">
    <h1>Добро пожаловать!</h1>

    <form action="/password-edit.jhtml" method="get">
        <input type="submit" value="Change Password" class="form__button">
    </form>
</t:myhtml>
