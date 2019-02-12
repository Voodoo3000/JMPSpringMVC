<%@tag description="user_cabinet_panel" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form id="user-form1" method="post" action="edit_user" modelAttribute="userModel">
    <div class="panel panel-primary" id="cabinet-panel">
        <h3 id="panel-title"><fmt:message key="default.cabinet_panel"/></h3>
        <div class="msg-error">${user_change_error}</div>
        <div class="panel-body">
            <div class="input-group">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="input-group">
                            <span class="input-group-addon"><fmt:message key="default.user_firstname"/></span>
                            <input type="text" class="form-control" name="firstName" value="${user.firstName}" required>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="input-group">
                            <span class="input-group-addon"><fmt:message key="default.user_lastname"/></span>
                            <input type="text" class="form-control" name="lastName" value="${user.lastName}" required>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="input-group">
                            <span class="input-group-addon"><fmt:message key="default.current_pass"/></span>
                            <input type="password" class="form-control" name="password" value=""
                                   required>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="input-group">
                            <span class="input-group-addon"><fmt:message key="default.new_pass"/></span>
                            <input type="password" class="form-control" name="newPassword" value="">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="input-group">
                            <span class="input-group-addon"><fmt:message key="default.repeat_pass"/></span>
                            <input type="password" class="form-control" name="newRePassword" value="">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="input-group">
                            <span class="input-group-addon"><fmt:message key="default.user_login"/></span>
                            <input readonly class="form-control" name="loginEmail" value="${user.loginEmail}">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="input-group">
                            <span class="input-group-addon"><fmt:message key="default.user_balance"/></span>
                            <input readonly class="form-control" name="wallet"
                                   value="${user.wallet}">
                            <span class="input-group-addon"><fmt:message key="default.currency"/></span>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="default.dosave"/></button>
            </div>
        </div>
    </div>
</form:form>