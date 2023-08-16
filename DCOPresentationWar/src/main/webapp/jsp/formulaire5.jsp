<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
	    
<jsp:include page="include.jsp" />
<script src="../js/formulaire.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		initForm5("${formulaireDCOForm.error}");
	});
</script>

<div id="content">
	<jsp:include page="error.jsp" />
	<c:set var="saveFormPath" value="saveForm5AndRedirect" scope="request"/>
	<jsp:include page="breadcrumbs.jsp" />
	<p>
		<spring:message code="page.formulaire.web.message.explanation.form5"/>
	</p>
	<div id="blocContainer">
		<form:form method="post" action="saveForm5" commandName="formulaireDCOForm" id="mainForm">
			<div class="hr_dotted"></div>
			<div class="content_field_block">
				<label class="content_label" for="legalRepresentative">
					<span class="warning">
						<spring:message code="page.asterisk" />
					</span>
					<spring:message code="page.formulaire.web.message.legalRepresentative" />
				</label>
				<form:select autocomplete="on" name="contact" multiple="multiple" path="entityRepresentatives" id="contact" class="content_field">
					<c:forEach items="${formulaireDCOForm.legalRepresentativesList}" var="legalRepresentative">
						<form:option value="${legalRepresentative.id}">${legalRepresentative.name}&nbsp;${legalRepresentative.firstName}</form:option>
					</c:forEach>
				</form:select>
				<div id="requiredImage"></div>
			</div>
			<div class="hr_dotted"></div>
			<div class="hr_dotted"></div>
			<div class="buttonOnRight">
				<span class="buttonBegin">
					<input type="button" id="submit_page5_back" class="button" value="<spring:message code="page.formulaire.web.message.previous"/>">
				</span> 
				<span class="buttonBegin">
					<input type="submit" id="submit_page5" class="button" value="<spring:message code="page.formulaire.web.message.save" />">
				</span> 
				<span class="buttonBegin">
					<input type="button" id="submit_page5_next"  class="button" value="<spring:message code="page.formulaire.web.message.next"/>">
				</span>
			</div>
			<div class="hr_dotted"></div>
		</form:form>
	</div>
</div>
<jsp:include page="footer.jsp" />