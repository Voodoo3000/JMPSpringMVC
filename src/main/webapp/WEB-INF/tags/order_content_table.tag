<%@tag description="order_content_table" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="user_content_table">
    <table class="table table-striped">
        <thead>
        <tr align="center" class="success">
            <th>#</th>
            <th><fmt:message key="default.water"/></th>
            <th><fmt:message key="default.bottle_value"/></th>
            <th><fmt:message key="default.quantity"/></th>
            <th><fmt:message key="default.output_price"/></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${contentList}" var="content" varStatus="i">
            <form method="post" action="remove_content">
                <tr bgcolor="#e6e6fa">
                    <td>${i.count}</td>
                    <td><fmt:message key="default.type_${content.water.type}"/></td>
                    <td><fmt:message key="default.size_${content.bottleSize.size}"/></td>
                    <td>${content.quantity}</td>
                    <td>${content.quantity*content.bottleSize.size*content.water.pricePerLiter}<fmt:message key="default.currency"/></td>
                    <td>
                        <button type="submit" class="btn btn-danger" name="contentPositionId" value=${content.id}>
                            <fmt:message key="default.remove"/>
                        </button>
                    </td>
                </tr>
            </form>
        </c:forEach>
        <tr class="info">
            <th></th>
            <th>
                <c:if test="${totalAmount == 0}">
                    <h4><fmt:message key="default.empty_cart"/></h4>
                </c:if>
                <c:if test="${user.wallet < totalAmount}">
                    <h4 class="alert-danger"><fmt:message key="default.not_enough_money"/></h4>
                </c:if>
            </th>
            <th></th>
            <th></th>
            <th><fmt:message key="default.total_amount"/>${totalAmount}<fmt:message key="default.currency"/></th>
            <th>
                <c:if test="${totalAmount != 0 && user.wallet >= totalAmount}">
                    <a href="#checkout_form" type="button" class="btn btn-primary" aria-pressed="false">
                        <fmt:message key="default.checkout"/>
                    </a>
                </c:if>
            </th>
        </tr>
    </table>
</div>