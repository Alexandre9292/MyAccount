<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="breadcrumbs">
<ul id="breadcrumbs-two">
	<c:choose>
		<c:when test="${formulaireDCOForm.breadcrumbs[0]}">
			<li><a href="#" onclick="$('#mainForm').attr('action', '${saveFormPath}/formulaire1'); $('#mainForm').removeAttr('onsubmit'); $('#mainForm').submit();" id="submit_etape1" class="accessible">
				<spring:message code="page.formulaire.web.message.etape1Breadcrumb" />
			</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="#" id="submit_etape1" class="no_reachable">
				<spring:message code="page.formulaire.web.message.etape1Breadcrumb" />
			</a></li>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${formulaireDCOForm.breadcrumbs[1]}">
			<li>
			<a href="#" onclick="$('#mainForm').attr('action', '${saveFormPath}/thirdParty'); $('#mainForm').removeAttr('onsubmit'); $('#mainForm').submit();" id="submit_thirdParty" class="accessible">
				<spring:message code="page.menu.client.third.party" />
			</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="#" id="submit_thirdParty" class="no_reachable">
				<spring:message code="page.menu.client.third.party" />
			</a></li>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${formulaireDCOForm.breadcrumbs[2]}">
			<li><a href="#" onclick="$('#mainForm').attr('action', '${saveFormPath}/formulaire3'); $('#mainForm').removeAttr('onsubmit'); $('#mainForm').submit();" id="submit_etape3" class="accessible">
				<spring:message code="page.formulaire.web.message.etape3Breadcrumb" />
			</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="#" id="submit_etape3" class="no_reachable">
				<spring:message code="page.formulaire.web.message.etape3Breadcrumb" />
			</a></li>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${formulaireDCOForm.breadcrumbs[3]}">
		    <li><a href="#" onclick="$('#mainForm').attr('action', '${saveFormPath}/formulaire4'); $('#mainForm').removeAttr('onsubmit'); $('#mainForm').submit();" id="submit_etape4" class="accessible">
		    	<spring:message	code="page.formulaire.web.message.etape4Breadcrumb" />
		    </a></li>
		</c:when>
		<c:otherwise>
		    <li><a href="#" id="submit_etape4" class="no_reachable">
		    	<spring:message	code="page.formulaire.web.message.etape4Breadcrumb" />
		    </a></li>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${formulaireDCOForm.breadcrumbs[4]}">
		    <li><a href="#" onclick="$('#mainForm').attr('action', '${saveFormPath}/formulaire5'); $('#mainForm').removeAttr('onsubmit'); $('#mainForm').submit();" id="submit_page5" class="accessible">
		    	<spring:message	code="page.formulaire.web.message.etape5Breadcrumb" />
		    </a></li>
		</c:when>
		<c:otherwise>
		    <li><a href="#" id="submit_page5" class="no_reachable">
		    	<spring:message	code="page.formulaire.web.message.etape5Breadcrumb" />
		    </a></li>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${formulaireDCOForm.breadcrumbs[5]}">
		    <li><a href="#" onclick="$('#mainForm').attr('action', '${saveFormPath}/formSummary'); $('#mainForm').removeAttr('onsubmit'); $('#mainForm').submit();" id="bc_summary" class="accessible">
		    	<spring:message	code="page.formulaire.web.message.stepSummaryBreadcrumb" />
		    </a></li>
		</c:when>
		<c:otherwise>
		    <li><a href="#" id="bc_summary" class="no_reachable">
		    	<spring:message	code="page.formulaire.web.message.stepSummaryBreadcrumb" />
		    </a></li>
		</c:otherwise>
	</c:choose>
</ul>
</div>
<br>