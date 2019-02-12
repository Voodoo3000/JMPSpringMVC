<%@tag description="user_cabinet_table" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="user_order_table">
    <table class="table table-striped">
        <thead>
        <tr align="center" class="success">
            <th>#</th>
            <th><fmt:message key="default.order_date"/></th>
            <th><fmt:message key="default.address"/></th>
            <th><fmt:message key="default.order_content"/></th>
            <th><fmt:message key="default.amount"/></th>
            <th><fmt:message key="default.status"/></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${orderList}" var="order" varStatus="i">
            <form:form method="post" action="cancel_order">
                <tr bgcolor="#e6e6fa">
                    <td>${i.count}</td>
                    <td>${order.orderDate}</td>
                    <td><fmt:message key="default.address_city"/> ${order.address.city},</br>
                        <fmt:message key="default.address_street"/> ${order.address.street},</br>
                        <fmt:message key="default.address_house"/> ${order.address.houseNumber},
                        <fmt:message key="default.address_apartment"/> ${order.address.apartmentNumber},</br>
                        <fmt:message key="default.address_phone"/> ${order.address.phoneNumber}
                    </td>
                    <td>
                        <c:forEach items="${order.contentList}" var="content" varStatus="i">
                            <fmt:message key="default.type_${content.water.type}"/>, <fmt:message
                                key="default.size_${content.bottleSize.size}"/>, <fmt:message
                                key="default.quantity'"/>: ${content.quantity}</br>
                        </c:forEach>
                    </td>
                    <td>${order.amount}<fmt:message key="default.currency"/></td>
                    <td>
                        <c:if test="${order.status == 'CREATING'}">
                            <fmt:message key="default.status_creating"/>
                        </c:if>
                        <c:if test="${order.status == 'PREPARATION'}">
                            <fmt:message key="default.status_preparation"/>
                        </c:if>
                        <c:if test="${order.status == 'DELIVERED'}">
                            <fmt:message key="default.status_delivered"/>
                        </c:if>
                        <c:if test="${order.status == 'CANCELLED'}">
                            <fmt:message key="default.status_cancelled"/>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${order.status == 'PREPARATION'}">
                            <button type="submit" class="btn btn-warning" name="id" value=${order.id}>
                                <fmt:message key="default.cancel"/>
                            </button>
                        </c:if>
                    </td>
                </tr>
            </form:form>
        </c:forEach>
        <tr class="info">
            <td></td>
            <td>
                <c:if test="${orderList.isEmpty()}">
                    <h4><fmt:message key="default.empty_order"/></h4>
                </c:if>
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</div>