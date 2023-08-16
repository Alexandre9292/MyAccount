<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<sec:authentication property="principal.preferences.dateFormat.labelDisplay" var="dateFormat"/>
<spring:message code="page.formulaire.web.message.annee" var="placeHolderAnnee" />
<spring:message code="page.formulaire.web.message.mois" var="placeHolderMois" />
<spring:message code="page.formulaire.web.message.jour" var="placeHolderJour" />
<spring:message code="page.formulaire.web.message.fieldsAddressesRequired" var="fieldsAddressesRequired" />
<spring:message code="page.patternTel" var="patternTel" />
<spring:message code="page.formulaire.web.message.address" var="placeHolderAddress" />
<spring:message code="page.formulaire.web.message.ZipCode" var="placeHolderZipCode" />
<spring:message code="page.formulaire.web.message.city" var="placeHolderCity" />
<spring:message code="page.formulaire.web.message.country.placeHolder" var="placeHolderCountry" />
<jsp:include page="include.jsp" />
<script src="../js/formulaire.js"></script>

<div id="content">
	<jsp:include page="error.jsp" />
	<c:set var="saveFormPath" value="saveThirdPartyAndRedirect" scope="request"/>
	<jsp:include page="breadcrumbs.jsp" />

	<p>
		<spring:message code="page.formulaire.web.message.explanation.thirdpartyform" />
	</p>
	<div id="blocContainer">
		<form:errors path="thirdPartyDto.*" cssClass="errorblock" element="div" />
		
		<form:form method="get" action="thirdParty" commandName="thirdPartyForm" id="selectThirdPartyForm">
			<div id="thirdPartyFields" class="content_field requiredText">
			<label class="content_label" for="thirdParty">
				<span class="warning">
					<spring:message code="page.asterisk" />
				</span>
				<spring:message code="page.formulaire.web.message.thirdParty.selected" />
			</label>
			<div class="content_field">
			<form:select autocomplete="on" name="thirdParty" path="thirdPartySelect" id="thirdParty" class="content_field" onChange="exitMyAccounts = false; this.form.submit();">
				<form:option value="-1">
					<spring:message code="page.formulaire.web.message.dropdownListethirdPartyDto" />
				</form:option>
				<c:forEach items="${thirdPartyForm.thirdPartyList}" var="tp">
					<form:option value="${tp.id}">${tp.splittedNames}</form:option>
				</c:forEach>
			</form:select>
			</div>
			<div id ="requiredImage"></div>
			</div>
		</form:form>
		
		<div class="hr_dotted"></div>
	
		<form:form method="post" action="saveThirdParty" name="thirdParty" commandName="thirdPartyForm" id="mainForm">
			<div id="nameFields" class="content_field requiredText">
				<label class="content_label default_required" for="name">
					<span class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.formulaire.web.message.name" />
				</label>
				<div class="content_field">
					<form:input class="content_field box_width" type="text" autocomplete="off" name="name" path="thirdPartyDto.name" id="name" maxlength="45" ></form:input>
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
			
			<div id="firstNameFields" class="content_field requiredText">
				<label class="content_label default_required" for="firstName">
					<span class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.formulaire.web.message.firstName" />
				</label>
				<div class="content_field">
					<form:input class="content_field box_width" type="text" autocomplete="off" name="firstName" path="thirdPartyDto.firstName" id="firstName" maxlength="45" ></form:input>
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
	
			<div id="typeCorrespondantLabelFields" class="content_field requiredText">
				<label id="typeCorrespondantLabel" class="content_label default_required" for="typeCorrespondant">
					<span class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.formulaire.type.correspondant" />
				</label>
				<div class="content_field">
					<form:checkbox path="thirdPartyDto.correspondantType" id="correspondantType" value="<%=Constants.THIRD_CONTACT%>" />
					<spring:message code="page.formulaire.type.correspondant.1" />&nbsp;&nbsp;&nbsp;&nbsp;
					<form:checkbox path="thirdPartyDto.correspondantTypeTwo" id="correspondantTypeTwo" value="<%=Constants.THIRD_SIGNATORY%>" />
					<spring:message code="page.formulaire.type.correspondant.2" />&nbsp;&nbsp;&nbsp;&nbsp;
					<form:checkbox path="thirdPartyDto.correspondantTypeThree" id="correspondantTypeThree" value="<%=Constants.THIRD_LEGAL_REPRESENTATIVE%>" />
					<spring:message code="page.formulaire.type.correspondant.3" />
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
	
			<div id="positionFields" class="content_field requiredText">
				<label class="content_label sign_required" for="position">
					<span id="positionWarning" class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.formulaire.web.message.position" />
				</label>
				<div class="content_field">
					<form:input class="content_field box_width" type="text" autocomplete="off" name="position" path="thirdPartyDto.positionName" id="position" maxlength="50" ></form:input>
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
	
			<div id="birthDateFields" class="content_field requiredText">
				<label class="content_label sign_required" for="birthDate">
					<span id="birthDateWarning" class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.formulaire.web.message.birthDate" />
				</label>
				<c:if test="${dateFormat == \"dd/mm/yyyy\"}">
					<div class="content_field">
						<form:input class="content_field box_width" placeholder="${placeHolderJour}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderJour}'" type="text" size="1" style="height:20px;" autocomplete="off" name="birthDay" path="birthDay" id="birthDay" maxlength="2" onkeyup="checklength(this, birthMonth, this.value)" ></form:input>
						<span class=slash><spring:message code="page.slash" /></span>
					</div>
					<div class="content_field">
						<form:input class="content_field box_width" placeholder="${placeHolderMois}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderMois}'" type="text" size="1" style="height:20px;" autocomplete="off" name="birthMonth" path="birthMonth" id="birthMonth" maxlength="2" onkeyup="checklength(this, birthYear, this.value)" ></form:input>
						<span class=slash><spring:message code="page.slash" /></span>
					</div>
					<div class="content_field">
						<form:input class="content_field box_width" placeholder="${placeHolderAnnee}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderAnnee}'" type="text" size="2" style="height:20px;" autocomplete="off" name="birthYear" path="birthYear" id="birthYear" maxlength="4" ></form:input>
					<span class="dateFormat">( ${dateFormat} )</span>
					</div>
					<div id ="requiredImage"></div>		
				</c:if>	
				<c:if test="${dateFormat == \"mm/dd/yyyy\"}">
					<div class="content_field">
						<form:input class="content_field box_width" placeholder="${placeHolderMois}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderMois}'" type="text" size="1" style="height:20px;" autocomplete="off" name="birthMonth" path="birthMonth" id="birthMonth" maxlength="2" onkeyup="checklength(this, birthDay, this.value)" ></form:input>
						<span class=slash><spring:message code="page.slash" /></span>
					</div>
					<div class="content_field">
						<form:input class="content_field box_width" placeholder="${placeHolderJour}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderJour}'" type="text" size="1" style="height:20px;" autocomplete="off" name="birthDay" path="birthDay" id="birthDay" maxlength="2" onkeyup="checklength(this, birthYear, this.value)" ></form:input>
						<span class=slash><spring:message code="page.slash" /></span>
					</div>
					<div class="content_field">
						<form:input class="content_field box_width" placeholder="${placeHolderAnnee}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderAnnee}'" type="text" size="2" style="height:20px;" autocomplete="off" name="birthYear" path="birthYear" id="birthYear" maxlength="4" ></form:input>
					<span class="dateFormat">( ${dateFormat} )</span>
					</div>
					<div id ="requiredImage"></div>		
				</c:if>	
				<c:if test="${dateFormat == \"yyyy/mm/dd\"}">
					<div class="content_field">
						<form:input class="content_field box_width" placeholder="${placeHolderAnnee}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderAnnee}'" type="text" size="2" style="height:20px;" autocomplete="off" name="birthYear" path="birthYear" id="birthYear" maxlength="4" onkeyup="checklength(this, birthMonth, this.value)" ></form:input>
						<span class=slash><spring:message code="page.slash" /></span>
					</div>
					<div class="content_field">
						<form:input class="content_field box_width" placeholder="${placeHolderMois}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderMois}'" type="text" size="1" style="height:20px;" autocomplete="off" name="birthMonth" path="birthMonth" id="birthMonth" maxlength="2" onkeyup="checklength(this, birthDay, this.value)" ></form:input>
						<span class=slash><spring:message code="page.slash" /></span>
					</div>
					<div class="content_field">
						<form:input class="content_field box_width" placeholder="${placeHolderJour}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderJour}'" type="text" size="1" style="height:20px;" autocomplete="off" name="birthDay" path="birthDay" id="birthDay" maxlength="2" ></form:input>
						<span class="dateFormat">( ${dateFormat} )</span>
					</div>
					<div id ="requiredImage"></div>		
				</c:if>	
			</div>
			<div class="hr_dotted"></div>
	
			<div id="birthPlaceFields" class="content_field requiredText">
				<label class="content_label sign_required" for="birthPlace">
					<span id="birthPlaceWarning" class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.formulaire.web.message.birthPlace" />
				</label>
				<div class="content_field">
					<form:input class="content_field box_width" type="text" autocomplete="off" name="birthPlace" path="thirdPartyDto.birthPlace" id="birthPlace" maxlength="45" ></form:input>
				<span class="dateFormat">
					<spring:message code="page.formulaire.web.message.birthPlace.info" />
				</span>
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
	
			<div id="citizenshipListFields" class="content_field requiredText">
				<label class="content_label sign_required" for="citizenshipList">
					<span id="citizenshipListWarning" class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.formulaire.web.message.nationality" />
				</label>
				<div class="content_field">
				<form:select path="citizenshipList" id="citizenshipList" name="citizenshipList" multiple="multiple" autocomplete="on" class="content_field">
					<c:forEach items="${thirdPartyForm.nationalityList}" var="locale">
						<form:option value="${locale.country}">${locale.getDisplayCountry(pageContext.response.locale)}</form:option>
					</c:forEach>
				</form:select>
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
	
			<div id="homeAddress1Fields" class="content_field requiredText">
				<label class="content_label sign_required" for="homeAddress1" id="addressFieldsRequired">
					<span id="homeAddressWarning" class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.formulaire.web.message.addressHome" />
				</label>
				<div class="content_field">
					<form:input type="text" autocomplete="off" size="39" name="homeAddress1" path="thirdPartyDto.homeAddress.fieldOne" placeholder="${placeHolderAddress}" id="homeAddress1" maxlength="38" class="content_field inputAddress box_width" ></form:input>
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
			
			<div id="homeAddress2Fields" class="content_field requiredText">
				<label class="content_label sign_required" for="homeAddress2">&nbsp; </label>
				<div class="content_field">
				<form:input type="text" autocomplete="off" name="homeAddress2" path="thirdPartyDto.homeAddress.fieldTwo" placeholder="${placeHolderZipCode}" id="homeAddress2" size="39" maxlength="38" class="content_field inputAddress box_width" ></form:input>
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
	
			<div id="homeAddress3Fields" class="content_field requiredText">
				<label class="content_label sign_required" for="homeAddress3">&nbsp; </label>
				<div class="content_field">
				<form:input type="text" autocomplete="off" name="homeAddress3" path="thirdPartyDto.homeAddress.fieldThree" placeholder="${placeHolderCity}" id="homeAddress3" size="39" maxlength="38" class="content_field inputAddress box_width" ></form:input>
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
	
			<div id="homeAddress4Fields" class="content_field requiredText">
				<label class="content_label sign_required" for="homeAddress4">&nbsp; </label>
				<div class="content_field">
				<form:input type="text" autocomplete="off" name="homeAddress4" path="thirdPartyDto.homeAddress.fieldFour" placeholder="${placeHolderCountry}" id="homeAddress4" size="39" maxlength="38" class="content_field inputAddress box_width" ></form:input>
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
	
			<label class="content_label">&nbsp;</label>
			<form:input type="text" autocomplete="off" name="homeAddress5" size="39" path="thirdPartyDto.homeAddress.fieldFive" id="homeAddress5" maxlength="38" class="content_field inputAddress box_width" ></form:input>
			<div class="hr_dotted"></div>
	
			<label class="content_label">&nbsp;</label>
			<form:input type="text" autoc0omplete="off" name="homeAddress6" path="thirdPartyDto.homeAddress.fieldSix" id="homeAddress6" size="39" maxlength="38" class="content_field inputAddress box_width" ></form:input>
			<div class="hr_dotted"></div>
	
			<label class="content_label">&nbsp;</label>
			<form:input type="text" autocomplete="off" name="homeAddress7" size="39" path="thirdPartyDto.homeAddress.fieldSeven" id="homeAddress7" maxlength="38" class="content_field inputAddress box_width" ></form:input>
			<div class="hr_dotted"></div>
	
			<div id="telFields" class="content_field requiredText">
				<label class="content_label contact_required" for="tel">
					<span id="telWarning" class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.formulaire.web.message.telephone" />
				</label>
				<div class="content_field">
					<form:input class="content_field box_width" type="text" autocomplete="off" name="tel" path="thirdPartyDto.tel" id="tel" maxlength="25"></form:input>
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
	
			<div id="faxFields" class="content_field requiredText">
				<label class="content_label" for="fax">
					<spring:message code="page.formulaire.web.message.fax" />
				</label>
				<div class="content_field">
					<form:input class="content_field box_width" type="text" autocomplete="off" name="fax" path="thirdPartyDto.fax" id="fax" maxlength="25"></form:input>
				</div>
			</div>
			<div class="hr_dotted"></div>
	
			<div id="mailFields" class="content_field requiredText">
				<label class="content_label contact_required" for="mail">
					<span id="mailWarning" class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.formulaire.web.message.mail" />
				</label>
				<div class="content_field">
					<form:input class="content_field box_width" type="text" autocomplete="off" name="mail" path="thirdPartyDto.mail" size="35" id="mail" maxlength="100"></form:input>
				</div>
				<div id ="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
	
			<div class="buttonOnRight">
				<span class="buttonBegin">
					<input type="button" class="button" id="submit_thirdParty_back" value="<spring:message code="page.formulaire.web.message.previous"/>" />
				</span>
				<span class="buttonBegin">
					<input type="button" id="delete_thirdParty" class="button" value="<spring:message code="page.delete" />" />
				</span>
				<span class="buttonBegin">
					<input type="submit" id="submit_thirdParty" name="submitThirdParty" class="button" value="<spring:message code="page.formulaire.web.message.save" />" />
				</span>
				<span class="buttonBegin">
					<input type="button" class="button" id="submit_thirdParty_next" value="<spring:message code="page.formulaire.web.message.next"/>" >
				</span>
			</div>
			<div class="hr_dotted"></div>
		</form:form>
		<div class="hr_dotted"></div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	initThirdParty("${patternTel}", "${fieldsAddressesRequired}", "${formulaireDCOForm.error}", "${dateFormat}");
});
</script>

<jsp:include page="footer.jsp" />