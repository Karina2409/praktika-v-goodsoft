<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header">
    <div class="logo-container">
        <img src="${pageContext.request.contextPath}/assets/logo.webp" alt="Logo" class="logo">
        <div class="logo-container__hello">
            Привет, <span class="logo-container__username">${user.login}.</span>
            <form action="/logout.jhtml" method="post">
                <input type="hidden" name="logout" value="true">
                <input type="submit" value="Выйти" class="logo-container__logout"/>
            </form>
        </div>
    </div>
    <hr class="line">
    <nav class="menu-container">
        <ul class="menu">
            <li class="menu__item"><a href="welcome.jhtml">Главная</a></li>
            <c:if test="${isAdmin}">
                <li class="menu__item"><a href="users.jhtml">Пользователи</a></li>
            </c:if>
        </ul>
    </nav>
</header>