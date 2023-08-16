<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>

<sec:authentication property="principal.preferences.dateFormat.label" 			var="dateFormat"/>
<sec:authentication property="principal.preferences.dateFormat.labelLong" 		var="dateFormatLong"/>
<sec:authentication property="principal.preferences.dateFormat.labelDisplay" 	var="dateFormatDisplay"/>

<spring:message code="page.formulaire.web.message.annee" var="placeHolderAnnee" />
<spring:message code="page.formulaire.web.message.mois" var="placeHolderMois" />
<spring:message code="page.formulaire.web.message.jour" var="placeHolderJour" />

<c:set var="pcPT"	value="<%=Constants.PARAM_CODE_ACC_THIRD_CNT_PT%>"	scope="request"/>
<c:set var="pcES"	value="<%=Constants.PARAM_CODE_ACC_THIRD_CNT_ES%>"	scope="request"/>
<c:set var="pcGB"	value="<%=Constants.PARAM_CODE_ACC_THIRD_CNT_GB%>"	scope="request"/>
<c:set var="pcIE"	value="<%=Constants.PARAM_CODE_ACC_THIRD_CNT_IE%>"	scope="request"/>

<c:set var="ppFR1"	value="<%=Constants.PARAM_CODE_ACC_THIRD_PWR_FR1%>"	scope="request"/>
<c:set var="ppFR2"	value="<%=Constants.PARAM_CODE_ACC_THIRD_PWR_FR2%>"	scope="request"/>
<c:set var="ppPT"	value="<%=Constants.PARAM_CODE_ACC_THIRD_PWR_PT%>"	scope="request"/>
<c:set var="ppES"	value="<%=Constants.PARAM_CODE_ACC_THIRD_PWR_ES%>"	scope="request"/>
<c:set var="ppGB"	value="<%=Constants.PARAM_CODE_ACC_THIRD_PWR_GB%>"	scope="request"/>

<c:set var="psanl"	value="<%=Constants.PARAM_CODE_AMOUNT_NO_LIMIT%>"	scope="request"/>
<c:set var="psal"	value="<%=Constants.PARAM_CODE_AMOUNT_LIMIT%>"		scope="request"/>

<jsp:include page="include.jsp" />
<link rel="stylesheet" href="../css/datatable/dataTableRowPage.css">
<link rel="stylesheet" href="../css/datatable/dataTableRow.css">
<script src="../js/formulaire.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		initForm4("${formulaireDCOForm.codeSAJoinW}", "${psal}", "${ppPT}", "${formulaireDCOForm.error}", "${dateFormatDisplay}");
	});
</script>

