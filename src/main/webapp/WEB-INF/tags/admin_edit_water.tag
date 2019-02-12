<%@tag description="admin_edit_water" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="admin_water_table">
    <table class="table table-striped">
        <div class="msg-error">${user_change_error}</div>
        <thead>
        <tr align="center" class="success">
            <th>#</th>
            <th><fmt:message key="default.water"/></th>
            <th><fmt:message key="default.output_price"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${waterList}" var="water" varStatus="i">
            <form:form method="post" action="change_water" modelAttribute="waterModel">
                <tr bgcolor="white">
                    <td><input id="N" class="form-control" value="${i.count}" readonly></td>
                    <td><input required type="text" class="form-control" name="type"
                               value="${water.type}"/>
                    </td>
                    <td><input id="Price" required type="text" class="form-control" name="pricePerLiter"
                               value="${water.pricePerLiter}">
                    </td>
                    <td><fmt:message key="default.currency"/></td>
                    <td>
                        <button class="btn btn-primary" name="id" value="${water.id}" type="submit">
                            <fmt:message key="default.dosave"/></button>
                    </td>
                </tr>
            </form:form>
        </c:forEach>
        <tfoot>
        <tr align="center" class="info">
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>
                <div align="left">
                    <button class="btn btn-success" name="add-user" type="button" data-toggle="modal"
                            data-target="#modalCreateUser"><fmt:message key="default.add"/></button>
                </div>
            </td>
        </tr>
        </tfoot>
    </table>
</div>

<div class="modal fade" id="modalCreateUser" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                </button>
                <h4 class="modal-title" align="center"><fmt:message key="default.add"/> <fmt:message
                        key="default.water'"/></h4>
            </div>
            <div class="msg-error" id="msg_u">${create_error}</div>
            <div class="modal-body">
                <form:form id="create-user-form" class="createForm" method="post" action="create_water" modelAttribute="waterForm">
                    <div class="input-group">
                        <div class="col-lg-12">
                            <div class="input-group">
                                <span class="input-group-addon"><fmt:message key="default.water_type"/></span>
                                <input type="text" class="form-control" id="firstname_u" name="type"
                                       value="${type}" placeholder="..." required>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="input-group">
                                <span class="input-group-addon"><fmt:message key="default.price_per_liter"/></span>
                                <input type="text" class="form-control" id="lastname_u" name="pricePerLiter"
                                       value="${pricePerLiter}" placeholder="..." required>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <button type="submit" class="btn btn-success"><fmt:message key="default.add"/></button>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                        key="default.cancel"/></button>
            </div>
        </div>
    </div>
</div>