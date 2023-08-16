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
		$('#menu-users').addClass("main-menu-selected")
		initClientManagementPage();
	});	
</script>
<div id="content">


	<jsp:include page="error.jsp" />


	<form:form method="post"
		action="<%=WebConstants.CLIENT_MANAGEMENT_FILTER_FORM_CONTROLLER%>"
		commandName="<%=WebConstants.CLIENT_MANAGEMENT_FILTER_FORM%>">

		<fieldset>

			<label class="content_label" for="lastName"><spring:message
					code="page.account.lastname" /></label>
			<form:input class="content_field box_width_admin" type="text" autocomplete="off"
				name="lastName" path="lastName" id="lastName" maxlength="100"
				pattern="<%=Constants.PATTERN_NAME%>"></form:input>

			<div class="hr_dotted"></div>

			<label class="content_label" for="firstName"><spring:message
					code="page.account.firstname" /></label>
			<form:input class="content_field box_width_admin" type="text" autocomplete="off"
				name="firstName" path="firstName" id="firstName" maxlength="100"
				pattern="<%=Constants.PATTERN_NAME%>"></form:input>

			<div class="hr_dotted"></div>

			<label class="content_label" for="login"><spring:message
					code="page.account.login" /></label>
			<form:input class="content_field box_width_admin" type="text" autocomplete="off"
				name="login" path="login" id="login" maxlength="50"></form:input>

			<div class="hr_dotted"></div>

			<label class="content_label" for="entity"><spring:message
					code="page.account.entity" /></label>
			<form:select autocomplete="on" name="entity" path="entity"
				id="entity" class="content_field">

				<form:option value="<%=Constants.EMPTY_FIELD%>">
					<spring:message code="page.all.bis" />
				</form:option>

				<c:forEach items="${clientManagementFilterForm.entitiesLabelList}"
					var="labelEntity">
					<form:option value="${labelEntity}">${labelEntity}</form:option>

				</c:forEach>
			</form:select>

		</fieldset>
			<div class="buttonOnRight">
				<span class="buttonBegin">
	
					<input type="button" id="resetClientFilter"
						value="<spring:message code="page.account.raz.filter"/>"
						class="button">
				</span>
				<span class="buttonBegin">
					<input type="submit"
						value="<spring:message code="page.document.apply"/>" class="button">
				</span>
			</div>
			<div class="hr_dotted"></div>
	</form:form>
	<div class="hr_dotted_bis"></div>
	<c:choose>
		<c:when test="${not empty clientManagementFilterForm.userDtoList}">

			<table style="width: 100%;" id="managementClientTable">
				<thead>
					<tr>
						<th><spring:message code="page.account.login.bis" /></th>
						<th><spring:message code="page.account.lastname.bis" /></th>
						<th><spring:message code="page.account.firstname.bis" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${clientManagementFilterForm.userDtoList}"
						var="user" varStatus="varStatusUser">

						<tr class="td_one clicable"
							onclick="javascript:modifyClientLoad(${user.id})">

							<td title="${user.login}">${user.login}</td>
							<td title="${user.lastName}">${user.lastName}</td>
							<td title="${user.firstName}">${user.firstName}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<p>
				<spring:message code="page.management.user.not.found" />
			</p>
		</c:otherwise>
	</c:choose>
	<BR><BR>
	<input type="hidden" id="tabLengthMenu" value="<spring:message code="datatable.tabLengthMenu"/>" />
	<input type="hidden" id="tabSearch" value="<spring:message code="datatable.tabSearch"/>" />
	<input type="hidden" id="tabZeroRecords" value="<spring:message code="datatable.tabZeroRecords"/>" />
	<input type="hidden" id="tabFirst" value="<spring:message code="datatable.tabFirst"/>" />
	<input type="hidden" id="tabLast" value="<spring:message code="datatable.tabLast"/>" />
	<input type="hidden" id="tabNext" value="<spring:message code="datatable.tabNext"/>" />
	<input type="hidden" id="tabPrevious" value="<spring:message code="datatable.tabPrevious"/>" />
</div>


<jsp:include page="footer.jsp" />