<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="push"></div>
</div>
<div id="footer" class="footer">
	<div class="page block-footer-content">
		<ul>
			<li><spring:message code="page.footer.copyright" /></li>
			<li>
				<a href="legalNotices"><spring:message code="page.footer.legalNotices"/></a>
			</li>
			<li style="display: none;">
				1.0.41
			</li>
		</ul>
		<p>
			<a href="<spring:message code="page.footer.bnp.link" />" target="_blank"><spring:message code="page.footer.bnp" /></a>
		</p>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(cookieDisplayDisclaimer);
</script>
<div id="cookie-disclaimer" class="c-message" style="display: none;">
	<div class="page">
		<a href="javascript:cookieAcceptDisclaimer();" class="dismiss js-dismiss"><spring:message code="page.cookie.accept"/></a>
		<p><spring:message code="page.cookie.info"/></p>
	</div>
</div>
</body>
</html>