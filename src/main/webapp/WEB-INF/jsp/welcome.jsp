<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${pageContext.response.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<t:myhtml title="Welcome">
    <h1>
        <fmt:message key="welcome"/>!
    </h1>
</t:myhtml>
