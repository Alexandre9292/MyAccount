<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>

<jsp:include page="include.jsp" />
<script type="text/javascript" src="../js/raphael-min.js"></script>
<script type="text/javascript" src="../js/home.js"></script>

<div id="content">

	<div id="loginField" >
		<form:form method="post" action="login" commandName="loginForm"
			onsubmit="return validateForm(this)">
			<div class="bnpBorder" style="margin-left: 335px; padding-left: 30px;padding-bottom: 30px">
				<img class="login_locker" alt="locker" src="../img/locker.png"
					height="16" width="16" /> <label for="login" class="login_label">
					<spring:message code="page.login.web.message.login" />
				</label>
				<form:input id="login" class="login_field" type="text" name="login"
					path="username"/>
				<label for="password_regularpassword" class="login_label"> <spring:message
						code="page.login.web.message.password" />
				</label>
				<form:input id="password_regularpassword" class="login_field"
					type="password" name="password" path="password" autocomplete="off" />
				<div class="login_field" style="margin-top: -5px;">
					<span class="buttonBegin"> <input
						id="submit_regularpassword" class="button" type="submit"
						value="<spring:message code="page.login.web.message.connection" />" />
					</span>
				</div>
				<br> <br> <a
					href="<%=WebConstants.FORGOTTEN_PASSWORD_LOAD%>" id="fetchPassword"
					style="float: right; margin-top: 5px;"> <spring:message
						code="page.login.web.message.forgetPassword" />
				</a>
				<!-- TEMPORARY -->
				<a href="<%=WebConstants.SIGNUP_LOAD%>" id="newWebsite">
					New Website
				</a>
			</div>
			<input type="hidden"
				value="<spring:message code="page.login.blank.login" />"
				id="emptyLoginFieldMessage" />
			<input type="hidden"
				value="<spring:message code="page.login.blank.password" />"
				id="emptyPasswordFieldMessage" />
		</form:form>
	</div>

	<jsp:include page="error.jsp" />
	<div class="commercialMessage">${loginForm.commercialMessage}</div>
	<div id="createAccount" class="newAccountLoginPage">
		<fieldset>
			<div class="bannerTitle">
				<div class="bnpBanner">
					<spring:message code="page.login.web.new.account" />
				</div>
			</div>
			<div class="loginQuestions">
				<spring:message code="page.login.web.message.newAccount" />
			</div>
			<form:form method="post" action="createNewAccount" commandName="newAccountForm">
				<div class="content_field_block">
					<label class="content_label" for="username">
						<spring:message code="page.login.web.message.login" />
					</label>
					<form:input type="text" autocomplete="off" name="username" path="userName" maxlength="50" id="username" class="content_field" />
					<br>
					<br>
					<label class="content_label" for="mail">
						<spring:message code="page.login.web.message.email" />
					</label>
					<form:input type="text" autocomplete="off" name="mail" size="35" path="emailClient" maxlength="100" pattern="<%=Constants.PATTERN_EMAIL%>" id="mail" class="content_field"/>
					<br>
					<br> 
					<label class="content_label" for="entity">
						<spring:message code="page.login.web.message.entity" />
					</label>
					<form:input type="text" autocomplete="off" name="entity" path="entity" maxlength="50" id="entity" class="content_field" />
					<br>
					<br>
					<label class="content_label" for="entityCountry">
						<spring:message code="page.login.web.message.entity.country" />
					</label>
					<form:select autocomplete="on" name="entityCountryPlace" path="country" id="entityCountry" class="content_field">
						<form:option value="">
							<spring:message code="page.formulaire.web.message.dropdownListCountry" />
						</form:option>
						<c:forEach items="${newAccountForm.countrySort}" var="country">
							<form:option value="${country.label2}">
								${country.label}
							</form:option>
						</c:forEach>
					</form:select>
					<script type="text/javascript">
						$(document).ready(
							function() {
								var temp = $("#entityCountry").val();
								$('#entityCountry option').sort(NASort).appendTo('#entityCountry');
								$("#entityCountry").val($("#entityCountry option:first").val());
								$("#entityCountry").val(temp);
								
								/** Changing inputs sizes when the browser is Chrome **/
								var userAgent = navigator.userAgent.toString().toLowerCase();
								if (userAgent.indexOf('chrome') != -1) {
									$("#login").attr("size", 15);
									$("#password_regularpassword").attr("size", 15);
									$("#username").attr("size", 15);
									$("#mail").attr("size", 28);
									$("#entity").attr("size", 15);
								}
							}
						);
					</script>
				</div>
				<div class="hr_dotted"></div>
				<div class="buttonOnRight">
					<span class="buttonBegin">
						<input type="submit" value="<spring:message code="page.login.web.message.create_account" />" class="button" id="submit_regularpassword" />
					</span>
				</div>
			</form:form>
		</fieldset>
	</div>
<div id="canvas_europe" style="margin-top: 10px;">
	</div>
	<script type="text/javascript">
		var tab = new Array();
		$("#entityCountry option").each(function() {
			var field = $(this);
			tab[field[0].value] = field[0].text;
		});
		loadMap("EN", tab, 403, 399);
	</script>
	<div class="loginQuestion">
		<spring:message code="page.login.web.message.questions" />
	</div>
</div>
<jsp:include page="footer.jsp" />