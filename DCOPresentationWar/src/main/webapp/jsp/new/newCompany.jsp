<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>

<div class="bnpAccount-p-mainContent">
<!-- 	<div class="errorblock" id="error" style="display: none"></div> -->
	<jsp:include page="../error.jsp" />
	<form:form method="post" action="saveNewCompany" commandName="newCompanyForm" id="companyForm">
		<div class="warningblock" id="warningOther" style="display: none"></div>
		<div class="bnpAccount-grid-2 bnpAccount-restrictedWidth">
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.legalName" /><b class="bnpAccount-moreInfo">?</b></label>
				<form:input type="text" required="true" class="bnpAccount-field" name="" id="" path="newEntity.label"></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.countryIncorp" /><b class="bnpAccount-moreInfo">?</b></label>
				<form:select onchange="showCountryLegalStatus()" autocomplete="on" required="true" name="countryIncorp" path="newEntity.countryIncorp" id="countryIncorp" class="bnpAccount-field">
					<form:option value=""> <spring:message code="page.formulaire.web.message.entity.select.country" /></form:option>
					<c:forEach items="${newCompanyForm.countriesSort}" var="country">		
						<form:option value="${country.label2}">${country.label}</form:option>		
					</c:forEach>
				</form:select>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.commercialRegister" /><b class="bnpAccount-moreInfo">?</b></label>
				<form:input type="text" required="true" class="bnpAccount-field" name="" id="" path="newEntity.commercialRegister"></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.taxInformation" /><b class="bnpAccount-moreInfo">?</b></label>
				<form:input type="text" required="true" class="bnpAccount-field" name="" id="" path="newEntity.taxInformation"></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.registrationCountry" /><b class="bnpAccount-moreInfo">?</b></label>
				<form:select autocomplete="on" required="true" name="registrationCountry" path="newEntity.registrationCountry" id="registrationCountry" class="bnpAccount-field">
					<form:option value=""> <spring:message code="page.formulaire.web.message.entity.select.country" /></form:option>
					<c:forEach items="${newCompanyForm.countryList}" var="locale">
						<form:option value="${locale.country}">${locale.getDisplayCountry(pageContext.response.locale)}</form:option>
					</c:forEach>
				</form:select>
			</p>
			
			<!-- LEGAL STATUS -->
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.legalStatus" /><b class="bnpAccount-moreInfo">?</b></label>
				
				<form:hidden path="newEntity.legalStatus.id" id="legalStatusId"/>
			
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" disabled="true" required="true" name="legalStatus" path="" id="legalStatus" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusAT" path="" id="legalStatusAT" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['AT']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusBE" path="" id="legalStatusBE" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['BE']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusBG" path="" id="legalStatusBG" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['BG']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusCZ" path="" id="legalStatusCZ" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['CZ']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusDK" path="" id="legalStatusDK" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['DK']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusFR" path="" id="legalStatusFR" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['FR']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusDE" path="" id="legalStatusDE" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['DE']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusHU" path="" id="legalStatusHU" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['HU']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusIE" path="" id="legalStatusIE" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['IE']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusIT" path="" id="legalStatusIT" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['IT']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusLU" path="" id="legalStatusLU" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['LU']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusNL" path="" id="legalStatusNL" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['NL']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusNO" path="" id="legalStatusNO" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['NO']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusPL" path="" id="legalStatusPL" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['PL']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusPT" path="" id="legalStatusPT" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['PT']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusRO" path="" id="legalStatusRO" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['RO']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusES" path="" id="legalStatusES" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['ES']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusSE" path="" id="legalStatusSE" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['SE']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusCH" path="" id="legalStatusCH" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['CH']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
				
				<form:select autocomplete="on" onchange="setLegalStatusId(this.id)" required="true" name="legalStatusGB" path="" id="legalStatusGB" class="bnpAccount-field">
					<form:option value=""><spring:message code="page.formulaire.web.message.entity.select.legalStatus" /></form:option>
					<c:forEach items="${newCompanyForm.mapLegalStatus['GB']}" var="entry">
						<form:option value="${entry.id}" id="${entry.entry}">${entry.value}</form:option>
					</c:forEach> 
				</form:select>
			</p>
		</div>
		<p><strong class="bnpAccount-legendLike"><spring:message code="page.formulaire.web.message.entity.address" /></strong></p>
		<div class="bnpAccount-grid-2 bnpAccount-restrictedWidth">
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.address.street" /></label>
				<form:input type="text" required="true" class="bnpAccount-field" name="adresseSiege1" id="adresseSiege1" onkeyup="copyAddressField(1);" path="newEntity.address.fieldOne"></form:input>
			</p>
			
			<p class="bnpAccount-fieldGroup">
				<label for=""><spring:message code="page.formulaire.web.message.entity.address.complement" /></label>
				<form:input type="text" class="bnpAccount-field" path="newEntity.address.fieldFive" name="adresseSiege5" id="adresseSiege5" onkeyup="copyAddressField(5);"></form:input>
			</p>
