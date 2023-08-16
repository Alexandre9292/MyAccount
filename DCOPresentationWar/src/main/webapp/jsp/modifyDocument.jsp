<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="include.jsp" />
<script type="text/javascript">
	$(document).ready($('#menu-documents').addClass("main-menu-selected"));
</script>
<div id="content">

	<jsp:include page="error.jsp" />


	<fieldset>
		<div class="bannerTitle">
			<div class="bnpBanner">
				<spring:message code="page.document.modify.document.pageTitle" />
		</div></div>

		<form:form method="post" action="doModifyDocument" commandName="documentForm">
			<div class="content_field_block">
				<label class="content_label" for="country">
					<spring:message	code="page.document.searching.country" />
				</label>
				<form:select autocomplete="on" name="country" path="country" id="country" class="content_field">
					<form:option value="-1" onclick="javascript:fetchLegalEntity(-1)">
						<spring:message code="page.all" />
					</form:option>
					<c:forEach items="${documentFilterForm.countrySort}" var="c">
						<form:option value="${c.id}" onclick="javascript:fetchLegalEntity(${c.id})">${c.label}</form:option>
					</c:forEach>
				</form:select>
				<div class="hr_dotted"></div>
				<label class="content_label" for="legalEntity">
					<spring:message	code="page.document.legal.entity" />
				</label>
				<form:select autocomplete="on" name="legalEntity" path="legalEntity" id="legalEntity" class="content_field">
					<form:option value="-1" onclick="javascript:fetchCountry(-1)">
						<spring:message code="page.document.dropdownlistlegal.entity" />
					</form:option>
					<form:options items="${documentFilterForm.legalEntityList}"	itemLabel="label" itemValue="id" onclick="javascript:fetchCountry(${legal.id})" />
				</form:select>
				<div class="hr_dotted"></div>
				<label class="content_label" for="language">
					<spring:message	code="page.document.searching.language" />
				</label>
				<form:select autocomplete="on" name="language" path="language" id="language" class="content_field">
					<form:option value="-1">
						<spring:message code="page.all.bis" />
					</form:option>
					<c:forEach items="${documentFilterForm.languageList}" var="l">
						<form:option value="${l.id}">${l.locale.getDisplayLanguage(pageContext.response.locale)}</form:option>
					</c:forEach>
				</form:select>
				<div class="hr_dotted"></div>
				<label class="content_label" for="documentType">
					<spring:message	code="page.document.searching.document.type" />
				</label>
				<form:select autocomplete="on" name="documentType" path="documentType" id="documentType" class="content_field">
					<form:options items="${documentFilterForm.documentTypeList}" itemLabel="label" itemValue="id" />
				</form:select>
				
				<div class="hr_dotted"></div>

				<label class="content_label" for="documentTitle">
					<span class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.document.searching.document.title" />
				</label>
				<form:input class="content_field box_width_admin" type="text" autocomplete="off" name="documentTitleWithoutExt" 
					path="documentTitleWithoutExt" id="documentTitleWithoutExt" size="50" maxlength="100" />
				<label class="content_field">
					<span>.${documentForm.documentExtension}</span>
				</label>
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
			<form:input type="hidden" path="id" />

			<div class="buttonOnRight">
				<span class="buttonBegin">
					<input type="button" value="<spring:message code="page.return"/>"
						class="button" id="returnButton">
				</span>
				<span class="buttonBegin">
					<input type="submit" id="modifyDocumentButton"
						value="<spring:message code="page.record"/>" 
						class="button" name="modifyDocumentButton">
				</span>
			</div>
			<input type="hidden" id="confirmCanceling"
				value="<spring:message code="page.document.cancelling"/>" />
		<div class="hr_dotted"></div>
		</form:form>

	</fieldset>

</div>
<script type="text/javascript" src="../js/documents.js"></script>
<jsp:include page="footer.jsp" />

<script>
$("#returnButton").click(function(){
	var controller = "../action/doCancelling";
		if (confirm($("#confirmCanceling").val())) {
			exitMyAccounts = false;
			window.location.replace(controller);
		}
		
	});
</script>