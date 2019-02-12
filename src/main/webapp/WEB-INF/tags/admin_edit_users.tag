<%@tag description="admin_edit_users" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="admin_edit_table">
    <table class="table table-striped">
        <div class="msg-error">${user_change_error}</div>
        <thead>
        <tr align="center" class="success">
            <th>#</th>
            <th><fmt:message key="default.user_login"/></th>
            <th><fmt:message key="default.user_password"/></th>
            <th><fmt:message key="default.user_firstname"/></th>
            <th><fmt:message key="default.user_lastname"/></th>
            <th><fmt:message key="default.user_role"/></th>
            <th><fmt:message key="default.user_status"/></th>
            <th><fmt:message key="default.user_wallet"/>, <fmt:message key="default.currency"/></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${userList}" var="user" varStatus="i">
            <form:form method="post" action="change_user" modelAttribute="userModel">
                <tr bgcolor="white">
                    <td><input id="N" class="form-control" value="${i.count}" readonly></td>
                    <td><input class="form-control" name="loginEmail" readonly="" value="${user.loginEmail}"></td>
                    <td><input required type="password" class="form-control" name="password" value="${user.password}">
                    </td>
                    <td><input required type="text" class="form-control" name="firstName" value="${user.firstName}">
                    </td>
                    <td><input required type="text" class="form-control" name="lastName" value="${user.lastName}"></td>
                    <td>
                        <select class="selectpicker show-menu-arrow" data-width="auto" name="role">
                            <c:forEach items="${roles}" var="role">
                                <option value="${role}" <c:if test="${user.role==role}">selected</c:if>>${role}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select class="selectpicker show-menu-arrow" data-width="auto" name="state">
                            <c:forEach items="${states}" var="state">
                                <option value="${state}"
                                        <c:if test="${user.state==state}">selected</c:if>>${state}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input class="form-control" name="wallet"
                               <c:if test="${user.role!='CLIENT'}">readonly="" </c:if> value="${user.wallet}"></td>
                    <td>
                        <button class="btn btn-primary" name="id" value="${user.id}" type="submit">
                            <fmt:message key="default.dosave"/></button>
                    </td>
                </tr>
            </form:form>
        </c:forEach>
    </table>
</div>