<div id="content">
	<jsp:include page="error.jsp" />
	<c:set var="saveFormPath" value="saveForm4AndRedirect" scope="request"/>	
	<jsp:include page="breadcrumbs.jsp" />
	<p>
		<spring:message code="page.formulaire.web.message.explanation.form4" /><span class="helperRedirect" onclick="javascript:openHelper(1);">&nbsp;&nbsp;&nbsp;&nbsp;</span>
	</p>
	<div id="blocContainer">		
		<c:choose>
			<c:when test="${not empty formulaireDCOForm.mapAccount}">
				<table class="display" id="datatableATP">
					<thead>
						<tr>
							<th></th>
							<th style="text-align: left; white-space: nowrap;">
								<spring:message code="page.formulaire.web.message.datatable.header" />
							</th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${formulaireDCOForm.mapAccount}" var="node" varStatus="formLoop">
							<c:choose>
								<c:when test="${empty formulaireDCOForm.accounts[node.value[0]].accountThirdPartyList}">
									<tr class="odd_gradeX">
										<td>
											<div class="infoAccountReference">${formulaireDCOForm.listAccountKey[formLoop.index].label}</div>
										</td>
										<td style="display:none;"></td>
										<td style="display:none;"></td>
										<td style="display:none;"></td>
										<td style="display:none;"></td>
										<td style="display:none;"></td>
										<td style="display:none;"></td>
										<td style="display:none;"></td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${formulaireDCOForm.accounts[node.value[0]].accountThirdPartyList}" var="accountThirdParty" varStatus="formLoopThird">
										<tr>
											<td>${formulaireDCOForm.listAccountKey[formLoop.index].label}</td>
											<td class="center">${accountThirdParty.thirdParty.name}&nbsp${accountThirdParty.thirdParty.firstName}</td>
											<td class="center">
												<c:choose>
													<c:when test="${accountThirdParty.powerType == ppFR1}">
														<spring:message code="page.formulaire.account.third.party.power.FR1" />
													</c:when>
													<c:when test="${accountThirdParty.powerType == ppFR2}">
														<spring:message code="page.formulaire.account.third.party.power.FR2" />
													</c:when>
													<c:when test="${accountThirdParty.powerType == ppPT}">
														<spring:message code="page.formulaire.account.third.party.power.PT" arguments="${dateFormatDisplay}"/>
													</c:when>
													<c:when test="${accountThirdParty.powerType == ppES}">
														<spring:message code="page.formulaire.account.third.party.power.ES" arguments="${dateFormatDisplay}"/>
													</c:when>
													<c:when test="${accountThirdParty.powerType == ppGB}">
														<spring:message code="page.formulaire.account.third.party.power.GB" arguments="${dateFormatDisplay}"/>
													</c:when>
												</c:choose>
											</td>
											<td class="center"><fmt:formatDate pattern="${dateFormatLong}" value="${accountThirdParty.boardResolutionDate}"/></td>
											<td class="center">${accountThirdParty.signatureAuthorization.value}</td>
											<td class="center">
												<c:forEach items="${accountThirdParty.authorizedList}" var="jointTP">
													<c:forEach items="${formulaireDCOForm.TPForATPList}" var="authTP">
														<c:if test="${jointTP.id.thirdPartyId == authTP.id }">${authTP.name} ${authTP.firstName}<br/></c:if>
													</c:forEach>
												</c:forEach>
											</td>
											<td class="center">
												<c:choose>
													<c:when test="${accountThirdParty.statusAmountLimit == psanl}">
														<spring:message code="page.formulaire.web.message.status.ammount.1" />
													</c:when>
													<c:when test="${accountThirdParty.statusAmountLimit == psal}">
													<spring:message code="page.formulaire.web.message.status.ammount.2" />:
													&nbsp;${accountThirdParty.amountLimit}&nbsp;${accountThirdParty.deviseAmountLimit}</c:when>
												</c:choose>
											</td>
											<td class="center">
												<form:form method="post" action="deleteFormulaire4" commandName="formulaireDCOForm" id="deleteFormulaire4${node.key}">
													<form:input type="hidden" path="deleteCountry" value="${node.key}" ></form:input>							
													<form:input type="hidden" path="deleteThirdParty" value="${accountThirdParty.thirdParty.id}" ></form:input>							
													<form:input type="hidden" path="deleteSignatureAuthorization" value="${accountThirdParty.signatureAuthorization.id}" ></form:input>							
													<div class="buttonOnRight">
														<span class="buttonBegin">
															<input type="submit" value="<spring:message code="page.delete"/>" style="background-color: green; color: black;">
														</span>
													</div>
												</form:form>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<spring:message	code="page.formulaire.web.message.no.account" />
			</c:otherwise>
		</c:choose>
		<div class="hr_dotted"></div>
		<div class="hr_dotted_bis"></div>
		<div><spring:message	code="page.formulaire.web.message.account.association.intro" /></div>
		<div class="hr_dotted"></div>
		<div class="content_field_block">
			<form:form method="post" action="saveFormulaire4" commandName="formulaireDCOForm" id="mainForm" onsubmit="return checkAccountThirdParty(${ppES})">
				<div id="countryATPField" class="content_field requiredSelect">
					<label class="content_label" for=countryATP>
						<span class="warning">
							<spring:message code="page.asterisk" />
						</span>
						<spring:message	code="page.formulaire.web.message.paysOuvertureCompte" />
					</label>
					<form:select id="countryATP"  name="countryATP" autocomplete="on" path="countryATP" class="content_field">
						<form:option value="">
							<spring:message	code="page.formulaire.web.message.dropdownlistePaysOuvertureCompte" />
						</form:option>
						<c:forEach items="${formulaireDCOForm.listAccountKey}" var="countryLabel">
							<form:option value="${countryLabel.label2}">${countryLabel.label}</form:option>
						</c:forEach>
					</form:select>
					<div id="requiredImage"></div>
				</div>
				<div class="hr_dotted"></div>
				<div id="thirdPartyField" class="content_field requiredSelect">
					<label class="content_label" for="thirdPartyDto">
						<span class="warning">
							<spring:message code="page.asterisk" />
						</span>
						<spring:message code="page.formulaire.web.message.thirdPartyDto" />
					</label>
					<form:select autocomplete="on" name="thirdParty" path="thirdPartyDtoSelect" id="thirdParty" class="content_field">
						<form:option value="">
							<spring:message
								code="page.formulaire.web.message.chooseThirdParty" />
						</form:option>
						<c:forEach items="${formulaireDCOForm.TPForATPList}" var="tp">
							<form:option value="${tp.id}">${tp.name} ${tp.firstName}</form:option>
						</c:forEach>
					</form:select>
					<div id="requiredImage"></div>
				</div>
				<div class="hr_dotted"></div>
				<div id="powerTypeField" class="content_field requiredSelect">
					<label class="content_label" for="powerType1">
						<span class="warning"><spring:message code="page.asterisk" /></span>
						<spring:message code="page.fomulaire.account.third.party.powerType" />
					</label>
					<form:select autocomplete="on" path="powerType" id="powerType" class="content_field">
						<c:choose>
							<c:when test="${formulaireDCOForm.entity.country.country == pcPT}">
								<form:option value="${ppPT}"><spring:message code="page.formulaire.account.third.party.power.PT" arguments="${dateFormatDisplay}"/></form:option>
							</c:when>
							<c:when test="${formulaireDCOForm.entity.country.country == pcES}">
								<form:option value="${ppES}"><spring:message code="page.formulaire.account.third.party.power.ES" arguments="${dateFormatDisplay}"/></form:option>	
							</c:when>
							<c:when test="${formulaireDCOForm.entity.country.country == pcGB || formulaireDCOForm.entity.country.country == pcIE}">
								<form:option value="${ppGB}"><spring:message code="page.formulaire.account.third.party.power.GB" arguments="${dateFormatDisplay}"/></form:option>	
							</c:when>
							<c:otherwise>
								<form:option value=""><spring:message code="page.formulaire.web.message.power"/></form:option>	
								<form:option value="${ppFR1}"><spring:message code="page.formulaire.account.third.party.power.FR1" /></form:option>	
								<form:option value="${ppFR2}"><spring:message code="page.formulaire.account.third.party.power.FR2" /></form:option>	
								<form:option value="${ppPT}"><spring:message code="page.formulaire.account.third.party.power.PT" arguments="${dateFormatDisplay}"/></form:option>	
							</c:otherwise>
						</c:choose>
					</form:select>
					<div class="helperRedirect" onclick="javascript:openHelper(2);">&nbsp;</div>
					<div id="requiredImage"></div>
				</div>
				<div id="boardResolutionDateFieldSpace" class="hr_dotted"></div>
				<div id="boardResolutionDateField" class="content_field requiredText">
					<label class="content_label" for="boardResolutionDate">
						<span class="warning"><spring:message code="page.asterisk" /></span>
						<c:choose>
							<c:when test="${formulaireDCOForm.entity.country.country == pcES}">
								<spring:message code="page.formulaire.account.third.party.power.ES" arguments="${dateFormatDisplay}"/>:
							</c:when>
							<c:when test="${formulaireDCOForm.entity.country.country == pcGB || formulaireDCOForm.entity.country.country == pcIE}">
								<spring:message code="page.formulaire.account.third.party.power.GB" arguments="${dateFormatDisplay}"/>:	
							</c:when>
							<c:otherwise>
								<spring:message code="page.formulaire.account.third.party.power.PT" arguments="${dateFormatDisplay}"/>:	
							</c:otherwise>
						</c:choose>
					</label>
					<c:if test="${dateFormatDisplay == \"dd/mm/yyyy\"}">
						<div class="content_field">
							<form:input class="content_field box_width" placeholder="${placeHolderJour}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderJour}'" type="text" size="1" style="height:20px;" autocomplete="off" path="boardResolutionDay" id="boardResolutionDay" maxlength="2" onkeyup="checklength(this, boardResolutionMonth, this.value)" />
							<span class=slash><spring:message code="page.slash" /></span>
						</div>
						<div class="content_field">
							<form:input class="content_field box_width" placeholder="${placeHolderMois}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderMois}'" type="text" size="1" style="height:20px;" autocomplete="off" path="boardResolutionMonth" id="boardResolutionMonth" maxlength="2" onkeyup="checklength(this, boardResolutionYear, this.value)" />
							<span class=slash><spring:message code="page.slash" /></span>
						</div>
						<div class="content_field">
							<form:input class="content_field box_width" placeholder="${placeHolderAnnee}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderAnnee}'" type="text" size="2" style="height:20px;" autocomplete="off" path="boardResolutionYear" id="boardResolutionYear" maxlength="4"  />
							<span class="dateFormat">( ${dateFormatDisplay} )</span>
						</div>	
						<div id="requiredImage"></div>			
					</c:if>	
					<c:if test="${dateFormatDisplay == \"mm/dd/yyyy\"}">
						<div class="content_field">
							<form:input class="content_field box_width" placeholder="${placeHolderMois}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderMois}'" type="text" size="1" style="height:20px;" autocomplete="off" path="boardResolutionMonth" id="boardResolutionMonth" maxlength="2" onkeyup="checklength(this, boardResolutionDay, this.value)" />
							<span class=slash><spring:message code="page.slash" /></span>
						</div>
						<div class="content_field">
							<form:input class="content_field box_width" placeholder="${placeHolderJour}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderJour}'" type="text" size="1" style="height:20px;" autocomplete="off" path="boardResolutionDay" id="boardResolutionDay" maxlength="2" onkeyup="checklength(this, boardResolutionYear, this.value)" />
							<span class=slash><spring:message code="page.slash" /></span>
						</div>
						<div class="content_field">
							<form:input class="content_field box_width" placeholder="${placeHolderAnnee}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderAnnee}'" type="text" size="2" style="height:20px;" autocomplete="off" path="boardResolutionYear" id="boardResolutionYear" maxlength="4"  />
							<span class="dateFormat">( ${dateFormatDisplay} )</span>
						</div>	
						<div id="requiredImage"></div>			
					</c:if>		
					<c:if test="${dateFormatDisplay == \"yyyy/mm/dd\"}">
						<div class="content_field">
							<form:input class="content_field box_width" placeholder="${placeHolderAnnee}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderAnnee}'" type="text" size="2" style="height:20px;" autocomplete="off" path="boardResolutionYear" id="boardResolutionYear" maxlength="4" onkeyup="checklength(this, boardResolutionMonth, this.value)" />
							<span class=slash><spring:message code="page.slash" /></span>
						</div>	
						<div class="content_field">
							<form:input class="content_field box_width" placeholder="${placeHolderMois}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderMois}'" type="text" size="1" style="height:20px;" autocomplete="off" path="boardResolutionMonth" id="boardResolutionMonth" maxlength="2" onkeyup="checklength(this, boardResolutionDay, this.value)" />
							<span class=slash><spring:message code="page.slash" /></span>
						</div>
						<div class="content_field">
							<form:input class="content_field box_width" placeholder="${placeHolderJour}" onFocus="javascript:this.placeholder=''" onBlur="javascript:this.placeholder='${placeHolderJour}'" type="text" size="1" style="height:20px;" autocomplete="off" path="boardResolutionDay" id="boardResolutionDay" maxlength="2" />
							<span class="dateFormat">( ${dateFormatDisplay} )</span>
						</div>
						<div id="requiredImage"></div>			
					</c:if>		
				</div>
				<c:if test="${formulaireDCOForm.entity.country.country == pcES}">
					<div id="publicDeedReferenceFieldSpace"  class="hr_dotted"></div>
					<div id="publicDeedReferenceField" class="content_field requiredText">
						<label class="content_label" for="publicDeedReference">
							<span class="warning"><spring:message code="page.asterisk" /></span>
							<spring:message code="page.formulaire.web.message.publicDeedReference" />
						</label>
						<form:input class="content_field box_width" type="text" autocomplete="off" name="publicDeedReference" path="publicDeedReference" id="publicDeedReference" maxlength="50"  onblur="checkInput('publicDeedReference', 'publicDeedReferenceField');"/>
						<div id="requiredImage"></div>
					</div>				
				</c:if>
				<div class="hr_dotted"></div>
				<div id="signatureAuthorizationSelectField" class="content_field requiredSelect">
					<label class="content_label" for="signatureAuthorizationSelect">
						<span class="warning"><spring:message code="page.asterisk" /></span>
						<spring:message	code="page.formulaire.web.message.signatureAuthorization" />
					</label>
					<form:select autocomplete="on" name="signatureAuthorizationSelect" path="signatureAuthorizationSelect" id="signatureAuthorizationSelect" class="content_field">
						<form:option value="" style="display:none">
							<spring:message code="page.formulaire.web.message.dropdownListesignatureAuthorization" />
						</form:option>
						<c:forEach items="${formulaireDCOForm.signatureAuthorizationList}" var="entry">
							<form:option value="${entry.id}">${entry.value}</form:option>
						</c:forEach>
					</form:select>
					<div id="requiredImage"></div>
				</div>
				<div id="authorizedThirdPartyListFieldSpace" class="hr_dotted"></div>
				<div id="authorizedThirdPartyListField" class="content_field requiredSelect" >								
					<label class="content_label" for="authorizedThirdPartyList">
						<span class="warning"><spring:message code="page.asterisk" /></span>
						<spring:message code="page.formulaire.web.message.thirdPartyDto" />
					</label>
					<form:select autocomplete="on" multiple="multiple" path="authorizedThirdPartyList" id="authorizedThirdPartyList" class="content_field">
						<c:forEach items="${formulaireDCOForm.TPForATPList}" var="tp">
							<form:option value="${tp.id}">${tp.name} ${tp.firstName}</form:option>
						</c:forEach>
					</form:select>		
					<div id="requiredImage"></div>
				</div>
				<div  id="statusAmountLimitFieldSpace" class="hr_dotted"></div>
				<div id="statusAmountLimitField" class="content_field requiredSelect">
					<label class="content_label" for="statusAmountLimit">
						<span class="warning"><spring:message code="page.asterisk" /></span>
						<spring:message code="page.formulaire.web.message.status.ammount" />
					</label>
					<form:select autocomplete="on" name="statusAmountLimit" path="statusAmountLimit" id="statusAmountLimit" class="content_field">
					 	<form:option value="" style="display:none">
							<spring:message code="page.formulaire.web.message.status.ammount.0" />
						</form:option>
						<form:option value="${psanl}"><spring:message code="page.formulaire.web.message.status.ammount.1" /></form:option>
						<form:option value="${psal}"><spring:message code="page.formulaire.web.message.status.ammount.2" /></form:option>
					</form:select>
					<div class="helperRedirect" onclick="javascript:openHelper(3);">&nbsp;</div>
					<div id="requiredImage"></div>
				</div>
				<div class="hr_dotted"  id="amountLimitDeviseSpace" ></div>
				<div id="amountLimitDevise" class="content_field requiredSelect" >
					<label class="content_label" for="deviseAmountLimit">
						<span class="warning">
							<spring:message code="page.asterisk" />
						</span>
						<spring:message code="page.formulaire.web.message.deviseCompte" />
					</label>
					<form:select autocomplete="on" name="deviseAmountLimit" path="deviseAmountLimit" id="deviseAmountLimit" class="content_field">
						<form:option value="" style="display:none">
							<spring:message code="page.formulaire.web.message.dropdownListeDeviseCompte" />
						</form:option>
						<c:forEach items="${formulaireDCOForm.mapAccount}" var="node" varStatus="nstatus">
							<c:forEach items="${node.value}" var="listId" >
								<c:set var="countryAccount" value="${formulaireDCOForm.accounts[listId].countryAccount.locale.country}" scope="request"/> 	
								<form:options items="${formulaireDCOForm.mapCurrency[countryAccount]}" itemLabel="value"
										itemValue="value" />
							</c:forEach>
						</c:forEach>
					</form:select>	
					<div id="requiredImage"></div>
				</div>
				<div class="hr_dotted"  id="amountLimitFieldSpace" ></div>
				<div id="amountLimitField" class="content_field requiredText" >
					<label class="content_label" for="amountLimit">
						<span class="warning">
							<spring:message code="page.asterisk" />
						</span>
						<spring:message code="page.formulaire.web.message.status.ammount.2" />:
					</label>
					<form:input class="content_field box_width" type="text"  autocomplete="off" name="amountLimit" path="amountLimit" id="amountLimit" maxlength="20" onkeyup="formatNumber(this);" onchange="formatNumber(this)"></form:input>
					<div id="requiredImage"></div>
				</div>
				<div class="hr_dotted"></div>
				<div class="buttonOnRight">
					<span class="buttonBegin">
						<input type="button" value="<spring:message code="page.formulaire.web.message.previous"/>" class="button" id="submit_etape4_back">
					</span>
					<span class="buttonBegin">
						<input type="submit" value="<spring:message	code="page.formulaire.web.message.add" />" class="button" id="submitFormulaire4">
					</span>
					<span class="buttonBegin">
						<input type="button" value="<spring:message code="page.formulaire.web.message.next"/>" class="button" id="submit_etape4_next">
					</span>
				</div>
				<div class="hr_dotted"></div>				
			</form:form>
		</div>
	</div>

	<input type="hidden" id="tabLengthMenu" value="<spring:message code="datatable.tabLengthMenu"/>" />
	<input type="hidden" id="tabSearch" value="<spring:message code="datatable.tabSearch"/>" />
	<input type="hidden" id="tabZeroRecords" value="<spring:message code="datatable.tabZeroRecords"/>" />
	<input type="hidden" id="tabFirst" value="<spring:message code="datatable.tabFirst"/>" />
	<input type="hidden" id="tabLast" value="<spring:message code="datatable.tabLast"/>" />
	<input type="hidden" id="tabNext" value="<spring:message code="datatable.tabNext"/>" />
	<input type="hidden" id="tabPrevious" value="<spring:message code="datatable.tabPrevious"/>" />
	<input type="hidden" id="deleteAccountThirdPartyMessage" value="<spring:message code="page.formulaire.web.message.deleteAccountThirdPartyMessage"/>" />

</div>
<jsp:include page="footer.jsp" />