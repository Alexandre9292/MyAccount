<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>


<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>MyAccount</title>
<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Open+Sans+Condensed:700' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="../../css/new/main.css">
<link rel="stylesheet" href="../../css/new/jquery-ui.min.css">
<script src="../../js/new/jquery-1.12.4.min.js"></script>
<script src="../../js/new/jquery-ui.min.js"></script>
<script src="../../js/new/main.js"></script>
<script src="../../js/new/accountDetail.js"></script>
</head>

<c:set var="maxDegree" value="${392.6990816987241}"/>
<c:set var="calcDegree" value="${392.6990816987241 - 0.01*accountDetailForm.account.pourcentage*392.6990816987241}"/>

<body class="bnpAccount">
	<button onClick="location.href='newFormDownload'" class="bnpAccount-btn bnpAccount-btn--action bnpAccount-btn--normalSubmit bnpAccount-btn--downloadLateral" type="button"><spring:message code="page.formulaire.web.message.document.download" /></button>
	<main class="bnpAccount-main"> 
	<jsp:include page="headerMenu.jsp" />
	
	<div class="bnpAccount-companyResume bnpAccount-plr-mainContent">
		<a class="bnpAccount-companyResume-backLink" href="<%=WebConstants.HOME_COMPANY_BACK_FROM_ACCOUNT%>"><img src="../../img/svg/back-arrow.svg" alt=""></a>
		<svg width="125px" height="125px" viewBox="0 0 125 125" class="bnpAccount-completeLevelSvg bnpAccount-completeLevel--big" xmlns="http://www.w3.org/2000/svg">
			<circle class="bnpAccount-completeLevel bnpAccount-completeLevel-back" cy="62.5" cx="50%" r="62.5"></circle>
			<!-- Circle ÃƒÂ  modifier -->
			<circle class="bnpAccount-completeLevel bnpAccount-completeLevel--front" cx="62.5" cy="50%" r="62.5" stroke-dashoffset="${calcDegree}" transform="rotate(-90 62.5 62.5)" stroke-dasharray="${maxDegree}"></circle>
			<!-- texte ÃƒÂ  modifier -->
			<text class="bnpAccount-completeLevel-value" text-anchor="middle" x="50%" y="50%" dy=".35em">${accountDetailForm.account.pourcentage}%</text>
		</svg>
		<h1 class="bnpAccount-companyResume-mainTitle">${accountDetailForm.account.countryAccount.locale.getDisplayCountry(pageContext.response.locale)} - ${accountDetailForm.account.reference} - ${accountDetailForm.account.name}</h1>
	</div>
	<jsp:include page="../error.jsp" />
	<ol class="bnpAccount-unorderedList bnpAccount-inscriptionList">
		<!-- 		ACCOUNT 		-->
		<li class="bnpAccount-p-mainContent bnpAccount-inscriptionList--accountRequest bnpAccount-listElement" id="blocPart1">
			<div class="bnpAccount-inscriptionList-content">
				<h2 class="bnpAccount-inscriptionList-mainTitle"><spring:message code="page.formulaire.web.message.section.account" /></h2>
				<button type="button" class="bnpAccount-btn bnpAccount-btnLink bnpAccount-btnLink--default bnpAccount-js-slideTrigger">
					<span class="bnpAccount-txtOpen"><spring:message code="page.formulaire.web.message.open" /></span>
					<span class="bnpAccount-txtClose"><spring:message code="page.formulaire.web.message.close" /></span>
				</button>
				<div class="bnpAccount-js-inscriptionList-slideContent is-open" id="contentPart1">
					<div class="errorblock" id="errorPart1" style="display: none"></div>
					<form:form method="post" action="saveAccountAndContacts" commandName="accountDetailForm" id="part1Form">
						<div class="bnpAccount-grid-1">
							<p class="bnpAccount-fieldGroup">
								<label for=""><spring:message code="page.formulaire.web.message.account.name" /></label>
								<form:input type="text" class="bnpAccount-field" path="account.name" id="accountName"></form:input>
							</p>
						</div>
						<div class="bnpAccount-grid-2">
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.account.type" /></label>
								<form:select autocomplete="on" required="true" path="account.typeAccount.id" id="typeAccount" class="bnpAccount-field">
									<form:option value=""><spring:message code="page.formulaire.web.message.account.select.type" /></form:option>
									<form:options items="${accountDetailForm.typeAccountList}" itemLabel="value" itemValue="id" />
								</form:select>
							</p>
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.account.currency" /></label>
								<form:select autocomplete="on" required="true" path="account.currency.id" id="currency" class="bnpAccount-field">
									<form:option value=""><spring:message code="page.formulaire.web.message.account.select.currency" /></form:option>
									<form:options items="${accountDetailForm.currencyList}" itemLabel="value" itemValue="id" />
								</form:select>
							</p>
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.account.frequency" /></label>
								<form:select autocomplete="on" required="true" path="account.periodicity.id" id="frequency" class="bnpAccount-field">
									<form:option value=""><spring:message code="page.formulaire.web.message.account.select.frequency" /></form:option>
									<form:options items="${accountDetailForm.periodicityList}" itemLabel="value" itemValue="id" />
								</form:select>
							</p>
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for="bnpAccountChannel"><spring:message code="page.formulaire.web.message.account.channel" /></label>
								<form:select autocomplete="on" required="true" path="account.channel" id="bnpAccountFrequency" class="bnpAccount-field">
									<form:option value=""><spring:message code="page.formulaire.web.message.account.select.chanel" /></form:option>
									<form:options items="${accountDetailForm.channelList}" itemLabel="value" itemValue="value" />
									
