<%@tag description="main_logo" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a id="main_logo" title="<fmt:message key="default.logo"/>" href='<c:url value="refresh"/>'>
    <img src="../static/pics/sticker2.jpg">
</a>