<!-- 			<p class="bnpAccount-fieldGroup"> -->
<%-- 				<label for=""><spring:message code="page.formulaire.web.message.entity.address.complement" /></label> --%>
<%-- 				<form:input type="text" class="bnpAccount-field" path="newEntity.address.fieldSix" name="adresseSiege6" id="adresseSiege6" onkeyup="copyAddressField(6);"></form:input> --%>
<!-- 			</p> -->
<!-- 			<p class="bnpAccount-fieldGroup"> -->
<%-- 				<label for=""><spring:message code="page.formulaire.web.message.entity.address.complement" /></label> --%>
<%-- 				<form:input type="text" class="bnpAccount-field" path="newEntity.address.fieldSeven" name="adresseSiege7" id="adresseSiege7" onkeyup="copyAddressField(7);"></form:input> --%>
<!-- 			</p> -->
			
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.address.city" /></label>
				<form:input type="text" required="true" class="bnpAccount-field" name="adresseSiege2" id="adresseSiege2" onkeyup="copyAddressField(2);" path="newEntity.address.fieldThree"></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.address.zipCode" /></label>
				<form:input type="text" maxlength="5" required="true" class="bnpAccount-field" name="adresseSiege3" id="adresseSiege3" onkeyup="copyAddressField(3);" path="newEntity.address.fieldTwo"></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.address.country" /></label>
				<form:select autocomplete="on" required="true" name="adresseSiege4" path="newEntity.address.fieldFour" onkeyup="copyAddressField(4);" id="adresseSiege4" class="bnpAccount-field">
					<form:option value=""> <spring:message code="page.formulaire.web.message.entity.select.country" /></form:option>
					<c:forEach items="${newCompanyForm.countryList}" var="locale">
						<form:option value="${locale.country}">${locale.getDisplayCountry(pageContext.response.locale)}</form:option>
					</c:forEach>
				</form:select>
			</p>
		</div>
		<p><strong class="bnpAccount-legendLike"><spring:message code="page.formulaire.web.message.entity.address.postal" /></strong></p>
		<p class="bnpAccount-fieldGroup--checkbox">
			<label for="postal">
				<form:checkbox name="postal" id="postal" onClick="copyPostalAddress();" path="newEntity.sameAddress" checked="true"/>
				<span class="bnpAccount-changedUi bnpAccount-checkbox">
					<svg class="bnpAccount-checkbox-check" xmlns="http://www.w3.org/2000/svg" width="20" height="17" viewBox="0 0 13 10">
						<polygon points="5 10 0 5.96 1.9 3.65 4.8 6 10.89 0 13 2.11 5 10"/>
					</svg>
				</span>
				<spring:message code="page.formulaire.web.message.entity.address.same" />
			</label>
		</p>
		<div id="postalAddress" class="bnpAccount-grid-2 bnpAccount-restrictedWidth">
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.address.street" /></label>
				<form:input type="text" required="true" class="bnpAccount-field" name="adressePostal1" id="adressePostal1" path="newEntity.addressMailing.fieldOne"></form:input>
			</p>
			
			<p class="bnpAccount-fieldGroup">
				<label for=""><spring:message code="page.formulaire.web.message.entity.address.complement" /></label>
				<form:input type="text" class="bnpAccount-field" path="newEntity.addressMailing.fieldFive" name="adressePostal5" id="adressePostal5"></form:input>
			</p>
