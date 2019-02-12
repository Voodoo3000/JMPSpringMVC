<%@tag description="order_panel" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form id="add_to_cart-form" method="get" action="add_to_cart">
    <div class="panel panel-default" id="order_panel">
        <h3 id="panel-title"><fmt:message key="default.order_panel"/></h3>
        <div class="panel-body">
            <div class="row">
                <div class="col-lg-12">
                    <div class="input-group">
                        <span class="input-group-addon" id="waterType"><fmt:message key="default.water"/></span>
                        <select class="selectpicker show-menu-arrow" data-width="auto" name="type" id="type">
                            <c:forEach items="${waterList}" var="water">
                                <option value="${water.type}">
                                    <fmt:message key="default.type_${water.type}"/> - ${water.pricePerLiter}<fmt:message key="default.currency"/> <fmt:message key="default.per_liter"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="input-group" id="bs">
                        <span class="input-group-addon" id="bottleSize"><fmt:message key="default.bottle_value"/></span>
                        <select class="selectpicker show-menu-arrow" data-width="auto" name="size" id="size">
                            <c:forEach items="${bottleSizes}" var="bottle">
                                <option value="${bottle.size}">
                                    <fmt:message key="default.size_${bottle.size}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <c:set var="count" value="1" scope="request"/>
            <div>
                <form class="form-inline">
                    <span class="btn btn-primary" id="minus">-</span>
                    <input type="text" class="form-control" id="input" value="${count}" name="count"
                           readonly="readonly">
                    <span class="btn btn-primary" id="plus">+</span>
                </form>

                <script>
                    jQuery(document).ready(function ($) {
                        $('#minus').click(function () {
                            var $input = $(this).parent().find('input');
                            var val = +$input[0].defaultValue;
                            var count = parseInt($input.val()) - 1;
                            count = count < val ? val : count;
                            $input.val(count);
                            $input.change();
                            return false;
                        });

                        $('#plus').click(function () {
                            var $input = $(this).parent().find('input');
                            var val = +$input[0].defaultValue;
                            $input.val(parseInt($input.val()) + 1);
                            $input.change();
                            return false;
                        });
                    });
                </script>
            </div>

            <c:if test="${user == null}">
                <a href="#authorization_form" type="button" class="btn btn-default" id="btn2" aria-pressed="false" autocomplete="off">
                    <fmt:message key="default.add_to_cart"/>
                </a>
            </c:if>

            <c:if test="${user != null}">
                <input type="submit" class="btn btn-default" id="btn2" aria-pressed="false"
                       autocomplete="off" value="<fmt:message key="default.add_to_cart"/>"/>
            </c:if>

            <a href="#x" class="overlay" id="authorization_form"></a>
            <div class="popup">
                <form id="authorization-form" method="post">
                    <h2><fmt:message key="default.need_authorization"/></h2>
                    <a href="#login_form" type="button" class="btn btn-primary" aria-pressed="false" autocomplete="off">
                        <fmt:message key="default.signin"/>
                    </a>
                    <a href="#join_form" type="button" class="btn btn-primary" id="btn4" aria-pressed="false" autocomplete="off">
                        <fmt:message key="default.signup"/>
                    </a>
                </form>
            </div>

            <c:if test="${user != null}">
                <a type="button" class="btn btn-default" id="btn3" href='<c:url value="open_customer_cart"/>'>
                    <fmt:message key="default.cart"/>
                </a>
            </c:if>

        </div>
    </div>
</form:form>