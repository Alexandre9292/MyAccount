<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<spring:message code="page.client.data.expTxt" var="expTxt" />
<spring:message code="page.client.data.cllpsTxt" var="cllpsTxt" />

<jsp:include page="include.jsp" />

<input type="hidden" id="expTxt" value="${expTxt}"/>
<input type="hidden" id="cllpsTxt" value="${cllpsTxt}"/>

<link rel="stylesheet" href="../css/accordion.css"></link>
<script src="../js/formulaire.js"></script>
<script type="text/javascript" src="../js/jquery.nestedAccordion.js"></script>
<script type="text/javascript" src="../js/expand.js"></script>
<script type="text/javascript" src="../js/accordionDCO.js"></script>
<spring:message code="page.formulaire.web.message.newAccount" var="newAccountRef"/>
<spring:message code="page.formulaire.web.message.address" var="placeHolderAddress" />
<spring:message code="page.formulaire.web.message.ZipCode" var="placeHolderZipCode" />
<spring:message code="page.formulaire.web.message.city" var="placeHolderCity" />
<spring:message code="page.formulaire.web.message.country.placeHolder" var="placeHolderCountry" />

<div id="content">
	<c:set var="statusNewAccount" value="0" scope="request"></c:set>
	<c:set var="flagNewAccount" value="0" scope="request"></c:set>
	<jsp:include page="error.jsp" />
	<c:set var="saveFormPath" value="saveForm3AndRedirect" scope="request"/>
	<jsp:include page="breadcrumbs.jsp" />
	<p>
		<spring:message code="page.formulaire.web.message.explanation.form3"/>
	</p>
	<div id="blocContainer">
		<form:form method="post" action="saveForm3" onsubmit="return checkRequiredFieldsAccountPage()" commandName="formulaireDCOForm" id="mainForm">
			<div id="main">
				<div class="accordion" id="acc2">
					<div id="existingAccounts">
						<c:forEach items="${formulaireDCOForm.mapAccount}" var="node" varStatus="nstatus">
							<div class="hr_dotted"></div>
							<h2 class="country_section">
								${formulaireDCOForm.listAccountKey[nstatus.index].label}
								<input type="hidden" id="countryData${nstatus.index}"  name="${formulaireDCOForm.listAccountKey[nstatus.index].label}" value="${formulaireDCOForm.listAccountKey[nstatus.index].label2}" disabled></input>							
							</h2>
							<h4 class="clicable bnpBanner">
								<spring:message code="page.formulaire.web.message.accounts.form.country.header"/>${formulaireDCOForm.listAccountKey[nstatus.index].label}
							</h4>
							<div class="inner">
								<div class="content_field_block">							
									<div id="periodicityDrawbackCountry${nstatus.index}Fields" class="content_field requiredSelect">
										<label class="content_label" for="periodiciteReleve">
											<span class="warning"><spring:message code="page.asterisk" /></span>
											<spring:message	code="page.formulaire.web.message.periodiciteReleve" />
										</label>
										<div class="content_field">
										<form:select autocomplete="on" name="periodiciteReleve" path="listPeriodicity[${nstatus.index}].id" id="periodicite${nstatus.index}" class="content_field">					
											<form:option value="" style="display:none"><spring:message code="page.formulaire.web.message.dropdownListePeriodiciteReleve"/></form:option>
											<form:options items="${formulaireDCOForm.mapPeriodicity[node.key]}" itemLabel="value" itemValue="id" />
										</form:select>
										</div>
										<div id="requiredImage"></div>
									</div>
									<div class="hr_dotted"></div>	
									<div class="content_field">
										<label class="content_label" for="reference"><spring:message code="page.formulaire.web.message.filiale" /></label>
										<input type="checkbox" id="check${nstatus.index}" name="subsidiary" onClick="resetSubsidiaryFields(${nstatus.index}, true);" value=""></input>
										<spring:message code="page.formulaire.web.message.yes" />
										<script type="text/javascript">
											$(document).ready(function(){
												displaySub("${nstatus.index}");
											});
										</script>
										<%--  path="listSubsidiary[${nstatus.index}]" --%>
									</div>
									<div class="helperRedirect" onclick="javascript:openHelper(4);">&nbsp;</div>
									<div class="hr_dotted"></div>
									<div id="subsidiary${nstatus.index}" style="display: none;">						
										<div id="branchNameSubsidiaryAccount${nstatus.index}Fields" class="content_field requiredText">
											<label class="content_label">
												<span class="warning"><spring:message code="page.asterisk" /></span>
												<spring:message code="page.formulaire.web.message.branchNameSubsidiary" />
											</label>
											<div class="content_field" id="required">
												<form:input type="text" autocomplete="off" name="branchName" path="listBranchName[${nstatus.index}]" id="branchName${nstatus.index}" maxlength="38"  size="39" class="content_field box_width" ></form:input>
											</div>
											<div id="requiredImage"></div>
										</div>
										<div class="hr_dotted"></div>
										<div id="addressSubsidiary1Account${nstatus.index}Fields" class="content_field requiredText">
											<label class="content_label" for="addressSubsidiary1">
												<spring:message code="page.formulaire.web.message.addressSubsidiaryField" />
											</label>
											<div class="content_field" style="width: 100px; float: left;"></div>
											<div class="hr_dotted"></div>
											<label class="content_label">
												<span class="warning"><spring:message code="page.asterisk" /></span>
											</label>
											<div class="content_field" id="required">
												<form:input type="text" autocomplete="off" name="addressSubsidiary1" 
												path="listAddress[${nstatus.index}].fieldOne" id="addressSubsidiary1${nstatus.index}" 
												maxlength="38"  size="39" 
												placeholder="${placeHolderAddress}"
												class="content_field box_width"></form:input>
											</div>
											<div id="requiredImage"></div>
										</div>
										<div class="hr_dotted"></div>
										<div id="addressSubsidiary2Account${nstatus.index}Fields" class="content_field requiredText">
											<label class="content_label">
												<span class="warning"><spring:message code="page.asterisk" /></span>
											</label>
											<div class="content_field" id="required">
												<form:input type="text" autocomplete="off" name="addressSubsidiary2" 
												path="listAddress[${nstatus.index}].fieldTwo" id="addressSubsidiary2${nstatus.index}" 
												maxlength="38"  size="39" 
												placeholder="${placeHolderZipCode}"
												class="content_field box_width"></form:input>
											</div>
											<div id="requiredImage"></div>
										</div>
										<div class="hr_dotted"></div>
										<div id="addressSubsidiary3Account${nstatus.index}Fields" class="content_field requiredText">
											<label class="content_label">
												<span class="warning"><spring:message code="page.asterisk" /></span>
											</label>
											<div class="content_field" id="required">
												<form:input type="text" autocomplete="off" name="addressSubsidiary3" 
												path="listAddress[${nstatus.index}].fieldThree" id="addressSubsidiary3${nstatus.index}" 
												maxlength="38"  size="39" 
												placeholder="${placeHolderCity}"
												class="content_field box_width"></form:input>
											</div>
											<div id="requiredImage"></div>
										</div>								
										<div class="hr_dotted"></div>
										<div id="addressSubsidiary4Account${nstatus.index}Fields" class="content_field requiredText">
											<label class="content_label">
												<span class="warning"><spring:message code="page.asterisk" /></span>
											</label>
											<div class="content_field" id="required">
												<form:input type="text" autocomplete="off" name="addressSubsidiary4" 
												path="listAddress[${nstatus.index}].fieldFour" id="addressSubsidiary4${nstatus.index}" 
												maxlength="38"  size="39" 
												placeholder="${placeHolderCountry}"
												class="content_field box_width"></form:input>
											</div>
											<div id="requiredImage"></div>
										</div>
										<div class="hr_dotted"></div>
										<label class="content_label">&nbsp;</label>
										<div class="content_field">
											<form:input type="text" autocomplete="off" name="addressSubsidiary5" path="listAddress[${nstatus.index}].fieldFive" id="addressSubsidiary5${nstatus.index}" maxlength="38" size="39" class="content_field box_width" ></form:input>
										</div>
										<div class="hr_dotted"></div>
										<label class="content_label">&nbsp;</label>
										<div class="content_field">
											<form:input type="text" autocomplete="off" name="addressSubsidiary6" path="listAddress[${nstatus.index}].fieldSix" id="addressSubsidiary6${nstatus.index}" maxlength="38" size="39" class="content_field box_width" ></form:input>
										</div>
										<div class="hr_dotted"></div>
										<label class="content_label">&nbsp;</label>
										<div class="content_field">
											<form:input type="text" autocomplete="off" name="addressSubsidiary7" path="listAddress[${nstatus.index}].fieldSeven" id="addressSubsidiary7${nstatus.index}" maxlength="38" size="39" class="content_field box_width" ></form:input>
										</div>
										<div class="hr_dotted"></div>
										<div id="commercialRegisterAccount${nstatus.index}Fields" class="content_field requiredText">
											<label class="content_label" for="commercialRegister">
												<span class="warning"><spring:message code="page.asterisk" /></span>
												<spring:message	code="page.formulaire.web.message.branchCommercialRegister" />
											</label>
											<div class="content_field" id="required">
												<form:input class="content_field box_width" type="text" autocomplete="off" name="commercialRegister" path="listCommercialRegister[${nstatus.index}]" id="commercialRegister${nstatus.index}" maxlength="100" ></form:input>
											</div>
											<div id="requiredImage"></div>
										</div>
										<div class="hr_dotted"></div>
										<div id="vatNumberSubsidiaryAccount${nstatus.index}Fields" class="content_field requiredText">
											<label class="content_label">
												<span class="warning"><spring:message code="page.asterisk" /></span>
												<spring:message code="page.formulaire.web.message.vatNumberSubsidiary" />
											</label>
											<div class="content_field" id="required">
												<form:input type="text" autocomplete="off" name="vatNumber" path="listVatNumber[${nstatus.index}]" id="vatNumber${nstatus.index}" maxlength="38"  size="39" class="content_field box_width" ></form:input>
											</div>
											<div id="requiredImage"></div>
										</div>
										<form:input  class="content_field box_width" type="hidden" autocomplete="off" id="country${nstatus.index}" path="listCountry[${nstatus.index}]" name="country"></form:input>	
										<div class="hr_dotted"></div>
										<br/>
									</div>
								</div>
							</div>								
							<script type="text/javascript">
								checkSubsidiaryAccount("${nstatus.index}", true);
							</script>
							<c:forEach items="${node.value}" var="listId" >
								<c:set var="account" value="${formulaireDCOForm.accounts[listId]}" scope="request"/>
								<c:set var="loopIndex" value="${listId}" scope="request"/>
								<c:set var="accountId" value="${account.id}" scope="request"/>
								<c:set var="accountPath" value="accounts[${loopIndex}]" scope="request"/>
								<c:set var="statementTypePath" value="accountStatementTypesBean.map[${account.id}]" scope="request"/>
								<c:set var="selectedCountriesPath" value="selectedCountries[${loopIndex}]" scope="request"/>
								<c:set var="reference" value="${account.reference}" scope="request"/>
								<c:set var="countryAccount" value="${account.countryAccount.locale.country}" scope="request"/> 
								<c:set var="enableComLang" value="${account.countryAccount.com_lang_enabled}" scope="request"/>
								<jsp:include page="formulaire3Unit.jsp" />				
							</c:forEach>
							<c:if test="${flagNewAccount == 0 && formulaireDCOForm.newAccount.countryAccount.locale.country == node.key}">
								<c:set var="loopIndex" value="-1" scope="request"/>
								<c:set var="accountId" value="-1" scope="request"/>
								<c:set var="accountPath" value="newAccount" scope="request"/>
								<c:set var="statementTypePath" value="accountStatementTypesForNewAccount" scope="request"/>
								<c:set var="selectedCountriesPath" value="selectedCountryForNewAccount" scope="request"/>
								<c:set var="reference" value="${newAccountRef}" scope="request"/>
								<c:set var="countryAccount" value="${formulaireDCOForm.newAccount.countryAccount.locale.country}" scope="request"/>
								<c:set var="enableComLang" value="${formulaireDCOForm.newAccount.countryAccount.com_lang_enabled}" scope="request"/>	
								<div id="accountToCreate" style="display:none;">
									<jsp:include page="formulaire3Unit.jsp" />
									<c:if test="${formulaireDCOForm.alert}">
										<script type="text/javascript">
											$(document).ready(function(){
											    alert("<spring:message code="page.formulaire.web.message.alert.countries" />");
											});
										</script>
									</c:if>
								</div>
								<c:set var="flagNewAccount" value="1" scope="request"></c:set>								
							</c:if>	
						</c:forEach>
					</div>
					<c:if test="${flagNewAccount == 0}">
						<c:set var="statusNewAccount" value="1" scope="request"></c:set>
						<c:set var="loopIndex" value="-1" scope="request"/>
						<c:set var="accountId" value="-1" scope="request"/>
						<c:set var="accountPath" value="newAccount" scope="request"/>
						<c:set var="statementTypePath" value="accountStatementTypesForNewAccount" scope="request"/>
						<c:set var="selectedCountriesPath" value="selectedCountryForNewAccount" scope="request"/>
						<c:set var="reference" value="${newAccountRef}" scope="request"/>
						<c:set var="countryAccount" value="${formulaireDCOForm.newAccount.countryAccount.locale.country}" scope="request"/>
						<c:set var="enableComLang" value="${formulaireDCOForm.newAccount.countryAccount.com_lang_enabled}" scope="request"/>	
						<div id="accountToCreate" style="display:none;">
							<jsp:include page="formulaire3Unit.jsp" />
							<c:if test="${formulaireDCOForm.alert}">
								<script type="text/javascript">
									$(document).ready(function(){
									    alert("<spring:message code="page.formulaire.web.message.alert.countries" />");
									});
								</script>
							</c:if>
						</div>
					</c:if>
				</div>
			</div>
			<BR>
			<div class="buttonOnRight">
				<span class="buttonBegin">
					<input type="button" value="<spring:message code="page.formulaire.web.message.previous"/>" class="button" id="submit_etape3_back">
				</span>
				<span class="buttonBegin">
					<input type="button" class="button" id="createNewAccountButton" onClick="displayNew();" value="<spring:message code="page.formulaire.web.message.createButton"/>">
				</span>
				<span class="buttonBegin">
					<input type="submit" name="submit_etape3" value="<spring:message code="page.formulaire.web.message.save" />" class="button" id="submitEtape3">
				</span>
				<span class="buttonBegin">
					<input type="submit" name="form3ToNext" value="<spring:message code="page.formulaire.web.message.next"/>" class="button" id="submit_etape3_next">
				</span>	
			</div>
			<br>
		</form:form>
	</div>
	<input type="hidden" id="createAccount0Button" value="<spring:message code="page.formulaire.web.message.createButton"/>" />
	<input type="hidden" id="removeAccount0Button" value="<spring:message code="page.formulaire.web.message.removeButton"/>" />
	<input type="hidden" id="deleteSubMessage" value="<spring:message code="page.formulaire.web.message.deleteSubMessage"/>" />
	<input type="hidden" id="deleteAccount0Message" value="<spring:message code="page.formulaire.web.message.deleteAccount0Message"/>" />
	<input type="hidden" id="deleteAccount" value="<spring:message code="page.formulaire.web.message.deleteAccount"/>" />
</div>
<script type="text/javascript">
	$(document).ready(function(){
		initForm3("${formulaireDCOForm.error}");
	});
</script>
<jsp:include page="footer.jsp" />