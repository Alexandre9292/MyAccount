<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%-- <jsp:include page="include.jsp" /> --%>
<div id="content">
	<div class="bannerTitle">
		<div class="bnpBanner">
			<spring:message code="page.technical.error.title" />
	</div></div>
	<br/>
	<br/>
	<spring:message code="page.technical.error" /><br/>
	<br/>
</div>
<%-- <jsp:include page="footer.jsp" /> --%>