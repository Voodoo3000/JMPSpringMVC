<%@tag description="reg_login_btn_form" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<a href="#login_form" type="button" class="btn btn-primary" id="btn" aria-pressed="false" autocomplete="off">
    <fmt:message key="default.signin"/>
</a>
<a href="#join_form" type="button" class="btn btn-primary" id="btn1" aria-pressed="false" autocomplete="off">
    <fmt:message key="default.signup"/>
</a>

<a href="#x" class="overlay" id="login_form"></a>
<div class="popup">
  <form:form method="post" action="sign_in" modelAttribute="userModel">
      <h2><fmt:message key="default.greetings"/></h2>
      <p><fmt:message key="default.user_verification"/></p>
      <div>
          <label for="email"><fmt:message key="default.user_login"/></label>
          <input type="email" id="login" name="loginEmail" required/>
      </div>
      <div>
          <label for="password"><fmt:message key="default.enter_pass"/></label>
          <input type="password" id="password" name="password" required/>
      </div>
      <input type="submit" value=<fmt:message key="default.signin"/>>
  </form:form>
</div>

<a href="#x" class="overlay" id="join_form"></a>
<div class="popup">
    <form:form id="registration-form" method="post" action="sign_up" modelAttribute="userModel">
        <h2><fmt:message key="default.signup_title"/></h2>
        <p><fmt:message key="default.user_details"/></p>
        <div>
            <label for="email"><fmt:message key="default.user_login"/></label>
            <input type="email" id="email" name="loginEmail" required>
        </div>
        <div>
            <label for="pass"><fmt:message key="default.enter_pass"/></label>
            <input type="password" id="pass" name="password" required>
        </div>
        <div>
            <label for="pass"><fmt:message key="default.repeat_pass"/></label>
            <input type="password" id="rePass" name="newRePassword" required>
        </div>
        <div>
            <label for="firstname"><fmt:message key="default.user_firstname"/></label>
            <input type="text" id="firstname" name="firstName" required>
        </div>
        <div>
            <label for="lastname"><fmt:message key="default.user_lastname"/></label>
            <input type="text" id="lastname" name="lastName" required>
        </div>
        <input type="submit" value="<fmt:message key="default.signup_button"/>"/>&nbsp;&nbsp;&nbsp;
        <fmt:message key="default.signup_or"/>&nbsp;&nbsp;&nbsp;
        <a href="#login_form" id="login_pop"><fmt:message key="default.signin"/></a>
    </form:form>
</div>