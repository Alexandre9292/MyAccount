<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>

<!-- Meta footer  -->
<footer class="meta-footer">
	<div class="container">
		<div class="col-md-6 pull-left footer-baseline"><span class="footer-logo"></span><span><spring:message code="web.header.bank" /></span></div>
		<div class="col-md-5 col-md-offset-1">
			<ul class="footer-list pull-right">
<!-- 				<li><a href="#">Contacts</a></li> -->
<!-- 				<li><a href="#">RSS</a></li> -->
<!-- 				<li><a href="#">Sitemap</a></li> -->
				<li><a href="legalNotices"><spring:message code="page.footer.legalNotices" /></a></li>
				<li><spring:message code="page.footer.copyright" /></li>
			</ul>				
		</div>
	</div>
</footer>