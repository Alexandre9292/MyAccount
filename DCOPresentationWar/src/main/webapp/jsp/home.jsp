<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<sec:authentication property="principal" var="userProfile" />

<c:set value="${userProfile.localeEntity.getCountry()}"
	var="localeEntity" />
<c:set value="${userProfile.preferences.commercialMessageClient}"
	var="commercialMessageClient" />

<jsp:include page="include.jsp" />
<div id="content">
	<jsp:include page="error.jsp" />
	
	<div id="dialogForm" class="center" style="margin-bottom:10px;">
		${commercialMessageClient}
	</div>
	<div style="font-size: 16px; float:right; padding: 2px;">
		<label>
		<img src="../img/green.png" >
		<span><spring:message code="page.home.country.ready" /></span><BR>
		<img src="../img/green_light.png" >
		<span><spring:message code="page.home.country.soon" /></span><BR>
		<img src="../img/gray.png" >
		<span><spring:message code="page.home.country.later" /></span><BR>
		<img src="../img/gray_light.png" >
		<span><spring:message code="page.home.country.never" /></span><BR>
		</label>

	</div>

	<div id="canvas_europe"
		style="border: width: 680px; height: 520px; text-align: center"></div>
		
	<input type="hidden" value="${localeEntity}" id="localeEntity" />
	
	<div id="countryList">
	
	<c:forEach items="${loginForm.countries}" var="country" varStatus="varStatus">
		<input type="hidden" id="${country.label1}" value="${country.label2}">	
	</c:forEach>
	
	</div>
	
</div>
<script type="text/javascript" src="../js/raphael-min.js"></script>
<script type="text/javascript" src="../js/home.js"></script>
<script type="text/javascript">

	var tab = new Array();

	$("#countryList input:hidden").each(function(){
		var field = $(this);
		tab[field[0].id] = field[0].value;
	});

	loadMap("${userProfile.localeEntity.getCountry()}", tab, 680, 520);
</script>
<jsp:include page="footer.jsp" />