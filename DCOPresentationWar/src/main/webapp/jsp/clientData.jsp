<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>

<spring:message code="page.client.data.expTxt" var="expTxt" />
<spring:message code="page.client.data.cllpsTxt" var="cllpsTxt" />


<jsp:include page="include.jsp" />

	<input type="hidden" id="expTxt" value="${expTxt}"/>
	<input type="hidden" id="cllpsTxt" value="${cllpsTxt}"/>
	
<link rel="stylesheet" href="../css/accordion.css"></link>
<script type="text/javascript" src="../js/jquery.nestedAccordion.js"></script>
<script type="text/javascript" src="../js/expand.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		$('#menu-view-clt-data').addClass("main-menu-selected");
	});
	
	$("html").addClass("js");
	$(function() {

	  $("#main").accordion({
	      objID: "#acc2", 
	      obj: "div", 
	      wrapper: "div", 
	      el: ".h", 
	      head: "h4, h5", 
	      next: "div", 
	      initShow : "div.shown",
	      standardExpansible : true
	    });

	  $("#main .accordion").expandAll({
	      trigger: ".h", 
	      ref: "h4.h", 
	      cllpsEl: "div.outer",
	      speed: 200,
	      oneSwitch : false,
	      instantHide: true
	  });

	  $("html").removeClass("js");
	});
	
</script>

<!--[if lte IE 7]>
<style type="text/css">
  .accordion .inner {position:static; overflow:visible}
</style>
<![endif]-->

<div id="content">

	<jsp:include page="error.jsp" />

	<form:form method="post"
		action="<%=WebConstants.CLIENT_DATA_FILTER_CONTROLLER%>"
		commandName="<%=WebConstants.CLIENT_DATA_FILTER_FORM%>">

		<label class="content_label" for="lastName"><spring:message
				code="page.account.login" /></label>
		<form:select autocomplete="on" name="login" path="login" id="login"
			class="content_field">

			<form:option value="<%=Constants.EMPTY_FIELD%>">
				<spring:message code="page.all" />
			</form:option>

			<c:forEach items="${clientDataFilterForm.clients}" var="client">
				<form:option value="${client.login}">${client.login}</form:option>

			</c:forEach>
		</form:select>
		
		<div class="buttonOnRight">
			<span class="buttonBegin">
				<input type="submit"
					value="<spring:message code="page.document.apply"/>" class="button">
			</span></div>
		<div class="hr_dotted"></div>
		<BR>

		<c:if test="${not empty clientDataFilterForm.entities}">
			<!-- 		/***********************************/ -->
			<div id="main">
				<div class="accordion" id="acc2">
				<c:forEach var="entity" items="${clientDataFilterForm.entities}"
					varStatus="entityLoop">
				<h4 class="clicable" id="showEntity${entityLoop.index}">${clientDataFilterForm.entities[entityLoop.index].label}</h4>
				<div class="inner">
					<div class="hr_dotted"></div>
					<div class="content_field_block" id="entityShow${entityLoop.index}"
						style="dislay: none;">
						<div class="hr_dotted"></div>
						<label class="content_label"><spring:message
								code="page.formulaire.web.message.nameField" /></label>
						<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].label}</div>
						<div class="hr_dotted"></div>
						<label class="content_label"><spring:message
								code="page.formulaire.web.message.countryField" /></label>
						<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].country.getDisplayCountry(pageContext.response.locale)}</div>
						<div class="hr_dotted"></div>
	
