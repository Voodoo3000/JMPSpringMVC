<%@tag description="greetings_title" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${user.role == 'CLIENT'}">
<p id="greetings_title" align="center">
    <fmt:message key="default.greetings_title"/>
    <a title="<fmt:message key="default.to_cabinet"/>"
       href='<c:url value="open_user_cabinet"/>'>${user.firstName}</a>! <fmt:message
        key="default.user_balance"/> ${user.wallet}<fmt:message key="default.currency"/>.
</p>
</c:if>

<c:if test="${user.role == 'ADMIN'}">
    <p id="greetings_title" align="center">
        <fmt:message key="default.greetings_title"/>${user.firstName}!
    </p>
</c:if>