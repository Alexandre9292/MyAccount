<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sec:authentication property="principal.preferences.dateFormat.label" var="dateFormat"/>
<c:set value="<%=Constants.ROLE_SUPER_ADMIN%>" var="roleSA" />
<c:set value="<%=Constants.ROLE_MGMT_DOCUMENTS%>" var="roleMgmtDoc" />
<c:set value="<%=Constants.PROFILE_BANK%>" var="profilBank" />
<sec:authentication property="principal.profile" var="userProfile" />

<jsp:include page="include.jsp" />
<script type="text/javascript">
	$(document).ready($('#menu-documents').addClass("main-menu-selected"));
</script>
<div id="content">

	<jsp:include page="error.jsp" />

	<form:form method="post" action="findDocument"
		commandName="documentFilterForm">
			<div class="content_field_block width95 bnpBorder center">
				<label class="content_label" for="country">
					<span class="warning"><spring:message code="page.asterisk" /></span>
					<spring:message code="page.document.searching.country" />
				</label>
				<form:select autocomplete="on" name="country" path="country"
					id="country" class="content_field">
					<form:option value="0">
						<spring:message code="page.document.country.filter" />
					</form:option>
					<form:option value="-1">
						<spring:message code="page.all" />
					</form:option>
					<c:forEach items="${documentFilterForm.countrySort}" var="c">
						<form:option value="${c.id}" legalEntityId="${c.login}">${c.label}</form:option>
					</c:forEach>
				</form:select>

				<c:if test="${userProfile == profilBank}">

					<br><br>

					<label class="content_label" for="legalEntity"><spring:message
							code="page.document.legal.entity" /></label>
					<form:select autocomplete="on" name="legalEntity"
						path="legalEntity" id="legalEntity" class="content_field">

						<form:option value="-1">
							<spring:message code="page.document.dropdownlistlegal.entity" />
						</form:option>

						<c:forEach items="${documentFilterForm.legalEntityList}"
							var="legal">
							<form:option value="${legal.id}">${legal.label}</form:option>
						</c:forEach>

					</form:select>
				</c:if>

				<form:hidden id="isDocumentsFilterBlockHidden" path="filterHidden" />
				<div id="hideDocumentsFilterBlock" style="display: none">
					<br><br>

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
					<br><br>

					<label class="content_label" for="documentType"><spring:message
							code="page.document.searching.document.type" /></label>
					<form:select autocomplete="on" name="documentType"
						path="documentType" id="documentType" class="content_field">

						<form:option value="-1">
							<spring:message code="page.all" />
						</form:option>
						<form:options items="${documentFilterForm.documentTypeList}"
							itemLabel="label" itemValue="id" />
					</form:select>
					<br><br>

					<label class="content_label" for="documentTitle"><spring:message
							code="page.document.searching.document.title" /></label>
					<form:input class="content_field box_width" type="text" autocomplete="off"
						name="documentTitle" path="documentTitle" id="documentTitle"
						maxlength="100" style="width: 200px"></form:input>
				</div>
				<BR>
			</div>

			<div class="buttonOnRight">
				<span class="buttonBegin">
	
					<input type="button" class="button" id="razDocumentsFilter"
						value="<spring:message code="page.document.raz.filter"/>"> 
				</span>
				<span class="buttonBegin">
					<input type="button" class="button" id="hideDocumentsFilterButton"
						value="<spring:message code="page.document.toggleUp.form"/>">
				</span>
				<span class="buttonBegin">	
					<input type="submit"
						value="<spring:message code="page.document.apply"/>" class="button">
				</span>
					<input type="hidden" id="toggleValueButton"
						value="<spring:message code="page.document.toggleDown.form"/>">
					
			</div>
			<br><br>
	</form:form>
	<div class="hr_dotted_bis"></div>
		<BR>
		<c:choose>
			<c:when test="${not empty documentFilterForm.documentList}">

				<form:form method="post" commandName="documentsCheckedForm"
					id="documentsCheckedForm" action="actionDocumentsChecked"
					onsubmit="return confirmAction()">
					<div id="documentsCheckedForm">
						<table id="documentsTable" style="width: 100%;">
							<thead>
								<tr>
									<th><spring:message
											code="page.document.searching.selection" /></th>
									<th><spring:message
											code="page.document.table.document.title" /></th>
									<th><spring:message code="page.document.table.country" /></th>
									<th><spring:message
											code="page.document.table.language" /></th>
									<th><spring:message
											code="page.document.table.document.type" /></th>
									<th><spring:message
											code="page.document.table.document.resident" /></th>
									<th><spring:message
											code="page.document.table.document.isXbasV2" /></th>
									<th><spring:message
											code="page.document.table.document.date" /></th>
									<c:if test="${userProfile == profilBank}">
										<th><spring:message code="page.document.table.legal.entity" /></th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${documentFilterForm.documentList}"
									var="document" varStatus="varStatus">
									<tr>
										<td><form:checkbox path="documentsChecked"
												name="documentsChecked" id="documentsChecked"
												value="${document.id}" /></td>

										<c:choose>
											<c:when test="${userProfile == profilBank}">
												<td class="clicable" title="${document.title}"
													onclick="javascript:modifyDocument(${document.id})">${document.title}</td>
											</c:when>
											<c:otherwise>
												<td title="${document.title}">${document.title}</td>
											</c:otherwise>
										</c:choose>
										
										<c:choose>
											<c:when test="${ not empty document.country}">
												<td>${document.country.locale.getDisplayCountry(pageContext.response.locale)}</td>
											</c:when>
											<c:otherwise>
												<td><spring:message code="page.document.country.all" /></td>
											</c:otherwise>
										</c:choose>
										
										<c:choose>
											<c:when test="${ not empty document.language}">
												<td>${document.language.locale.getDisplayLanguage(pageContext.response.locale)}</td>
											</c:when>
											<c:otherwise>
												<td><spring:message code="page.document.language.all" /></td>
											</c:otherwise>
										</c:choose>
										
										<td>${document.documentType.label}</td>
										
										<c:choose>
											<c:when test="${not empty document.resident}">
												<c:choose>
													<c:when test="${document.resident}">
														<td><spring:message code="page.document.table.document.is.resident" /></td>
													</c:when>
													<c:otherwise>
														<td><spring:message code="page.document.table.document.is.not.resident" /></td>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
										
										<c:choose>
											<c:when test="${not empty document.xbasV2}">
												<c:choose>
													<c:when test="${document.xbasV2}">
														<td><spring:message code="page.param.table.yes" /></td>
													</c:when>
													<c:otherwise>
														<td><spring:message code="page.param.table.no" /></td>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
										
										<td>
											<fmt:formatDate pattern="${dateFormat}" 
								            value="${document.uptodate}" />
										</td>
										<c:if test="${userProfile == profilBank}">
											<c:choose>
												<c:when test="${not empty document.legalEntity}">
													<td>${document.legalEntity.label}</td>
												</c:when>
												<c:otherwise>
													<td><spring:message code="page.document.legal.all" /></td>
												</c:otherwise>
											</c:choose>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<BR>
						<br><br>

						<div class="buttonOnRight">
						
							<sec:authorize access="hasAnyRole('${roleSA}','${roleMgmtDoc}')">
								<span class="buttonBegin">
									<input type="button" class="button" id="addDocument"
										value="<spring:message code="page.document.add" />">
								</span>
							</sec:authorize>
							<span class="buttonBegin">
								<input type="button" id="downloadDocumentsButton"
								value="<spring:message code="page.download"/>" class="button">
							</span>
							<sec:authorize access="hasAnyRole('${roleSA}','${roleMgmtDoc}')">
								<span class="buttonBegin">
									<input type="submit" id="deleteDocumentsButton"
									name="deleteDocumentsButton"
									value="<spring:message code="page.document.delete"/>"
									class="button">
								</span>
									<input type="hidden"
									value="<spring:message code="page.document.delete.confirm"/>"
									id="deleteConfirmValue">

							</sec:authorize>
						</div>

					</div>
				</form:form>
			</c:when>
			<c:otherwise>
				<p>
					<spring:message code="page.document.documents.not.found" />
				</p>
				
			<sec:authorize access="hasAnyRole('${roleSA}','${roleMgmtDoc}')">
				<div class="buttonOnRight">
					<span class="buttonBegin">
						<input type="button" class="button" id="addDocument"
							value="<spring:message code="page.document.add" />">
					</span>
				</div>
			</sec:authorize>
			
			</c:otherwise>
		</c:choose>
		<br><br>
