<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><spring:message code="page.title.start"/><spring:message code="page.title.end"/></title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<link rel="stylesheet" href="../css/jquery-ui.css">
<link rel="stylesheet" href="../css/jquery.dataTables.css">
<link rel="stylesheet" href="../css/main.css">
<link rel="stylesheet" href="../css/fonts.css">
<link rel="stylesheet" href="../css/select2.css">
<!--[if lt IE 9]>
<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<!-- /container -->
<script src="../js/jquery-1.8.3.min.js"></script>
<script src="../js/jquery-ui-1.10.3.min.js"></script>
<script src="../js/jquery.dataTables.js"></script>
<script src="../js/jquery.cookie.js"></script>
<script src="../js/plugins.js"></script>
<script src="../js/main.js"></script>
<script src="../js/select2.js"></script>
<script src="../js/jquery.dataTables.rowGrouping.js"></script>
<script src="../js/dataTables.scrollingPagination.js"></script>
<script>$(function () {
		$('nav li ul').hide().removeClass('fallback');
		$('nav li').hover(function () {
			$('ul', this).stop().slideToggle(200);
		});
	});</script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-76231432-1', 'auto');
  ga('send', 'pageview');

</script>
</head>
<body>
	<div class="wrapper">
		<header id="header" class="header">
			<div class="header-inner">
				<div class="page clearfix">
					<a href="../action/homeLoad" title="Home" rel="home" id="logo">
						<img src="../img/logo.png" alt="The bank for a changing world | BNP Paribas" />
					</a>
					<sec:authorize access="isAuthenticated()">
						<div id="header-userinfo" class="header-userinfo">
							<div id="header-entityblock" class="header-entityblock">
								<sec:authentication property="principal.entity" var="entityName"/>
								<c:if test="${not empty entityName}">
									${entityName}
								</c:if>
							</div>
						
							&nbsp;
							<a href="<%=WebConstants.FORM_HELPER%>">
								<img alt="<spring:message code="page.menu.help" />" src="../img/question_help.png" height="20" width="20">
							</a>
							&nbsp;
							<a href="<%=WebConstants.PREFERENCES_LOAD%>">
								<img alt="<spring:message code="page.menu.preferences.link" />" src="../img/settings.png" height="20" width="20">
							</a>
							&nbsp;
							<a href="logout">
								<img alt="<spring:message code="page.menu.client.logout.link" />" src="../img/poweroff.png" title="<spring:message code="page.menu.client.logout.link" />" height="20" width="20">
							</a>
	
							<div class="bannerUserNames">
								<span title='<sec:authentication property="principal.firstName"/>'>
									<sec:authentication property="principal.splitFirstName" />
								</span>
								<span title='<sec:authentication property="principal.LastName"/>'>
									<sec:authentication property="principal.splitLastName" />
								</span>
							</div>
							
						</div>					
					</sec:authorize>
				</div>
				<div class="page">
					<div id="header-titleblock" class="header-titleblock">
						<div class="content">
							<div id="header-titleblock-title-1"
								class="header-titleblock-title"><spring:message code="page.title.start"/>
							</div>
							<div id="header-titleblock-title-2"
								class="header-titleblock-title"><spring:message code="page.title.end"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</header>
	
		<jsp:include page="menu.jsp" />
	</div>
</html>
	