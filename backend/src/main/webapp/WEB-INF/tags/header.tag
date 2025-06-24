<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<header class="header">
    <div class="logo-container">
        <img src="${pageContext.request.contextPath}/assets/logo.webp" alt="<fmt:message key="logo"/>" class="logo">
        <div class="locale">
            <c:url var="ruUrl" value="${currentPath}">
                <c:param name="lang" value="ru" />
                <c:if test="${not empty param.login}">
                    <c:param name="login" value="${param.login}" />
                </c:if>
            </c:url>

            <c:url var="enUrl" value="${currentPath}">
                <c:param name="lang" value="en" />
                <c:if test="${not empty param.login}">
                    <c:param name="login" value="${param.login}" />
                </c:if>
            </c:url>

            <a href="${ruUrl}">
                <img src="${pageContext.request.contextPath}/assets/icons/russia_flag.png" alt="Ru" class="logo">
            </a>
            <a href="${enUrl}">
                <img src="${pageContext.request.contextPath}/assets/icons/united_kingdom_flag.png" alt="En" class="logo">
            </a>
        </div>
        <c:if test="${not empty loginUser}">
            <div class="logo-container__hello">
                <fmt:message key="greeting"/>, <span class="logo-container__username">${loginUser.login}.</span>
                <form:form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit" class="logo-container__logout">
                        <fmt:message key="button.logout"/>
                    </button>
                </form:form>
            </div>
        </c:if>
    </div>
    <c:if test="${not empty loginUser}">
        <hr class="line">
        <nav class="menu-container">
            <ul class="menu">
                <li class="menu__item"><a href="${pageContext.request.contextPath}/welcome"><fmt:message key="menu.main"/></a></li>
                <c:if test="${isAdmin}">
                    <li class="menu__item"><a href="${pageContext.request.contextPath}/users"><fmt:message key="menu.users"/></a></li>
                </c:if>
            </ul>
        </nav>
    </c:if>
</header>