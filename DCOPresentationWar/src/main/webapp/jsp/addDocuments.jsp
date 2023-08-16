<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="include.jsp" />
<script type="text/javascript">
	$(document).ready($('#menu-documents').addClass("main-menu-selected"));
</script>
<div id="content" class="container">

	<jsp:include page="error.jsp" />

	<fieldset>
		<div class="bannerTitle">
			<div class="bnpBanner">
				<spring:message code="page.document.add.document.feature" />
		</div></div>
	
		<form:form method="post" action="doAddDocument" id="uploadForm"
			commandName="documentForm" enctype="multipart/form-data">
			<div class="content_field_block">

				<label class="content_label" for="country"><spring:message
						code="page.document.searching.country" /></label>
				<form:select autocomplete="on" name="country" path="country"
					id="country" class="content_field">

					<form:option value="-1" onclick="javascript:fetchLegalEntity(-1)">
						<spring:message code="page.all" />
					</form:option>
					<c:forEach items="${documentFilterForm.countrySort}" var="c">
						<form:option value="${c.id}"
							onclick="javascript:fetchLegalEntity(${c.id})">${c.label}</form:option>
					</c:forEach>
				</form:select>

				<div class="hr_dotted"></div>

				<label class="content_label" for="legalEntity"><spring:message
						code="page.document.legal.entity" /></label>
				<form:select autocomplete="on" name="legalEntity" path="legalEntity"
					id="legalEntity" class="content_field">

					<form:option value="-1" onclick="javascript:fetchCountry(-1)">
						<spring:message code="page.document.dropdownlistlegal.entity" />
					</form:option>

					<c:forEach items="${documentFilterForm.legalEntityList}"
						var="legal">
						<form:option value="${legal.id}"
							onclick="javascript:fetchCountry(${legal.id})">${legal.label}</form:option>
					</c:forEach>

				</form:select>
				<div class="hr_dotted"></div>

				<label class="content_label" for="language"><spring:message
						code="page.document.searching.language" /></label>
				<form:select autocomplete="on" name="language" path="language"
					id="language" class="content_field">

					<form:option value="-1">
						<spring:message code="page.all.bis" />
					</form:option>
					<c:forEach items="${documentFilterForm.languageList}" var="l">
						<form:option value="${l.id}">${l.locale.getDisplayLanguage(pageContext.response.locale)}</form:option>
					</c:forEach>
				</form:select>
				<div class="hr_dotted"></div>

				<label class="content_label" for="documentType"><spring:message
						code="page.document.searching.document.type" /></label>
				<form:select autocomplete="on" name="documentType"
					path="documentType" id="documentType" class="content_field">

					<form:options items="${documentFilterForm.documentTypeList}"
						itemLabel="label" itemValue="id" />
				</form:select>
				<div class="hr_dotted"></div>

				<label class="content_label" for="documentTitle">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message code="page.document.searching.document.title" />
				</label>
				<form:input type="file" path="file" class="content_field"
					id="fileupload" />
				<div class="hr_dotted"></div>

				<label class="content_label" for="resident">
					<spring:message code="page.document.searching.document.resident" />
				</label>
				<form:checkbox class="content_field" name="resident" path="resident" id="resident"/>
				<div class="hr_dotted"></div>

				<label class="content_label" for="isXbasV2">
					<spring:message code="page.document.searching.document.isXbasV2" />
				</label>
				<form:checkbox class="content_field" name="xbasV2" path="xbasV2" id="isXbasV2"/>
			</div>
			<div class="hr_dotted"></div>
			<div style="float:left;">
			<input type="button" value="<spring:message code="page.return"/>"
				class="button" id="returnButton">
			</div>

			<div style="float:right;">
				<span class="buttonBegin">
					<input type="submit" id="addDocumentButton"
						value="<spring:message code="page.record"/>" class="button"
						name="addDocumentButton" />
				</span>
			</div>

			<input type="hidden" id="confirmMatchingAll"
				value="<spring:message code="page.document.add.confirmMatchingAll"/>" />

			<input type="hidden" id="confirmMatching"
				value="<spring:message code="page.document.add.confirmMatching"/>" />

			<form:input type="hidden" path="confirm" id="confirm" />
			<form:input type="hidden" path="matchAll" id="matchAll" />
			<form:input type="hidden" path="match" id="match" />
			
		<div class="hr_dotted"></div>
		</form:form>

	</fieldset>
</div>
<script type="text/javascript" src="../js/documents.js"></script>

<script>

	$(function() {
		var controller = "../action/doAddDocumentConfirm";
	
// 		if("${documentForm.matchAll}"){
		if($("#matchAll").val() == 'true'){
			if (confirm($("#confirmMatchingAll").val())) {
				$("#confirm").val('true');
				$('body').append("<div class='top-layer'></div>");
				exitMyAccounts = false;
				window.location.replace(controller);
			}	
		}
// 		else if("${documentForm.match}"){
		else if($("#match").val() == 'true'){
			if (confirm($("#confirmMatching").val())) {
				$("#confirm").val('true');
				$('body').append("<div class='top-layer'></div>");
				exitMyAccounts = false;
				window.location.replace(controller);
			}	
		}
	});

	$("#returnButton").click(function(){
		exitMyAccounts = false;
		var controller = "../action/doCancelling";
		window.location.replace(controller);
	});


	$("#addDocumentButton").click(function(){
		if($("#fileupload").val() != ""){
			$('body').append("<div class='top-layer'></div>");
		}
	});

</script>

<jsp:include page="footer.jsp" />