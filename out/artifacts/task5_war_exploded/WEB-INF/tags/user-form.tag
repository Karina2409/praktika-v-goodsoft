<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${empty add}">
        <h2>Изменение пользователя</h2>
        <form action="doEdit-user.jhtml" method="post" class="form">
        <input type="hidden" name="login" value="${user.login}"/>
    </c:when>
    <c:otherwise>
        <h2>Добавление пользователя</h2>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <form action="doAdd-user.jhtml" method="post" class="form">
        <label for="login" class="label">Логин</label>
        <input type="text" class="input-field" name="login" value="" id="login" required/>

        <label for="password" class="label">Пароль</label>
        <input type="password" class="input-field" name="password" value="" id="password" required/>
    </c:otherwise>
</c:choose>

<label for="email" class="label">Email</label>
<input type="email" id="email" class="input-field" value="${add ? '' : user.email}" name="email" required>

<label for="surname" class="label">Фамилия</label>
<input type="text" id="surname" class="input-field" value="${add ? '' : user.surname}" name="surname"
       required>

<label for="name" class="label">Имя</label>
<input type="text" id="name" class="input-field" value="${add ? '' : user.name}" name="name" required>

<label for="patronymic" class="label">Отчество</label>
<input type="text" id="patronymic" class="input-field" value="${add ? '' : user.patronymic}"
       name="patronymic"
       required>

<label for="birthday" class="label">День рождения</label>
<input type="date" id="birthday" class="input-field" value="${add ? '' : user.birthday}" name="birthday"
       required>

<label for="role" class="label">Роль</label>
<select name="role" id="role" class="input-field" required>
    <c:forEach var="role" items="${roles}">
        <option value="${role}"
                <c:if test="${!add && user != null && user.role == role}">selected</c:if>>${role}</option>
    </c:forEach>
</select>

<input type="submit" value="Сохранить" class="form__button"/>
</form>