<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<t:myhtml title="Users">
    <h2><fmt:message key="users.list"/></h2>
    <div class="main__table-wrapper">
        <table class="table">
            <thead>
            <tr>
                <td><fmt:message key="login"/></td>
                <td><fmt:message key="name"/></td>
                <td><fmt:message key="birthday"/></td>
                <td><fmt:message key="age"/></td>
                <td><fmt:message key="salary"/></td>
                <td><fmt:message key="roles"/></td>
                <td><fmt:message key="edit"/></td>
                <td><fmt:message key="delete"/></td>
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
                        <form:form action="${pageContext.request.contextPath}/users/edit" method="get">
                            <input type="hidden" name="login" value="${userWithRoles.user.login}" />
                            <button type="submit" title="<fmt:message key='edit'/>" class="icon-button">
                                <img src="${pageContext.request.contextPath}/assets/svg/edit.svg" alt="<fmt:message key='edit'/>">
                            </button>
                        </form:form>
                    </td>
                    <td>
                        <form:form action="${pageContext.request.contextPath}/users/delete" method="post">
                            <input type="hidden" name="login" value="${userWithRoles.user.login}" />
                            <button type="submit" title="<fmt:message key='delete'/>" class="icon-button">
                                <img src="${pageContext.request.contextPath}/assets/svg/delete.svg" alt="<fmt:message key='delete'/>"
                                     class="icon-button__icon">
                            </button>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <form action="${pageContext.request.contextPath}/users/add" method="get">
        <button type="submit" title="<fmt:message key='add'/>" class="icon-button">
            <img src="${pageContext.request.contextPath}/assets/svg/add.svg" alt="<fmt:message key='add'/>" class="icon-button__icon">
        </button>
    </form>
</t:myhtml>