<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>

<header class="bnpAccount-main-header bnpAccount-text--right">
	<h1 class="bnpAccount-main-header-title"><spring:message code="web.header.title.firstPart" /><span class="bnpAccount-highLight">@<spring:message code="web.header.title.secondPart" /></span></h1>
	<a href="<%=WebConstants.PROFILE_LOAD%>" class="bnpAccount-btn bnpAccount-btn--big bnpAccount-btn--important bnpAccount-text--center"><spring:message code="page.menu.client.profile" /></a>
	<a href="logout" class="bnpAccount-btn bnpAccount-btn--big bnpAccount-btn--action bnpAccount-text--center"><spring:message code="page.menu.client.logout.link" /></a>
</header>