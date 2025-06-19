<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${empty add}">
        <h2>Изменение пользователя</h2>
        <div class="main__form-wrapper">

        </div>
        <form action="edit-user.jhtml" method="post" class="form">
        <input type="hidden" name="login" value="${user.login}"/>
    </c:when>
    <c:otherwise>
        <h2>Добавление пользователя</h2>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <div class="main__form-wrapper">
        <form action="add-user.jhtml" method="post" class="form">
        <label for="login" class="label">Логин</label>
        <input type="text" class="input-field" name="login" value="" id="login" required/>

        <label for="password" class="label">Пароль</label>
        <input type="password" class="input-field" name="password" value="" id="password" required/>
    </c:otherwise>
</c:choose>

<label for="name" class="label">Имя</label>
<input type="text" id="name" class="input-field" value="${add ? '' : user.name}" name="name" required>

<label for="birthday" class="label">Дата рождения</label>
<input type="date" id="birthday" class="input-field" value="${add ? '' : user.birthday}" name="birthday"
       required>

<label for="age" class="label">Возраст</label>
<input type="number" id="age" class="input-field" name="age" value="${add ? '' : user.age}" required/>

<label for="salary" class="label">Зарплата</label>
<input type="number" id="salary" class="input-field" name="salary" step="0.01" value="${add ? '' : user.salary}"
       required/>

<label for="role" class="label">Роли</label>
<select name="roleIds" id="role" class="input-field" multiple required>
    <c:forEach var="role" items="${roles}">
        <option value="${role.id}"
                <c:if test="${!add && userRoles != null && userRoles.contains(role.name)}">selected</c:if>>
                ${role.name}
        </option>
    </c:forEach>
</select>

<input type="submit" value="Сохранить" class="form__button"/>
</form>
</div>