<%-- 									<form:option value=""><spring:message code="page.formulaire.web.message.account.select.chanel" /></form:option> --%>
<%-- 									<form:option value="paper mail">paper mail</form:option> --%>
<%-- 									<form:option value="e-statement">e-statement</form:option> --%>
<%-- 									<form:option value="e-statement and paper">e-statement and paper</form:option> --%>
								</form:select>
							</p>
						</div>
						<p class="bnpAccount-text--center">
							<button type="button" onclick="part1Submit()" class="bnpAccount-btn bnpAccount-btn--normalSubmit bnpAccount-btn--action"><spring:message code="page.formulaire.web.message.next" /></button>
						</p>
					</form:form>
				</div>
			</div>
		</li>
		
		<!-- 		SIGNING STRATEGY 		-->
		<li class="bnpAccount-p-mainContent bnpAccount-inscriptionList--representative bnpAccount-listElement bnpAccount-inscriptionListItem--slidedUp" id="blocPart2">
			<form:form method="post" action="saveSigningStrategy" commandName="accountDetailForm" id="part2Form">
				<div class="bnpAccount-inscriptionList-content">
					<h2 class="bnpAccount-inscriptionList-mainTitle"><spring:message code="page.formulaire.web.message.section.signingStrategy" /></h2>
					<button type="button" class="bnpAccount-btn bnpAccount-btnLink bnpAccount-btnLink--default bnpAccount-js-slideTrigger">
						<span class="bnpAccount-txtOpen"><spring:message code="page.formulaire.web.message.open" /></span>
						<span class="bnpAccount-txtClose"><spring:message code="page.formulaire.web.message.close" /></span>
					</button>
					<div class="bnpAccount-js-inscriptionList-slideContent" id="contentPart2">
						<div class="errorblock" id="errorPart2" style="display: none"></div>
						<p class="bnpAccount-formIntro"><spring:message code="page.formulaire.web.message.signatory.intro" /></p>
						<p class="bnpAccount-fieldGroup--radio">
							<label for="bnpAccounthaveLegalDocument">
								<form:radiobutton value="Yes" path="account.strategyDocument" id="bnpAccounthaveLegalDocument"></form:radiobutton>
								<span class="bnpAccount-radioCircle bnpAccount-changedUi"></span><spring:message code="page.formulaire.web.message.yes" />
							</label>
							<label for="bnpAccountcantDelegate">
								<form:radiobutton value="No" path="account.strategyDocument" id="bnpAccountcantDelegate"></form:radiobutton>
								<span class="bnpAccount-radioCircle bnpAccount-changedUi"></span><spring:message code="page.formulaire.web.message.no" />
							</label>
						</p>
						<p class="bnpAccount-p bnpAccount-js-inscriptionList-slideContent" id="bnpAccount-legalDocument-description"><spring:message code="page.formulaire.web.message.signatory.document.text" /></p>
					</div>
				</div>
						
				<div class="bnpAccount-inscriptionList-content bnpAccount-signatories" id="signatoryForm">
					<p class="bnpAccount-p" id="bnpAccount-legalDocument-intro"><spring:message code="page.formulaire.web.message.signatory.document.intro" /></p>
					
					
					<!-- SIGNATORY -->
					<div class="bnpAccount-signatoryBloc bnpAccount-js-inscriptionList-slideContent bnpAccount-mt-big" id="contentPart2_signatories">
						<p><strong class="bnpAccount-legendLike"><spring:message code="page.formulaire.web.message.section.signatory" /></strong></p>
						
						<!--  RESUME -->
						<div class="" id="resumeSignatories" style="display:none"> <!-- bnpAccount-signatoriesResume bnpAccount-signatories bnpAccount-js-inscriptionList-slideContent -->
							<p class="bnpAccount-p" id="bnpAccount-legalDocument-description"><spring:message code="page.formulaire.web.message.signatory.document.text" /></p>
							<div class="bnpAccount-infoRequest-signatories-container">
								<form:hidden path="nbSignatories" id="nbSignatories"/>
								<form:hidden path="nbSignatories2" id="nbSignatories2"/>
								<form:hidden path="nbCollegesBySignatory" id="nbCollegesBySignatory"/>
								<c:forEach items="${accountDetailForm.signatoriesToResume}" var="signatory" varStatus="count">
									<input type="hidden" name="signatoriesToResume[${count.index}].name" value="${signatory.name}" id="signatoryToDelete${count.index}">
									<div class="bnpAccount-signatoriesResume-content bnpAccount-infoRequest-container" id="listSignatoryResume${count.index}">
										<p>
											<svg class="bnpAccount-svg" width="23" height="22" viewBox="0 0 83 84.92">
												<path d="M82.81,84.92H0.19l-0.08-2C0,79.07-.37,66.16,1.92,62.57S9.65,57,16,54.53c1.64-.63,3.34-1.29,5-2,4.76-2,7.26-3.17,8.47-3.77l0.05-5.47c-1-1.17-2.74-3.71-3.6-8.38A4.56,4.56,0,0,1,25,34.41a8.54,8.54,0,0,1-3.18-5.69c0-.21-0.31-5.15,2-7.17l0.28-.22a38.23,38.23,0,0,1-.42-7C24.4,7.28,31.24,0,41.5,0S58.6,7.28,59.28,14.32a38.05,38.05,0,0,1-.42,7l0.28,0.22c2.32,2,2,7,2,7.17A8.47,8.47,0,0,1,58,34.39a4.49,4.49,0,0,1-1,.53c-0.86,4.67-2.56,7.21-3.59,8.38l0.06,5.48c1.21,0.6,3.71,1.77,8.47,3.77,1.68,0.7,3.38,1.35,5,2,6.38,2.46,11.89,4.59,14.1,8.05s2,16.5,1.81,20.37ZM4.15,80.81H78.85c0.16-6.88-.23-14.46-1.23-16h0C76.13,62.46,71,60.47,65.5,58.36c-1.67-.65-3.4-1.31-5.12-2a94.9,94.9,0,0,1-10-4.56l-1-.59-0.1-9.72,0.81-.62h0s2.32-2,3.08-8l0.31-2.38,2.3,0.68a6.47,6.47,0,0,0,1.25-2.63,9.39,9.39,0,0,0-.47-3.59l-2.38.5,0.37-2.86a48.6,48.6,0,0,0,.62-7.82C54.7,9.5,49.44,4.1,41.5,4.1S28.31,9.5,27.8,14.71a49.46,49.46,0,0,0,.62,7.82l0.36,2.86-2.38-.5a9.31,9.31,0,0,0-.47,3.56,4.34,4.34,0,0,0,1.54,2.65l2.31-.67,0,2.39c0.77,6,3.09,8,3.12,8l0.74,0.62,0,1L33.6,51.18l-1,.59a95.87,95.87,0,0,1-10,4.55c-1.73.72-3.46,1.38-5.12,2C12,60.47,6.87,62.46,5.38,64.79,4.38,66.35,4,73.94,4.15,80.81Z"/>
											</svg>
										</p>
										<div class="bnpAccount-resumeDataList-container">
											<dl class="bnpAccount-resumeDataList bnpAccount-resumeDataList--info">
												<dt><spring:message code="page.formulaire.web.message.college.signatory.name" />
												</dt><dd>
													${signatory.name} ${signatory.firstname}
												</dd>
											</dl>
											<p class="bnpAccount-resumeDataList-actionBtns">
