
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
	<script src="../../js/new/main.js"></script>
</head>
<body class="bnpAccount">
	<main class="bnpAccount-main">
		<jsp:include page="headerMenu.jsp" />
		
		<div class="bnpAccount-companyResume bnpAccount-plr-mainContent">
			<h1 class="bnpAccount-companyResume-mainTitle">WELCOME<br> to myaccount</h1>
			<h2 class="bnpAccount-companyResume-subTitle">Your manager for BNP account opening</h2>
		</div>
		<div class="bnpAccount-p-mainContent bnpAccount-listElement">
			<p><strong class="bnpAccount-legendLike">Customer</strong></p>
			<p class="bnpAccount-formIntro">
				Choose a customer or create a new one
			</p>
			<div class="bnpAccount-grid-2 bnpAccount-restrictedWidth">
				<p class="bnpAccount-fieldGroup">
					<label class="bnpAccount-labelRequired" for="bnpAccountCustomerMail">Mail adress</label>
					<input type="mail" required class="bnpAccount-field" name="bnpAccountCustomerMail" id="bnpAccountCustomerMail"></p>
				<p class="bnpAccount-fieldGroup">
					<label class="bnpAccount-labelRequired" for="bnpAccountCustomerFirstName">First Name</label>
					<input type="text" required class="bnpAccount-field" name="bnpAccountCustomerFirstName" id="bnpAccountCustomerFirstName">
				</p>
				<p class="bnpAccount-fieldGroup">
					<label class="bnpAccount-labelRequired" for="bnpAccountCustomerLastName">Last Name</label>
					<input type="text" required class="bnpAccount-field" name="bnpAccountCustomerLastName" id="bnpAccountCustomerLastName">
				</p>
			</div>
			<p>
				<button type="button" class="bnpAccount-btnReverse bnpAccount-btn--small bnpAccount-btnReverse--action bnpAccount-btn--add">+ Add custommer</button>
			</p>
		</div>
			<div class="bnpAccount-p-mainContent bnpAccount-listElement bnpAccount-inscriptionListItem--slidedUp">
				<div class="bnpAccount-signatoryBloc bnpAccount-inscriptionList-content bnpAccount-actionBtns-container">
					<p class="bnpAccount-inscriptionList-mainTitle">Alexandre SIMON</p>
					<dl class="bnpAccount-resumeDataList bnpAccount-resumeDataList--company">
						<dt>Company</dt><dd>SImonson SA</dd>
						<dt>E-Mail</dt><dd>alex@simonson.com</dd>
						<dt>Contact</dt><dd>emna.kamtri@bnpparibas.net</dd>
					</dl>
					<p class="bnpAccount-resumeDataList-actionBtns">
						<button class="bnpAccount-btn bnpAccountresumeDataList-action-btn">
							<img src="images/svg/create.svg" alt="">
						</button><button class="bnpAccount-btn bnpAccountresumeDataList-action-btn">
							<img src="images/svg/delete.svg" alt="">
						</button>
					</p>
				</div>
			</div>
			<div class="bnpAccount-p-mainContent bnpAccount-listElement bnpAccount-inscriptionListItem--slidedUp">
			<div class="bnpAccount-signatoryBloc  bnpAccount-inscriptionList-content bnpAccount-actionBtns-container">
				<p class="bnpAccount-inscriptionList-mainTitle bnpAccount-text--left">Alexandre SIMON<button type="button" class="bnpAccount-btn bnpAccount-btn--small bnpAccount-btn--action bnpAccount-text--left" id="bnpAccount-invite"><svg class="bnpAccount-svg" width="16" height="11" viewBox="0 0 16 11" xmlns="http://www.w3.org/2000/svg"><path d="M1 1l7.088 6.314 7.14-6.303L1 1zm14.04 8.806L10.22 5.59 8.085 7.474l-2.13-1.898-4.92 4.22 14.007.01zM1 1h14.04v8.806H1V1z" stroke="#FFF" fill="none" fill-rule="evenodd" stroke-linecap="round" stroke-linejoin="round"/></svg>invite
				</button><button type="button" class="bnpAccount-btn bnpAccount-btn--small bnpAccount-btn--action bnpAccount-text--left">Manage customer</button>
				</p>
				<dl class="bnpAccount-resumeDataList bnpAccount-resumeDataList--company">
					<dt>Company</dt><dd>SImonson SA</dd>
					<dt>E-Mail</dt><dd>alex@simonson.com</dd>
					<dt>Contact</dt><dd>emna.kamtri@bnpparibas.net</dd>
				</dl>
				<p class="bnpAccount-resumeDataList-actionBtns">
					<button class="bnpAccount-btn bnpAccountresumeDataList-action-btn">
						<img src="images/svg/create.svg" alt="">
					</button><button class="bnpAccount-btn bnpAccountresumeDataList-action-btn">
						<img src="images/svg/delete.svg" alt="">
					</button>
				</p>
			</div>
		</div>
	</main>
	<div class="bnpAccount-modalContainer">
		<div class="bnpAccount-modal bnpAccount-modal--small">
			<p class="bnpAccount-modal-title">THANK YOU TO SUBMIT</p>
			<p class="bnpAccount-formIntro">Please fill the following information to activate your account</p>
			<form>
				<div class="bnpAccount-grid-1">
					<p class="bnpAccount-fieldGroup">
						<label  class="bnpAccount-labelRequired" for="modalFirstName">First name</label>
						<input class="bnpAccount-field" id="modalFirstName" name="modalFirstName" required type="text">
					</p>
					<p class="bnpAccount-fieldGroup">
						<label class="bnpAccount-labelRequired" for="modalLastName">Last name</label>
						<input class="bnpAccount-field" required id="modalLastName" name="modalLastName" type="text"></p>
					<p class="bnpAccount-fieldGroup">
						<label class="bnpAccount-labelRequired" for="modalFirstPassword">Password</label>
						<input class="bnpAccount-field" required id="modalFirstPassword" name="modalFirstPassword" type="password"></p>
					<p class="bnpAccount-fieldGroup">
						<label class="bnpAccount-labelRequired" for="modalSecondPassword">Confirm your password</label>
						<input class="bnpAccount-field" required id="modalSecondPassword" name="modalSecondPassword" type="password">
					</p>
					<p class="bnpAccount-fieldGroup bnpAccount-text--center">
						<button type="submit" class="bnpAccount-btn bnpAccount-btn--normalSubmit bnpAccount-btn--action">Validate</button>
					</p>
				</div>
			</form>
		</div>
	</div>
	
	<jsp:include page="footer.jsp" />
</body>
</html>