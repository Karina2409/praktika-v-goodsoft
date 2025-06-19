<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:choose>
    <c:when test="${empty add}">
        <t:myhtml title="Edit User">
            <t:user-form/>
        </t:myhtml>
    </c:when>
    <c:otherwise>
        <t:myhtml title="Add User">
            <t:user-form/>
        </t:myhtml>
    </c:otherwise>
</c:choose>