<%@tag description="locale_changer" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="locale_changer" class="dropdown">
    <button class="btn btn-default dropdown-toggle" id="flags_btn" data-toggle="dropdown">
        <c:if test="${locale.language=='ru'}"><img src='<c:url value="/static/pics/RU.png"/>'/></c:if>
        <c:if test="${locale.language=='en'}"><img src='<c:url value="/static/pics/EN.png"/>'/></c:if>
        <span class="caret"></span>
    </button>
    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu3">
        <li role="option"><a role="menuitem" tabindex="-1" href="locale?locale=ru"><img
                src='<c:url value="/static/pics/RU.png"/>'/>Русский</a></li>
        <li role="option"><a role="menuitem" tabindex="-1" href="locale?locale=en"><img
                src='<c:url value="/static/pics/EN.png"/>'/>English</a></li>
    </ul>
</div>