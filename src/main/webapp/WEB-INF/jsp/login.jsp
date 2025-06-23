<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<t:myhtml title="Login">
    <div class="login-page">
        <c:if test="${not empty error}">
            <p class="error"> ${error} </p>
        </c:if>

        <form:form action="/login" method="post" cssClass="form" modelAttribute="loginForm">
            <form:label path="login" cssClass="label">
                <fmt:message key="login"/>
            </form:label>
            <form:input path="login" cssClass="input-field" required="true" />

            <form:label path="password" cssClass="label">
                <fmt:message key="password"/>
            </form:label>
            <form:password path="password" cssClass="input-field" required="true" />

            <button type="submit" class="form__button">
                <fmt:message key="button.login"/>
            </button>
        </form:form>
    </div>

</t:myhtml>