<!-- 												<button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn" id="editButton"> -->
<!-- 													<img src="../img/svg/create.svg" alt=""> -->
<!-- 												</button> -->
												<button type="button" onClick="deleteSignatory(${count.index})" class="bnpAccount-btn bnpAccountresumeDataList-action-btn deleteButton">
													<img src="../img/svg/delete.svg" alt="">
												</button>
											</p>
										</div>	
									</div>
								</c:forEach>
								<div class="bnpAccount-collegeResume-before" id="collegesBloc">
								</div>
								<div class="bnpAccount-collegeResume">
									<form:hidden path="nbColleges" id="nbColleges"/>
									<form:hidden path="nbColleges2" id="nbColleges2"/>
								</div>
								<c:forEach items="${accountDetailForm.colleges}" var="college" varStatus="countCollege">	
									<div id="collegeResume-group${countCollege.index}" class="bnpAccount-collegeResume">
										<c:forEach items="${college.signatoriesList}" var="signatory" varStatus="count">					
											<c:forEach items="${accountDetailForm.signatories}" var="sign" varStatus="countIndex">	
												<c:if test="${signatory.id == sign.id}">
													<c:set var="countSignatoryGroup" value="${countIndex.index}"/>
												</c:if>
											</c:forEach>
											<input type="hidden" name="colleges[${countCollege.index}].signatoriesList[${count.index}].name" value="${signatory.name}" id="signatoryGroupToDelete${countSignatoryGroup}">
											<div class="bnpAccount-signatoriesResume-content listSignatoryGroupResume${countSignatoryGroup}">
												<p class="bnpAccount-actionBtns-container">
													<svg class="bnpAccount-svgGroup" width="29" height="22" viewBox="0 0 108.94 84.92">
														<polygon points="55.76 30.87 55.76 30.43 55 30.65 55.76 30.87"/>
														<path d="M59.62,41.44l-0.74-.62a7.45,7.45,0,0,1-1.72-2.66A15.81,15.81,0,0,1,55,42.59c0.2,0.27.38,0.51,0.55,0.7l0,5.47L54.93,49c0.94,0.45,2.42,1.14,4.65,2.11l0-8.68v-1Z"/>
														<path d="M107,62.6c-2.21-3.46-7.72-5.59-14.1-8-1.62-.65-3.32-1.3-5-2-4.76-2-7.26-3.17-8.47-3.77L79.39,43.3c1-1.17,2.73-3.71,3.59-8.38a4.49,4.49,0,0,0,1-.53,8.47,8.47,0,0,0,3.14-5.68c0-.17.32-5.17-2-7.17l-0.28-.22a38.05,38.05,0,0,0,.42-7C84.58,7.28,77.74,0,67.48,0A18.71,18.71,0,0,0,55,4.53a16.79,16.79,0,0,1,2.71,3.06A14.71,14.71,0,0,1,67.48,4.1c7.94,0,13.2,5.4,13.67,10.67a48.6,48.6,0,0,1-.62,7.82l-0.37,2.86,2.38-.5A9.39,9.39,0,0,1,83,28.54a6.47,6.47,0,0,1-1.25,2.63l-2.3-.68-0.31,2.38c-0.76,6-3.08,8-3.08,8l-0.81.62,0.1,9.72,1,0.59a94.9,94.9,0,0,0,10,4.56c1.72,0.69,3.45,1.35,5.12,2,5.5,2.11,10.63,4.1,12.12,6.45,1,1.54,1.39,9.12,1.23,16H83.9c0,0.86,0,1.61-.07,2.16l0,2h25l0-2C109,79.1,109.23,66.06,107,62.6Z"/><path d="M82.79,84.92H0.17l-0.08-2C0,79.07-.39,66.16,1.9,62.57S9.63,57,16,54.53c1.64-.63,3.34-1.29,5-2,4.76-2,7.26-3.17,8.47-3.77l0-5.47a17.44,17.44,0,0,1-3.6-8.38,4.56,4.56,0,0,1-.92-0.5,8.54,8.54,0,0,1-3.18-5.69c0-.21-0.31-5.15,2-7.17l0.28-.22a38.23,38.23,0,0,1-.42-7C24.38,7.28,31.22,0,41.48,0s17.1,7.28,17.78,14.32a38,38,0,0,1-.42,7l0.28,0.22c2.32,2,2,7,2,7.17A8.47,8.47,0,0,1,58,34.39a4.49,4.49,0,0,1-1,.53c-0.86,4.67-2.56,7.21-3.59,8.38l0.06,5.48c1.21,0.6,3.71,1.77,8.47,3.77,1.68,0.7,3.38,1.35,5,2C73.3,57,78.81,59.14,81,62.6S83,79.1,82.83,83ZM4.13,80.81h74.7c0.16-6.88-.23-14.46-1.23-16h0c-1.49-2.35-6.62-4.34-12.12-6.45-1.67-.65-3.4-1.31-5.12-2a94.9,94.9,0,0,1-10-4.56l-1-.59-0.1-9.72,0.81-.62h0s2.32-2,3.08-8l0.31-2.38,2.3,0.68A6.47,6.47,0,0,0,57,28.54a9.39,9.39,0,0,0-.47-3.59l-2.38.5,0.37-2.86a48.6,48.6,0,0,0,.62-7.82C54.68,9.5,49.42,4.1,41.48,4.1S28.29,9.5,27.78,14.71a49.46,49.46,0,0,0,.62,7.82l0.36,2.86-2.38-.5a9.31,9.31,0,0,0-.47,3.56,4.34,4.34,0,0,0,1.54,2.65l2.31-.67v2.39c0.77,6,3.09,8,3.12,8l0.74,0.62v1l0,8.74-1,.59a95.87,95.87,0,0,1-10,4.55c-1.73.72-3.46,1.38-5.12,2C12,60.47,6.85,62.46,5.36,64.79,4.36,66.35,4,73.94,4.13,80.81Z"/>
													</svg><br>
													${college.name}
												</p>
	<%-- 										<form:hidden path="college.nbSignatory" id="nbSignatoryByColleges"/> --%>
	
												<div class="bnpAccount-collegeResume-Signatories">
													<div class="bnpAccount-signatoriesResume-content bnpAccount-infoRequest-container">
														<div class="bnpAccount-resumeDataList-container">
															<dl class="bnpAccount-resumeDataList bnpAccount-resumeDataList--info">
																<dt><spring:message code="page.formulaire.web.message.college.signatory.name" /></dt>
																<dd>${signatory.name} ${signatory.firstname}</dd>
															</dl>
														<p class="bnpAccount-resumeDataList-actionBtns">
		<!-- 													<button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn" id="editButton"> -->
		<!-- 														<img src="../img/svg/create.svg" alt=""> -->
		<!-- 													</button> -->
															<button type="button" onClick="deleteSignatoryGroup(${countSignatoryGroup})"  class="bnpAccount-btn bnpAccountresumeDataList-action-btn deleteButton">
																<img src="../img/svg/delete.svg" alt="">
															</button>
														</p>
														</div>
													</div>
												</div>
											</div>
										</c:forEach> 
									</div>
								</c:forEach>
<!-- 								<div class="bnpAccount-afterSignatoriesGroup"></div> -->
								<div class="bnpAccount-classbeforeGroup"></div>
							</div>
						</div>
					
						<!-- FORMULAIRE -->
						<div class="bnpAccount-unorderedList--Signatories">
							<div class="errorblock" id="errorPart2-Signatory" style="display: none"></div>
<%-- 							<p class="bnpAccount-formIntro"><spring:message code="page.formulaire.web.message.signatory.warning" /></p> --%>
							<div class="bnpAccount-grid-2">
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.signatory.firstname" /><b class="bnpAccount-moreInfo">?</b></label>
								<form:input type="text" required="true" class="bnpAccount-field" path="newSignatory.firstname" name="" id="firstname"></form:input>
							</p>
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.signatory.name" /><b class="bnpAccount-moreInfo">?</b></label>
								<form:input type="text" required="true" class="bnpAccount-field" path="newSignatory.name" name="" id="name"></form:input>
							</p>
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.signatory.role" /></label>
								<form:input type="role" required="true" class="bnpAccount-field" path="newSignatory.role" id="bnpAccountSignatoryRole"></form:input>
							</p>
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for="bnpAccountSignatoryCitizenships"><spring:message code="page.formulaire.web.message.signatory.citizenships" /><b class="bnpAccount-moreInfo">?</b></label>
								<span class="bnpAccount-birthDateContainer"> 
									<form:select autocomplete="on" required="true" path="newSignatory.citizenship" id="bnpAccountSignatoryCitizenships" class="bnpAccount-field">
										<form:option value=""> <spring:message code="page.formulaire.web.message.signatory.select.citizenships" /></form:option>
										<c:forEach items="${accountDetailForm.countryList}" var="locale">
											<form:option value="${locale.country}">${locale.getDisplayCountry(pageContext.response.locale)}</form:option>
										</c:forEach>
									</form:select>
								</span>
							</p>
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for="bnpAccountSignatoryBirthDay"><spring:message code="page.formulaire.web.message.signatory.birthDate" /></label>
								<span class="bnpAccount-birthDateContainer"> 
									<form:input type="text" onKeyup="autotab(this, 'bnpAccountSignatoryBirthMonth')" maxlength="2" class="bnpAccount-field" path="newSignatory.birthDay" id="bnpAccountSignatoryBirthDay"></form:input>
									<form:input type="text" onKeyup="autotab(this, 'bnpAccountSignatoryBirthYear')" maxlength="2" class="bnpAccount-field" path="newSignatory.birthMonth" id="bnpAccountSignatoryBirthMonth"></form:input>
									<form:input type="text" required="true" maxlength="4" class="bnpAccount-field" path="newSignatory.birthYear" id="bnpAccountSignatoryBirthYear"></form:input>
								</span>
							</p>
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for="bnpAccountSignatoryBirthPlace"><spring:message code="page.formulaire.web.message.signatory.birthPlace" /></label>
								<form:input type="text" required="true" class="bnpAccount-field" path="newSignatory.birthPlace" id="bnpAccountSignatoryBirthPlace"></form:input>
							</p>
						</div>
