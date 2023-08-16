<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>

<sec:authentication property="principal.preferences.dateFormat.labelDisplay" var="dateFormat"/>
<c:set var="legalStatusOtherConstant" value="<%=Constants.PARAM_TYPE_LEGAL_STATUS_OTHER%>"	scope="request"/>

<spring:message code="page.formulaire.web.message.address" var="placeHolderAddress" />
<spring:message code="page.formulaire.web.message.ZipCode" var="placeHolderZipCode" />
<spring:message code="page.formulaire.web.message.city" var="placeHolderCity" />
<spring:message code="page.formulaire.web.message.country.placeHolder" var="placeHolderCountry" />
<spring:message code="page.formulaire.web.message.fieldsAddressesRequired" var="fieldsAddressesRequired" />

<jsp:include page="include.jsp" />
<script src="../js/formulaire.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initForm1("${fieldsAddressesRequired}", "${legalStatusOtherConstant}", "${formulaireDCOForm.error}");
	});
</script>

<div id="content">
	<jsp:include page="error.jsp" />
	<c:set var="saveFormPath" value="saveForm1AndRedirect"	scope="request"/>
	<jsp:include page="breadcrumbs.jsp" />
	<p>
		<spring:message code="page.formulaire.web.message.explanation.form1" />
	</p>
	<div id="blocContainer">
		<form:form method="post" action="saveForm1" commandName="formulaireDCOForm" id="mainForm" onsubmit="return checkRequiredFields(this.id)">
			<div class="content_field_block">
			
				<div class="hr_dotted"></div>
				<label class="content_label" for="nameField">
					<spring:message code="page.formulaire.web.message.nameField" />
				</label>
				<div class="content_field">${formulaireDCOForm.entity.label}</div>
			
				<div class="hr_dotted"></div>
				<label class="content_label" for="nameField">
					<spring:message code="page.formulaire.web.message.countryField" />
				</label>
				<div class="content_field">${formulaireDCOForm.entity.country.getDisplayCountry(pageContext.response.locale)}</div>
				
				<div class="hr_dotted"></div>
				<div id="legalStatusFields" class="content_field requiredSelect">
					<label class="content_label" for="legalStatus">
						<span class="warning">
							<spring:message code="page.asterisk" />
						</span>
						<spring:message code="page.formulaire.web.message.legalStatus" />
					</label>
					<c:choose>
						<c:when test="${fn:length(formulaireDCOForm.mapLegalStatus[formulaireDCOForm.entity.country.country]) == 1}"> 
							<form:select autocomplete="on" disable="true" name="legalStatus" path="entity.legalStatus.id" id="legalStatus" class="content_field">
								<c:forEach items="${formulaireDCOForm.mapLegalStatus[formulaireDCOForm.entity.country.country]}" var="entry">
									<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
								</c:forEach>
							</form:select>
						</c:when>
						<c:otherwise>
							<form:select autocomplete="on" name="legalStatus" path="entity.legalStatus.id" id="legalStatus" class="content_field">
								<form:option value="" id="legalStatusOtherEmptyOption">
									<spring:message code="page.formulaire.web.message.dropdownListeStatutLegal" />
								</form:option>
								<c:forEach items="${formulaireDCOForm.mapLegalStatus[formulaireDCOForm.entity.country.country]}" var="entry">
									<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
								</c:forEach> 
							</form:select>
						</c:otherwise>
					</c:choose>
					<div id="requiredImage"></div>
				</div>
				
				<div class="hr_dotted" id="legalStatusOtherSpace"></div>
				<div class="content_field requiredText" id="legalStatusOtherDiv">
					<label class="content_label" for="legalStatusOther">
						<span class="warning">
							<spring:message code="page.asterisk" />
						</span>
						<span id="legalStatusOtherSpan">
							${entity.legalStatus.entry}
						</span>
					</label>
					<div class="content_field">
						<form:input class="content_field box_width"  type="text" name="legalStatusOther" path="entity.legalStatusOther" id="legalStatusOther" maxlength="50" ></form:input>
					</div>
					<div id="requiredImage"></div>
				</div>
				
				<div class="hr_dotted"></div>
				<div id="commercialRegisterFields" class="content_field requiredText">
					<label class="content_label" for="commercialRegister">
						<span class="warning">
							<spring:message code="page.asterisk" />
						</span> 
						<spring:message code="page.formulaire.web.message.commercialRegister" />
					</label>
					<div class="content_field">
						<form:input class="content_field box_width" type="text" autocomplete="off" name="commercialRegister" path="entity.commercialRegister" id="commercialRegister" maxlength="100" ></form:input>
					</div>
					<div id="requiredImage"></div>
				</div>
				
				<div class="hr_dotted"></div>
				<div id="taxInformationFields" class="content_field requiredText">
					<label class="content_label" for="taxInformation">
						<span class="warning">
							<spring:message code="page.asterisk" />
						</span>
						<spring:message code="page.formulaire.web.message.taxInformation" />
					</label>
					<form:input class="content_field box_width" type="text" autocomplete="off" name="taxInformation" path="entity.taxInformation" id="taxInformation" maxlength="100" ></form:input>
					<div id="requiredImage"></div>
				</div>
				
				<div class="hr_dotted"></div>
				<div id="countryFields" class="content_field requiredSelect">
					<label class="content_label" for="country">
						<span class="warning">
							<spring:message code="page.asterisk" />
						</span> <spring:message code="page.formulaire.web.message.registrationCountry" />
					</label>
					<div class="content_field">
						<form:select autocomplete="on" name="country" path="entity.registrationCountry" id="country" class="content_field">
							<form:option value="">
								<spring:message code="page.formulaire.web.message.dropdownListCountry" />
							</form:option>
							<c:forEach items="${formulaireController.countryList}" var="locale">
								<form:option value="${locale.country}">${locale.getDisplayCountry(pageContext.response.locale)}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<div id="requiredImage"></div>
				</div>
				<div class="hr_dotted"></div>
	
				<div id="adresseSiege1Fields" class="content_field requiredText">
					<label class="content_label" for="adresseSiege1" id="fieldsAddressesRequired">
						<span class="warning">
							<spring:message code="page.asterisk" />
						</span> 
						<spring:message	code="page.formulaire.web.message.adressField" />
					</label>
					<form:input type="text" autocomplete="off" name="adresseSiege1" class="content_field inputAddress box_width" placeholder="${placeHolderAddress}" size="39" path="entity.address.fieldOne" id="adresseSiege1" maxlength="38" onkeyup="javascript:copyAddressField(1);" ></form:input>
					<div id="requiredImage"></div>
				</div>
				<div class="hr_dotted"></div>
	
				<div id="adresseSiege2Fields" class="content_field requiredText">
					<label class="content_label" for="adresseSiege2">&nbsp;</label>
					<form:input type="text" autocomplete="off" name="adresseSiege2" class="content_field inputAddress box_width" placeholder="${placeHolderZipCode}" size="39" path="entity.address.fieldTwo" id="adresseSiege2" maxlength="38" onkeyup="javascript:copyAddressField(2);" ></form:input>
					<div id="requiredImage"></div>
				</div>
				<div class="hr_dotted"></div>
	
				<div id="adresseSiege3Fields" class="content_field requiredText">
					<label class="content_label" for="adresseSiege3">&nbsp;</label>
					<form:input type="text" autocomplete="off" name="adresseSiege3" class="content_field inputAddress box_width" placeholder="${placeHolderCity}" size="39" path="entity.address.fieldThree" id="adresseSiege3" maxlength="38" onkeyup="javascript:copyAddressField(3);" ></form:input>
					<div id="requiredImage"></div>
				</div>
				<div class="hr_dotted"></div>
	
				<div id="adresseSiege4Fields" class="content_field requiredText">
					<label class="content_label" for="adresseSiege4">&nbsp;</label>
					<form:input type="text" autocomplete="off" name="adresseSiege4" class="content_field inputAddress box_width" placeholder="${placeHolderCountry}" size="39" path="entity.address.fieldFour" id="adresseSiege4" maxlength="38" onkeyup="javascript:copyAddressField(4);" ></form:input>
					<div id="requiredImage"></div>
				</div>
				<div class="hr_dotted"></div>
	
				<div class="content_field">
					<label class="content_label">&nbsp;</label>
					<form:input type="text" autocomplete="off" name="adresseSiege5" size="39" class="content_field inputAddress box_width" path="entity.address.fieldFive" id="adresseSiege5" maxlength="38" onkeyup="javascript:copyAddressField(5);" ></form:input>
				</div>
				<div class="hr_dotted"></div>
	
				<div class="content_field">
					<label class="content_label">&nbsp;</label>
					<form:input type="text" autocomplete="off" name="adresseSiege6" size="39" class="content_field inputAddress box_width" path="entity.address.fieldSix" id="adresseSiege6" maxlength="38" onkeyup="javascript:copyAddressField(6);" ></form:input>
				</div>
				<div class="hr_dotted"></div>
	
				<div class="content_field">
					<label class="content_label">&nbsp;</label>
					<form:input type="text" autocomplete="off" name="adresseSiege7" size="39" class="content_field inputAddress box_width" path="entity.address.fieldSeven" id="adresseSiege7" maxlength="38" onkeyup="javascript:copyAddressField(7);" ></form:input>
				</div>
				<div class="hr_dotted"></div>
	
				<div class="hr_dotted"></div>
				<div class="hr_dotted"></div>
				 
				<label class="content_label" for="reference">
					<spring:message code="page.formulaire.web.message.copyAddress" />
				</label>
				<div class="content_field">
					<form:checkbox id="check" name="subsidiary" onClick="copyPostalAddress();" path="entity.sameAddress" value="true"/>
					<spring:message code="page.formulaire.web.message.yes" />
				</div>
				<div class="hr_dotted"></div>
					
				<div id="postalAddress">
					<div id="adressePostal1Fields" class="content_field requiredText">
						<label class="content_label" for="adressePostal1" id="fieldsPostalAddressesRequired">
							<span class="warning">
								<spring:message code="page.asterisk" />
							</span> 
							<spring:message	code="page.formulaire.web.message.adressPostalField" />
						</label>
						<form:input type="text" autocomplete="off" name="adressePostal1" class="content_field inputAddress box_width" size="39" path="entity.addressMailing.fieldOne" placeholder="${placeHolderAddress}" id="adressePostal1" maxlength="38" ></form:input>
						<div id="requiredImage"></div>
					</div>
					<div class="hr_dotted"></div>
	
					<div id="adressePostal2Fields" class="content_field requiredText">
						<label class="content_label" for="adressePostal2">&nbsp; </label>
						<form:input type="text" autocomplete="off" name="adressePostal2" class="content_field inputAddress box_width" size="39" path="entity.addressMailing.fieldTwo" placeholder="${placeHolderZipCode}" id="adressePostal2" maxlength="38" ></form:input>
						<div id="requiredImage"></div>
					</div>
					<div class="hr_dotted"></div>
	
					<div id="adressePostal3Fields" class="content_field requiredText">
						<label class="content_label" for="adressePostal3">&nbsp; </label>
						<form:input type="text" autocomplete="off" name="adressePostal3" class="content_field inputAddress box_width" size="39" path="entity.addressMailing.fieldThree" placeholder="${placeHolderCity}" id="adressePostal3" maxlength="38" ></form:input>
						<div id="requiredImage"></div>
					</div>
					<div class="hr_dotted"></div>
	
					<div id="adressePostal4Fields" class="content_field requiredText">
						<label class="content_label" for="adressePostal4">&nbsp; </label>
						<form:input type="text" autocomplete="off" name="adressePostal4" class="content_field inputAddress box_width" size="39" path="entity.addressMailing.fieldFour" placeholder="${placeHolderCountry}" id="adressePostal4" maxlength="38" ></form:input>
						<div id="requiredImage"></div>
					</div>
					<div class="hr_dotted"></div>
					
					<div class="content_field">
						<label class="content_label">&nbsp;</label>
						<form:input type="text" autocomplete="off" name="adressePostal5" size="39" class="content_field inputAddress box_width" path="entity.addressMailing.fieldFive" id="adressePostal5" maxlength="38" ></form:input>
					</div>
					<div class="hr_dotted"></div>
	
					<div class="content_field">
						<label class="content_label">&nbsp;</label>
						<form:input type="text" autocomplete="off" name="adressePostal6" size="39" class="content_field inputAddress box_width" path="entity.addressMailing.fieldSix" id="adressePostal6" maxlength="38" ></form:input>
					</div>
					<div class="hr_dotted"></div>
	
					<div class="content_field">
						<label class="content_label">&nbsp;</label>
						<form:input type="text" autocomplete="off" name="adressePostal7" size="39" class="content_field inputAddress box_width" path="entity.addressMailing.fieldSeven" id="adressePostal7" maxlength="38" ></form:input>
					</div>
					<div class="hr_dotted"></div>
				</div>				
					
				<div class="hr_dotted"></div>
				<div class="buttonOnRight">
					<span class="buttonBegin">
						<input type="submit" name="submitForm1" id="submit_etape1" class="button" value="<spring:message code="page.formulaire.web.message.save" />">
					</span>
					<span class="buttonBegin">
						<input type="submit" name="form1ToNext" id="submit_etape1_next" class="button" value="<spring:message code="page.formulaire.web.message.next"/>" >
					</span>
				</div>
				<div class="hr_dotted"></div>
			</div>
		</form:form>
	</div>
</div>
<jsp:include page="footer.jsp" />