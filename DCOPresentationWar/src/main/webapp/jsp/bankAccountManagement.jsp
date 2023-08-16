<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>

<jsp:include page="include.jsp" />
<script src="../js/account.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#menu-users').addClass("main-menu-selected");
		initBankManagementPage();
		// applied to search filter
		userBankMainPageToggleSuperAdm(true);
		$('#role_1').on("change", function(){
			userBankMainPageToggleSuperAdm();
		});
	});
</script>
<div id="content">

	<c:set value="<%=Constants.ROLE_SUPER_ADMIN%>" var="roleSA" />
	<c:set value="<%=Constants.ROLE_MGMT_ACCOUNT%>" var="roleMgmtAccnt" />
	<c:set value="<%=Constants.ROLE_MGMT_DOCUMENTS%>" var="roleMgmtDoc" />
	<c:set value="<%=Constants.ROLE_VIEW_CLIENT_DATA%>"
		var="roleViewCltData" />
	<c:set value="<%=Constants.ROLE_VIEW_STATISTICS%>" var="roleViewStat" />
	<c:set value="<%=Constants.ROLE_MGMT_PARAMETERS%>" var="roleMgmtParam" />

	<jsp:include page="error.jsp" />

	<form:form method="post" action="bankAccountFilter"
		commandName="bankAccountFilterForm">

		<fieldset>

			<div id="content_part_left">
				<div class="content_field_block">

					<label class="content_label" for="loginUser"><spring:message
							code="page.user" /></label>
					<form:input class="content_field box_width_admin" type="text" autocomplete="off"
						name="loginUser" path="login" id="loginUser" maxlength="50"></form:input>

					<div class="hr_dotted"></div>

					<label class="content_label" for="legalEntity"><spring:message
							code="page.account.legal.entity" /></label>
					<form:select autocomplete="on" name="legalEntity"
						path="legalEntity" id="legalEntity" class="content_field">

						<form:option value="-1">
							<spring:message code="page.all.bis" />
						</form:option>
						<form:options items="${bankAccountFilterForm.legalEntityList}"
							itemLabel="label" itemValue="id" />
					</form:select>

				</div>
			</div>

				<div class="hr_dotted"></div>
				<div class="content_field_block">
					<c:set var="role_" value="role_" />
					<label class="content_label"><spring:message
							code="page.account.role.selection" /></label>
							<div class="hr_dotted"></div>
					<table id="filterRole">
						<c:forEach items="${bankAccountFilterForm.rolesList}" var="role"
							varStatus="varStatus">
							
							<c:if test="${varStatus.index % 2 == 0}">
								<tr>
							</c:if>
							
								<td><label class="content_label" for="${role_}${role.id}">
										<spring:message code="page.account.role.${role.id}" />
								</label></td>
								<td><form:checkbox path="roles" name="roles"
										id="${role_}${role.id}" value="${role.id}"
										class="content_field" /></td>

							<c:if test="${(varStatus.index + 1 % 2) == 0}">
								</tr>
							</c:if>
						
						</c:forEach>
					</table>
				</div>
				<div class="buttonOnRight">
					<span class="buttonBegin">

					<input type="button"
						value="<spring:message code="page.account.bank.filter"/>"
						class="button" onclick="javascript:noFilter();">
				</span>
				<span class="buttonBegin">
					<input type="submit"
						value="<spring:message code="page.document.apply"/>" class="button">
				</span>
			</div>

		</fieldset>

	</form:form>
	<div class="hr_dotted_bis"></div>
	<c:choose>
		<c:when test="${not empty bankAccountManagementForm.usersList}">

			<form:form method="post" action="bankAccountManagement"
				commandName="bankAccountManagementForm">
				<table style="width: 100%;" id="managementRoleTable">
					<thead>
						<tr>
							<th><spring:message code="page.login" /></th>
							<th><spring:message code="page.account.role.1" /></th>
							<th><spring:message code="page.account.role.2" /></th>
							<th><spring:message code="page.account.role.3" /></th>
							<th><spring:message code="page.account.role.4" /></th>
							<th><spring:message code="page.account.role.6" /></th>
							<th><spring:message code="page.account.role.5" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${bankAccountManagementForm.usersList}"
							var="user" varStatus="varStatusUser">
							<tr>
								<td title="${user.login}" class="clicable" onclick="javascript:modifyUser(${user.id})">${user.login}</td>
								<td class="td_center"><form:checkbox id="chkSA-${user.id}"
										path="bean.map[${user.id}].roleSA"
										onchange="javascript:hasChangeColumn(${user.id});" /></td>
								<td class="td_center"><form:checkbox
										id="chkMgmtAccnt-${user.id}"
										path="bean.map[${user.id}].roleMgmtAccnt"
										onchange="javascript:hasChangeColumn(${user.id})" /></td>
								<td class="td_center"><form:checkbox
										id="chkMgmtDoc-${user.id}"
										path="bean.map[${user.id}].roleMgmtDoc"
										onchange="javascript:hasChangeColumn(${user.id})" /></td>
								<td class="td_center"><form:checkbox
										id="chkViewCltData-${user.id}"
										path="bean.map[${user.id}].roleViewCltData"
										onchange="javascript:hasChangeColumn(${user.id})" /></td>
								<td class="td_center"><form:checkbox
										id="chkViewStat-${user.id}"
										path="bean.map[${user.id}].roleViewStat"
										onchange="javascript:hasChangeColumn(${user.id})" /></td>
								<td class="td_center"><form:checkbox
										id="chkMgmtParam-${user.id}"
										path="bean.map[${user.id}].roleMgmtParam"
										onchange="javascript:hasChangeColumn(${user.id})" /></td>
								<form:hidden path="bean.map[${user.id}].hasChangeColumn" value="false"
									id="hasChangeColumn-${user.id}" />
							</tr>
							<script type="text/javascript">
							$(document).ready(function(){
								// applied to userBank list
								userBankListToggleSuperAdm(${user.id}, true);
								$("#chkSA-"+${user.id}).on("change", function(){
									userBankListToggleSuperAdm(${user.id}, false);
								});								
							});
						</script>
						</c:forEach>
					</tbody>
				</table>
				<br><br>
				<div class="buttonOnRight">
					<span class="buttonBegin">

						<input type="button"
							value="<spring:message code="page.account.bank.create"/>"
							class="button" id="addBankUser">
					</span>				
					<span class="buttonBegin">
						<input type="submit"
							value="<spring:message code="page.bank.user.modify.roles.save"/>" class="button">
					</span>
				</div>
				<br>
			</form:form>

		</c:when>
		<c:otherwise>
			<p>
				<spring:message code="page.management.user.not.found" />
			</p>
										<div style="float:right;">
							<input type="button"
					value="<spring:message code="page.account.bank.create"/>"
					class="button" id="addBankUser">
										</div>
						<div class="hr_dotted"></div>
		</c:otherwise>
	</c:choose>
	
	<input type="hidden" id="tabLengthMenu" value="<spring:message code="datatable.tabLengthMenu"/>" />
	<input type="hidden" id="tabSearch" value="<spring:message code="datatable.tabSearch"/>" />
	<input type="hidden" id="tabZeroRecords" value="<spring:message code="datatable.tabZeroRecords"/>" />
	<input type="hidden" id="tabFirst" value="<spring:message code="datatable.tabFirst"/>" />
	<input type="hidden" id="tabLast" value="<spring:message code="datatable.tabLast"/>" />
	<input type="hidden" id="tabNext" value="<spring:message code="datatable.tabNext"/>" />
	<input type="hidden" id="tabPrevious" value="<spring:message code="datatable.tabPrevious"/>" />
	
</div>
<jsp:include page="footer.jsp" />