<!-- 						<div class="bnpAccount-grid-2"> -->
<!-- 							<p class="bnpAccount-fieldGroup"> -->
<%-- 								<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.signatory.email" /></label> --%>
<%-- 								<form:input type="mail" required="true" class="bnpAccount-field" path="newSignatory.email" name="bnpAccountSignatoryName" id="bnpAccountSignatoryMail"></form:input> --%>
<!-- 							</p> -->
<!-- 							<p class="bnpAccount-fieldGroup"> -->
<%-- 								<label for="bnpAccountSignatoryPhone"><spring:message code="page.formulaire.web.message.signatory.phone" /></label> --%>
<%-- 								<form:input type="tel" placeholder="+" class="bnpAccount-field" path="newSignatory.tel" id="bnpAccountSignatoryPhone"></form:input> --%>
<!-- 							</p>	 -->
<!-- 							<p class="bnpAccount-fieldGroup"> -->
<%-- 								<label for="bnpAccountSignatoryFax"><spring:message code="page.formulaire.web.message.signatory.fax" /></label> --%>
<%-- 								<form:input type="tel" placeholder="+" class="bnpAccount-field" path="newSignatory.fax" id="bnpAccountSignatoryFax"></form:input> --%>
<!-- 							</p>		 -->
<!-- 						</div> -->
						<div class="bnpAccount-grid-2">
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for="bnpAccountSignatoryStreet"><spring:message code="page.formulaire.web.message.signatory.address" /></label>
								<form:input type="text" required="true" class="bnpAccount-field" path="newSignatory.homeAddress.fieldOne" id="bnpAccountSignatoryStreet"></form:input>
							</p>
							
							<p class="bnpAccount-fieldGroup">
								<label for=""><spring:message code="page.formulaire.web.message.signatory.addressComplement" /></label>
								<form:input type="text" class="bnpAccount-field" path="newSignatory.homeAddress.fieldFive" name="" id="fieldFive"></form:input>
							</p>
<!-- 							<p class="bnpAccount-fieldGroup"> -->
<%-- 								<label for=""><spring:message code="page.formulaire.web.message.signatory.addressComplement" /></label> --%>
<%-- 								<form:input type="text" class="bnpAccount-field" path="newSignatory.homeAddress.fieldSix" name="" id="fieldSix"></form:input> --%>
<!-- 							</p> -->
<!-- 							<p class="bnpAccount-fieldGroup"> -->
<%-- 								<label for=""><spring:message code="page.formulaire.web.message.signatory.addressComplement" /></label> --%>
<%-- 								<form:input type="text" class="bnpAccount-field" path="newSignatory.homeAddress.fieldSeven" name="" id="fieldSeven"></form:input> --%>
<!-- 							</p>		 -->
							
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for="bnpAccountSignatoryCity"><spring:message code="page.formulaire.web.message.signatory.city" /></label>
								<form:input type="text" required="true" class="bnpAccount-field" path="newSignatory.homeAddress.fieldTwo" id="bnpAccountSignatoryCity"></form:input>
							</p>
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for="bnpAccountSignatoryZip"><spring:message code="page.formulaire.web.message.signatory.zipCode" /></label>
								<form:input type="text" maxlength="5" required="true" class="bnpAccount-field" path="newSignatory.homeAddress.fieldThree" id="bnpAccountSignatoryZip"></form:input>
							</p>
								
							<p class="bnpAccount-fieldGroup">
								<label class="bnpAccount-labelRequired" for="homeAddressCountry"><spring:message code="page.formulaire.web.message.signatory.country" /></label>
								<form:select autocomplete="on" required="true" path="newSignatory.homeAddress.fieldFour" id="homeAddressCountry" class="bnpAccount-field">
									<form:option value=""> <spring:message code="page.formulaire.web.message.signatory.select.country" /></form:option>
									<c:forEach items="${accountDetailForm.countryList}" var="locale">
										<form:option value="${locale.country}">${locale.getDisplayCountry(pageContext.response.locale)}</form:option>
									</c:forEach>
								</form:select>
							</p>
											
						</div>
							<div class="bnpAccount-infoRequestParent">
								<div id="assignGroup" class="bnpAccount-infoRequest bnpAccount-infoRequest--solo">
									<button type="button" onclick="groupButton()" class="bnpAccount-infoRequest-btnClose bnpAccount-btn">x</button>
									<p class="bnpAccount-infoRequest--title">
										<spring:message code="page.formulaire.web.message.signatory.assign.group" /> 
									</p>
									<div class="bnpAccount-resumeGroupListGroup">
										<div class="errorblock" id="errorGroupPart" style="display: none"></div>
										<c:forEach items="${accountDetailForm.account.collegeList}" var="college" varStatus="count">
											<input type="hidden" name="account.collegeList[${count.index}].name" value="${college.name}" id="groupToDelete${count.index}">
											<p class="bnpAccount-fieldGroup bnpAccount-actionBtns-container" id="listGroupResume${count.index}">
												<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountSignatoryGroupe0">
												<form:checkbox name="bnpAccountSignatoryGroupe${nbColleges}" value="${count.index};${college.name}" path="newCollege.name" id="bnpAccountSignatoryGroupe${nbColleges}"></form:checkbox>
												<img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace">${college.name}</label>
												<span class="bnpAccount-SignatoryGroup-actionBtns">
