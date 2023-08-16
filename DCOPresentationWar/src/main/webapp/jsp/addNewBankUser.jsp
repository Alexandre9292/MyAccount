<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set value="<%=Constants.ROLE_SUPER_ADMIN_ID%>" var="roleSA" />
<c:set value="<%=Constants.ROLE_MGMT_ACCOUNT_ID%>" var="roleMgmtAccnt" />
<c:set value="<%=Constants.ROLE_MGMT_DOCUMENTS_ID%>" var="roleMgmtDoc" />
<c:set value="<%=Constants.ROLE_VIEW_CLIENT_DATA_ID%>"
	var="roleViewCltData" />
<c:set value="<%=Constants.ROLE_VIEW_STATISTICS_ID%>" var="roleViewStat" />
<c:set value="<%=Constants.ROLE_MGMT_PARAMETERS_ID%>"
	var="roleMgmtParam" />

<jsp:include page="include.jsp" />
<script src="../js/account.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#menu-users').addClass("main-menu-selected");
		initBankAddingPage();
		$('#roleSA').on("change", function(){
			userBankUniqueToggleSuperAdm();
		});
	});	
</script>
<div id="content">

	<jsp:include page="error.jsp" />

	<form:form method="post" action="doAddBankUser"
		commandName="manageBankUserForm">

		<fieldset>
			<div class="bannerTitle">
				<div class="bnpBanner">
					<spring:message code="page.bank.user.create.pageTitle"/>
			</div></div>

			<div class="content_field_block">

				<label class="content_label" for="lastName">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message code="page.login.web.message.lastName"/>
				</label>
				<form:input class="content_field box_width_admin" type="text" autocomplete="off"
					name="lastName" path="userDto.lastName" id="lastName"
					maxlength="50" pattern="<%=Constants.PATTERN_NAME%>"></form:input>
				<div class="hr_dotted"></div>

				<label class="content_label" for="firstName">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message code="page.login.web.message.firstName"/>
				</label>
				<form:input class="content_field box_width_admin" type="text" autocomplete="off"
					name="firstName" path="userDto.firstName" id="firstName"
					maxlength="50" pattern="<%=Constants.PATTERN_NAME%>"
					></form:input>

				<div class="hr_dotted"></div>

				<label class="content_label" for="email">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message code="page.login.web.message.email"/>
				</label>
				<form:input class="content_field box_width_admin" type="text" autocomplete="off"
					name="email" path="userDto.email" id="email"
					pattern="<%=Constants.PATTERN_EMAIL%>" size="35"
					maxlength="100" ></form:input>

				<div class="hr_dotted"></div>

				<label class="content_label" for="legalEntity"><spring:message code="page.bank.user.legal.entity"/></label>
				<form:select autocomplete="on" name="legalEntity"
					path="legalEntities" id="legalEntity" class="content_field">

					<form:options items="${bankAccountFilterForm.legalEntityList}"
						itemLabel="label" itemValue="id" />
				</form:select>

				<div class="hr_dotted"></div>

				<label class="content_label"><spring:message code="page.bank.user.roles"/></label>
				<BR>
				<table>
					<c:forEach items="${bankAccountFilterForm.rolesList}" var="role"
						varStatus="varStatus">
						
						<c:if test="${varStatus.index % 2 == 0}">
							<tr>
						</c:if>
							<td>
								<label class="content_label" for="roles">
									<spring:message code="page.account.role.${role.id}" />
								</label>
							</td>
							<td><c:choose>
									<c:when test="${role.id == roleSA}">
										<form:checkbox name="roleSA" id="roleSA" path="roleSA"
											value="${role.id}" class="content_field" />
									</c:when>
									<c:when test="${role.id == roleMgmtAccnt}">
										<form:checkbox name="roleMgmtAccnt" id="roleMgmtAccnt"
											path="roleMgmtAccnt" value="${role.id}" class="content_field" />
									</c:when>
									<c:when test="${role.id == roleMgmtDoc}">
										<form:checkbox name="roleMgmtDoc" id="roleMgmtDoc"
											path="roleMgmtDoc" value="${role.id}" class="content_field" />
									</c:when>
									<c:when test="${role.id == roleViewCltData}">
										<form:checkbox name="roleViewCltData" id="roleViewCltData"
											path="roleViewCltData" value="${role.id}"
											class="content_field" />
									</c:when>
									<c:when test="${role.id == roleViewStat}">
										<form:checkbox name="roleViewStat" id="roleViewStat"
											path="roleViewStat" value="${role.id}" class="content_field" />
									</c:when>
									<c:when test="${role.id == roleMgmtParam}">
										<form:checkbox name="roleMgmtParam" id="roleMgmtParam"
											path="roleMgmtParam" value="${role.id}" class="content_field" />
									</c:when>
								</c:choose></td>
						<c:if test="${(varStatus.index + 1 % 2) == 0}">
							</tr>
						</c:if>
					</c:forEach>
				</table>

			</div>
			<div class="hr_dotted">
			<div style="float:right;">
				<span class="buttonBegin">
					<input type="submit" value="<spring:message code="page.create"/>" class="button">
				</span>
				<span class="buttonBegin">
					<input type="button" value="<spring:message code="page.return"/>" class="button" id="returnButtonAddBankUser">
				</span>
			</div>
			</div>
		</fieldset>

	</form:form>

</div>

<jsp:include page="footer.jsp" />