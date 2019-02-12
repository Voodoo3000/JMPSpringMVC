<%@tag description="admin_edit_orders" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="admin_edit_table">
    <table class="table table-striped">
        <div class="msg-error">${order_change_error}</div>
        <thead>
        <tr align="center" class="success">
            <th>#</th>
            <th><fmt:message key="default.user"/></th>
            <th><fmt:message key="default.order_content"/></th>
            <th><fmt:message key="default.order_date"/></th>
            <th><fmt:message key="default.address"/></th>
            <th><fmt:message key="default.amount"/></th>
            <th><fmt:message key="default.status"/></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${orderList}" var="order" varStatus="i">
            <form:form method="post" action="change_order_status" modelAttribute="modelOrder">
                <tr bgcolor="white">
                    <td>${i.count}</td>
                    <td>${order.customer.lastName} ${order.customer.firstName}</td>
                    <td>
                        <c:forEach items="${order.contentList}" var="content" varStatus="i">
                            <fmt:message key="default.type_${content.water.type}"/>, <fmt:message
                                key="default.size_${content.bottleSize.size}"/>, <fmt:message
                                key="default.quantity'"/>: ${content.quantity}</br>
                        </c:forEach>
                    </td>
                    <td>${order.orderDate}</td>
                    <td><fmt:message key="default.address_city"/> ${order.address.city},</br>
                        <fmt:message key="default.address_street"/> ${order.address.street},</br>
                        <fmt:message key="default.address_house"/> ${order.address.houseNumber},
                        <fmt:message key="default.address_apartment"/> ${order.address.apartmentNumber},</br>
                        <fmt:message key="default.address_phone"/> ${order.address.phoneNumber}
                    </td>
                    <td>${order.amount}</td>
                    <td>
                        <select class="selectpicker" data-width="auto" name="status">
                            <c:forEach items="${statuses}" var="status">
                                <option value="${status}"
                                        <c:if test="${order.status==status}">
                                            selected
                                        </c:if>>${status}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <button class="btn btn-primary" name="id" value="${order.id}" type="submit">
                            <fmt:message key="default.dosave"/></button>
                    </td>
                </tr>
            </form:form>
        </c:forEach>
        <tr class="info">
            <td></td>
            <td>
                <c:if test="${orderList.isEmpty()}">
                    <h4><fmt:message key="default.empty_admin_order"/></h4>
                </c:if>
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</div>