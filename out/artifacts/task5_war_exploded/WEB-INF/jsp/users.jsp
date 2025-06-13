<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:myhtml title="Users">
    <h2>Список пользователей</h2>
    <table class="table">
        <thead>
        <tr>
            <td>Логин</td>
            <td>Фамилия</td>
            <td>Имя</td>
            <td>Отчество</td>
            <td>Email</td>
            <td>День рождения</td>
            <td>Роль</td>
            <td>Изменить</td>
            <td>Удалить</td>
        </tr>
        </thead>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.login}</td>
                <td>${user.surname}</td>
                <td>${user.name}</td>
                <td>${user.patronymic}</td>
                <td>${user.email}</td>
                <td>${user.birthday}</td>
                <td>${user.role}</td>
                <td>
                    <form action="edit-user.jhtml" method="get">
                        <input type="hidden" name="login" value="${user.login}"/>
                        <button type="submit" title="Редактировать" class="icon-button">
                            <img src="${pageContext.request.contextPath}/assets/svg/edit.svg" alt="Edit">
                        </button>
                    </form>
                </td>
                <td>
                    <form action="delete-user.jhtml" method="post">
                        <input type="hidden" name="login" value="${user.login}"/>
                        <button type="submit" title="Удалить" class="icon-button">
                            <img src="${pageContext.request.contextPath}/assets/svg/delete.svg" alt="Delete" class="icon-button__icon">
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <form action="add-user.jhtml" method="get">
        <button type="submit" title="Добавить" class="icon-button">
            <img src="${pageContext.request.contextPath}/assets/svg/add.svg" alt="Add" class="icon-button__icon">
        </button>
    </form>
</t:myhtml>