<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:myhtml title="${add ? 'Add User' : 'Edit User'}">
    <h2>${add ? "Добавление пользователя" : "Изменение пользователя"}</h2>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <div class="main__form-wrapper">
        <form:form method="post" modelAttribute="user" cssClass="form"
                   action="${add ? '/users/add' : '/users/edit'}">
            <form:hidden path="userId"/>
            <form:label path="login" cssClass="label">Логин</form:label>
            <form:input path="login" cssClass="input-field" required="true" readonly="${!add}"/>
            <c:if test="${add}">
                <form:label path="password" cssClass="label">Пароль</form:label>
                <form:password path="password" cssClass="input-field" required="true"/>
            </c:if>

            <form:label path="name" cssClass="label">Имя</form:label>
            <form:input path="name" cssClass="input-field" required="true"/>
            <form:errors path="name" cssClass="error"/>

            <form:label path="birthday" cssClass="label">Дата рождения</form:label>
            <form:input path="birthday" type="date" cssClass="input-field" required="true"/>
            <form:errors path="birthday" cssClass="error"/>

            <form:label path="salary" cssClass="label">Зарплата</form:label>
            <form:input path="salary" type="number" step="0.01" cssClass="input-field" required="true"/>
            <form:errors path="salary" cssClass="error"/>

            <label for="role" class="label">Роли</label>
            <select name="roleIds" id="role" class="input-field" multiple required>
                <c:forEach var="role" items="${roles}">
                    <option value="${role.id}"
                            <c:if test="${!add && userRoles != null && userRoles.contains(role.name)}">selected</c:if>>
                            ${role.name}
                    </option>
                </c:forEach>
            </select>

            <button type="submit" class="form__button">Сохранить</button>
        </form:form>
    </div>
</t:myhtml>
