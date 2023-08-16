<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>

<sec:authentication property="principal.preferences.dateFormat.labelLong" var="dateFormatLong"/>
<sec:authentication property="principal.preferences.dateFormat.labelDisplay" var="dateFormatDisplay"/>

<c:set var="pcPT"	value="<%=Constants.PARAM_CODE_ACC_THIRD_CNT_PT%>"	scope="request"/>
<c:set var="pcES"	value="<%=Constants.PARAM_CODE_ACC_THIRD_CNT_ES%>"	scope="request"/>
<c:set var="pcGB"	value="<%=Constants.PARAM_CODE_ACC_THIRD_CNT_GB%>"	scope="request"/>
<c:set var="pcIE"	value="<%=Constants.PARAM_CODE_ACC_THIRD_CNT_IE%>"	scope="request"/>
<c:set var="ppfr1"	value="<%=Constants.PARAM_CODE_ACC_THIRD_PWR_FR1%>"	scope="request"/>
<c:set var="ppfr2"	value="<%=Constants.PARAM_CODE_ACC_THIRD_PWR_FR2%>"	scope="request"/>
<c:set var="ppPT"	value="<%=Constants.PARAM_CODE_ACC_THIRD_PWR_PT%>"	scope="request"/>
<c:set var="ppES"	value="<%=Constants.PARAM_CODE_ACC_THIRD_PWR_ES%>"	scope="request"/>
<c:set var="ppGB"	value="<%=Constants.PARAM_CODE_ACC_THIRD_PWR_GB%>"	scope="request"/>
<c:set var="ppGB"	value="<%=Constants.PARAM_CODE_ACC_THIRD_PWR_GB%>"	scope="request"/>
<c:set var="psanl"	value="<%=Constants.PARAM_CODE_AMOUNT_NO_LIMIT%>"	scope="request"/>
<c:set var="psal"	value="<%=Constants.PARAM_CODE_AMOUNT_LIMIT%>"		scope="request"/>

<c:set var="legalStatusOtherConstant" value="<%=Constants.PARAM_TYPE_LEGAL_STATUS_OTHER%>"	scope="request"/>

<jsp:include page="include.jsp" />
<script src="../js/formulaire.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		initSummary();
	});
</script>

