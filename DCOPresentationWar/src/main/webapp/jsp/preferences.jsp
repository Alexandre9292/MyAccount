<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="page.patternTel" var="patternTel" />
<spring:message code="page.preferences.web.message.delete_data.confirm.yes" var="confirmYes" />
<spring:message code="page.preferences.web.message.delete_data.confirm.no" var="confirmNo" />
<spring:message code="page.preferences.web.message.delete_data.confirm.content" var="confirmContent" />
<spring:message code="page.preferences.web.message.delete_data.confirm.already_deleted" var="confirmAlreadyDeleted" />

<sec:authentication property="principal.profile" var="userProfile" />
<sec:authentication property="principal.deleted" var="userDeleted" />
<c:set value="<%=Constants.PROFILE_CLIENT%>" var="profileClient" />

<jsp:include page="include.jsp" />

<div id="content">
	<jsp:include page="error.jsp" />
	<form:form method="post" action="savePreferences" commandName="preferencesForm">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<fieldset>
			<div class="bannerTitle">
				<div class="bnpBanner">
					<spring:message code="page.preferences.web.message.preferences" />
				</div>
			</div>
			<div class="content_field_block">
				<label class="content_label" for="firstName">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message	code="page.login.web.message.firstName" />
				</label>
				<form:input class="content_field box_width" type="text" autocomplete="off" name="firstName" path="userDto.firstName" id="firstName" maxlength="100"></form:input>
				<div class="hr_dotted"></div>
				<label class="content_label" for="lastName">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message code="page.login.web.message.lastName" />
				</label>
				<form:input class="content_field box_width" type="text" autocomplete="off" name="lastName" path="userDto.lastName" id="lastName" maxlength="100"></form:input>
				<div class="hr_dotted"></div>
				<label class="content_label" for="email">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message code="page.login.web.message.email" />
				</label>
				<form:input class="content_field box_width" type="text" autocomplete="off" size="35" name="email" path="userDto.email" id="email" maxlength="100"></form:input>
				<div class="hr_dotted"></div>
				<label class="content_label" for="tel">
					<spring:message code="page.preferences.web.message.tel" />
				</label>
				<form:input class="content_field box_width" type="text" autocomplete="off" name="tel" path="userDto.tel" id="tel" maxlength="45" ></form:input>
				<div class="hr_dotted"></div>
				<c:choose>
					<c:when test="${userProfile == profileClient}">
						<c:forEach items="${preferencesForm.userDto.entities}" var="entity" varStatus="entityLoop">
							<label class="content_label" for="bankContact">
								<spring:message code="page.preferences.web.message.bankContact" />
							</label>
							<form:input class="content_field box_width" type="text" autocomplete="off" name="bankContact" path="userDto.entities[${entityLoop.index}].bankContact" id="bankContact" maxlength="100"></form:input>
						</c:forEach>
					</c:when>
				</c:choose>
			</div>
		</fieldset>
		<fieldset>
			<div class="bannerTitle">
				<div class="bnpBanner">
					<spring:message code="page.preferences.web.message.regionalParam" />
				</div>
			</div>
			<div class="content_field_block">
				<label class="content_label" for="ihmLanguage">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message code="page.preferences.web.message.ihmLanguage" />
				</label>
				<form:select autocomplete="on" name="ihmLanguage" path="ihmLanguage" id="ihmLanguage" class="content_field">
					<form:option value="-1">
						<spring:message code="page.preferences.web.message.dropdownlistlanguage" />
					</form:option>
					<c:forEach items="${preferencesForm.ihmLanguageList}" var="l">
						<form:option value="${l.id}">${l.locale.getDisplayLanguage(pageContext.response.locale)}</form:option>
					</c:forEach>
				</form:select>
				<div class="hr_dotted"></div>
				<label class="content_label" for="ihmDateFormat">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message code="page.preferences.web.message.ihmDateFormat" />
				</label>
				<form:select autocomplete="on" name="ihmDateFormat" path="ihmDateFormat" id="ihmDateFormat" class="content_field">
					<form:option value="-1">
						<spring:message code="page.preferences.web.message.dropdownlistdateformat" />
					</form:option>
					<form:options items="${preferencesForm.ihmDateFormatList}" itemLabel="labelDisplay" itemValue="id" />
				</form:select>
			</div>
		</fieldset>
		<fieldset>
			<div class="bannerTitle">
				<div class="bnpBanner">
					<spring:message code="page.preferences.web.message.switchPassword" />
				</div>
			</div>
			<div class="content_field_block">
				<label class="content_label" for="oldPassword">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message	code="page.preferences.web.message.oldPassword" />
				</label>
				<form:password class="content_field box_width" autocomplete="off" name="oldPassword" path="password" id="oldPassword" maxlength="50" />
				<div class="hr_dotted"></div>
				<label class="content_label" for="newPassword">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message	code="page.preferences.web.message.newPassword" />
				</label>
				<form:password class="content_field box_width" autocomplete="off"	name="newPassword" path="newPassword" id="newPassword" maxlength="50" />
				<div class="hr_dotted"></div>
				<label class="content_label" for="newPasswordConfirm">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message code="page.preferences.web.message.newPasswordConfirm" />						
				</label>
				<form:password class="content_field box_width" autocomplete="off"	name="newPasswordConfirm" path="newPasswordConfirm" maxlength="50" id="newPasswordConfirm" />
			</div>
		</fieldset>
		<c:if test="${userProfile == profileClient}">			
			<fieldset>			
				<div class="bannerTitle">
					<div class="bnpBanner">
						<spring:message code="page.preferences.web.message.entity" />
					</div>
				</div>
				<div class="content_field_block">
					<label class="content_label">
						<span class="warning"><spring:message code="page.asterisk" /></span>
						<spring:message code="page.preferences.web.message.entity.name" />
					</label>
					<form:input class="content_field box_width" type="text" autocomplete="off" path="userDto.entities[0].label" maxlength="50"></form:input>
					<div class="hr_dotted"></div>
					<label class="content_label"><spring:message code="page.preferences.web.message.entity.taxResidenceCode" /></label>
					<label class="content_field">${preferencesForm.userDto.entities[0].country.getDisplayCountry(pageContext.response.locale)}</label>
				</div>
			</fieldset>
		</c:if>
		<br><br>
		<c:if test="${userProfile == profileClient}">
			<div class="buttonOnLeft">
				<span class="buttonBegin">
					<input type="button" onclick="$('#dialog-confirm').dialog('open');" value="<spring:message code="page.preferences.web.message.delete_data" />" class="button redButton" id="">
				</span>
			</div>
			<div id="dialog-confirm">
			  <c:choose>
			  	<c:when test="${userDeleted}">
			  		<p>${confirmAlreadyDeleted}</p>
			  	</c:when>
			  	<c:otherwise>
			  		<p>${confirmContent}</p>
			  	</c:otherwise>
			  </c:choose>
			</div>
		</c:if>
		<div class="buttonOnRight">
			<span class="buttonBegin">
				<input type="submit" value="<spring:message code="page.preferences.web.message.save_preferences" />" class="button" id="submit_regularpassword">
			</span>
		</div>
		<br><br>
	</form:form>
</div>
<script>
	$(document).ready(function(){
	});
	createDeleteDataPopup(${userDeleted}, "${confirmYes}", "${confirmNo}");
</script>
<jsp:include page="footer.jsp" />