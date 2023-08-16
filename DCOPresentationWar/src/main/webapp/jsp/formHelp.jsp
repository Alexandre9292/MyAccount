<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<spring:message code="page.form.help.anchor.total.number" var="totalNumberAnchor" />

<jsp:include page="include.jsp" />
<script src="../js/formulaire.js"></script>
<script type="text/javascript">
var URL = document.location.href;
var iStart = URL.lastIndexOf('#')+1;
var iEnd = URL.length;
var num = URL.substring(iStart, iEnd);
$(document).ready(function(){
		initFormHelp(num, "${totalNumberAnchor}");
	});
</script> 
<%-- <script type="text/javascript">
	$(document).ready(function(){
		initFormHelp(0);
	});
</script>--%>
<div id="content">
	
	<div class="bannerTitle">
		<div class="bnpBanner">
			<spring:message code="page.form.help.title"/>
		</div>
	</div>
	<br/>	
	<c:forEach var="anchorNumber" begin="1" end="${totalNumberAnchor}">
		<div id="${anchorNumber}" style="display:none;">
			<h6><spring:message code="page.form.help.anchor.${anchorNumber}.title"/></h6><br/>
			<spring:message code="page.form.help.anchor.${anchorNumber}.text"/><br/>
			<br/>
		</div>
	</c:forEach>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		exitMyAccounts = false;
	});
</script>
<jsp:include page="footer.jsp" />