
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>

<!DOCTYPE html>
<!--[if lt IE 9]>         <html class="lt-ie9"> <![endif]-->
<!--[if lt IE 10]>         <html class="lt-ie10"> <![endif]-->
<!--[if gte IE 9]><!--> <html> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>BNP - My Account - Home</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <link rel="shortcut icon" type="image/x-icon" href="../../img/new/favicon.ico">
        <link rel="stylesheet" href="../../css/new/style.css">
        <!--[if lt IE 10]>
          <script src="../../js/new/libs/html5shiv.js"></script>
          <script src="../../js/new/libs/respond-1.4.2.js"></script>
        <![endif]-->
        <!--[if lt IE 9]>
          <script src="../../js/new/libs/selectivizr-min.js"></script>
        <![endif]-->
    </head>
    
    <body>
        <header>
        	<!-- Brand Bar -->
        	<div class="brandbar hidden-xs hidden-sm"></div>
        	 
        	<!-- Header -->
        	<div class="topbar">
        		<div class="container">
        			<div class="row">
        				<div class="col-md-9">
        					<!-- classe has-sitename a ajouter si l'element sitename est présent -->
        					<h1 class="main-logo has-sitename">
        						<a href="<%=WebConstants.SIGNUP_LOAD%>"><spring:message code="web.header.bnpp" /></a> 
        						<span class="baseline"><spring:message code="web.header.bank" /></span>
        					</h1>
        				</div>
        				<!-- Core feature sur desktop (3 colonnes)  -->
        				<div class="col-md-3 hidden-xs hidden-sm">
        					<a href="<%=WebConstants.NEW_LOGIN_LOAD%>" class="core-feat">
        						<span><spring:message code="page.formulaire.web.message.home.login" /></span>
        					</a>
        				</div>
        			</div>
        		</div>
        	</div>    
        </header>
		
		<!-- Contenu -->
		<section class="main">
			<div class="page-header">
				<div class="video-wrapper">
					<video autoplay loop>
						<source src="../../video/video.mp4" type="video/mp4">
						 Your browser does not support the video tag.
					</video>
				</div>
				<div class="cache"></div>
				<div class="container">
					<jsp:include page="../error.jsp" />
					<div class="page-header_title"><spring:message code="page.formulaire.web.message.home.signup.text" /></div>
					<form:form method="post" action="newUserAccount" commandName="newLoginForm" id="formAccount">
						<ul class="fields-list x4">
							<spring:message code="page.formulaire.web.message.home.email" var="email"/>
							<spring:message code="page.formulaire.web.message.home.password" var="password"/>
							<spring:message code="page.formulaire.web.message.home.verification" var="verif"/>
							
							<li><form:input type="text" path="userMail" placeholder="${email}"></form:input></li>
							<li><form:input type="password" path="password"  id="password" placeholder="${password}"></form:input><img src="../../img/new/eye.png" class="bt-password" alt="show/hide password"></li>
							<li><form:input type="password" path="passwordConfirmation" placeholder="${verif}"></form:input></li>
							<li><button type="submit" value="Create"><spring:message code="page.formulaire.web.message.home.signup" /></button></li>
						</ul>
					</form:form>
				</div>

				<div class="scrolldown">
					<div class="arrow-down"></div>
					<div class="arrow-down"></div>
				</div>
			</div>
		
			<div class="page-content">
				<div class="container">
					<img src="../../img/new/map.png" alt="" class="map">
					<div class="title"><span><spring:message code="web.header.title.firstPart" /><img src="../../img/new/corner.png" alt=""></span></div>
					<div class="subtitle"><span>@<spring:message code="web.header.title.secondPart" /></span></div>
					<div class="description"><spring:message code="page.formulaire.web.message.home.detail.firstPart" /> <span class="green"><spring:message code="page.formulaire.web.message.home.detail.secondPart" /></span></div>
					<div class="lists">
						<ul>
							<li><spring:message code="page.formulaire.web.message.country.austria" /></li>
							<li><spring:message code="page.formulaire.web.message.country.belgium" /></li>
							<li><spring:message code="page.formulaire.web.message.country.bulgaria" /></li>
							<li><spring:message code="page.formulaire.web.message.country.czech" /></li>
							<li><spring:message code="page.formulaire.web.message.country.denmark" /></li>
							<li><spring:message code="page.formulaire.web.message.country.france" /></li>
							<li><spring:message code="page.formulaire.web.message.country.germany" /></li>
							<li><spring:message code="page.formulaire.web.message.country.hungary" /></li>
							<li><spring:message code="page.formulaire.web.message.country.ireland" /></li>
							<li><spring:message code="page.formulaire.web.message.country.italy" /></li>
						</ul>
						<ul>
							<li><spring:message code="page.formulaire.web.message.country.luxembourg" /></li>
							<li><spring:message code="page.formulaire.web.message.country.netherlands" /></li>
							<li><spring:message code="page.formulaire.web.message.country.norway" /></li>
							<li><spring:message code="page.formulaire.web.message.country.poland" /></li>
							<li><spring:message code="page.formulaire.web.message.country.portugal" /></li>
							<li><spring:message code="page.formulaire.web.message.country.romania" /></li>
							<li><spring:message code="page.formulaire.web.message.country.spain" /></li>
							<li><spring:message code="page.formulaire.web.message.country.sweden" /></li>
							<li><spring:message code="page.formulaire.web.message.country.switzerland" /></li>
							<li><spring:message code="page.formulaire.web.message.country.uk" /></li>
						</ul>
					</div>
				</div>
			</div>
		</section>
		
		
		<jsp:include page="footer.jsp" />
		
		<script src="../../js/new/libs/jquery-1.11.0.min.js"></script>
		<script src="../../js/new/script.js"></script>
    </body>
</html>