<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>

<!DOCTYPE html>
<html lang="fr">
<head>
	<meta charset="UTF-8">
	<title>Profil page</title>
	<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Open+Sans+Condensed:700' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="../../css/new/jquery-ui.min.css">
	<link rel="stylesheet" href="../../css/jquery-ui.css">
	<link rel="stylesheet" href="../../css/new/main.css">
	<script src="../../js/new/jquery-1.12.4.min.js"></script>
	<script src="../../js/jquery-ui-1.10.3.min.js"></script>
	<script src="../../js/new/jquery-ui.min.js"></script>
	<script src="../../js/new/main.js"></script>
	<script src="../../js/new/profile.js"></script>
</head>
<body class="bnpAccount">
	<main class="bnpAccount-main">
	
		<spring:message code="page.preferences.web.message.delete_data.confirm.yes" var="confirmYes" />
	<spring:message code="page.preferences.web.message.delete_data.confirm.no" var="confirmNo" />
	<spring:message code="page.preferences.web.message.delete_data.confirm.content" var="confirmContent" />
	<spring:message code="page.preferences.web.message.delete_data.confirm.already_deleted" var="confirmAlreadyDeleted" />
	
	<sec:authentication property="principal.profile" var="userProfile" />
	<sec:authentication property="principal.deleted" var="userDeleted" />
	<c:set value="<%=Constants.PROFILE_CLIENT%>" var="profileClient" />

		<jsp:include page="headerMenu.jsp" />
		
		<div class="bnpAccount-companyResume bnpAccount-plr-mainContent">
			<a class="bnpAccount-companyResume-backLink" href="<%=WebConstants.HOME_COMPANY_BACK%>"><img src="../../img/svg/back-arrow.svg" alt=""></a>
			<h1 class="bnpAccount-companyResume-mainTitle"><spring:message code="page.formulaire.web.message.profile.title" /></h1>
		</div>
				
		<div class="bnpAccount-p-mainContent">			
			<!-- Message bloc -->
			<jsp:include page="../error.jsp" />

			<form:form method="post" action="saveProfile" commandName="preferencesForm">
				<div class="bnpAccount-grid-2 bnpAccount-restrictedWidth">
					<p class="bnpAccount-fieldGroup">
						<label class="bnpAccount-labelRequired" for="bnpAccountProfilFirstName"><spring:message code="page.formulaire.web.message.profile.firstname" /><b class="bnpAccount-moreInfo">?</b></label>
						<form:input type="text" class="bnpAccount-field" required="true" path="userDto.firstName" name="bnpAccountProfilFirstName" id="bnpAccountProfilFirstName"></form:input>
					</p>
					<p class="bnpAccount-fieldGroup">
						<label class="bnpAccount-labelRequired" for="bnpAccountProfilLastName"><spring:message code="page.formulaire.web.message.profile.lastname" /><b class="bnpAccount-moreInfo">?</b></label>
						<form:input type="text" class="bnpAccount-field" required="true" path="userDto.lastName" name="bnpAccountProfilLastName" id="bnpAccountProfilLastName"></form:input>
					</p>
					<p class="bnpAccount-fieldGroup">	<!-- bnpAccount-threeFifth -->
						<label class="bnpAccount-labelRequired" for="bnpAccountProfilMail"><spring:message code="page.formulaire.web.message.profile.email" /></label>
						<form:input type="mail" class="bnpAccount-field" required="true" path="userDto.email" name="bnpAccountProfilMail" id="bnpAccountProfilMail"></form:input>
					</p>
				</div>
				<p class="bnpAccount-mt-big"><strong class="bnpAccount-legendLike"><spring:message code="page.formulaire.web.message.profile.regionalSetting" /></strong></p>
				<div class="bnpAccount-grid-2 bnpAccount-restrictedWidth">
					<p class="bnpAccount-fieldGroup">
						<label class="bnpAccount-labelRequired" for="bnpAccountProfilLanguage"><spring:message code="page.formulaire.web.message.profile.regionalSetting.language" /></label>
						<form:select class="bnpAccount-field" required="true" autocomplete="on" name="bnpAccountProfilLanguage" path="ihmLanguage" id="bnpAccountProfilLanguage">
							<form:option value="-1"><spring:message code="page.preferences.web.message.dropdownlistlanguage" /></form:option>
							<c:forEach items="${preferencesForm.ihmLanguageList}" var="l">
								<form:option value="${l.id}">${l.locale.getDisplayLanguage(pageContext.response.locale)}</form:option>
							</c:forEach>
						</form:select>
					</p>
					<p class="bnpAccount-fieldGroup">
						<label class="bnpAccount-labelRequired" for="bnpAccountProfilDate"><spring:message code="page.formulaire.web.message.profile.regionalSetting.dateFormat" /></label>
						<form:select class="bnpAccount-field" required="true" autocomplete="on" name="bnpAccountProfilDate" path="ihmDateFormat" id="bnpAccountProfilDate">
							<form:option value="-1"><spring:message code="page.preferences.web.message.dropdownlistdateformat" /></form:option>
							<form:options items="${preferencesForm.ihmDateFormatList}" itemLabel="labelDisplay" itemValue="id" />
						</form:select>
					</p>
				</div>
				<p class="bnpAccount-mt-big"><strong class="bnpAccount-legendLike"><spring:message code="page.formulaire.web.message.profile.passwordChange" /></strong></p>
				<div class="bnpAccount-grid-2 bnpAccount-restrictedWidth">
					<p class="bnpAccount-fieldGroup bnpAccount-grid-pull">
						<label class="bnpAccount-labelRequired" for="bnpAccountProfilOldPassword"><spring:message code="page.formulaire.web.message.profile.passwordChange.old" /></label>
						<form:password class="bnpAccount-field" path="password" name="bnpAccountProfilOldPassword" id="bnpAccountProfilOldPassword"></form:password>
					</p>
					<p class="bnpAccount-fieldGroup bnpAccount-grid-pull">
						<label class="bnpAccount-labelRequired" for="bnpAccountProfilNewPassword"><spring:message code="page.formulaire.web.message.profile.passwordChange.new" /></label>
						<form:password class="bnpAccount-field" path="newPassword" name="bnpAccountProfilNewPassword" id="bnpAccountProfilNewPassword"></form:password>
					</p>
					<p class="bnpAccount-fieldGroup bnpAccount-grid-pull">
						<label class="bnpAccount-labelRequired" for="bnpAccountProfilConfirmPassword"><spring:message code="page.formulaire.web.message.profile.passwordChange.confirm" /></label>
						<form:password class="bnpAccount-field" path="newPasswordConfirm" name="bnpAccountProfilConfirmPassword" id="bnpAccountProfilConfirmPassword"></form:password>
					</p>
				</div>
				<p class="bnpAccount-fieldGroup bnpAccount-restrictedWidth bnpAccount-mt-big">
					<button type="button" onclick="$('#dialog-confirm').dialog('open');" class="bnpAccount-btn bnpAccount-btn--normalSubmit bnpAccount-btn--danger bnpAccount-pullLeft"><spring:message code="page.formulaire.web.message.profile.delete" /></button>
					<c:if test="${userProfile == profileClient}">
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
							
					<button type="button" class="bnpAccount-btn bnpAccount-btn--normalSubmit bnpAccount-btn--action" onclick="location.href='<%=WebConstants.HOME_COMPANY_BACK%>'"><spring:message code="page.formulaire.web.message.profile.cancel" /></button>
					<button type="button" class="bnpAccount-btn bnpAccount-btn--normalSubmit bnpAccount-btn--action" onclick="submit()"><spring:message code="page.formulaire.web.message.validate" /></button>
				</p>
			</form:form>
		</div>
		
		<script>
			$(document).ready(function(){});
			createDeleteDataPopup(${userDeleted}, "${confirmYes}", "${confirmNo}");
		</script>
	</main>
	<jsp:include page="footer.jsp" />
</body>
</html>