</div>

<spring:message code="datatable.tabLengthMenu" var="tabLengthMenu" />
<spring:message code="datatable.tabSearch" var="tabSearch" />
<spring:message code="datatable.tabZeroRecords" var="tabZeroRecords" />
<spring:message code="datatable.tabFirst" var="tabFirst" />
<spring:message code="datatable.tabLast" var="tabLast" />
<spring:message code="datatable.tabNext" var="tabNext" />
<spring:message code="datatable.tabPrevious" var="tabPrevious" />

<script>
$(document).ready(function(){

	var oTable = $('#documentsTable').dataTable({
         	"sPaginationType": "bootstrap",

         	"oLanguage": {
         	    "sLengthMenu":     "${tabLengthMenu}",
         	    "sSearch":         "${tabSearch}",
         	    "sZeroRecords":    "${tabZeroRecords}",
         	    "oPaginate": {
         	        "sFirst":    "${tabFirst}",
         	        "sLast":     "${tabLast}",
         	        "sNext":     "${tabNext}",
         	        "sPrevious": "${tabPrevious}"
         	    }
           },
         	
         	"aoColumnDefs": [{ "bSortable": false, "aTargets": [ 0 ] }] 
   	});
	
});
$("#addDocument").click(function(){
	exitMyAccounts = false;
	var controller = "../action/addDocument";
	window.location.replace(controller);
});
</script>
<script type="text/javascript" src="../js/documents.js"></script>
<jsp:include page="footer.jsp" />