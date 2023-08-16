<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>

<!DOCTYPE html>
<html lang="fr">
<head>
	<meta charset="UTF-8">
	<title>Welcome to MyAccount</title>
	<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Open+Sans+Condensed:700' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="../../css/new/main.css">
	<link rel="stylesheet" href="../../css/new/jquery-ui.min.css">
	<script src="../../js/new/jquery-1.12.4.min.js"></script>
	<script src="../../js/new/jquery-ui.min.js"></script>
	<script src="../../js/new/homeCompany.js"></script>
	<script src="../../js/new/newCompany.js"></script>
	<script src="../../js/new/main.js"></script>
</head>
<body class="bnpAccount">
	<main class="bnpAccount-main">
		<jsp:include page="headerMenu.jsp" />
		
		<div class="bnpAccount-companyResume bnpAccount-plr-mainContent">
			<form:form method="post" action="changeOrCreateCompany" commandName="userCompaniesForm" id="selectCompany">
				<form:hidden id="selectedTagValue" path="selectedTag"/>
				
				<h2 class="bnpAccount-companyResume-subTitle"><spring:message code="web.header.title.welcome.submessage" /></h2>		
		
				<!-- Onglets -->
				<footer class="bnpAccount-companyResume-footer">
					<div class="bnpAccount-pullRight">
						<button type="button" class="bnpAccount-btn bnpAccount-btn--normal--companyResume" id="bnpAccount-btn--backScrollResume"><<</button>
						<button type="button" class="bnpAccount-btn bnpAccount-btn--normal--companyResume" id="bnpAccount-btn--forwardScrollResume">>></button>
					</div>
					<div class="bnpAccount-companyResume-scrollContainer">
						<div class="bnpAccount-companyResume-scroll">
							<c:forEach items="${userCompaniesForm.userEntities}" var="entity" varStatus="count">
		<%-- 						<h2 class="bnpAccount-inscriptionList-mainTitle bnpAccount-companyResume-footer-mainTitle">${entity.label}</h2> --%>
								<button type="button" onclick="changeSelectedTagValue(${count.index}); submit();" id="Company${count.index}" class="bnpAccount-btn bnpAccount-btn--normal--companyResume">${entity.label}</button>
							</c:forEach>
							<button type="button" onclick="changeSelectedTagValue(-1); submit();" id="Company-1" class="bnpAccount-btn bnpAccount-btn--normal--companyResume"><spring:message code="page.menu.client.newCompany" /></button> <!-- window.location.href='newCompanyLoad' -->
						</div>
					</div>
				</footer>
			</form:form>
		</div>
			
		<!-- Contenu de l'onglet -->
		<c:if test = "${userCompaniesForm.selectedTag >= 0 && !userCompaniesForm.modeEdition}">
			<c:set var="entity" value="${userCompaniesForm.selectedEntity}" scope="request"/>
			<jsp:include page="detailCompany.jsp" />
		</c:if>
		
		<c:if test = "${userCompaniesForm.selectedTag == -1 || userCompaniesForm.modeEdition}">
			<jsp:include page="newCompany.jsp" />
		</c:if>
	</main>
	
	<jsp:include page="footer.jsp" />
</body>
</html>