<!-- 													<button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn" id="editButton"> -->
<!-- 														<img src="../img/svg/create.svg" alt=""> -->
<!-- 													</button> -->
													<button type="button" onClick="deleteGroup(${count.index})" class="bnpAccount-btn bnpAccountresumeDataList-action-btn" id="deleteButton">
														<img src="../img/svg/delete.svg" alt="">
													</button>
												</span>
											</p>
										</c:forEach>
									</div>
									<p class="bnpAccount-fieldGroup bnpAccount-fieldGroup-Group">
										<label for="" class="bnpAccount-infoRequest-label"><spring:message code="page.formulaire.web.message.signatory.group.name" /><br><form:input type="text" class="bnpAccount-field" path="newCollege.name" name="" id="collegeName"></form:input> 
										</label>
									</p>
									<p class="bnpAccount-fieldGroup">
										<button type="button" onclick="groupeSubmit()" class="bnpAccount-btn bnpAccount-btn--normal bnpAccount-btn--action bnpAccount-infoRequest-btn"><spring:message code="page.formulaire.web.message.validate" /> </button>
									</p>
								</div>
								<button type="button" onclick="groupButton()" class="bnpAccount-btnReverse bnpAccount-btn--small bnpAccount-btnReverse--important bnpAccount-btn--add"> 
									<svg class="bnpAccount-svg bnpAccount-svg--userGroupSmall" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 108.94 84.92"> 
										<polygon points="55.76 30.87 55.76 30.43 55 30.65 55.76 30.87"/> 
										<path d="M59.62,41.44l-0.74-.62a7.45,7.45,0,0,1-1.72-2.66A15.81,15.81,0,0,1,55,42.59c0.2,0.27.38,0.51,0.55,0.7l0,5.47L54.93,49c0.94,0.45,2.42,1.14,4.65,2.11l0-8.68v-1Z"/> 
											<path d="M107,62.6c-2.21-3.46-7.72-5.59-14.1-8-1.62-.65-3.32-1.3-5-2-4.76-2-7.26-3.17-8.47-3.77L79.39,43.3c1-1.17,2.73-3.71,3.59-8.38a4.49,4.49,0,0,0,1-.53,8.47,8.47,0,0,0,3.14-5.68c0-.17.32-5.17-2-7.17l-0.28-.22a38.05,38.05,0,0,0,.42-7C84.58,7.28,77.74,0,67.48,0A18.71,18.71,0,0,0,55,4.53a16.79,16.79,0,0,1,2.71,3.06A14.71,14.71,0,0,1,67.48,4.1c7.94,0,13.2,5.4,13.67,10.67a48.6,48.6,0,0,1-.62,7.82l-0.37,2.86,2.38-.5A9.39,9.39,0,0,1,83,28.54a6.47,6.47,0,0,1-1.25,2.63l-2.3-.68-0.31,2.38c-0.76,6-3.08,8-3.08,8l-0.81.62,0.1,9.72,1,0.59a94.9,94.9,0,0,0,10,4.56c1.72,0.69,3.45,1.35,5.12,2,5.5,2.11,10.63,4.1,12.12,6.45,1,1.54,1.39,9.12,1.23,16H83.9c0,0.86,0,1.61-.07,2.16l0,2h25l0-2C109,79.1,109.23,66.06,107,62.6Z"/> 
											<path d="M82.79,84.92H0.17l-0.08-2C0,79.07-.39,66.16,1.9,62.57S9.63,57,16,54.53c1.64-.63,3.34-1.29,5-2,4.76-2,7.26-3.17,8.47-3.77l0-5.47a17.44,17.44,0,0,1-3.6-8.38,4.56,4.56,0,0,1-.92-0.5,8.54,8.54,0,0,1-3.18-5.69c0-.21-0.31-5.15,2-7.17l0.28-.22a38.23,38.23,0,0,1-.42-7C24.38,7.28,31.22,0,41.48,0s17.1,7.28,17.78,14.32a38,38,0,0,1-.42,7l0.28,0.22c2.32,2,2,7,2,7.17A8.47,8.47,0,0,1,58,34.39a4.49,4.49,0,0,1-1,.53c-0.86,4.67-2.56,7.21-3.59,8.38l0.06,5.48c1.21,0.6,3.71,1.77,8.47,3.77,1.68,0.7,3.38,1.35,5,2C73.3,57,78.81,59.14,81,62.6S83,79.1,82.83,83ZM4.13,80.81h74.7c0.16-6.88-.23-14.46-1.23-16h0c-1.49-2.35-6.62-4.34-12.12-6.45-1.67-.65-3.4-1.31-5.12-2a94.9,94.9,0,0,1-10-4.56l-1-.59-0.1-9.72,0.81-.62h0s2.32-2,3.08-8l0.31-2.38,2.3,0.68A6.47,6.47,0,0,0,57,28.54a9.39,9.39,0,0,0-.47-3.59l-2.38.5,0.37-2.86a48.6,48.6,0,0,0,.62-7.82C54.68,9.5,49.42,4.1,41.48,4.1S28.29,9.5,27.78,14.71a49.46,49.46,0,0,0,.62,7.82l0.36,2.86-2.38-.5a9.31,9.31,0,0,0-.47,3.56,4.34,4.34,0,0,0,1.54,2.65l2.31-.67v2.39c0.77,6,3.09,8,3.12,8l0.74,0.62v1l0,8.74-1,.59a95.87,95.87,0,0,1-10,4.55c-1.73.72-3.46,1.38-5.12,2C12,60.47,6.85,62.46,5.36,64.79,4.36,66.35,4,73.94,4.13,80.81Z"/> 
									</svg><spring:message code="page.formulaire.web.message.signatory.assign.group" />  
								</button>
							</div>
						</div>
						<p class="bnpAccount-mt-big">
							<button type="button" onclick="addSignatoriesButton()" class="bnpAccount-btnReverse bnpAccount-btn--small bnpAccount-btnReverse--action bnpAccount-btn--add bnpAccount-rulesList-btnReverse">
							<svg class="bnpAccount-svg bnpAccount-svg--userSmall" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 83 84.92">
								<path d="M82.81,84.92H0.19l-0.08-2C0,79.07-.37,66.16,1.92,62.57S9.65,57,16,54.53c1.64-.63,3.34-1.29,5-2,4.76-2,7.26-3.17,8.47-3.77l0.05-5.47c-1-1.17-2.74-3.71-3.6-8.38A4.56,4.56,0,0,1,25,34.41a8.54,8.54,0,0,1-3.18-5.69c0-.21-0.31-5.15,2-7.17l0.28-.22a38.23,38.23,0,0,1-.42-7C24.4,7.28,31.24,0,41.5,0S58.6,7.28,59.28,14.32a38.05,38.05,0,0,1-.42,7l0.28,0.22c2.32,2,2,7,2,7.17A8.47,8.47,0,0,1,58,34.39a4.49,4.49,0,0,1-1,.53c-0.86,4.67-2.56,7.21-3.59,8.38l0.06,5.48c1.21,0.6,3.71,1.77,8.47,3.77,1.68,0.7,3.38,1.35,5,2,6.38,2.46,11.89,4.59,14.1,8.05s2,16.5,1.81,20.37ZM4.15,80.81H78.85c0.16-6.88-.23-14.46-1.23-16h0C76.13,62.46,71,60.47,65.5,58.36c-1.67-.65-3.4-1.31-5.12-2a94.9,94.9,0,0,1-10-4.56l-1-.59-0.1-9.72,0.81-.62h0s2.32-2,3.08-8l0.31-2.38,2.3,0.68a6.47,6.47,0,0,0,1.25-2.63,9.39,9.39,0,0,0-.47-3.59l-2.38.5,0.37-2.86a48.6,48.6,0,0,0,.62-7.82C54.7,9.5,49.44,4.1,41.5,4.1S28.31,9.5,27.8,14.71a49.46,49.46,0,0,0,.62,7.82l0.36,2.86-2.38-.5a9.31,9.31,0,0,0-.47,3.56,4.34,4.34,0,0,0,1.54,2.65l2.31-.67,0,2.39c0.77,6,3.09,8,3.12,8l0.74,0.62,0,1L33.6,51.18l-1,.59a95.87,95.87,0,0,1-10,4.55c-1.73.72-3.46,1.38-5.12,2C12,60.47,6.87,62.46,5.38,64.79,4.38,66.35,4,73.94,4.15,80.81Z"/>
							</svg><spring:message code="page.formulaire.web.message.signatory.new.signatory" /></button>
						</p>
					</div>
				</div>
				
				<!-- RULES-->
				<div class="bnpAccount-inscriptionList-content bnpAccount-rules bnpAccount-signatories" id="rulesForm">
					<div id="rulesFormContent" class="bnpAccount-js-inscriptionList-slideContent">
						<p class="bnpAccount-mt-big"><strong class="bnpAccount-legendLike"><spring:message code="page.formulaire.web.message.section.rules" /></strong></p>
						<div class="errorblock" id="errorPart2-Rules" style="display: none"></div>
						<div class="bnpAccount-mt-big bnpAccount-tableContainer--rules" style="display: block;" id="blockRules">
							<c:if test="${accountDetailForm.nbRules > 0}">
								<table class="bnpAccount-table--rules">
									<thead>
										<tr>
											<th>Signatory / Group</th>
											<th>Signatory / Group</th>
											<th>Acting</th>
											<th>Bank operation</th>
											<th>Amount</th>
											<th>Edition</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${accountDetailForm.listRules}" var="rule" varStatus="count">
											<input type="hidden" name="listRules[${count.index}].typeOperation" value="${rule.typeOperation}" id="ruleToDelete${count.index}">
											<tr id="listRulesResume${count.index}">
												<td>
													<c:choose>
														<c:when test="${(not empty rule.college2) || (not empty rule.signatory2)}">
															<c:choose>
																<c:when test="${(not empty rule.college2)}">
																	<c:forEach items="${accountDetailForm.colleges}" var="college" varStatus="countCol">
																		<c:if test="${college.id == rule.college2.id}">
																			<div id="collegeUseInRules2${countCol.index}"></div>
																		</c:if>
																	</c:forEach>
																	${rule.college2.name}
																</c:when>
																<c:otherwise>
																	<c:forEach items="${accountDetailForm.signatories}" var="signatory" varStatus="countSign">
																		<c:if test="${signatory.id == rule.signatory2.id}">
																			<div id="signatoryUseInRules2${countSign.index}"></div>
																		</c:if>
																	</c:forEach>
																	${rule.signatory2.firstname} ${rule.signatory2.name}
