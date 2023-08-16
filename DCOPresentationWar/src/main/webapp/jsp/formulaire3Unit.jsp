<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="page.formulaire.web.message.address" var="placeHolderAddress" />
<spring:message code="page.formulaire.web.message.ZipCode" var="placeHolderZipCode" />
<spring:message code="page.formulaire.web.message.city" var="placeHolderCity" />
<spring:message code="page.formulaire.web.message.country.placeHolder" var="placeHolderCountry" />

<c:if test="${statusNewAccount == 1}">
	<div class="hr_dotted"></div>
	<h2 class="country_section">
		${span} ${reference}
	</h2>
</c:if>
<h4 class="clicable bnpBanner">${span} ${reference}</h4>

<div class="inner" id="account${loopIndex}">
		<div class="content_field_block">
		
			<div class="buttonOnRight">

						<input class="button" type="button" id="deleteAccount${accountId}" 
							onclick="javascript:deleteAccount(${accountId});" value="<spring:message code="page.delete" />"/>
			</div>
			
			<BR><BR><BR>
			<c:if test="${statusNewAccount == 1}">
				<div id="openingCountryAccount${loopIndex}Fields" class="content_field requiredSelect">
					<label class="content_label" for="paysOuvertureCompte">
						<span class="warning">
							<spring:message code="page.asterisk" />
						</span>
						<spring:message	code="page.formulaire.web.message.paysOuvertureCompte" />
					</label>
					<div class="content_field">
					<form:select autocomplete="on" name="paysOuvertureCompte" path="${selectedCountriesPath}" id="paysOuvertureCompte${loopIndex}" class="content_field">
						<form:option value="" style="display:none">
							<spring:message	code="page.formulaire.web.message.dropdownlistePaysOuvertureCompte" />
						</form:option>		
						<c:forEach items="${formulaireDCOForm.accountCountriesSort}" var="country">		
								<form:option value="${country.label2}">${country.label}</form:option>		
						</c:forEach>		
					</form:select>
					</div>
					<div id="requiredImage"></div>
				</div>
			</c:if>
			<div class="hr_dotted"></div>
			
			<div id="referenceAccount${loopIndex}Fields" class="content_field" style="display: none;">
				<label class="content_label" for="reference" style="display: none;"><span
				class="warning"><spring:message code="page.asterisk" /></span><spring:message
						code="page.formulaire.web.message.reference" /></label>
	
				<div class="content_field" style="display: none;">
					<form:input class="content_field box_width" type="text" autocomplete="off" 
						name="reference" path="${accountPath}.reference" 
						id="reference${loopIndex}" maxlength="45" readonly="true"></form:input>
					<form:hidden path="${accountPath}.id" name="id" id="id${loopIndex}"/>
				</div>
			</div>
			<div class="hr_dotted" style="display: none;"></div>
			
			<c:if test="${statusNewAccount == 1}">
				<div class="content_field">
				<label class="content_label" for="reference"><spring:message
						code="page.formulaire.web.message.filiale" /></label> 
				<input type="checkbox" id="check${loopIndex}" name="subsidiary" onClick="resetSubsidiaryFields(${loopIndex});" value=""></input>
				<spring:message code="page.formulaire.web.message.yes" />
				<script type="text/javascript">
					$(document).ready(function(){
						displaySub("${loopIndex}");
					});
				</script>
				<%-- path="${accountPath}.Subsidiary" --%>
				</div>
				<div class="hr_dotted"></div>
			
				<div id="subsidiary${loopIndex}" style="display: none;">
				
				<div id="branchNameSubsidiaryAccount${loopIndex}Fields"
					class="content_field requiredText">
					<label class="content_label"> <span class="warning"><spring:message
								code="page.asterisk" /></span> <spring:message
							code="page.formulaire.web.message.branchNameSubsidiary" />
					</label>
					<div class="content_field" id="required">
						<form:input type="text" autocomplete="off" 
							name="branchName"
							path="${accountPath}.branchName"
							id="branchName${loopIndex}"
							maxlength="38" size="39"
							class="content_field box_width"></form:input>
					</div>
					<div id="requiredImage"></div>
				</div>
				<div class="hr_dotted"></div>
					
					<div id="addressSubsidiary1Account${loopIndex}Fields" class="content_field requiredText">
						<label class="content_label" for="addressSubsidiary1"><spring:message
								code="page.formulaire.web.message.addressSubsidiaryField" /></label>
						<div class="content_field" style="width: 100px; float: left;"></div>
						<div class="hr_dotted"></div>
						<label class="content_label">
							<span class="warning"><spring:message code="page.asterisk" /></span>
						</label>
						<div class="content_field" id="required">
						<form:input type="text" autocomplete="off" 
							name="addressSubsidiary1"
							path="${accountPath}.address.fieldOne"
							id="addressSubsidiary1${loopIndex}" 
							placeholder="${placeHolderAddress}"
							maxlength="38"  size="39"
							class="content_field box_width"></form:input>
						</div>
						<div id="requiredImage"></div>
					</div>
					<div class="hr_dotted"></div>
					<div id="addressSubsidiary2Account${loopIndex}Fields" class="content_field requiredText">
						<label class="content_label">
							<span class="warning"><spring:message code="page.asterisk" /></span>
						</label>
						<div class="content_field" id="required">
						<form:input type="text" autocomplete="off" 
							name="addressSubsidiary2"
							path="${accountPath}.address.fieldTwo"
							id="addressSubsidiary2${loopIndex}" 
							placeholder="${placeHolderZipCode}"
							maxlength="38"  size="39"
							class="content_field box_width"></form:input>
						</div>
						<div id="requiredImage"></div>
					</div>
					<div class="hr_dotted"></div>
					<div id="addressSubsidiary3Account${loopIndex}Fields" class="content_field requiredText">
						<label class="content_label">
							<span class="warning"><spring:message code="page.asterisk" /></span>
						</label>
						<div class="content_field" id="required">
						<form:input type="text" autocomplete="off" 
							name="addressSubsidiary3"
							path="${accountPath}.address.fieldThree"
							id="addressSubsidiary3${loopIndex}" 
							placeholder="${placeHolderCity}"
							maxlength="38"  size="39"
							class="content_field box_width"></form:input>
						</div>
						<div id="requiredImage"></div>
					</div>								
					<div class="hr_dotted"></div>
					<div id="addressSubsidiary4Account${loopIndex}Fields" class="content_field requiredText">
						<label class="content_label">
							<span class="warning"><spring:message code="page.asterisk" /></span>
						</label>
						<div class="content_field" id="required">
						<form:input type="text" autocomplete="off" 
							name="addressSubsidiary4"
							path="${accountPath}.address.fieldFour"
							id="addressSubsidiary4${loopIndex}"
							placeholder="${placeHolderCountry}" 
							maxlength="38"  size="39"
							class="content_field box_width"></form:input>
						</div>
						<div id="requiredImage"></div>
					</div>
					<div class="hr_dotted"></div>
					<label class="content_label">&nbsp;</label>
					<div class="content_field">
					<form:input type="text" autocomplete="off" 
						name="addressSubsidiary5"
						path="${accountPath}.address.fieldFive"
						id="addressSubsidiary5${loopIndex}" 
						maxlength="38" size="39"
						class="content_field box_width"></form:input>
					</div>
					<div class="hr_dotted"></div>
					<label class="content_label">&nbsp;</label>
					<div class="content_field">
					<form:input type="text" autocomplete="off"
						name="addressSubsidiary6"
						path="${accountPath}.address.fieldSix"
						id="addressSubsidiary6${loopIndex}" 
						maxlength="38" size="39"
						class="content_field box_width"></form:input>
					</div>
					<div class="hr_dotted"></div>
					<label class="content_label">&nbsp;</label>
					<div class="content_field">
					<form:input type="text" autocomplete="off" 
						name="addressSubsidiary7"
						path="${accountPath}.address.fieldSeven"
						id="addressSubsidiary7${loopIndex}" 
						maxlength="38" size="39"
						class="content_field box_width"></form:input>
					</div>
					<div class="hr_dotted"></div>
					<div id="commercialRegisterAccount${loopIndex}Fields" class="content_field requiredText">
						<label class="content_label" for="commercialRegister">
							<span class="warning"><spring:message code="page.asterisk" /></span>
							<spring:message	code="page.formulaire.web.message.branchCommercialRegister" />
						</label>
						<div class="content_field" id="required">
						<form:input class="content_field box_width" type="text" autocomplete="off" 
							name="commercialRegister"
							path="${accountPath}.commercialRegister" 
							id="commercialRegister${loopIndex}" maxlength="100"></form:input>
						</div>
						<div id="requiredImage"></div>
					</div>

					<div class="hr_dotted"></div>
					<div id="vatNumberSubsidiaryAccount${loopIndex}Fields" class="content_field requiredText">
						<label class="content_label">
							<span class="warning"><spring:message code="page.asterisk" /></span>
							<spring:message code="page.formulaire.web.message.vatNumberSubsidiary" />
						</label>
						<div class="content_field" id="required">
						<form:input type="text" autocomplete="off" 
							name="vatNumber"
							path="${accountPath}.vatNumber"
							id="vatNumber${loopIndex}" 
							maxlength="38"  size="39"
							class="content_field box_width"></form:input>
						</div>
						<div id="requiredImage"></div>
					</div>
					<form:input  class="content_field box_width" type="hidden" autocomplete="off" 
							id="country${loopIndex}" path="${accountPath}.country" name="country"></form:input>	
					<div class="hr_dotted"></div>
					<br/>
				</div>
							
				<script type="text/javascript">
					checkSubsidiaryAccount("${loopIndex}");
				</script>
			</c:if>

			<div id="typeAccountAccount${loopIndex}Fields" class="content_field requiredSelect">
				<label class="content_label" for="typeCompte">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message	code="page.formulaire.web.message.typeCompte" />
				</label>
				<div class="content_field">
				<c:choose>
					<c:when test="${fn:length(formulaireDCOForm.mapTypeAccount[countryAccount])==1}">
						<form:select  autocomplete="on"
							disabled="true"
							name="typeCompte"
							path="${accountPath}.typeAccount.id"
							id="typeCompte${loopIndex}" class="content_field">
							<form:options items="${formulaireDCOForm.mapTypeAccount[countryAccount]}" itemLabel="value"
								itemValue="id" />
						</form:select>
					</c:when>
					<c:otherwise>
						<form:select  autocomplete="on"
							name="typeCompte"
							path="${accountPath}.typeAccount.id"
							id="typeCompte${loopIndex}" class="content_field">
							<form:option style="display:none" value="">
								<spring:message
									code="page.formulaire.web.message.dropdownListeTypeCompte" />
							</form:option>
							<form:options items="${formulaireDCOForm.mapTypeAccount[countryAccount]}" itemLabel="value"
								itemValue="id" />
						</form:select>
					</c:otherwise>
				</c:choose>
				</div>
				<div id="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
			
			<div id="deviseAccountAccount${loopIndex}Fields" class="content_field requiredSelect">
				<label class="content_label" for="deviseCompte">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message	code="page.formulaire.web.message.deviseCompte" />
				</label>
				<div class="content_field">
				<form:select  autocomplete="on"
					name="deviseCompte"
					path="${accountPath}.currency.id"
					id="deviseCompte${loopIndex}" class="content_field">
	
					<form:option value="" style="display:none">
						<spring:message
							code="page.formulaire.web.message.dropdownListeDeviseCompte" />
					</form:option>
					<form:options items="${formulaireDCOForm.mapCurrency[countryAccount]}" itemLabel="value"
						itemValue="id" />
				</form:select>
				</div>
				<div id="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
			<c:if test="${statusNewAccount == 1}">
				<div id="periodicityDrawbackAccount${loopIndex}Fields" class="content_field requiredSelect">
					<label class="content_label" for="periodiciteReleve">
						<span class="warning"><spring:message code="page.asterisk" /></span>
						<spring:message	code="page.formulaire.web.message.periodiciteReleve" />
					</label>
					<div class="content_field">
					<form:select autocomplete="on" name="periodiciteReleve"
						path="${accountPath}.periodicity.id"
						id="periodiciteReleve${loopIndex}" class="content_field">
		
						<form:option value="" style="display:none">
							<spring:message
								code="page.formulaire.web.message.dropdownListePeriodiciteReleve" />
						</form:option>
						<form:options items="${formulaireDCOForm.mapPeriodicity[countryAccount]}" itemLabel="value"
							itemValue="id" />
					</form:select>
					</div>
					<div id="requiredImage"></div>
				</div>
			</c:if>

			<div class="hr_dotted"></div>
			<c:if test="${enableComLang}">
				<div id="communicationLanguage${loopIndex}Fields" class="content_field requiredSelect">
					<label class="content_label" for="communicationLanguage">
						<span class="warning"><spring:message code="page.asterisk" /></span>
						<spring:message	code="page.formulaire.web.message.communicationLanguage" />
					</label>
					<div class="content_field">
					<form:select autocomplete="on" name="communicationLanguage"
						path="${accountPath}.communicationLanguage.id" 
						id="communicationLanguage${loopIndex}" class="content_field">
						
						<form:option value="" style="display:none">
							<spring:message
								code="page.formulaire.web.message.dropdownListeCommunicationLanguage" />
						</form:option>
						<form:options items="${formulaireDCOForm.mapCountryComLangs[countryAccount]}" itemLabel="login"
							itemValue="id" />
					</form:select>	
					<div id="requiredImage"></div>
					</div>
				</div>
			</c:if>						
	</div>
</div>
<script>
	getAllParamFuncList("paysOuvertureCompte${loopIndex}");
</script>