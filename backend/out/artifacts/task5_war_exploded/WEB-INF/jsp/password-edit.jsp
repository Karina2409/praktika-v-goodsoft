<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:myhtml title="Change Password">
    <h2>Изменение пароля</h2>
    <form action="/do-password-edit.jhtml"
          method="post"
          class="form">
        <label for="old-password" class="label">
            Введите старый пароль
        </label>
        <input type="text" id="old-password" name="old-password" class="input-field" required>

        <label for="new-password"  class="label">
            Введите новый пароль
        </label>
        <input type="text" id="new-password" name="new-password" class="input-field" required>
        <input type="submit" value="Change" class="form__button">
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <c:if test="${not empty message}">
            <p class="message">${message}</p>
        </c:if>
    </form>
    <form action="/welcome.jhtml" method="get">
        <input type="submit" value="Back" class="form__button">
    </form>
</t:myhtml>