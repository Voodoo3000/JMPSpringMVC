<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="i18n.message">

<html>
<head>
    <title>mountainspring.com</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../static/css/mainPageStyle.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="../static/css/cssmodal.css" rel="stylesheet" type="text/css" media="screen"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type='text/javascript' src='<c:url value="/static/js/bootstrap-select.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/webjars/bootstrap/3.2.0/js/bootstrap.js"/>'></script>
</head>
<body>

<h2 id="success_order"><fmt:message key="default.successful_order"/></h2>

<img id="success_logo" src="<c:url value="../static/pics/succsess_img.jpg"/>"/>

<h2 id="order_delivery"><fmt:message key="default.order_expect"/></h2>

<a type="button" class="btn btn-primary" id="btn5" onclick='location.href="<c:url value="/"/>"'>
    <fmt:message key="default.order_continue"/>
</a>

<t:signout_btn/>

</body>
</html>
</fmt:bundle>