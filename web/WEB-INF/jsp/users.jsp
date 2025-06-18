<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:myhtml title="Users">
    <h2>Список пользователей</h2>
    <div class="main__table-wrapper">
        <table class="table">
            <thead>
            <tr>
                <td>Логин</td>
                <td>Имя</td>
                <td>Дата рождения</td>
                <td>Возраст</td>
                <td>Зарплата</td>
                <td>Роли</td>
                <td>Изменить</td>
                <td>Удалить</td>
            </tr>
            </thead>
            <c:forEach var="userWithRoles" items="${users}">
                <tr>
                    <td>${userWithRoles.user.login}</td>
                    <td>${userWithRoles.user.name}</td>
                    <td>${userWithRoles.user.birthday}</td>
                    <td>${userWithRoles.user.age}</td>
                    <td>${userWithRoles.user.salary}</td>
                    <td>
                        <div class="role__list">
                            <c:forEach var="role" items="${userWithRoles.roles}">
                                <div class="role-name">
                                        ${role.name}
                                </div>
                            </c:forEach>
                        </div>
                    </td>
                    <td>
                        <form action="edit-user.jhtml" method="get">
                            <input type="hidden" name="login" value="${userWithRoles.user.login}"/>
                            <button type="submit" title="Редактировать" class="icon-button">
                                <img src="${pageContext.request.contextPath}/assets/svg/edit.svg" alt="Edit">
                            </button>
                        </form>
                    </td>
                    <td>
                        <form action="delete-user.jhtml" method="post">
                            <input type="hidden" name="login" value="${userWithRoles.user.login}"/>
                            <button type="submit" title="Удалить" class="icon-button">
                                <img src="${pageContext.request.contextPath}/assets/svg/delete.svg" alt="Delete"
                                     class="icon-button__icon">
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <form action="add-user.jhtml" method="get">
        <button type="submit" title="Добавить" class="icon-button">
            <img src="${pageContext.request.contextPath}/assets/svg/add.svg" alt="Add" class="icon-button__icon">
        </button>
    </form>
</t:myhtml>