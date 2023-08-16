<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="page.formulaire.web.message.address" var="placeHolderAddress" />
<spring:message code="page.formulaire.web.message.ZipCode" var="placeHolderZipCode" />
<spring:message code="page.formulaire.web.message.city" var="placeHolderCity" />
<spring:message code="page.formulaire.web.message.country.placeHolder" var="placeHolderCountry" />
<spring:message code="page.formulaire.web.message.fieldsAddressesRequired" var="fieldsAddressesRequired" />

<jsp:include page="include.jsp" />
<script type="text/javascript">
	$(document).ready($('#menu-param').addClass("main-menu-selected"));
</script>
<div id="content">
	<jsp:include page="error.jsp" />
	<fieldset>
		<div class="content_field_block">
			<form:form id="formFilterType" method="post" action="paramFilterType" commandName="form">
				<label class="content_label" for="type"><spring:message code="page.param.type" /></label>
				<form:select autocomplete="on" name="selectedType" path="selectedType" id="type" class="content_field"
					onchange="exitMyAccounts = false; document.forms['formFilterType'].submit();">
					<form:option value="0">&nbsp</form:option>
					<c:forEach items="${form.types}" var="type">
						<form:option value="${type.id}"><spring:message code="page.param.types.${type.id}"/></form:option>
					</c:forEach>
				</form:select>
			</form:form>
			<c:if test="${form.selectedType != 0}">
				<c:if test="${form.standardAllCountries or form.standardBankCountries}">
					<form:form id="formFilterCountry" method="post" action="paramStdFilterCountry" commandName="form">
						<div class="hr_dotted"></div>
						<label class="content_label" for="country"><spring:message code="page.param.country" /></label>
						<form:select autocomplete="on" name="selectedCountry" path="selectedCountry" id="country" class="content_field"
							onchange="exitMyAccounts = false; document.forms['formFilterCountry'].submit();">
							<form:option value="">&nbsp</form:option>
							<c:if test="${form.standardAllCountries}">
								<c:forEach items="${formulaireController.countryList}" var="country">
									<form:option value="${country.country}">${country.getDisplayCountry(pageContext.response.locale)}</form:option>
								</c:forEach>
							</c:if>
							<c:if test="${form.standardBankCountries}">
								<c:forEach items="${form.countries}" var="country">
									<form:option value="${country.locale.country}">${country.locale.getDisplayCountry(pageContext.response.locale)}</form:option>
								</c:forEach>
							</c:if>
						</form:select>
						<script type="text/javascript">
							$(document).ready(function(){
								$('#country option').sort(NASort).appendTo('#country');
								$("#country").val($("#country option:first").val());
							});
						</script>
					</form:form>
					<c:if test="${not empty form.selectedCountry}">
						<br/>
						<div class="hr_dotted"></div>
						<div class="hr_dotted_bis"></div>
						<form:form id="formAdd" method="post" action="paramStdAdd" commandName="form">
							<spring:message code="page.param.entry" />&nbsp;<input name="newEntry" class="box_width_admin">
							<c:set var="addLabel"><spring:message code="page.param.add" /></c:set>
							<div style="float:right;">
								<span class="buttonBegin">							
									<input type="submit" class="button" value="${addLabel}">
								</span>
							</div>
						</form:form>
						<c:if test="${not empty form.parameters}">
							<br/>
							<div class="hr_dotted_bis"></div>
							<form:form id="formInput" method="post" action="paramStdSave" commandName="form">
								<c:set var="saveLabel"><spring:message code="page.param.save" /></c:set>
								<div style="float:right;">
									<span class="buttonBegin">								
										<input type="submit" class="button" value="${saveLabel}">
								</span></div>
								<c:forEach items="${form.parameters}" var="entry" varStatus="entryLoop">
									<table id="paramTable-${entryLoop.index}">
										<thead>
											<tr>
												<td>${entry.key}</td>
												<td class="clicable" onclick="deleteParam(${form.selectedType}, '${form.selectedCountry}','${entry.key}');"><spring:message code="page.param.delete" /></td>
											</tr>
											<tr>
												<td><spring:message code="page.param.table.language"/></td>
												<td><spring:message code="page.param.table.value"/></td>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${entry.value}" var="parameter" varStatus="paramLoop">
												<tr>
													<td>${parameter.language.locale.getDisplayLanguage(pageContext.response.locale)}</td>
													<td><form:input class="box_width_admin" path="parameters[${entry.key}][${paramLoop.index}].value"/></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<script type="text/javascript">
										$(document).ready(function(){
											$('#paramTable-'+${entryLoop.index}).dataTable({
												"bFilter":false,
												"bInfo":false,
												"bLengthChange":false,
												"bPaginate":false,
												"bJQueryUI": true
											});
										});
									</script>
								</c:forEach>
							</form:form>
						</c:if>
					</c:if>
				</c:if>
				<c:if test="${not form.standardAllCountries and not form.standardAllCountries}">
					<c:set var="typeEntity" value="<%=Constants.PARAM_TYPE_CUSTOM_ENTITY%>" />
					<c:set var="typeCountry" value="<%=Constants.PARAM_TYPE_CUSTOM_COUNTRY%>" />
					<c:set var="typeLang" value="<%=Constants.PARAM_TYPE_CUSTOM_LANG%>" />
					<c:set var="typeCommMessLogin" value="<%=Constants.PARAM_TYPE_CUSTOM_COMMERCIAL_MESSAGE_LOGIN%>" />
					<c:set var="typeCommMessClient" value="<%=Constants.PARAM_TYPE_CUSTOM_COMMERCIAL_MESSAGE_CLIENT%>" />
					<c:set var="typeCountryLang" value="<%=Constants.PARAM_TYPE_CUSTOM_COUNTRY_LANG%>" />
					<c:set var="typeCountryEntity" value="<%=Constants.PARAM_TYPE_CUSTOM_COUNTRY_ENTITY%>" />
					<c:choose>
						<c:when test="${form.selectedType == typeEntity}">
							<br/>
							<div class="hr_dotted"></div>
							<div class="hr_dotted_bis"></div>
							<form:form id="formAdd" method="post" action="paramEntityAdd" commandName="form">
								<spring:message code="page.param.entry" />&nbsp;<input name="newEntry">
								<c:set var="addLabel"><spring:message code="page.param.add" /></c:set>
								<div style="float:right;">
									<span class="buttonBegin">
									<input type="submit" class="button" value="${addLabel}">
								</span></div>
							</form:form>
							<br/>
							<div class="hr_dotted_bis"></div>
							<form:form id="formEntity" method="post" action="paramEntitySave" commandName="form">
								<c:set var="saveLabel"><spring:message code="page.param.save" /></c:set>
								<c:set var="editLabel"><spring:message code="page.param.table.editAddress" /></c:set>
								<div style="float:right;">
									<span class="buttonBegin">
										<input type="submit" class="button" value="${saveLabel}">
								</span></div>
								<table id="paramTable">
									<thead>
										<tr>
											<td><spring:message code="page.param.table.name" /></td>
											<td><spring:message code="page.param.table.address" /></td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${form.entities}" var="entity" varStatus="entityLoop">
											<tr>
												<td width="30%"><form:input class="box_width_admin" path="entities[${entityLoop.index}].label"/></td>
												<td width="70%">
													<div id="entityAddressPreview${entityLoop.index}">
														<c:choose>
															<c:when test="${not empty entity.address.fieldOne}">
																${entity.address.fieldOne}, ${entity.address.fieldTwo} ${entity.address.fieldThree} (${entity.address.fieldFour})
															</c:when>
															<c:otherwise>
																<spring:message code="page.param.table.addressNoPreview"/>
															</c:otherwise>
														</c:choose>
														<div class="buttonBegin">
															<input type="button" class="button editEntityAddress" data-toggle="#entityAddress${entityLoop.index}" value="${editLabel}">
														</div>
													</div>
													<div id="entityAddress${entityLoop.index}" class="entityAddressForm">
														<div id="address1Fields" class="content_field">
															<label class="content_label" for="address2">&nbsp;</label>
															<form:input type="text" autocomplete="off" name="address1"
																class="content_field inputAddress box_width_admin" placeholder="${placeHolderAddress}" size="39"
																 path="entities[${entityLoop.index}].address.fieldOne" id="address1"
																maxlength="100" onkeyup="javascript:copyFieldAddress(1);"></form:input>
															<div id="requiredImage"></div>
														</div>
														<div class="hr_dotted"></div>
								
														<label class="content_label" for="address2">&nbsp;</label>
														<div id="address2Fields" class="content_field requiredText">
															<form:input type="text" autocomplete="off" name="address2"
																class="content_field inputAddress box_width_admin" placeholder="${placeHolderZipCode}" size="39"
																 path="entities[${entityLoop.index}].address.fieldTwo" id="address2"
																maxlength="100" onkeyup="javascript:copyFieldAddress(2);"></form:input>
															<div id="requiredImage"></div>
														</div>
														<div class="hr_dotted"></div>
								
														<div id="address3Fields" class="content_field requiredText">
															<label class="content_label" for="address3">&nbsp;</label>
															<form:input type="text" autocomplete="off" name="address3"
																class="content_field inputAddress box_width_admin" placeholder="${placeHolderCity}" size="39"
																 path="entities[${entityLoop.index}].address.fieldThree" id="address3"
																maxlength="100" onkeyup="javascript:copyFieldAddress(3);"></form:input>
															<div id="requiredImage"></div>
														</div>
														<div class="hr_dotted"></div>
								
														<div id="address4Fields" class="content_field requiredText">
															<label class="content_label" for="address4">&nbsp;</label>
															<form:input type="text" autocomplete="off" name="address4"
																class="content_field inputAddress box_width_admin" placeholder="${placeHolderCountry}" size="39"
																 path="entities[${entityLoop.index}].address.fieldFour" id="address4"
																maxlength="100" onkeyup="javascript:copyFieldAddress(4);"></form:input>
															<div id="requiredImage"></div>
														</div>
														<div class="hr_dotted"></div>
								
														<label class="content_label">&nbsp;</label>
														<form:input type="text" autocomplete="off" name="address5" size="39"
															class="content_field inputAddress box_width_admin" path="entities[${entityLoop.index}].address.fieldFive"
															id="address5" maxlength="100" onkeyup="javascript:copyFieldAddress(5);"></form:input>
														<div class="hr_dotted"></div>
								
														<label class="content_label">&nbsp;</label>
														<form:input type="text" autocomplete="off" name="address6" size="39"
															class="content_field inputAddress box_width_admin" path="entities[${entityLoop.index}].address.fieldSix"
															id="address6" maxlength="100" onkeyup="javascript:copyFieldAddress(6);"></form:input>
														<div class="hr_dotted"></div>
								
														<label class="content_label">&nbsp;</label>
														<form:input type="text" autocomplete="off" name="address7" size="39"
															class="content_field inputAddress box_width_admin" path="entities[${entityLoop.index}].address.fieldSeven"
															id="address7" maxlength="100" onkeyup="javascript:copyFieldAddress(7);"></form:input>
														<div class="hr_dotted"></div>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<script type="text/javascript">
									$(document).ready(function(){
										$('#paramTable').dataTable({
											"bFilter":false,
											"bInfo":false,
											"bLengthChange":false,
											"bPaginate":false,
											"bJQueryUI": true
										});
										initAddressPreview();
									});
								</script>
							</form:form>
						</c:when>
						<c:when test="${form.selectedType == typeCountry}">
							<br/>
							<div class="hr_dotted"></div>
							<div class="hr_dotted_bis"></div>
							<form:form id="formAdd" method="post" action="paramCountryAdd" commandName="form">
								<spring:message code="page.param.entry" />&nbsp;
								<form:select autocomplete="on" path="newEntry">
									<c:forEach items="${form.allLocales}" var="locale">
										<form:option value="${locale.country}">${locale.getDisplayCountry(pageContext.response.locale)}</form:option>
									</c:forEach>
								</form:select>
								<script type="text/javascript">
									$(document).ready(function(){
										$('#newEntry option').sort(NASort).appendTo('#newEntry');
										$("#newEntry").val($("#newEntry option:first").val());
									});
								</script>
								<c:set var="addLabel"><spring:message code="page.param.add" /></c:set>
								<div style="float:right;">
									<span class="buttonBegin">
										<input type="submit" class="button" value="${addLabel}">
								</span></div>
							</form:form>
							<br/>
							<div class="hr_dotted_bis"></div>
							<form:form id="formCountry" method="post" action="paramCountrySave" commandName="form">
								<c:set var="saveLabel"><spring:message code="page.param.save" /></c:set>
								<div style="float:right;">
									<span class="buttonBegin">
										<input type="submit" class="button" value="${saveLabel}">
								</span></div>
								<table id="paramTable">
									<thead>
										<tr>
											<td><spring:message code="page.param.table.name" /></td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${form.countries}" var="country" varStatus="countryLoop">
											<tr>
												<td>${country.locale.getDisplayCountry(pageContext.response.locale)}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<script type="text/javascript">
									$(document).ready(function(){
										$('#paramTable').dataTable({
											"bFilter":false,
											"bInfo":false,
											"bLengthChange":false,
											"bPaginate":false,
											"bJQueryUI": true
										});
									});
								</script>
							</form:form>
						</c:when>
						<c:when test="${form.selectedType == typeLang}">
							<br/>
							<div class="hr_dotted"></div>
							<div class="hr_dotted_bis"></div>
							<form:form id="formAdd" method="post" action="paramLangAdd" commandName="form">
								<spring:message code="page.param.entry" />&nbsp;
								<form:select autocomplete="on" path="newEntry">
									<c:forEach items="${form.allLocales}" var="locale">
										<form:option value="${locale.language}">${locale.getDisplayLanguage(pageContext.response.locale)}</form:option>
									</c:forEach>
								</form:select>
								<script type="text/javascript">
									$(document).ready(function(){
										$('#newEntry option').sort(NASort).appendTo('#newEntry');
										$("#newEntry").val($("#newEntry option:first").val());
									});
								</script>
								<c:set var="addLabel"><spring:message code="page.param.add" /></c:set>
								<div style="float:right;">
									<span class="buttonBegin">
										<input type="submit" class="button" value="${addLabel}">
								</span></div>
							</form:form>
							<br/>
							<div class="hr_dotted_bis"></div>
							<form:form id="formLang" method="post" action="paramLangSave" commandName="form">
								<c:set var="saveLabel"><spring:message code="page.param.save" /></c:set>
								<div style="float:right;">
									<span class="buttonBegin">
										<input type="submit" class="button" value="${saveLabel}">
								</span></div>
								<table id="paramTable">
									<thead>
										<tr>
											<td><spring:message code="page.param.table.language" /></td>
											<td><spring:message code="page.param.table.interface.language" /></td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${form.languages}" var="language" varStatus="languageLoop">
											<tr>
												<td>${language.locale.getDisplayLanguage(pageContext.response.locale)}</td>
												<td><form:checkbox path="languages[${languageLoop.index}].userInterface"/></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<script type="text/javascript">
									$(document).ready(function(){
										$('#paramTable').dataTable({
											"bFilter":false,
											"bInfo":false,
											"bLengthChange":false,
											"bPaginate":false,
											"bJQueryUI": true
										});
									});
								</script>
							</form:form>
						</c:when>
						<c:when test="${form.selectedType == typeCommMessLogin}">
							<br/>
							<div class="hr_dotted"></div>
							<div class="hr_dotted_bis"></div>
							<form:form id="typeCommMessLogin" method="post" action="paramCommMessSave" commandName="form">
								<c:set var="saveLabel"><spring:message code="page.param.save" /></c:set>
								<div style="float:right;">
									<span class="buttonBegin">								
										<input type="submit" class="button" value="${saveLabel}">
								</span></div>
								<table id="paramTable">
									<thead>
										<tr>
											<td><spring:message code="page.param.table.language" /></td>
											<td><spring:message code="page.param.table.message" /></td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${form.languagesCommMess}" var="language" varStatus="languageLoop">
											<tr>
												<td>${language.locale.getDisplayLanguage(pageContext.response.locale)}</td>
												<td><form:textarea path="languagesCommMess[${languageLoop.index}].commercialMessageLogin" cssClass="param-comm-mess-txtarea"/></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<script type="text/javascript">
									$(document).ready(function(){
										$('#paramTable').dataTable({
											"bFilter":false,
											"bInfo":false,
											"bLengthChange":false,
											"bPaginate":false,
											"bJQueryUI": true
										});
									});
								</script>
							</form:form>
						</c:when>
						<c:when test="${form.selectedType == typeCommMessClient}">
							<br/>
							<div class="hr_dotted"></div>
							<div class="hr_dotted_bis"></div>
							<form:form id="formCommMessClient" method="post" action="paramCommMessSave" commandName="form">
								<c:set var="saveLabel"><spring:message code="page.param.save" /></c:set>
								<div style="float:right;">
									<span class="buttonBegin">								
										<input type="submit" class="button" value="${saveLabel}">
								</span></div>
								<table id="paramTable">
									<thead>
										<tr>
											<td><spring:message code="page.param.table.language" /></td>
											<td><spring:message code="page.param.table.message" /></td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${form.languagesCommMess}" var="language" varStatus="languageLoop">
											<tr>
												<td>${language.locale.getDisplayLanguage(pageContext.response.locale)}</td>
												<td><form:textarea path="languagesCommMess[${languageLoop.index}].commercialMessageClient" cssClass="param-comm-mess-txtarea"/></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<script type="text/javascript">
									$(document).ready(function(){
										$('#paramTable').dataTable({
											"bFilter":false,
											"bInfo":false,
											"bLengthChange":false,
											"bPaginate":false,
											"bJQueryUI": true
										});
									});
								</script>
							</form:form>
						</c:when>
						<c:when test="${form.selectedType == typeCountryLang}">
							<br/>
							<div class="hr_dotted"></div>
							<div class="hr_dotted_bis"></div>
							<form:form id="formCountryLang" method="post" action="paramCountryLangSave" commandName="form">
								<c:set var="saveLabel"><spring:message code="page.param.save" /></c:set>
								<div style="float:right;">
									<span class="buttonBegin">								
										<input type="submit" class="button" value="${saveLabel}">
								</span></div>
								<table id="paramTable">
									<thead>
										<tr>
											<td><spring:message code="page.param.table.country" /></td>
											<td><spring:message code="page.param.table.language" /></td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${form.countries}" var="country" varStatus="countryLoop">
											<tr>
												<td>${country.locale.getDisplayCountry(pageContext.response.locale)}
													<br><br>
													<spring:message code="page.param.table.allowLangSelection" />
													<spring:message code="page.param.table.yes" />
													<form:radiobutton id="enableLangChoice${countryLoop.count}" class="radioButton" path="enableComLang.map[${country.id}]"  value="true"/>
													<spring:message code="page.param.table.no" />
													<form:radiobutton id="disableLangChoice${countryLoop.count}" class="radioButton" path="enableComLang.map[${country.id}]" value="false"/>
												</td>
												<td>
													<div>
														<c:forEach items="${form.languages}" var="language" varStatus="languageLoop">
															<div>
																<form:checkbox path="countryLang.map[${country.id}][${language.id}]"
																	id="country-lang-${country.id}-${language.id}"/>
																<label for="country-lang-${country.id}-${language.id}">${language.locale.getDisplayLanguage(pageContext.response.locale)}</label><br/>
															</div>
														</c:forEach>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<script type="text/javascript">
									$(document).ready(function(){
										$('#paramTable').dataTable({
											"bFilter":false,
											"bInfo":false,
											"bLengthChange":false,
											"bPaginate":false,
											"bJQueryUI": true
										});
										//Fix a display issue on the last radio buttons
										if($('#enableLangChoice1')[0].attributes.getNamedItem("checked")!=null){
											if($('#enableLangChoice1')[0].attributes.getNamedItem("checked").value == "checked"){
												$('#enableLangChoice1').attr("checked", "");
											}
										}
										if($('#disableLangChoice1')[0].attributes.getNamedItem("checked")!=null){
											if($('#disableLangChoice1')[0].attributes.getNamedItem("checked").value == "checked"){
												$('#disableLangChoice1').attr("checked", "");
											}
										}
									});
								</script>
							</form:form>
						</c:when>
						<c:when test="${form.selectedType == typeCountryEntity}">
							<br/>
							<div class="hr_dotted"></div>
							<div class="hr_dotted_bis"></div>
							<form:form id="formCountryEntity" method="post" action="paramCountryEntitySave" commandName="form">
								<c:set var="saveLabel"><spring:message code="page.param.save" /></c:set>
								<div style="float:right;">
									<span class="buttonBegin">								
										<input class="button" type="submit" value="${saveLabel}">
								</span></div>
								<table id="paramTable">
									<thead>
										<tr>
											<td><spring:message code="page.param.table.country" /></td>
											<td><spring:message code="page.param.table.legal.entity" /></td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${form.countries}" var="country" varStatus="countryLoop">
											<tr>
												<td>${country.locale.getDisplayCountry(pageContext.response.locale)}</td>
												<td>
													<form:select autocomplete="on" path="countryEntity.map[${country.id}]">
														<form:option value="0">&nbsp</form:option>
														<c:forEach items="${form.entities}" var="entity">
															<form:option value="${entity.id}">${entity.label}</form:option>
														</c:forEach>
													</form:select>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<script type="text/javascript">
									$(document).ready(function(){
										$('#paramTable').dataTable({
											"bFilter":false,
											"bInfo":false,
											"bLengthChange":false,
											"bPaginate":false,
											"bJQueryUI": true
										});
									});
								</script>
							</form:form>
						</c:when>
					</c:choose>
				</c:if>
			</c:if>
		</div>
	</fieldset>
</div>
<jsp:include page="footer.jsp" />