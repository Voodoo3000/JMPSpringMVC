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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <link rel="stylesheet" type="text/css" media="screen" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.3/css/bootstrap-select.min.css">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.3/js/bootstrap-select.min.js"></script>
    </head>
    <body>

    <div id="header" align="center">

        <t:main_logo/>

        <c:if test="${user.role == 'CLIENT'}">
            <t:logo_title/>
        </c:if>

        <c:if test="${user != null}">
            <t:greetings_title/>
            <t:signout_btn/>
        </c:if>

        <c:if test="${user == null}">
            <t:reg_login_btn_form/>
        </c:if>

        <t:locale_changer/>

    </div>

    <div id="body" class="value_img">

        <c:if test="${errormsg != null}">
            <h5 id="errormsg" align="center">${errormsg}</h5>
        </c:if>

        <c:if test="${user == null || user.role == 'CLIENT'}">
            <t:title_bodylines/>
            <t:order_panel/>
        </c:if>

    </div>

    <c:if test="${user == null || user.role == 'CLIENT'}">
        <t:footer/>
    </c:if>

    </body>
    </html>
</fmt:bundle>