<%-- 																	<c:forEach items="${accountDetailForm.signatories}" var="signatory" varStatus="count"> --%>
<%-- 																		<c:if test="${rule.signatory2.id == count.index}"> --%>
<%-- 																			${signatory.name} ${signatory.firstname} --%>
<%-- 																		</c:if> --%>
<%-- 																	</c:forEach> --%>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															N/A
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${(not empty rule.college)}">
															<c:forEach items="${accountDetailForm.colleges}" var="college" varStatus="countCol2">
																<c:if test="${college.id == rule.college.id}">
																	<div id="collegeUseInRules${countCol2.index}"></div>
																</c:if>
															</c:forEach>
															${rule.college.name}
														</c:when>
														<c:otherwise>
															<c:forEach items="${accountDetailForm.signatories}" var="signatory" varStatus="countSign2">
																<c:if test="${signatory.id == rule.signatory.id}">
																	<div id="signatoryUseInRules${countSign2.index}"></div>
																</c:if>
															</c:forEach>
															${rule.signatory.firstname} ${rule.signatory.name}
<%-- 															<c:forEach items="${accountDetailForm.signatories}" var="signatory" varStatus="count"> --%>
<%-- 																<c:if test="${rule.signatory.id == signatory.id}"> --%>
<%-- 																	${signatory.name} ${signatory.firstname} --%>
<%-- 																</c:if> --%>
<%-- 															</c:forEach> --%>
														</c:otherwise>
													</c:choose>
												<td>
													<c:choose>
														<c:when test="${(not empty rule.college2) || (not empty rule.signatory2)}">
															Jointly
														</c:when>
														<c:otherwise>
															Individually
														</c:otherwise>
													</c:choose>
												</td>
												<td>${rule.typeOperation} ${rule.field}</td>
												<td>
													<fmt:formatNumber type="currency" pattern="#####.#####" var="amountMin" value="${rule.amountMin}" minFractionDigits="0" maxFractionDigits="5"/>
													<fmt:formatNumber type="currency" pattern="#####.#####" var="amountMax" value="${rule.amountMax}" minFractionDigits="0" maxFractionDigits="5"/>
													<c:choose>
														<c:when test="${(empty rule.amountMin) && (empty rule.amountMax)}">
															No Limit
														</c:when>
														<c:when test="${(not empty rule.amountMin) && (empty rule.amountMax)}">
															Above ${amountMin} ${accountDetailForm.account.currency.value}
														</c:when>
														<c:when test="${(empty rule.amountMin) && (not empty rule.amountMax)}">
															Below ${amountMax} ${accountDetailForm.account.currency.value}
														</c:when>
														<c:otherwise>
															Between ${amountMin} ${accountDetailForm.account.currency.value} and ${amountMax} ${accountDetailForm.account.currency.value}
														</c:otherwise>
													</c:choose>
												</td>
												<td>