<%-- 						<label class="content_label"> <spring:message --%>
<%-- 								code="page.formulaire.web.message.naceCode" /></label> --%>
<%-- 						<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].naceCode}</div> --%>
<!-- 						<div class="hr_dotted"></div> -->
	
						<label class="content_label"> <spring:message
								code="page.formulaire.web.message.taxInformation" /></label>
						<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].taxInformation}</div>
						<div class="hr_dotted"></div>
	
						<label class="content_label"> <spring:message
								code="page.formulaire.web.message.legalStatus" /></label>
						<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].legalStatus.entry}</div>
						<div class="hr_dotted"></div>
	
						<label class="content_label"> <spring:message
								code="page.formulaire.web.message.commercialRegister" /></label>
						<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].commercialRegister}</div>
						<div class="hr_dotted"></div>
	
						<label class="content_label"> <spring:message
								code="page.formulaire.web.message.country" /></label>
						<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].country.getDisplayCountry(pageContext.response.locale)}</div>
						<div class="hr_dotted"></div>
	
	
							<label class="content_label" for="tel"> <spring:message
									code="page.formulaire.web.message.telephone" /></label>
							<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].thirdParty.tel}</div>
							<div class="hr_dotted"></div>
	
							<label class="content_label" for="mail"> <spring:message
									code="page.formulaire.web.message.mail" /></label>
							<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].thirdParty.mail}</div>
							<div class="hr_dotted"></div>
							
							<label class="content_label" for="fax"> <spring:message
									code="page.formulaire.web.message.fax" /></label>
							<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].thirdParty.fax}</div>
							<div class="hr_dotted"></div>
		
							<label class="content_label" for="adresseSiege1"> <spring:message
									code="page.formulaire.web.message.adressField" /></label>
							<div class="content_field" style="width: 100px; float: left;"></div>
							<div class="hr_dotted"></div>
	
							<label class="content_label" for="adresseSiege1">&nbsp; </label>
							<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].address.fieldOne}</div>
							<div class="hr_dotted"></div>
	
							<label class="content_label" for="adresseSiege2">&nbsp; </label>
							<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].address.fieldTwo}</div>
							<div class="hr_dotted"></div>
	
							<label class="content_label" for="adresseSiege3">&nbsp; </label>
							<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].address.fieldThree}</div>
							<div class="hr_dotted"></div>
	
							<label class="content_label" for="adresseSiege4">&nbsp; </label>
							<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].address.fieldFour}</div>
							<div class="hr_dotted"></div>
	
							<c:if test="${not empty clientDataFilterForm.entities[entityLoop.index].address.fieldFive}">	
								<span class="content_label">&nbsp; </span>
								<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].address.fieldFive}</div>
								<div class="hr_dotted"></div>
							</c:if>
							
							<c:if test="${not empty clientDataFilterForm.entities[entityLoop.index].address.fieldSix}">	
								<span class="content_label">&nbsp; </span>
								<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].address.fieldSix}</div>
								<div class="hr_dotted"></div>
							</c:if>
							
							<c:if test="${not empty clientDataFilterForm.entities[entityLoop.index].address.fieldSeven}">	
								<span class="content_label">&nbsp; </span>
								<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].address.fieldSeven}</div>
								<div class="hr_dotted"></div>
							</c:if>
							<label class="content_label" for="adressePostal1"><spring:message
									code="page.formulaire.web.message.adressPostalField" /></label>
							<div class="content_field" style="width: 100px; float: left;"></div>
							<div class="hr_dotted"></div>
	
							<label class="content_label" for="adressePostal1">&nbsp; </label>
							<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].addressMailing.fieldOne}</div>
							<div class="hr_dotted"></div>
	
							<label class="content_label" for="adressePostal2">&nbsp; </label>
							<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].addressMailing.fieldTwo}</div>
							<div class="hr_dotted"></div>
	
							<label class="content_label" for="adressePostal3">&nbsp; </label>
							<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].addressMailing.fieldThree}</div>
							<div class="hr_dotted"></div>
	
							<label class="content_label" for="adressePostal4">&nbsp; </label>
							<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].addressMailing.fieldFour}</div>
							<div class="hr_dotted"></div>
	
							<c:if test="${not empty clientDataFilterForm.entities[entityLoop.index].addressMailing.fieldFive}">
								<span class="content_label">&nbsp; </span>
								<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].addressMailing.fieldFive}</div>
								<div class="hr_dotted"></div>
							</c:if>
							
							<c:if test="${not empty clientDataFilterForm.entities[entityLoop.index].addressMailing.fieldSix}">
								<span class="content_label">&nbsp; </span>
								<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].addressMailing.fieldSix}</div>
								<div class="hr_dotted"></div>
							</c:if>
							
							<c:if test="${not empty clientDataFilterForm.entities[entityLoop.index].addressMailing.fieldSeven}">							
								<span class="content_label">&nbsp; </span>
								<div class="content_field inputAddress">${clientDataFilterForm.entities[entityLoop.index].addressMailing.fieldSeven}</div>
								<div class="hr_dotted"></div>
							</c:if>

					</div>
	
					<c:if test="${not empty clientDataFilterForm.entities[entityLoop.index].accountsForm}">
	
						<c:forEach items="${clientDataFilterForm.entities[entityLoop.index].accountsForm}"
							var="account" varStatus="formLoop">
	
							<c:if
								test="${not empty clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList}">
								<c:forEach
									items="${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList}"
									var="account" varStatus="accountDtoLoop">
									<div class="hr_dotted"></div>
									<h5 class="clicable" id="showAccounts${accountDtoLoop.index}">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].reference}</h5>
									<div class="inner"
										id="accountsShow${accountDtoLoop.index}">
	
										<c:if test="${formLoop.index > 0}">
											<div class="hr_dotted_bis"
												style="margin-top: 10px; width: 60%;"></div>
										</c:if>
	
										<div id="account${formLoop.index}">
	
	
											<div class="content_field_block">
	
												<label class="content_label" for="reference"><spring:message
														code="page.formulaire.web.message.reference" /></label>
												<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].reference}</div>
												<div class="hr_dotted"></div>
	
												<c:choose>
													<c:when
														test="${
														(not empty clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldOne) or
														(not empty clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldTwo) or
														(not empty clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldThree) or
														(not empty clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldFour) or
														(not empty clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldFive) or
														(not empty clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldSix) or
														(not empty clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldSeven) or
														(not empty clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].commercialRegister) or
														((not empty clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].country)
														&& (clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].country != 0))}">
														
														<label class="content_label" for="check${accountDtoLoop.index}"><spring:message
																code="page.formulaire.web.message.filiale" /></label> <input
															type="checkbox" id="check${accountDtoLoop.index}"
															name="subsidiary" value="" disabled="disabled" checked="checked"/>
														<div class="hr_dotted"></div>
														
														
														<div id="subsidiary${accountDtoLoop.index}">
															<div class="hr_dotted"></div>
															<label class="content_label" for="addressSub"><spring:message
																	code="page.formulaire.web.message.addressSubsidiaryField" /></label>
															<div class="content_field"
																style="width: 100px; float: left;" id="addressSub">
			
			
																<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldOne}</div>
																<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldTwo}</div>
																<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldThree}</div>
																<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldFour}</div>
																<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldFive}</div>
																<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldSix}</div>
																<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].address.fieldSeven}</div>
			
															</div>
			
															<div class="hr_dotted"></div>
			
															<label class="content_label"
																for="commercialRegister${formLoop.index}"><spring:message
																	code="page.formulaire.web.message.commercialRegister" /></label>
															<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].commercialRegister}</div>
															<div class="hr_dotted"></div>
			
															<label class="content_label" for="country">
																<spring:message code="page.formulaire.web.message.country" /></label>
															<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].country}</div>
															<div class="hr_dotted"></div>
														</div>
												
													</c:when>
													<c:otherwise>
													</c:otherwise>
												</c:choose>
	
												<label class="content_label" for="typeCompte">
													<spring:message
														code="page.formulaire.web.message.typeCompte" /></label>
												<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].typeAccount.value}</div>
												<div class="hr_dotted"></div>
	
												<label class="content_label" for="deviseCompte"><spring:message
														code="page.formulaire.web.message.deviseCompte" /></label>
												<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].currency.value}</div>
												<div class="hr_dotted"></div>
	
	
												<label class="content_label" for="paysOuvertureCompte"><spring:message
														code="page.formulaire.web.message.paysOuvertureCompte" /></label>
												<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].countryAccount.locale.getDisplayCountry(pageContext.response.locale)}</div>
												<div class="hr_dotted"></div>
	
												<label class="periodiciteReleve content_label"
													for="periodiciteReleve"> <spring:message
														code="page.formulaire.web.message.periodiciteReleve" /></label>
												<div class="content_field">${clientDataFilterForm.entities[entityLoop.index].accountsForm[formLoop.index].accountDtoList[accountDtoLoop.index].periodicity.value}</div>
												<div class="hr_dotted"></div>
	
											</div>
										</div>
									</div>
								</c:forEach>
							</c:if>
						</c:forEach>
					</c:if>
					<div class="hr_dotted"></div>
					<BR>
				</div>
				</c:forEach>
				</div>
			</div>
		</c:if>
		<div class="content_field_block" id="thirdsShow"></div>

	</form:form>

