<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="include.jsp" />
<script type="text/javascript">
	$(document).ready($('#menu-stats').addClass("main-menu-selected"));
</script>
<div id="content">

	<jsp:include page="error.jsp" />
	<div class="bannerTitle">
		<div class="bnpBanner">
			<spring:message code="page.stats.title" />
		</div>
	</div>

	<form:form method="post" action="statsCSV" commandName="statsForm"
		id="mainForm">

		<form:radiobutton path="typeStat" value="1" id="type1"/> 
		<label class="block-footer-content" for="type1">
			<spring:message code="page.stats.type1" />
		</label>
		<BR>
		<form:radiobutton path="typeStat" value="2"  id="type2"/> 
		<label class="block-footer-content" for="type2">
			<spring:message code="page.stats.type2" />
		</label>
		<BR>		
		<form:radiobutton path="typeStat" value="3"  id="type3"/> 
		<label class="block-footer-content" for="type3">
			<spring:message code="page.stats.type3" />
		</label>
		<BR>	
		<form:radiobutton path="typeStat" value="4"  id="type4"/> 
		<label class="block-footer-content" for="type4">
			<spring:message code="page.stats.type4" />
		</label>
		<BR>	
		<form:radiobutton path="typeStat" value="5"  id="type5"/> 
		<label class="block-footer-content" for="type5">
			<spring:message code="page.stats.type5" />
		</label>
		<BR>	
		<form:radiobutton path="typeStat" value="6"  id="type6"/> 
		<label class="block-footer-content" for="type6">
			<spring:message code="page.stats.type6" />
		</label>
		<BR>
		<form:radiobutton path="typeStat" value="7"  id="type7"/> 
		<label class="block-footer-content" for="type7">
			<spring:message code="page.stats.type7" />
		</label>
		<BR>
<!-- 		TODO YCA: last stats type -->
<%-- 		<form:radiobutton path="typeStat" value="8"  id="type8"/>  --%>
<!-- 		<label class="block-footer-content" for="type8"> -->
<%-- 			<spring:message code="page.stats.type8" /> --%>
<!-- 		</label> -->
<!-- 		<BR> -->

			<div class="buttonOnRight">
				<span class="buttonBegin">
				<input type="submit"
					value="<spring:message code="page.stats.apply"/>"
					class="button" id="statsApply">
			</span>
		</div>
		<BR>

	</form:form>
</div>
<jsp:include page="footer.jsp" />

<c:choose>
    <c:when test="${(not empty statsForm.csv) and (not empty statsForm.fileName)}">
     <script type="text/javascript">
    	var controller = "../action/getCSV";
    	window.open(controller);
	</script>
    </c:when>
    <c:otherwise>
     <script type="text/javascript">
        $("#mainForm").attr("action", "statsCSV");
	</script>
    </c:otherwise>
</c:choose>