<div id="content">
	<jsp:include page="error.jsp" />
	<jsp:include page="breadcrumbs.jsp" />
	<p>
		<spring:message code="page.formulaire.web.message.explanation.formsummary" />
	</p>
	<div id="blocContainer">
		<form:form method="post" action="replacedInJS" commandName="formulaireDCOForm" id="mainForm"></form:form>
		<form:form method="post" action="formDownload" commandName="formulaireDCOForm" id="form">
			<fieldset>
				<h2><spring:message code="page.form.web.summary.entity" /></h2>
				<div class="hr_dotted"></div>
				<label class="content_label"><spring:message code="page.formulaire.web.message.nameField" /></label>
				<div class="content_field">${formulaireDCOForm.entity.label}</div>
				<div class="hr_dotted"></div>
				<label class="content_label"><spring:message code="page.formulaire.web.message.countryField" /></label>
				<div class="content_field">${formulaireDCOForm.entity.country.getDisplayCountry(pageContext.response.locale)}</div>
				<div class="hr_dotted"></div>
				<label class="content_label"><spring:message code="page.formulaire.web.message.legalStatus" /></label>
				<div class="content_field">${formulaireDCOForm.entity.legalStatus.value}</div>
				<div class="hr_dotted"></div>
				<c:if test="${fn:toUpperCase(formulaireDCOForm.entity.legalStatus.entry) == fn:toUpperCase(legalStatusOtherConstant)}">
					<label class="content_label">${formulaireDCOForm.entity.legalStatus.value}:</label>
					<div class="content_field">${formulaireDCOForm.entity.legalStatusOther}</div>
					<div class="hr_dotted"></div>
				</c:if>
				<label class="content_label"><spring:message code="page.formulaire.web.message.commercialRegister" /></label>
				<div class="content_field">${formulaireDCOForm.entity.commercialRegister}</div>
				<div class="hr_dotted"></div>
				<label class="content_label"><spring:message code="page.formulaire.web.message.taxInformation" /></label>
				<div class="content_field">${formulaireDCOForm.entity.taxInformation}</div>
				<div class="hr_dotted"></div>
				<label class="content_label"><spring:message code="page.formulaire.web.message.registrationCountry" /></label>
				<div class="content_field">${formulaireDCOForm.getDisplayCountryFromString(formulaireDCOForm.entity.registrationCountry,pageContext.response.locale)}</div>
				<div class="hr_dotted"></div>
				<label class="content_label"><spring:message code="page.formulaire.web.message.adressField" /></label>
				<div class="content_field">
					${formulaireDCOForm.entity.address.fieldOne}<br/>
					${formulaireDCOForm.entity.address.fieldTwo}<br/>
					${formulaireDCOForm.entity.address.fieldThree}<br/>
					${formulaireDCOForm.entity.address.fieldFour}
					<c:if test="${not empty formulaireDCOForm.entity.address.fieldFive}">
						<br/>${formulaireDCOForm.entity.address.fieldFive}
					</c:if>
					<c:if test="${not empty formulaireDCOForm.entity.address.fieldSix}">
						<br/>${formulaireDCOForm.entity.address.fieldSix}
					</c:if>
					<c:if test="${not empty formulaireDCOForm.entity.address.fieldSeven}">
						<br/>${formulaireDCOForm.entity.address.fieldSeven}
					</c:if>
				</div>
				<div class="hr_dotted"></div>
				<label class="content_label"><spring:message code="page.formulaire.web.message.adressPostalField" /></label>
				<div class="content_field">
					${formulaireDCOForm.entity.addressMailing.fieldOne}<br/>
					${formulaireDCOForm.entity.addressMailing.fieldTwo}<br/>
					${formulaireDCOForm.entity.addressMailing.fieldThree}<br/>
					${formulaireDCOForm.entity.addressMailing.fieldFour}
					<c:if test="${not empty formulaireDCOForm.entity.addressMailing.fieldFive}">
						<br/>${formulaireDCOForm.entity.addressMailing.fieldFive}
					</c:if>
					<c:if test="${not empty formulaireDCOForm.entity.addressMailing.fieldSix}">
						<br/>${formulaireDCOForm.entity.addressMailing.fieldSix}
					</c:if>
					<c:if test="${not empty formulaireDCOForm.entity.addressMailing.fieldSeven}">
						<br/>${formulaireDCOForm.entity.addressMailing.fieldSeven}
					</c:if>
				</div>
				<div class="hr_dotted"></div>
			</fieldset>
			<fieldset>
				<h2><spring:message code="page.form.web.summary.contacts" /></h2>
				<div class="hr_dotted"></div>
				<c:choose>
					<c:when test="${empty formulaireDCOForm.entity.thirdParty2 && empty formulaireDCOForm.entity.thirdParty}">
						<label class="content_label"><spring:message code="page.formulaire.web.message.contact" /></label>
						<div class="content_field"></div>
						<label class="content_label"><spring:message code="page.formulaire.web.message.telephone" /></label>
						<div class="content_field"></div>
						<div class="hr_dotted"></div>
						<label class="content_label"><spring:message code="page.formulaire.web.message.mail" /></label>
						<div class="content_field"></div>
						<div class="hr_dotted"></div>
						<label class="content_label"><spring:message code="page.formulaire.web.message.fax" /></label>
						<div class="content_field"></div>
						<div class="hr_dotted"></div>
					</c:when>
					<c:otherwise>	
						<c:if test="${not empty formulaireDCOForm.entity.thirdParty}">
							<label class="content_label"><spring:message code="page.formulaire.web.message.contact" /></label>
							<div class="content_field">${formulaireDCOForm.entity.thirdParty.name}&nbsp;${formulaireDCOForm.entity.thirdParty.firstName}</div>
							<div class="hr_dotted"></div>
							<label class="content_label"><spring:message code="page.formulaire.web.message.telephone" /></label>
							<div class="content_field">${formulaireDCOForm.entity.thirdParty.tel}</div>
							<div class="hr_dotted"></div>
							<label class="content_label"><spring:message code="page.formulaire.web.message.mail" /></label>
							<div class="content_field">${formulaireDCOForm.entity.thirdParty.mail}</div>
							<div class="hr_dotted"></div>
							<label class="content_label"><spring:message code="page.formulaire.web.message.fax" /></label>
							<div class="content_field">${formulaireDCOForm.entity.thirdParty.fax}</div>
							<div class="hr_dotted"></div>
						</c:if>				
						<c:if test="${not empty formulaireDCOForm.entity.thirdParty2}">
							<label class="content_label"><spring:message code="page.formulaire.web.message.contact" /></label>
							<div class="content_field">${formulaireDCOForm.entity.thirdParty2.name}&nbsp;${formulaireDCOForm.entity.thirdParty2.firstName}</div>
							<div class="hr_dotted"></div>
							<label class="content_label"><spring:message code="page.formulaire.web.message.telephone" /></label>
							<div class="content_field">${formulaireDCOForm.entity.thirdParty2.tel}</div>
							<div class="hr_dotted"></div>
							<label class="content_label"><spring:message code="page.formulaire.web.message.mail" /></label>
							<div class="content_field">${formulaireDCOForm.entity.thirdParty2.mail}</div>
							<div class="hr_dotted"></div>
							<label class="content_label"><spring:message code="page.formulaire.web.message.fax" /></label>
							<div class="content_field">${formulaireDCOForm.entity.thirdParty2.fax}</div>
							<div class="hr_dotted"></div>
						</c:if>
					</c:otherwise>
				</c:choose>
				<label class="content_label"><spring:message code="page.formulaire.web.message.legalRepresentative" /></label>
				<div class="content_field">
					<c:forEach items="${formulaireDCOForm.entity.thirdParties}" var="legalRepresentative" varStatus="status">
						<c:if test="${status.index>0}"><br/></c:if>
						${legalRepresentative.name}&nbsp;${legalRepresentative.firstName}
					</c:forEach>
				</div>
				<div class="hr_dotted"></div>
			</fieldset>
			<fieldset>
				<h2><spring:message code="page.form.web.summary.accounts" /></h2>
				<div class="hr_dotted"></div>
				<c:forEach items="${formulaireDCOForm.mapAccount}" var="node" varStatus="nstatus">				
					<h3>${formulaireDCOForm.listAccountKey[nstatus.index].label}</h3>
						<fieldset class="form-summary-account-subs-fieldset">
							<legend class="form-summary-account-subs-fieldset-legend"><spring:message code="page.formulaire.web.message.accounts.form.country.header"/>${formulaireDCOForm.listAccountKey[nstatus.index].label}</legend>
							<label class="content_label"><spring:message code="page.formulaire.web.message.periodiciteReleve" /></label>
							<div class="content_field">${formulaireDCOForm.listPeriodicity[nstatus.index].value}</div>
							<div class="hr_dotted"></div>
							<c:if test="${not empty formulaireDCOForm.listAddress[nstatus.index].fieldOne
								or not empty formulaireDCOForm.listBranchName[nstatus.index]
								or not empty formulaireDCOForm.listVatNumber[nstatus.index]
								or not empty formulaireDCOForm.listCommercialRegister[nstatus.index]}">
								<fieldset class="form-summary-account-subs-fieldset" style="border-right-width: 0px; border-left-width: 0px; border-bottom-width: 0px;">
									<legend class="form-summary-account-subs-fieldset-legend"><spring:message code="page.form.web.summary.accounts.subsidiary" /></legend>
									<label class="content_label"><spring:message code="page.formulaire.web.message.branchNameSubsidiary" /></label>
									<div class="content_field">${formulaireDCOForm.listBranchName[nstatus.index]}</div>
									<div class="hr_dotted"></div>
									<label class="content_label"><spring:message code="page.formulaire.web.message.vatNumberSubsidiary" /></label>
									<div class="content_field">${formulaireDCOForm.listVatNumber[nstatus.index]}</div>
									<div class="hr_dotted"></div>
									<label class="content_label"><spring:message code="page.formulaire.web.message.addressSubsidiaryField" /></label>
									<div class="content_field">
										${formulaireDCOForm.listAddress[nstatus.index].fieldOne}
										<div class="hr_dotted"></div>
										${formulaireDCOForm.listAddress[nstatus.index].fieldTwo}
										<div class="hr_dotted"></div>
										${formulaireDCOForm.listAddress[nstatus.index].fieldThree}
										<div class="hr_dotted"></div>
										${formulaireDCOForm.listAddress[nstatus.index].fieldFour}
										<div class="hr_dotted"></div>
										<c:if test="${not empty formulaireDCOForm.listAddress[nstatus.index].fieldFive}">
											${formulaireDCOForm.listAddress[nstatus.index].fieldFive}
											<div class="hr_dotted"></div>
										</c:if>
										<c:if test="${not empty formulaireDCOForm.listAddress[nstatus.index].fieldSix}">
											${formulaireDCOForm.listAddress[nstatus.index].fieldSix}
											<div class="hr_dotted"></div>
										</c:if>
										<c:if test="${not empty formulaireDCOForm.listAddress[nstatus.index].fieldSeven}">
											${formulaireDCOForm.listAddress[nstatus.index].fieldSeven}
											<div class="hr_dotted"></div>
										</c:if>
									</div>
									<div class="hr_dotted"></div>
									<label class="content_label"><spring:message code="page.formulaire.web.message.branchCommercialRegister" /></label>
									<div class="content_field">${formulaireDCOForm.listCommercialRegister[nstatus.index]}</div>
									<div class="hr_dotted"></div>
							</fieldset>
						</c:if>
						<c:if test="${not empty formulaireDCOForm.accounts[node.value[0]].accountThirdPartyList}">
							<fieldset class="form-summary-account-subs-fieldset" style="border-right-width: 0px; border-left-width: 0px; border-bottom-width: 0px;">
								<legend class="form-summary-account-subs-fieldset-legend"><spring:message code="page.formulaire.web.message.thirdParty" /></legend>
								<div class="content_field">
									<c:forEach items="${formulaireDCOForm.accounts[node.value[0]].accountThirdPartyList}" var="accountThirdParty" varStatus="status">
										<c:if test="${status.index>0}">
											<div class="hr_dotted"></div>
										</c:if>
										<label class="content_label">${accountThirdParty.thirdParty.name}&nbsp;${accountThirdParty.thirdParty.firstName}</label>
										<div class="content_field" style="width: 680px;">
											<c:choose>
												<c:when test="${accountThirdParty.powerType == ppfr1}">
													<spring:message code="page.formulaire.account.third.party.power.FR1" />
												</c:when>
												<c:when test="${accountThirdParty.powerType == ppfr2}">
													<spring:message code="page.formulaire.account.third.party.power.FR2" />
												</c:when>
												<c:when test="${accountThirdParty.powerType == ppPT}">
													<spring:message code="page.formulaire.account.third.party.power.PT" arguments="${dateFormatDisplay}"/>
													&nbsp;-&nbsp;<fmt:formatDate pattern="${dateFormatLong}" value="${accountThirdParty.boardResolutionDate}"/>
												</c:when>
												<c:when test="${accountThirdParty.powerType == ppES}">
													<spring:message code="page.formulaire.account.third.party.power.ES" arguments="${dateFormatDisplay}"/>
													&nbsp;-&nbsp;<fmt:formatDate pattern="${dateFormatLong}" value="${accountThirdParty.boardResolutionDate}"/>
													&nbsp;-&nbsp;${accountThirdParty.publicDeedReference}
												</c:when>
												<c:when test="${accountThirdParty.powerType == ppGB}">
													<spring:message code="page.formulaire.account.third.party.power.GB" arguments="${dateFormatDisplay}"/>
													&nbsp;-&nbsp;<fmt:formatDate pattern="${dateFormatLong}" value="${accountThirdParty.boardResolutionDate}"/>
												</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${accountThirdParty.statusAmountLimit == psanl}">
													&nbsp;-&nbsp;<spring:message code="page.formulaire.web.message.status.ammount.1" />
												</c:when>
												<c:when test="${accountThirdParty.statusAmountLimit == psal}">
													&nbsp;-&nbsp;<spring:message code="page.formulaire.web.message.status.ammount.2" />:
													&nbsp;${accountThirdParty.amountLimit}
													&nbsp;${accountThirdParty.deviseAmountLimit}
												</c:when>
											</c:choose>
											<c:if test="${accountThirdParty.signatureAuthorization != null}">
												&nbsp;-&nbsp;${accountThirdParty.signatureAuthorization.value}
											</c:if>
											<c:forEach items="${accountThirdParty.authorizedList}" var="jointTP">
												<c:forEach items="${formulaireDCOForm.TPForATPList}" var="authTP">
													<c:if test="${jointTP.id.thirdPartyId == authTP.id }">
														 &nbsp;-&nbsp;${authTP.name} ${authTP.firstName}
													</c:if>
												</c:forEach>
											</c:forEach>
										</div>
									</c:forEach>
								</div>
							</fieldset>
						</c:if>
					<div class="hr_dotted"></div>
					</fieldset>
					<div class="hr_dotted"></div>
					<c:forEach items="${node.value}" var="listId" >
						<fieldset class="form-summary-account-subs-fieldset">
							<legend class="form-summary-account-subs-fieldset-legend">${formulaireDCOForm.accounts[listId].reference}</legend>
							<div class="hr_dotted"></div>
							<label class="content_label"><spring:message code="page.formulaire.web.message.typeCompte" /></label>
							<div class="content_field">${formulaireDCOForm.accounts[listId].typeAccount.value}</div>
							<div class="hr_dotted"></div>
							<label class="content_label"><spring:message code="page.formulaire.web.message.deviseCompte" /></label>
							<div class="content_field">${formulaireDCOForm.accounts[listId].currency.value}</div>
							<div class="hr_dotted"></div>		
							<c:if test="${formulaireDCOForm.accounts[listId].countryAccount.com_lang_enabled}">							
								<label class="content_label"><spring:message code="page.formulaire.web.message.communicationLanguage" /></label>
								<c:forEach items="${formulaireDCOForm.mapCountryComLangs[formulaireDCOForm.accounts[listId].countryAccount.locale.country]}" var="ccl">
									<c:if test="${formulaireDCOForm.accounts[listId].communicationLanguage.id == ccl.id }">
										 <div class="content_field">${ccl.login}</div>
									</c:if>
								</c:forEach>
								<div class="hr_dotted"></div>
							</c:if>
						</fieldset>
						<div class="hr_dotted"></div>
					</c:forEach>
				</c:forEach>
			</fieldset>
			<fieldset style="display: none;">
				<h2><spring:message code="page.form.web.summary.accounts" /></h2>
				<c:forEach items="${formulaireDCOForm.accounts}" var="account">
					<h3>${account.reference}</h3><br/>
					<label class="content_label"><spring:message code="page.formulaire.web.message.paysOuvertureCompte" /></label>
					<div class="content_field">${account.countryAccount.locale.getDisplayCountry(pageContext.response.locale)}</div>
					<div class="hr_dotted"></div>
					<label class="content_label"><spring:message code="page.formulaire.web.message.typeCompte" /></label>
					<div class="content_field">${account.typeAccount.value}</div>
					<div class="hr_dotted"></div>
					<label class="content_label"><spring:message code="page.formulaire.web.message.deviseCompte" /></label>
					<div class="content_field">${account.currency.value}</div>
					<div class="hr_dotted"></div>
					<label class="content_label"><spring:message code="page.formulaire.web.message.periodiciteReleve" /></label>
					<div class="content_field">${account.periodicity.value}</div>
					<div class="hr_dotted"></div>
					<label class="content_label"><spring:message code="page.formulaire.web.message.thirdParty" /></label>
					<div class="content_field">
						<c:forEach items="${account.accountThirdPartyList}" var="accountThirdParty" varStatus="status">
							<c:if test="${status.index>0}"><br/></c:if>
							${accountThirdParty.thirdParty.name}&nbsp;${accountThirdParty.thirdParty.firstName}
							<c:if test="${accountThirdParty.signatureAuthorization != null}">
								&nbsp;-&nbsp;${accountThirdParty.signatureAuthorization.value}
							</c:if>
							<c:if test="${not empty accountThirdParty.amountLimit and accountThirdParty.amountLimit != 0}">
								&nbsp;-&nbsp;${accountThirdParty.amountLimit}
							</c:if>
						</c:forEach>
					</div>
					<div class="hr_dotted"></div>
					<c:if test="${not empty account.address.fieldOne
						or not empty account.address.fieldTwo
						or not empty account.address.fieldThree
						or not empty account.address.fieldFour
						or not empty account.address.fieldFive
						or not empty account.address.fieldSix
						or not empty account.address.fieldSeven
						or not empty account.commercialRegister
						or not empty account.country}">
						<fieldset class="form-summary-account-subs-fieldset">
							<legend class="form-summary-account-subs-fieldset-legend">
								<spring:message code="page.form.web.summary.accounts.subsidiary" />
							</legend>
							<label class="content_label"><spring:message code="page.formulaire.web.message.addressSubsidiaryField" /></label>
							<div class="content_field">
								${account.address.fieldOne}<br/>
								${account.address.fieldTwo}<br/>
								${account.address.fieldThree}<br/>
								${account.address.fieldFour}<br/>
								<c:if test="${not empty account.address.fieldFive}">
									${account.address.fieldFive}<br/>
								</c:if>
								<c:if test="${not empty account.address.fieldSix}">
									${account.address.fieldSix}<br/>
								</c:if>
								<c:if test="${not empty account.address.fieldSeven}">
									${account.address.fieldSeven}<br/>
								</c:if>
							</div>
							<div class="hr_dotted"></div>
							<label class="content_label"><spring:message code="page.formulaire.web.message.commercialRegister" /></label>
							<div class="content_field">${account.commercialRegister}</div>
							<div class="hr_dotted"></div>
							<label class="content_label"><spring:message code="page.formulaire.web.message.registrationCountry" /></label>
							<div class="content_field">${formulaireDCOForm.getDisplayCountryFromString(account.country,pageContext.response.locale)}</div>
							<div class="hr_dotted"></div>
						</fieldset>
					</c:if>
				</c:forEach>
				<div class="hr_dotted"></div>
			</fieldset>
			<fieldset>
				<h2><spring:message code="page.form.web.summary.documents" /></h2>
				<spring:message code="page.form.web.summary.documents.explication" />
				<c:forEach items="${formulaireDCOForm.mapCountryDocs}" var="entryCountryDocs">
					<div id="country-${entryCountryDocs.key.country}">
						<h3>${entryCountryDocs.key.getDisplayCountry(pageContext.response.locale)}</h3><br/>
						<label class="content_label" style="display: none"><spring:message code="page.form.web.summary.country.language" /></label>
						<div class="content_field">
							<form:select  style="display: none" name="language" path="summary.mapCountrySelectedLang[${entryCountryDocs.key.country}]"
								id="lang${loopIndex}" class="content_field" onchange="exitMyAccounts = false; $('#form').attr('action','formSummary');$('#form').submit();">
								<c:forEach items="${formulaireDCOForm.summary.mapCountryLangs[entryCountryDocs.key.country]}" var="lang">
									<form:option value="${lang.language}">${lang.getDisplayLanguage(pageContext.response.locale)}</form:option>						
								</c:forEach>
							</form:select>
						</div>
							- ${entryCountryDocs.key.getDisplayCountry(pageContext.response.locale)}&nbsp;<spring:message code="pdf.bank.form.filename.suffix" />.pdf<br/>
						<c:forEach items="${entryCountryDocs.value}" var="countryDoc">
							- ${countryDoc.title}<label class="content_label_summary">${countryDoc.documentType.label}</label><br/>
						</c:forEach>
					</div>
				</c:forEach>
				<div class="hr_dotted"></div>
			</fieldset>
			<fieldset>
			<h2><spring:message code="page.formulaire.web.message.etape5.warning" /></h2>
			<div class="hr_dotted"></div>
			</fieldset>
			<div class="buttonOnRight">
				<span class="buttonBegin">
					<input type="button" class="button" id="submit_summary_back" value="<spring:message code="page.formulaire.web.message.previous"/>">
				</span>
				<span class="buttonBegin">
					<input type="submit" class="button" id="submit_download" value="<spring:message code="page.formulaire.web.message.download"/>">
				</span>
			</div>
		</form:form>
		<br><br>
	</div>
</div>
<jsp:include page="footer.jsp" />