<%@page import="com.bnpp.dco.common.constant.Constants"%>
<%@page import="com.bnpp.dco.presentation.utils.constants.WebConstants"%>
<%@page import="com.bnpp.dco.presentation.utils.PropertiesHelper"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<sec:authorize access="isAuthenticated()">
	<nav id="main-nav" role="navigation">
		<sec:authentication property="principal.preferences" var="userPreferences" />
		<sec:authentication property="principal.passwordUpToDate" var="passwordUpToDate" />
<%-- 		<c:if test="${(not empty userPreferences) and passwordUpToDate}"> --%>
			<ul id="menu-list" class="main-menu clearfix">
				<sec:authentication property="principal.profile" var="userProfile" />
				<c:set value="<%=Constants.PROFILE_BANK%>" var="profileBank" />
				<c:set value="<%=Constants.ROLE_SUPER_ADMIN%>" var="roleSA" />
				<c:set value="<%=Constants.ROLE_MGMT_ACCOUNT%>" var="roleMgmtAccnt" />
				<c:set value="<%=Constants.ROLE_MGMT_DOCUMENTS%>" var="roleMgmtDoc" />
				<c:set value="<%=Constants.ROLE_VIEW_CLIENT_DATA%>"
					var="roleViewCltData" />
				<c:set value="<%=Constants.ROLE_VIEW_STATISTICS%>"
						var="roleViewStat" />
				<c:set value="<%=Constants.ROLE_MGMT_PARAMETERS%>"
					var="roleMgmtParam" />

				<c:choose>
					<c:when test="${userProfile == profileBank}">
						<sec:authorize access="hasRole('${roleSA}')">
							<li>
								<a id="menu-users" target="_self" href="#">
									<spring:message code="page.menu.bank.user" />
								</a>
								<ul id="menu-usermgmt" class="fallback">
									<li><a target="_self" href="<%=WebConstants.BANK_ACCOUNT_MANAGEMENT_LOAD%>">
										<spring:message code="page.menu.bank.user.bank" />
									</a></li>
<%-- 									<li><a target="_self" href="<%=WebConstants.CLIENT_MANAGEMENT_LOAD%>"> --%>
<%-- 										<spring:message code="page.menu.bank.user.client" /> --%>
<!-- 									</a></li> -->
								</ul>
							</li>
						</sec:authorize>
						<sec:authorize access="hasRole('${roleMgmtAccnt}')">
							<li>
								<a id="menu-users" target="_self" href="<%=WebConstants.CLIENT_MANAGEMENT_LOAD%>">
									<spring:message code="page.menu.bank.user.client" />
								</a>
							</li>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('${roleSA}','${roleMgmtDoc}')">
							<li>
								<a id="menu-documents" target="_self" href="<%=WebConstants.DOCUMENTS_LOAD%>">
									<spring:message code="page.menu.bank.document" />
								</a>
							</li>
						</sec:authorize>
<%-- 						<sec:authorize access="hasAnyRole('${roleSA}','${roleViewCltData}')"> --%>
<!-- 							<li> -->
<%-- 								<a id="menu-view-clt-data" target="_self" href="<%=WebConstants.CLIENT_DATA_LOAD%>"> --%>
<%-- 									<spring:message code="page.menu.bank.clientdata" /> --%>
<!-- 								</a> -->
<!-- 							</li> -->
<%-- 						</sec:authorize> --%>
						<sec:authorize access="hasAnyRole('${roleSA}','${roleViewStat}')">
							<li>
								<a id="menu-stats" target="_self" href="<%=WebConstants.STATS_LOAD%>">
									<spring:message code="page.menu.bank.statistics" />
								</a>
							</li>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('${roleSA}','${roleMgmtParam}')">
							<li>
								<a id="menu-param" target="_self" href="<%=WebConstants.PARAM%>">
									<spring:message code="page.menu.bank.parameters" />
								</a>
							</li>
						</sec:authorize>
					</c:when>
					<c:otherwise>
						<li class="first"><a id="menu-home-client" target="_self" href="<%=WebConstants.HOME_LOAD%>">
								<span class="menu-home-client"></span>
						</a></li>
						<li><a id="menu-form" target="_self" href="<%=WebConstants.INIT_FORM%>">
							<spring:message code="page.menu.client.form" />
						</a></li>
						<li class="menu-doc"><a id="menu-documents" target="_self" href="<%=WebConstants.DOCUMENTS_LOAD%>">
							<spring:message code="page.menu.client.document" />
						</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
<%-- 		</c:if> --%>
	</nav>
</sec:authorize>