<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<spring:message code="page.legal.notices.terms.number" var="totalNumberTerm" />

<jsp:include page="include.jsp" />
<div id="content">
	<h3>
		<spring:message code="page.legal.notices.title"/>
	</h3>
	<br/><br/>
	<spring:message code="page.legal.notices.intro"/>
	<br/><br/>
	<spring:message code="page.legal.notices.compagny.information"/>
	<br/><br/>
	<spring:message code="page.legal.notices.terms.intro1"/>
	<br/><br/>
	<spring:message code="page.legal.notices.terms.intro2"/>
	<br/><br/>
	<c:forEach var="termNumber" begin="1" end="${totalNumberTerm}">
		<b>
			<c:out value="${termNumber}"/>&nbsp;<spring:message code="page.legal.notices.terms.label${termNumber}"/>
		</b>
		<br/>
		<spring:message code="page.legal.notices.terms.data${termNumber}"/>
		<br/><br/>
	</c:forEach>
</div>
<jsp:include page="footer.jsp" />