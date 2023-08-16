<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>

<jsp:include page="include.jsp" />
<script src="../js/account.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#menu-users').addClass("main-menu-selected");
		initClientModifyPage()
	});
</script>
<div id="content">


	<jsp:include page="error.jsp" />


	<form:form method="post"
		action="<%=WebConstants.CLIENT_MANAGEMENT_MODIFY_CONTROLLER%>"
		commandName="<%=WebConstants.CLIENT_MANAGEMENT_FILTER_FORM%>">

			<div class="bannerTitle">
					<div class="bnpBanner">
						<spring:message code="page.account.modify.client.title" />
					</div>
			</div>

			<label class="content_label" for="login"><spring:message
					code="page.account.login" /></label>
			<label class="content_field">
				${clientManagementFilterForm.userToModify.login}
			</label>

			<br><br>

			<label class="content_label" for="lastName">
				<spring:message code="page.account.lastname" />
			</label>
			<label class="content_field">
				${clientManagementFilterForm.userToModify.lastName}
			</label>

			<br><br>

			<label class="content_label" for="firstName">
				<spring:message code="page.account.firstname" />
			</label>
			<label class="content_field">
				${clientManagementFilterForm.userToModify.firstName}
			</label>

			<br><br>

			<label class="content_label" for="email">
				<span class="warning"><spring:message code="page.asterisk" /></span>
				<spring:message	code="page.account.email" />
			</label>
			<form:input class="content_field box_width_admin" type="text" autocomplete="off"
				name="email" path="userToModify.email" id="email" size="35"
				pattern="<%=Constants.PATTERN_EMAIL%>"></form:input>

			<br><br>

			<label class="content_label" for="tel">
				<span class="warning"><spring:message code="page.asterisk" /></span>
				<spring:message	code="page.account.tel" />
			</label>
			<form:input class="content_field box_width_admin" type="text" autocomplete="off"
				name="tel" path="userToModify.tel" id="telClient"
				pattern="<%=Constants.PATTERN_TEL%>"></form:input>

			<br><br>

			<c:forEach
				items="${clientManagementFilterForm.userToModify.entities}"
				var="entity" varStatus="entityLoop">
				<label class="content_label" for="emailContact"><spring:message
						code="page.account.emailContact" /></label>
				<form:input class="content_field box_width_admin" type="text" autocomplete="off" size="35"
					path="userToModify.entities[${entityLoop.index}].bankContact"
					pattern="<%=Constants.PATTERN_EMAIL%>"></form:input>
					
				<br><br>
			</c:forEach>

			<label class="content_label" for="isXbasV2">
				<spring:message	code="page.account.isXbasV2" />
			</label>
			<form:checkbox class="content_field" name="xbasV2" path="userToModify.xbasV2" id="isXbasV2"/>

			<br><br>

			<div class="buttonOnRight">
				<span class="buttonBegin">
					<input type="button" value="<spring:message code="page.cancel"/>"
						name="cancel" class="button" id="cancelButtonClient">
				</span>
				<span class="buttonBegin">
					<input type="submit" value="<spring:message code="page.document.apply"/>" class="button">
				</span>
				<span class="buttonBegin">
					<input type="button"
						value="<spring:message code="page.reset.password"/>" name="reset"
						class="button" id="resetButtonClient">
				</span>
				<c:choose>
					<c:when
						test="${clientManagementFilterForm.userToModify.locked}">
						<span class="buttonBegin">
							<input type="button" value="<spring:message code="page.unlock"/>"
								name="cancel" class="button" id="unlockButtonClient">
						</span>
					</c:when>
					<c:otherwise>
						<span class="buttonBegin">
							<input type="button" value="<spring:message code="page.lock"/>"
								name="cancel" class="button" id="lockButtonClient">
						</span>
					</c:otherwise>
				</c:choose>
				
			</div>

		<input type="hidden" id="confirmCanceling"
			value="<spring:message code="page.account.cancelling"/>" />

		<input type="hidden" id="confirmDeleting"
			value="<spring:message code="page.account.confirm.delete.client"/>" />

		<br><br>

	</form:form>

	<input type="hidden" id="patternTel"
			value="<spring:message code="page.patternTel"/>" />
	
</div>
<jsp:include page="footer.jsp" />