<!-- 													<button class="bnpAccount-btn bnpAccountresumeDataList-action-btn"> -->
<!-- 														<img src="../img/svg/create.svg" alt=""> -->
<!-- 													</button> -->
													<button type="button" onClick="deleteRules(${count.index})" class="bnpAccount-btn bnpAccountresumeDataList-action-btn deleteButton">
														<img src="../img/svg/delete.svg" alt="">
													</button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:if>
						</div>
						<div class="bnpAccount-unorderedList--Rules">
							<p class="bnpAccount-p"><spring:message code="page.formulaire.web.message.section.rules.intro" /></p>
							<form:hidden path="nbRules" id="nbRules"/>
							<form:hidden path="nbRules2" id="nbRules2"/>
							<ul class="bnpAccount-rulesList bnpAccount-unorderedList">
								<li class="bnpAccount-infoRequestParent">
									<div class="bnpAccount-infoRequest bnpAccount-infoRequest--double" id="bnpAccount-infoRequest-people" style="display:none">
										<button type="button" class="bnpAccount-infoRequest-btnClose bnpAccount-btn">x</button>
										<div class="bnpAccount-grid--infoRequest">
											<div>
												<p class="bnpAccount-infoRequest--title bnpAccount-p">
													<spring:message code="page.formulaire.web.message.rules.signatories" />
												</p>
												<div class="bnpAccount-infoRequest-container bnpAccount-signatoriesRules">
													<c:forEach items="${accountDetailForm.account.signatoriesList}" var="signatory" varStatus="count">
														<div class="bnpAccount-infoRequest-container bnpAccount-signatoriesRules" id="signatoryResumeInRules${count.index}">
															<p class="bnpAccount-fieldGroup">
																<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountSignatoryPeople2">
																	<input type="radio" name="GroupOrSignatory1" value="S;${signatory.name} ${signatory.firstname};${count.index}" id="Signatory1">
																	<img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace">
																	${signatory.name} ${signatory.firstname}
																</label>
															</p>
														</div>
													</c:forEach>
												</div>
												<div class="bnpAccount-resumeSagnatoryInRules"></div>
											</div>
											<div>
												<p class="bnpAccount-infoRequest--title bnpAccount-p">
													<spring:message code="page.formulaire.web.message.rules.group.signatories" />
												</p>
												<div class="bnpAccount-infoRequest-container bnpAccount-collegesRules">
													<c:forEach items="${accountDetailForm.account.collegeList}" var="colleges" varStatus="count">
														<p class="bnpAccount-fieldGroup" id="collegeResumeInRules${count.index}">
															<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountRulesGroupe1">
																<input type="radio" name="GroupOrSignatory1" value="G;${colleges.name};${count.index}" id="College1">
																<img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace">
																${colleges.name}
															</label>
														</p>
													</c:forEach>
												</div>
												<div class="bnpAccount-resumeGroupInRules"></div>
											</div>
										</div>
										<p class="bnpAccount-text--center">
											<button class="bnpAccount-btn bnpAccount-btn--normal bnpAccount-btn--action bnpAccount-infoRequest-btn"><spring:message code="page.formulaire.web.message.validate" /> </button>
										</p>
									</div>
									<p class="bnpAccount-p"><spring:message code="page.formulaire.web.message.rules.field1" /></p>
									<button type="button" id="rulesSignatoryButton" class="bnpAccount-btnReverse bnpAccount-rulesList-btnReverse bnpAccount-btn--normal bnpAccount-btnReverse--action"><spring:message code="page.formulaire.web.message.rules.button1" /></button>
								</li><li class="bnpAccount-infoRequestParent">
									<div class="bnpAccount-infoRequest bnpAccount-infoRequest--solo" id="bnpAccount-infoRequest-With" style="display:none">
										<button class="bnpAccount-infoRequest-btnClose bnpAccount-btn">x</button>
										<p class="bnpAccount-fieldGroup">
											<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountSignatoryActing1"><input type="radio" name="bnpAccountSignatoryActing" id="bnpAccountSignatoryActing1" value="Individually"><img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace"> <spring:message code="page.formulaire.web.message.rules.individually" /></label>
										</p>
										<p class="bnpAccount-fieldGroup">
											<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountSignatoryActing2"><input type="radio" name="bnpAccountSignatoryActing" id="bnpAccountSignatoryActing2" value="Jointly with "><img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace"><spring:message code="page.formulaire.web.message.rules.jointly" /></label>
										</p>
										<p class="bnpAccount-fieldGroup">
											<button type="button" class="bnpAccount-btn bnpAccount-btn--normal bnpAccount-btn--action bnpAccount-infoRequest-btn"><spring:message code="page.formulaire.web.message.validate" /> </button>
										</p>
									</div>
									<div class="bnpAccount-infoRequest bnpAccount-infoRequest--double" id="bnpAccount-infoRequest-jointlyPeople" style="display:none">
										<button type="button" class="bnpAccount-infoRequest-btnClose bnpAccount-btn">x</button>
										<div class="bnpAccount-grid--infoRequest">
											<div>
												<p class="bnpAccount-infoRequest--title bnpAccount-p">
													<spring:message code="page.formulaire.web.message.rules.signatories" />
												</p>
												<div class="bnpAccount-infoRequest-container bnpAccount-signatoriesRules2">
													<c:forEach items="${accountDetailForm.account.signatoriesList}" var="signatory" varStatus="count">
														<div class="bnpAccount-infoRequest-container bnpAccount-signatoriesRules" id="signatoryResumeInRules2${count.index}">
															<p class="bnpAccount-fieldGroup">
																<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountJointlyPeople1">
																	<input type="radio" name="GroupOrSignatory2" value="S;${signatory.name} ${signatory.firstname};${count.index}" id="Signatory2">
																	<img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace">
																	${signatory.name} ${signatory.firstname}
																</label>
															</p>
														</div>
													</c:forEach>
												</div>
												<div class="bnpAccount-resumeSagnatoryInRules2"></div>
											</div>
											<div>
												<p class="bnpAccount-infoRequest--title bnpAccount-p">
													<spring:message code="page.formulaire.web.message.rules.group.signatories" />
												</p>
												<div class="bnpAccount-infoRequest-container bnpAccount-collegesRules2">
													<c:forEach items="${accountDetailForm.account.collegeList}" var="colleges" varStatus="count">
														<p class="bnpAccount-fieldGroup" id="collegeResumeInRules2${count.index}">
															<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountRulesGroupe1">
																<input type="radio" name="GroupOrSignatory2" value="G;${colleges.name};${count.index}" id="College2">
																<img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace">
																${colleges.name}
															</label>
														</p>
													</c:forEach>
												</div>
												<div class="bnpAccount-resumeGroupInRules2"></div>
											</div>
										</div>
										<p class="bnpAccount-text--center">
											<button type="button" class="bnpAccount-btn bnpAccount-btn--normal bnpAccount-btn--action bnpAccount-infoRequest-btn"><spring:message code="page.formulaire.web.message.validate" /> </button>
										</p>
									</div>
									<p class="bnpAccount-p"><spring:message code="page.formulaire.web.message.rules.field2" /></p>
									<button type="button" id="rulesActingButton" disabled class="bnpAccount-btnReverse bnpAccount-rulesList-btnReverse bnpAccount-btn--normal bnpAccount-btnReverse--action"><spring:message code="page.formulaire.web.message.rules.button2" /></button>
								</li>
								<li class="bnpAccount-infoRequestParent">
									<div class="bnpAccount-infoRequest bnpAccount-infoRequest--action" id="bnpAccount-infoRequest-BankOperation" style="display:none">
										<form:hidden path="account.currency.value" id="currencyRules"/>
										<button type="button" class="bnpAccount-infoRequest-btnClose bnpAccount-btn">x</button>
										<div>
											<p class="bnpAccount-infoRequest--title bnpAccount-p">
												<spring:message code="page.formulaire.web.message.rules.bank.operation" />
											</p>
											<p>
												<select name="bnpAccount-infoRequest-actionFirst" id="bnpAccount-infoRequest-actionFirst" class="bnpAccount-field bnpAccount-infoRequest-actionChoice">
													<option value="All operations"><spring:message code="page.formulaire.web.message.rules.action.all" /></option>
													<option value="All operations except"><spring:message code="page.formulaire.web.message.rules.action.allExcept" /></option>
													<option value=""><spring:message code="page.formulaire.web.message.rules.action.free" /></option>
												</select><input style="display:none" class="bnpAccount-field bnpAccount-infoRequest-actionChoice bnpAccount-infoRequest-freeText" type="text" name="" placeholder="" id="bnpAccount-infoRequest-operation">
											</p>
										</div>
										<div>
											<p class="bnpAccount-infoRequest--title bnpAccount-p">
												<spring:message code="page.formulaire.web.message.rules.field4" />
											</p>
											<p>
												<select name="bnpAccount-infoRequest-actionLimits" id="bnpAccount-infoRequest-actionLimits" class="bnpAccount-field bnpAccount-infoRequest-actionChoice">
													<option value="No limit"><spring:message code="page.formulaire.web.message.rules.action.noLimit" /></option>
													<option value="Above"><spring:message code="page.formulaire.web.message.rules.action.above" /></option>
													<option value="Below"><spring:message code="page.formulaire.web.message.rules.action.below" /></option>
													<option value="Between"><spring:message code="page.formulaire.web.message.rules.action.fromto" /></option>
												</select>
												<spring:message code="page.formulaire.web.message.rules.euro" var="devise"/>
												<input class="bnpAccount-field bnpAccount-infoRequest-ruleAmount" type="text" name="bnpAccount-ruleAmount-first" id="bnpAccount-ruleAmount-first" style="display:none"><input class="bnpAccount-field bnpAccount-infoRequest-ruleAmount" type="text" name="bnpAccount-ruleAmount-second" id="bnpAccount-ruleAmount-second" style="display:none">
											</p>
										</div>
										<p class="bnpAccount-text--center">
											<button type="button" class="bnpAccount-btn bnpAccount-btn--normal bnpAccount-btn--action bnpAccount-infoRequest-btn"><spring:message code="page.formulaire.web.message.validate" /></button>
										</p>
									</div>
		
									<p class="bnpAccount-p"><spring:message code="page.formulaire.web.message.rules.field3" /></p>
									<button id="rulesOperationButton" disabled class="bnpAccount-btnReverse bnpAccount-rulesList-btnReverse bnpAccount-btn--normal bnpAccount-btnReverse--action"><spring:message code="page.formulaire.web.message.rules.button3" /></button>
								</li>
							</ul>
						</div>
						<p class="bnpAccount-mt-big">
							<button type="button" onClick="addRulesButton()" class="bnpAccount-btnReverse bnpAccount-btn--small bnpAccount-btnReverse--action bnpAccount-btn--add" id="bnpAccount-addRule"><spring:message code="page.formulaire.web.message.rules.add" /></button>
						</p>
					</div>
				</div>
				<p class="bnpAccount-text--center bnpAccount-mt-big" id="NextPart2" style="display : none">
					<button type="button" onclick="part2Submit()" class="bnpAccount-btn bnpAccount-btn--normalSubmit bnpAccount-btn--action"><spring:message code="page.formulaire.web.message.next" /></button>
				</p>
					
			</form:form>	
		</li>
		
		<!-- 		DOCUMENT 		-->
		<li class="bnpAccount-p-mainContent bnpAccount-listElement bnpAccount-inscriptionListItem--slidedUp" id="blocPart3">
			<div class="bnpAccount-inscriptionList-content">
				<h2 class="bnpAccount-inscriptionList-mainTitle">
					<spring:message code="page.formulaire.web.message.section.documents" />
				</h2>
				<button type="button" class="bnpAccount-btn bnpAccount-btnLink bnpAccount-btnLink--default bnpAccount-js-slideTrigger">
					<span class="bnpAccount-txtOpen"><spring:message code="page.formulaire.web.message.open" /></span>
					<span class="bnpAccount-txtClose"><spring:message code="page.formulaire.web.message.close" /></span>
				</button>
				<div class="bnpAccount-js-inscriptionList-slideContent bnpAccount-accountChecked" id="contentPart3">
					<div class="errorblock" id="errorPart3" style="display: none"></div>
					<p class="bnpAccount-legendLike"><strong>
						<spring:message code="page.formulaire.web.message.section.representative" /></strong></p>
					<p class="bnpAccount-p"><spring:message code="page.formulaire.web.message.representive.document.intro" /> </p>
											
					<span class="bnpAccount-infoRequestParent">
						<form:form method="post" action="getListRepresentativeId" commandName="accountDetailForm" id="representativeForm">
							<div id="selectRep" class="bnpAccount-infoRequest bnpAccount-infoRequest--solo bnpAccount-infoRequest--popUp">
								<button type="button" class="bnpAccount-infoRequest-btnClose bnpAccount-btn">x</button>
								<p class="bnpAccount-infoRequest--title">
										<spring:message code="page.formulaire.web.message.section.representative" />
								</p>
								<div class="bnpAccount-infoRequest-container">
									<c:forEach items="${accountDetailForm.entity.representativesList}" var="representatives" varStatus="nb">
										<p class="bnpAccount-fieldGroup">
											<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountDocumentsPeople${nb.index}">
												<input type="checkbox" name="listRepresentativeSelectedInt" value="${representatives.id};${representatives.firstname} ${representatives.name}" id="bnpAccountDocumentsPeople${nb.index}">
												<img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace">
												${representatives.firstname} ${representatives.name}
											</label>
										</p>
									</c:forEach>
								</div>
								<p class="bnpAccount-text--center">
									<button type="button" onClick="repButton2()" class="bnpAccount-btn bnpAccount-btn--normal bnpAccount-btn--action bnpAccount-infoRequest-btn"><spring:message code="page.formulaire.web.message.validate" /></button>
								</p>
							</div>
							<button type="button" onclick="repButton()" class="bnpAccount-btnReverse bnpAccount-rulesList-btnReverse bnpAccount-btn--normal">
							<c:if test="${fn:length(accountDetailForm.listRepresentativeSelected) == 0}">
								<spring:message code="page.formulaire.web.message.representative.document.select" />
							</c:if>
							<c:forEach items="${accountDetailForm.listRepresentativeSelected}" var="representatives" varStatus="nb">
								<c:choose>
									<c:when test="${nb.index == 0}">
										<c:if test="${empty representatives.firstname}">
											<spring:message code="page.formulaire.web.message.representative.document.select" />
										</c:if>
										<c:if test="${not empty representatives.firstname}">
											${representatives.firstname} ${representatives.name}
											<script type="text/javascript">
												checkedRep(${nb.index});
											</script>
										</c:if>
									</c:when>
									<c:otherwise>
										, ${representatives.firstname} ${representatives.name}
										<script type="text/javascript">
											checkedRep(${nb.index});
										</script>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</button>
						</form:form>
					</span>
					
					<p class="bnpAccount-mt-big">
						<p class="bnpAccount-legendLike"><strong><spring:message code="page.formulaire.web.message.document.list" /></strong></p>
						<p class="bnpAccount-p"><spring:message code="page.formulaire.web.message.document.list.intro" /></p>
						<p class="bnpAccount-p">${accountDetailForm.country.locale.getDisplayCountry(pageContext.response.locale)}</p>
						
						<div calss="bnpAccount-adddocuments">
							<c:forEach items="${accountDetailForm.mapCountryDocs}" var="entryCountryDocs">
								<dl class="bnpAccount-resumeDataList bnpAccount-resumeDataList--documents">
									<dt><strong><spring:message code="page.formulaire.web.message.document.list.field1" /></strong></dt>
									<dd>
										<ul class="bnpAccount-unorderedList">
											<c:forEach items="${entryCountryDocs.value}" var="countryDoc">
												<c:if test="${countryDoc.documentType.label == \"Account Agreements\"}">
													<li>${countryDoc.title}</li>
												</c:if>
											</c:forEach>
										</ul>
									</dd>
								</dl>
							</c:forEach>
							<c:forEach items="${accountDetailForm.mapCountryDocs}" var="entryCountryDocs">
								<dl class="bnpAccount-resumeDataList bnpAccount-resumeDataList--documents">
									<dt><strong><spring:message code="page.formulaire.web.message.document.list.field2" /></strong></dt>
									<dd>
										<ul class="bnpAccount-unorderedList">
											<c:forEach items="${entryCountryDocs.value}" var="countryDoc">
												<c:if test="${countryDoc.documentType.label == \"Customer Requirements\"}">
													<li>${countryDoc.title}</li>
												</c:if>
											</c:forEach>
										</ul>
									</dd>
								</dl>
							</c:forEach>
							<c:forEach items="${accountDetailForm.mapCountryDocs}" var="entryCountryDocs">
								<dl class="bnpAccount-resumeDataList bnpAccount-resumeDataList--documents">
									<dt><strong><spring:message code="page.formulaire.web.message.document.list.field3" /></strong></dt>
									<dd>
										<ul class="bnpAccount-unorderedList">
											<c:forEach items="${entryCountryDocs.value}" var="countryDoc">
												<c:if test="${countryDoc.documentType.label == \"Common supporting\"}">
													<li>${countryDoc.title}</li>
												</c:if>
											</c:forEach>
										</ul>
									</dd>
								</dl>
							</c:forEach>
						</div>
						<div calss="bnpAccount-adddocuments-before"></div>
					</p>
					<form:form method="post" action="newFormDownload" commandName="accountDetailForm" id="downloadForm">
						<p class="bnpAccount-mt-big">
							<p class="bnpAccount-p">
								<spring:message code="page.formulaire.web.message.document.merge.intro" /><br>
								<spring:message code="page.formulaire.web.message.document.merge.intro2" />
							</p>
	<%-- 						<c:if test="${accountDetailForm.nbAccounts > 1}"> --%>
	<%-- 							<p class="bnpAccount-legendLike"><strong><spring:message code="page.formulaire.web.message.document.merge" /></strong></p> --%>
	<%-- 							<p class="bnpAccount-p bnpAccount-mt-big"><spring:message code="page.formulaire.web.message.document.merge.intro3" /></p> --%>
	<%-- 						</c:if> --%>
							<div id="listAccountChecked" class="bnpAccount-accountcheck">
								<c:forEach items="${accountDetailForm.listAccountCountry}" var="accounts" varStatus="nb">
									<c:if test="${nb.index < 1}">
										<p class="bnpAccount-legendLike"><strong><spring:message code="page.formulaire.web.message.document.merge" /></strong></p>
										<p class="bnpAccount-p bnpAccount-mt-big"><spring:message code="page.formulaire.web.message.document.merge.intro3" /></p>
									</c:if>
									<p class="bnpAccount-fieldGroup--checkbox">
										<label for="bnpAccount${nb.index}">
											<input type="checkbox" name="listAccountSelectedIDS[${nb.index}]" value="${accounts.id}" id="bnpAccount${nb.index}">
											<span class="bnpAccount-changedUi bnpAccount-checkbox">
												<svg class="bnpAccount-checkbox-check" xmlns="http://www.w3.org/2000/svg" width="20" height="17" viewBox="0 0 13 10">
													<polygon points="5 10 0 5.96 1.9 3.65 4.8 6 10.89 0 13 2.11 5 10"/>
												</svg>
											</span>
											${accounts.name}
										</label>
									</p>
								</c:forEach>
							</div>
							<div class="bnpAccount-accountcheckbefore"></div>
						</p>
						<p class="bnpAccount-text--center bnpAccount-fieldGroup">
							<button onClick="location.href='<%=WebConstants.HOME_COMPANY_BACK_FROM_ACCOUNT%>'" type="button" class="bnpAccount-btn bnpAccount-btn--normalSubmit bnpAccount-btn--action"><spring:message code="page.formulaire.web.message.document.add" /></button>
							<button onClick="location.href='newFormDownload'" class="bnpAccount-btn bnpAccount-btn--normalSubmit bnpAccount-btn--action"><spring:message code="page.formulaire.web.message.document.download" /></button>
						</p>
					</form:form>
				</div>
			</div>
		</li>
	</ol>
	</main>
	
	<jsp:include page="footer.jsp" />
</body>
</html>