<%@tag description="checkout_form" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<a href="#x" class="overlay" id="checkout_form"></a>
<div class="popup">
    <form:form id="checkout-form" method="post" action="get_order" modelAttribute="addressModel">
        <h2><fmt:message key="default.ordering"/></h2>
        <div>
            <label><fmt:message key="default.address_city"/></label>
            <input type="text" class="form-control" name="city" value="${address.city}" required>
        </div>
        <div>
            <label><fmt:message key="default.address_street"/></label>
            <input type="text" class="form-control" name="street" value="${address.street}" required>
        </div>
        <div>
            <label><fmt:message key="default.address_house"/></label>
            <input type="text" class="form-control" name="houseNumber" value="${address.houseNumber}" required>
        </div>
        <div>
            <label><fmt:message key="default.address_apartment"/></label>
            <input type="text" class="form-control" name="apartmentNumber" value="${address.apartmentNumber}" required>
        </div>
        <div>
            <label><fmt:message key="default.address_phone"/></label>
            <input type="text" class="form-control" name="phoneNumber" value="${address.phoneNumber}" required>
        </div>
        <input type="submit" class="btn btn-primary" value=<fmt:message key="default.order"/>>
    </form:form>
</div>