</div>

<script>
function fetchUsers(label){
	var ret = $.ajax({
		async:false,
		type: 'GET',
		url: "../action/getUserFromEntity?label=" + label,
		dataType: 'json',
		cache:false,
		success: function(data){
			fillUsersSelect(data);
		}
	});
	return ret;
}

function fillUsersSelect(data){
	var idSelected = $("#login").find('option:selected').val();
	$("#login").find('option:not(:first)').remove();

	$.each(data, function() {
		if(idSelected == this.login){
			$("#login").append($("<option selected='selected' onclick='javascript:fetchEntity("+this.id+")'/>").val(this.login).text(this.login));
			
		}else{
			$("#login").append($("<option onclick='javascript:fetchEntity("+this.id+")'/>").val(this.login).text(this.login));
		}
	});
}

function fetchEntity(id){
	var ret = $.ajax({
		async:false,
		type: 'GET',
		url: "../action/getEntityFromUser?id=" + id,
		dataType: 'json',
		cache:false,
		success: function(data){
			fillEntitySelect(data);
		}
	});
	return ret;
}

function fillEntitySelect(data){
	var idSelected = $("#entity").find('option:selected').val();
	$("#entity").find('option:not(:first)').remove();

	$.each(data, function() {
		var label = '"' + this.label + '"';
		if(idSelected == this.label){
			$("#entity").append($("<option selected='selected' onclick='javascript:fetchUsers("+label+")'/>").val(this.label).text(this.label));
			
		}else{
			$("#entity").append($("<option onclick='javascript:fetchUsers("+label+")'/>").val(this.label).text(this.label));
		}
	});
}
</script>

<jsp:include page="footer.jsp" />