<!-- 			<p class="bnpAccount-fieldGroup"> -->
<%-- 				<label for=""><spring:message code="page.formulaire.web.message.entity.address.complement" /></label> --%>
<%-- 				<form:input type="text" class="bnpAccount-field" path="newEntity.addressMailing.fieldSix" name="adressePostal6" id="adressePostal6"></form:input> --%>
<!-- 			</p> -->
<!-- 			<p class="bnpAccount-fieldGroup"> -->
<%-- 				<label for=""><spring:message code="page.formulaire.web.message.entity.address.complement" /></label> --%>
<%-- 				<form:input type="text" class="bnpAccount-field" path="newEntity.addressMailing.fieldSeven" name="adressePostal7" id="adressePostal7"></form:input> --%>
<!-- 			</p> -->
			
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.address.city" /></label>
				<form:input type="text" required="true" class="bnpAccount-field" name="adressePostal2" id="adressePostal2" path="newEntity.addressMailing.fieldThree"></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.address.zipCode" /></label>
				<form:input type="text" maxlength="5" required="true" class="bnpAccount-field" name="adressePostal3" id="adressePostal3" path="newEntity.addressMailing.fieldTwo"></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.entity.address.country" /></label>
				<form:select autocomplete="on" required="true" name="adressePostal4" path="newEntity.addressMailing.fieldFour" id="adressePostal4" class="bnpAccount-field">
					<form:option value=""> <spring:message code="page.formulaire.web.message.entity.select.country" /></form:option>
					<c:forEach items="${newCompanyForm.countryList}" var="locale">
						<form:option value="${locale.country}">${locale.getDisplayCountry(pageContext.response.locale)}</form:option>
					</c:forEach>
				</form:select>
			</p>
		</div>
		
		
		<!-- CONTACTS -->
		<p class="bnpAccount-mt-big"><strong class="bnpAccount-legendLike"><spring:message code="page.formulaire.web.message.section.contact" /><b class="bnpAccount-moreInfo">?</b></strong></p>
		<p class="bnpAccount-formIntro">
			<spring:message code="page.formulaire.web.message.contact.warning" />
		</p>
		<div class="bnpAccount-grid-2 bnpAccount-restrictedWidth">
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.contact.firstname" /><b class="bnpAccount-moreInfo">?</b></label>
				<form:input type="text" required="true" class="bnpAccount-field" path="newEntity.contact1.firstname" name="" id=""></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.contact.name" /><b class="bnpAccount-moreInfo">?</b></label>
				<form:input type="text" required="true" class="bnpAccount-field" path="newEntity.contact1.name" name="" id=""></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.contact.email" /></label>
				<form:input type="mail" required="true" class="bnpAccount-field" path="newEntity.contact1.mail" name="" id=""></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.contact.phone" /></label>
				<form:input type="tel" placeholder="+" required="true" class="bnpAccount-field" path="newEntity.contact1.tel" name="" id=""></form:input>
			</p>
			
