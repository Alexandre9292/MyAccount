<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="infoBlock" class="infoBlock">
	<c:if test="${not empty messages.errors}">
		<div class="errorblock">
			<c:forEach items="${messages.errors}" var="error">${error}<br/></c:forEach>
		</div>
	</c:if>
	<c:if test="${not empty messages.warnings}">
		<c:forEach items="${messages.warnings}" var="warning"><div class="warningblock">${warning}<br/></div></c:forEach>
	</c:if>
	<c:if test="${not empty messages.confirms}">
		<div class="confirmblock">
			<c:forEach items="${messages.confirms}" var="confirm">${confirm}<br/></c:forEach>
		</div>
	</c:if>
	<spring:eval expression="@genericController.resetMessages()" />
</div>