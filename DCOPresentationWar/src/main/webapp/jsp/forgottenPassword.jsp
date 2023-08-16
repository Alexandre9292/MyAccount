<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="include.jsp" />
<div id="content">
	<jsp:include page="error.jsp" />
	<div class="bannerTitle">
		<div class="bnpBanner">
			<spring:message code="page.forgotten.password.title" />
		</div>
	</div>
	<form:form method="post" action="forgottenPassword">
		<div>
			<label for="login" class="content_label">
				<spring:message code="page.login.web.message.login" />
			</label>
			<div class="content_field">
				<input id="login" type="text" name="login" class="content_field" />
			</div>
			<br><br>
			<label for="email" class="content_label">
				<spring:message code="page.login.web.message.email" />
			</label>
			<div class="content_field">
				<input id="email" size="35" maxlength="100" type="text" name="email" class="content_field" />
			</div>
			<br>
			<div class="buttonOnRight">
				<span class="buttonBegin">
					<input id="submit" type="submit" class="button"
						value="<spring:message code="page.forgotten.password.send" />">
				</span>
			</div>
		</div>
	</form:form>
</div>
<jsp:include page="footer.jsp" />