<!-- 			<p> -->
<!-- 				<button id="addContact" type="button" onClick="addContact2();" class="bnpAccount-btnReverse bnpAccount-btn--small bnpAccount-btnReverse--action bnpAccount-btn--add"> -->
<!-- 					<svg class="bnpAccount-svg bnpAccount-svg--userSmall" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 83 84.92"> -->
<!-- 						<path class="cls-1" d="M82.81,84.92H0.19l-0.08-2C0,79.07-.37,66.16,1.92,62.57S9.65,57,16,54.53c1.64-.63,3.34-1.29,5-2,4.76-2,7.26-3.17,8.47-3.77l0.05-5.47c-1-1.17-2.74-3.71-3.6-8.38A4.56,4.56,0,0,1,25,34.41a8.54,8.54,0,0,1-3.18-5.69c0-.21-0.31-5.15,2-7.17l0.28-.22a38.23,38.23,0,0,1-.42-7C24.4,7.28,31.24,0,41.5,0S58.6,7.28,59.28,14.32a38.05,38.05,0,0,1-.42,7l0.28,0.22c2.32,2,2,7,2,7.17A8.47,8.47,0,0,1,58,34.39a4.49,4.49,0,0,1-1,.53c-0.86,4.67-2.56,7.21-3.59,8.38l0.06,5.48c1.21,0.6,3.71,1.77,8.47,3.77,1.68,0.7,3.38,1.35,5,2,6.38,2.46,11.89,4.59,14.1,8.05s2,16.5,1.81,20.37ZM4.15,80.81H78.85c0.16-6.88-.23-14.46-1.23-16h0C76.13,62.46,71,60.47,65.5,58.36c-1.67-.65-3.4-1.31-5.12-2a94.9,94.9,0,0,1-10-4.56l-1-.59-0.1-9.72,0.81-.62h0s2.32-2,3.08-8l0.31-2.38,2.3,0.68a6.47,6.47,0,0,0,1.25-2.63,9.39,9.39,0,0,0-.47-3.59l-2.38.5,0.37-2.86a48.6,48.6,0,0,0,.62-7.82C54.7,9.5,49.44,4.1,41.5,4.1S28.31,9.5,27.8,14.71a49.46,49.46,0,0,0,.62,7.82l0.36,2.86-2.38-.5a9.31,9.31,0,0,0-.47,3.56,4.34,4.34,0,0,0,1.54,2.65l2.31-.67,0,2.39c0.77,6,3.09,8,3.12,8l0.74,0.62,0,1L33.6,51.18l-1,.59a95.87,95.87,0,0,1-10,4.55c-1.73.72-3.46,1.38-5.12,2C12,60.47,6.87,62.46,5.38,64.79,4.38,66.35,4,73.94,4.15,80.81Z" /> -->
<!-- 					</svg> -->
<%-- 					<spring:message code="page.formulaire.web.message.contact.add" /> --%>
<!-- 				</button> -->
<!-- 			</p> -->
			
			<p class="bnpAccount-fieldGroup--checkbox">
				<label for="addContact">
					<form:checkbox name="addContact" id="addContact" onClick="addContact2();" path="newEntity.hasContact2"/>
					<span class="bnpAccount-changedUi bnpAccount-checkbox">
						<svg class="bnpAccount-checkbox-check" xmlns="http://www.w3.org/2000/svg" width="20" height="17" viewBox="0 0 13 10">
							<polygon points="5 10 0 5.96 1.9 3.65 4.8 6 10.89 0 13 2.11 5 10"/>
						</svg>
					</span>
					<spring:message code="page.formulaire.web.message.detailCompany.contact.second" />
				</label>
			</p>
		</div>
		
		<div id="contact2" class="bnpAccount-grid-2 bnpAccount-restrictedWidth">
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.contact.firstname" /></label>
				<form:input type="text" required="true" class="bnpAccount-field" path="newEntity.contact2.firstname" name="contact2-firstname" id="contact2-firstname"></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.contact.name" /></label>
				<form:input type="text" required="true" class="bnpAccount-field" path="newEntity.contact2.name" name="contact2-name" id="contact2-name"></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.contact.email" /></label>
				<form:input type="mail" required="true" class="bnpAccount-field" path="newEntity.contact2.mail" name="contact2-mail" id="contact2-mail"></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.contact.phone" /></label>
				<form:input type="tel" placeholder="+" required="true" class="bnpAccount-field" path="newEntity.contact2.tel" name="contact2-phone" id="contact2-phone"></form:input>
			</p>
		</div>
		
		<!-- REPRESENTATIVE -->
		<p class="bnpAccount-mt-big">
			<strong class="bnpAccount-legendLike"><spring:message code="page.formulaire.web.message.section.representative" /><b class="bnpAccount-moreInfo">?</b></strong>
		</p>
		<p class="bnpAccount-formIntro">
			<spring:message code="page.formulaire.web.message.representative.warning" />
		</p>
		<form:hidden path="nbRepresentatives" id="nbRepresentatives"/>
		
		<div class="bnpAccount-infoRequest-signatories-container">
			<c:forEach items="${newCompanyForm.representatives}" var="customerRepresentative" varStatus="count">
			<input type="hidden" name="newEntity.representativesList[${count.index}].name" value="${customerRepresentative.name}" id="customerRepresentative${count.index}">
				<div class="bnpAccount-signatoriesResume-content bnpAccount-infoRequest-container bnpAccount-resumeDataList-container">
					<dl class="bnpAccount-resumeDataList bnpAccount-resumeDataList--info">
	<%-- 					<dt><spring:message code="page.formulaire.web.message.representative.firstname" /></dt> --%>
						<dd>${customerRepresentative.firstname}</dd>
						<dd>${customerRepresentative.name}</dd>
					</dl>
					<p class="bnpAccount-resumeDataList-actionBtns">
	<!-- 					<button class="bnpAccount-btn bnpAccountresumeDataList-action-btn"> -->
	<!-- 						<img alt="" src="../img/svg/create.svg"> -->
	<!-- 					</button> -->
						<button type="button" onClick="deleteRepresentative(${count.index})" class="bnpAccount-btn bnpAccountresumeDataList-action-btn deleteButton">
							<img alt="" src="../img/svg/delete.svg">
						</button>
					</p>
				</div>
			</c:forEach>
		</div>
		
		<div class="errorblock" id="error" style="display: none"></div>
		<div class="bnpAccount-representativeInputs bnpAccount-grid-2 bnpAccount-restrictedWidth">
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.representative.firstname" /></label>
				<form:input type="text" required="true" class="bnpAccount-field" path="representative.firstname" name="" id="representativeFirstName"></form:input>
			</p>
			<p class="bnpAccount-fieldGroup">
				<label class="bnpAccount-labelRequired" for=""><spring:message code="page.formulaire.web.message.representative.name" /></label>
				<form:input type="text" required="true" class="bnpAccount-field" path="representative.name" name="" id="representativeLastName"></form:input>
			</p>
		</div>
		<p>
			<button id="addRepresentative" type="button" onclick="addRepresentativeToList()" class="bnpAccount-btnReverse bnpAccount-btn--small bnpAccount-btnReverse--action bnpAccount-btn--add">
				<svg class="bnpAccount-svg bnpAccount-svg--userSmall" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 83 84.92">
					<path class="cls-1" d="M82.81,84.92H0.19l-0.08-2C0,79.07-.37,66.16,1.92,62.57S9.65,57,16,54.53c1.64-.63,3.34-1.29,5-2,4.76-2,7.26-3.17,8.47-3.77l0.05-5.47c-1-1.17-2.74-3.71-3.6-8.38A4.56,4.56,0,0,1,25,34.41a8.54,8.54,0,0,1-3.18-5.69c0-.21-0.31-5.15,2-7.17l0.28-.22a38.23,38.23,0,0,1-.42-7C24.4,7.28,31.24,0,41.5,0S58.6,7.28,59.28,14.32a38.05,38.05,0,0,1-.42,7l0.28,0.22c2.32,2,2,7,2,7.17A8.47,8.47,0,0,1,58,34.39a4.49,4.49,0,0,1-1,.53c-0.86,4.67-2.56,7.21-3.59,8.38l0.06,5.48c1.21,0.6,3.71,1.77,8.47,3.77,1.68,0.7,3.38,1.35,5,2,6.38,2.46,11.89,4.59,14.1,8.05s2,16.5,1.81,20.37ZM4.15,80.81H78.85c0.16-6.88-.23-14.46-1.23-16h0C76.13,62.46,71,60.47,65.5,58.36c-1.67-.65-3.4-1.31-5.12-2a94.9,94.9,0,0,1-10-4.56l-1-.59-0.1-9.72,0.81-.62h0s2.32-2,3.08-8l0.31-2.38,2.3,0.68a6.47,6.47,0,0,0,1.25-2.63,9.39,9.39,0,0,0-.47-3.59l-2.38.5,0.37-2.86a48.6,48.6,0,0,0,.62-7.82C54.7,9.5,49.44,4.1,41.5,4.1S28.31,9.5,27.8,14.71a49.46,49.46,0,0,0,.62,7.82l0.36,2.86-2.38-.5a9.31,9.31,0,0,0-.47,3.56,4.34,4.34,0,0,0,1.54,2.65l2.31-.67,0,2.39c0.77,6,3.09,8,3.12,8l0.74,0.62,0,1L33.6,51.18l-1,.59a95.87,95.87,0,0,1-10,4.55c-1.73.72-3.46,1.38-5.12,2C12,60.47,6.87,62.46,5.38,64.79,4.38,66.35,4,73.94,4.15,80.81Z" />
				</svg>
				<spring:message code="page.formulaire.web.message.representive.add" />
			</button>
		</p>
		
		<p class="bnpAccount-mt-big">		
			<p class="bnpAccount-text--center">
				<button type="button" onclick="location.href='<%=WebConstants.HOME_COMPANY_BACK%>'" class="bnpAccount-btn bnpAccount-btn--normalSubmit bnpAccount-btn--action"><spring:message code="page.formulaire.web.message.cancel" /></button>
				<button type="button" onclick="companyFormSubmit()" class="bnpAccount-btn bnpAccount-btn--normalSubmit bnpAccount-btn--action"><spring:message code="page.formulaire.web.message.validate" /></button>
			</p>
		</p>
	</form:form>
</div>