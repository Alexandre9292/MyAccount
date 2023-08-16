/**************************************************************************************************
 * 
 * 			SECTION GENERAL
 * 
 * ***********************************************************************************************/
function validateEmail(email) {
    var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    return re.test(email);
}

function validateTel(tel) {
    var re = /^\+([\+(]?(\d){2,}[)]?[- \.]?(\d){2,}[- \.]?(\d){2,}[- \.]?(\d){2,}[- \.]?(\d){2,})|([\+(]?(\d){2,}[)]?[- \.]?(\d){2,}[- \.]?(\d){2,}[- \.]?(\d){2,})|([\+(]?(\d){2,}[)]?[- \.]?(\d){2,}[- \.]?(\d){2,})/; 
    return re.test(tel);
}

function checkNumber(obj) {
	var numberReg = new RegExp("^[0-9]+$");
	return numberReg.test($(obj).val());
}

function formatNumber(obj) {
	if (!checkNumber()) {
		var notNumber = new RegExp("[^0-9]+", "g");
		var $obj = $(obj);
		$obj.val($obj.val().replace(notNumber, ""));
	}
}

// Open the helper Page
function openHelper(id) {
	var controller = "../action/formHelper#" + id;
	window.open(controller);
}

function initFormHelp(id, totalNumberAnchor) {
	if(id<"1" || id>"4"){
		for(var i=1; i<=totalNumberAnchor; i++){
			$("#"+i).show();
		}
	}
	else {
		if($("#"+id).css('display') == "none"){
			$("#"+id).show();
		}
	}
}

function checkRequiredFields(dom) {
	var ret = true;
	$("#" + dom + " .requiredText").each(function() {
				if ($("#" + this.id + " input:text").val() == "") {
					$("#" + this.id + " #requiredImage").removeClass().addClass("requiredFail");
					ret = false;
				} else {
					$("#" + this.id + " #requiredImage").removeClass().addClass("requiredSucceed");
				}
			});
	$("#" + dom + " .requiredSelect").each(function() {
				if ($("#" + this.id + " select").val() == "" || $("#" + this.id + " select").val() == 0 || $("#" + this.id + " select").val() == null) {
					$("#" + this.id + " #requiredImage").removeClass().addClass("requiredFail");
					ret = false;
				} else {
					$("#" + this.id + " #requiredImage").removeClass().addClass("requiredSucceed");
				}
			});
	$("#" + dom + " .requiredCheck").each(function() {
				var n = $("#" + this.id + " input:checked").length;
				if (n > 0) {
					$("#" + this.id + " #requiredImage").removeClass().addClass("requiredSucceed");
				} else {
					$("#" + this.id + " #requiredImage").removeClass().addClass("requiredFail");
					ret = false;
				}
				ret = (n > 0) && ret;
			});
	return true;
}

function checkSelect(id, idParent) {
	var value = $("#" + id).val() ;
	if (value == "" || value == 0 || value == null || value == -1) {
		$("#" + idParent + " #requiredImage").removeClass().addClass("requiredFail");
	} else {
		$("#" + idParent + " #requiredImage").removeClass().addClass("requiredSucceed");
	}
}

function checkInput(id, idParent) {
	if ($("#" + id ).val() == "" 
		|| $("#" + id ).val() == $("#" + id ).attr("placeholder")) {
		$("#" + idParent + " #requiredImage").removeClass().addClass("requiredFail");
	} else {
		$("#" + idParent + " #requiredImage").removeClass().addClass("requiredSucceed");
	}
}

function checkdate(idd, idm, idy, idParent) {
	if ($("#" + idd ).val() == "" || $("#" + idd ).val() == $("#" + idd ).attr("placeholder")
			|| $("#" + idm ).val() == "" || $("#" + idm ).val() == $("#" + idm ).attr("placeholder")
			|| $("#" + idy ).val() == "" || $("#" + idy ).val() == $("#" + idy ).attr("placeholder")) {

		$("#" + idParent + " #requiredImage").removeClass().addClass("requiredFail");
	} else {
		if(!checkNumber("#" + idd ) || !checkNumber("#" + idm ) || !checkNumber("#" + idy )){
			$("#" + idParent + " #requiredImage").removeClass().addClass("requiredFail");
		}else{
			$("#" + idParent + " #requiredImage").removeClass().addClass("requiredSucceed");
		}
	}
}

function checklength(element, element_suivant, v)
{
 if (v.length==element.maxLength)
 {
     element_suivant.focus();
 }
}


/**************************************************************************************************
 * 
 * 									SECTION FORM1
 * 
 *************************************************************************************************/

function initForm1(fieldsAddressesRequired, legalStatusOtherConstant, error) {
	if(error == "true"){
		checkRequiredFields("mainForm");
	}
	
	$('#menu-form').addClass("main-menu-selected");
	$("#submit_etape1").addClass("current");
	
	var selectedIndex = $("#country option:selected");
	$('#country option').sort(NASort).appendTo('#country');
	$("#country").val(selectedIndex.val());
		
	var legalStatus = $("#legalStatus option:selected").attr("id");
	toogleLegalStatusOther(legalStatus, legalStatusOtherConstant);
	tooglePostalAddress();
	
	$("#legalStatus").on("select2-close", function(){
		var legalStatus = $("#legalStatus option:selected").attr("id");
		toogleLegalStatusOther(legalStatus, legalStatusOtherConstant);
		checkSelect("legalStatus", "legalStatusFields");
	});
	$("#s2id_legalStatus").on("focusout	", function(){
	   	checkSelect("legalStatus", "legalStatusFields");
	});
	
	$("#legalStatusOther").on("blur", function(){
		checkInput("legalStatusOther", "legalStatusOtherDiv");
	});
	
	$("#commercialRegister").on("blur", function(){
		checkInput("commercialRegister", "commercialRegisterFields");
	});
	
	$("#taxInformation").on("blur", function(){
		checkInput("taxInformation", "taxInformationFields");
	});
	
	$("#country").on("select2-close", function(){
		checkSelect("country", "countryFields");
	});
	$("#s2id_country").on("focusout	", function(){
	   	checkSelect("country", "countryFields");
	});
	$("#country").on("blur	", function(){
	   	checkSelect("country", "countryFields");
	});
	
	$("#adresseSiege1").on("blur", function(){
		checkInput("adresseSiege1", "adresseSiege1Fields");
	});

	$("#adresseSiege2").on("blur", function(){
		checkInput("adresseSiege2", "adresseSiege2Fields");
	});

	$("#adresseSiege3").on("blur", function(){
		checkInput("adresseSiege3", "adresseSiege3Fields");
	});

	$("#adresseSiege4").on("blur", function(){
		checkInput("adresseSiege4", "adresseSiege4Fields");
	});
	
	$("#adressePostal1").on("blur", function(){
		checkInput("adressePostal1", "adressePostal1Fields");
	});
	
	$("#adressePostal2").on("blur", function(){
		checkInput("adressePostal2", "adressePostal2Fields");
	});

	$("#adressePostal3").on("blur", function(){
		checkInput("adressePostal3", "adressePostal3Fields");
	});
	
	$("#adressePostal4").on("blur", function(){
		checkInput("adressePostal4", "adressePostal4Fields");
	});

	tooltipMe("fieldsAddressesRequired", fieldsAddressesRequired);
	tooltipMe("fieldsPostalAddressesRequired", fieldsAddressesRequired);
}

function tooglePostalAddress() {
	var flag = true;
	for ( var i = 1; i < 8; i++) {
		if ($("#adressePostal" + i).val() != $("#adresseSiege" + i).val()) {
			flag = false;
		}
	}
	if (flag == true) {
		$("#check").attr("checked", "checked");
		for ( var i = 1; i < 8; i++) {
			$("#adressePostal" + i).attr("readonly", "true");
		}
		$("#postalAddress").hide();
	} else {
		$("#postalAddress").show();
		$("#postalAddress #requiredImage").each(function(){
			$(this).removeClass();
		});		
	}
}

function copyPostalAddress() {
	if ($("#check").is(":checked")) {
		$("#postalAddress").hide();
		$("#postalAddress #requiredImage").removeClass().addClass("requiredSucceed");
		for ( var i = 1; i < 8; i++) {
			$("#adressePostal" + i).val($("#adresseSiege" + i).val());
			$("#adressePostal" + i).attr("readonly", "true");
		}
	} else {
		var ua = $.browser;
		var isIE = false;
		if (ua.msie && ua.version.slice(0, 1) <= 9 && ua.version.slice(1, 2) == ".") {
			isIE = true; // for IE version <= 9.
		}
		$("#postalAddress").show();
		$("#postalAddress #requiredImage").removeClass();
		for ( var i = 1; i < 8; i++) {
			$("#adressePostal" + i).val("");
			$("#adressePostal" + i).removeAttr("readonly");
			if (isIE){
				$("#adressePostal" + i).val($("#adressePostal" + i).attr("placeholder"));
			}
		}
	}
}

function copyAddressField(fieldNumber) {
	if ($("#check").is(":checked")) {
		$("#adressePostal" + fieldNumber).val($("#adresseSiege" + fieldNumber).val());
	}
}

function toogleLegalStatusOther(legalStatus, legalStatusOtherConstant) {
	if (legalStatus.toUpperCase() == legalStatusOtherConstant.toUpperCase()) {
		$("#legalStatusOtherSpan").text($("#legalStatus option:selected").text() +":");
		$("#legalStatusOther").removeAttr("disabled");
		$("#legalStatusOtherSpace").show();
		$("#legalStatusOtherDiv").show();
	} else {
		$("#legalStatusOtherDiv").hide();
		$("#legalStatusOtherSpace").hide();
		$("#legalStatusOther").attr("disabled", true);
		$("#legalStatusOtherSpan").text($("#legalStatus option:selected").text() +":");
	}
}

function submitEtape1() {
	$("#submit_etape1").click(function() {
		$("#mainForm").attr("action", "backToForm1");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
}

/**************************************************************************************************
 * 
 * 										SECTION THIRDPARTY
 * 
 *************************************************************************************************/

function initThirdParty(patternTel, addressRequired, error, dateFormatDisplay){
	if(error == "true"){
		checkRequiredFields("mainForm");
		checkdate("birthDay", "birthMonth", "birthYear", "birthDateFields");
		checkSelect("citizenshipList", "citizenshipListFields");
		checkCheckBox();
		if(validateTel($("#tel").val()) == true){
			$("#telFields #requiredImage").removeClass().addClass("requiredSucceed");			
		}else{
			$("#telFields #requiredImage").removeClass().addClass("requiredFail");
		}
		if(validateEmail($("#mail").val()) == true){
			$("#mailFields #requiredImage").removeClass().addClass("requiredSucceed");			
		}else{
			$("#mailFields #requiredImage").removeClass().addClass("requiredFail");
		}
	}
	
	$('#menu-form').addClass("main-menu-selected");	
	$("#submit_thirdParty").addClass("current");
	
	$("#citizenshipList option").sort(NASort).appendTo("#citizenshipList");
	
	tooltipMe("addressFieldsRequired", addressRequired);
	tooltipMe("fieldsAdressPostalRequired", addressRequired);
	
	$("#submit_thirdParty_back").click(function() {
		$("#mainForm").attr("action", "backToForm1");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
	
	$("#delete_thirdParty").click(function(){
		if($("#thirdParty").val() != -1){
			if(confirm($("#delete_thirdParty").val() + " " + $("#name").val() + " " +  $("#firstName").val() +"?")){
				$("#mainForm").attr("action", "deleteThirdParty");
				exitMyAccounts = false;
				document.createElement('form').submit.call(document.thirdParty);
			}
		}
	});
	
	$("#name").on("blur", function(){
		checkInput("name", "nameFields");
	});
	
	$("#firstName").on("blur", function(){
		checkInput("firstName", "firstNameFields");
	});
	
	$("#position").on("blur", function(){
		if($("#positionFields").find('.content_field').attr("required") == "required"){
			checkInput("position", "positionFields");
		}else{
			$("#positionFields #requiredImage").removeClass();
		}
	});
	
	if(dateFormatDisplay == "yyyy/mm/dd"){
		$("#birthDay").on("blur", function(){
			if($("#birthDateFields").find('.content_field').attr("required") == "required"){
				checkdate("birthDay", "birthMonth", "birthYear", "birthDateFields");
			}else{
				$("#birthDateFields #requiredImage").removeClass();
			}
		});
	}
	else {
		$("#birthYear").on("blur", function(){
			if($("#birthDateFields").find('.content_field').attr("required") == "required"){
				checkdate("birthDay", "birthMonth", "birthYear", "birthDateFields");
			}else{
				$("#birthDateFields #requiredImage").removeClass();
			}
		});
	}
	
	$("#citizenshipList").on("select2-close", function(){
		if($("#citizenshipListFields").find('.content_field').attr("required") == "required"){
			checkSelect("citizenshipList", "citizenshipListFields");
		}else{
			$("#citizenshipListFields #requiredImage").removeClass();
		}
	});
	
	$("#s2id_citizenshipList").on("focusout	", function(){
		if($("#citizenshipListFields").find('.content_field').attr("required") == "required"){
			checkSelect("citizenshipList", "citizenshipListFields");
		}else{
			$("#citizenshipListFields #requiredImage").removeClass();
		}
	});
	
	$("#homeAddress1").on("blur", function(){
		if($("#homeAddress1Fields").find('.content_field').attr("required") == "required"){
			checkInput("homeAddress1", "homeAddress1Fields");
		}else{
			$("#homeAddress1Fields #requiredImage").removeClass();
		}
	});
	
	$("#homeAddress2").on("blur", function(){
		if($("#homeAddress1Fields").find('.content_field').attr("required") == "required"){
			checkInput("homeAddress2", "homeAddress2Fields");
		}else{
			$("#homeAddress2Fields #requiredImage").removeClass();
		}
		
	});
	
	$("#homeAddress3").on("blur", function(){
		if($("#homeAddress1Fields").find('.content_field').attr("required") == "required"){
			checkInput("homeAddress3", "homeAddress3Fields");
		}else{
			$("#homeAddress3Fields #requiredImage").removeClass();
		}
	});
	
	$("#homeAddress4").on("blur", function(){
		if($("#homeAddress1Fields").find('.content_field').attr("required") == "required"){
			checkInput("homeAddress4", "homeAddress4Fields");
		}else{
			$("#homeAddress4Fields #requiredImage").removeClass();
		}
	});
	
	$("#tel").on("blur", function(){
		if($("#telFields").children()[1].getAttribute("required")== "required"){
			if(validateTel($("#tel").val()) == true){
				$("#telFields #requiredImage").removeClass().addClass("requiredSucceed");			
			}else{
				$("#telFields #requiredImage").removeClass().addClass("requiredFail");
			}
		}else{
			$("#telFields #requiredImage").removeClass();
		}
	});
	
	$("#mail").on("blur", function(){
		if($("#mailFields").children()[1].getAttribute("required")== "required"){
			if(validateEmail($("#mail").val()) == true){
				$("#mailFields #requiredImage").removeClass().addClass("requiredSucceed");			
			}else{
				$("#mailFields #requiredImage").removeClass().addClass("requiredFail");
			}
		}else{
			$("#mailFields #requiredImage").removeClass();
		}
	});
	
	$("#submit_thirdParty_next").click(function() {
		$("#mainForm").attr("action", "thirdPartyToNext");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
	
	tooggleWarning();
	
	$("#correspondantType").on("click", function(){
		tooggleWarning();
		checkCheckBox()
	});
	
	$("#correspondantTypeTwo").on("click", function(){
		tooggleWarning();
		checkCheckBox()
	});
	
	$("#correspondantTypeThree").on("click", function(){
		tooggleWarning();
		checkCheckBox();
	});
}

//Check the current selected correspondant type and call the associated method
function tooggleWarning() {
	$(".content_label").find(".warning").hide();
	$(".content_field").removeAttr("required");
	$(".default_required .warning").show();
	$(".default_required + .content_field").attr("required","required");
	if ($("#correspondantType").attr("checked") == "checked") {
		$(".contact_required .warning").show();
		$(".contact_required + .content_field").attr("required","required");
	}else {	
		 $("#telFields #requiredImage").removeClass();
		 $("#mailFields #requiredImage").removeClass();
	}
	if ($("#correspondantTypeTwo").attr("checked") == "checked") {
		$(".sign_required .warning").show();
		$(".sign_required + .content_field").attr("required","required");
	}else {
		 $("#positionFields #requiredImage").removeClass();
		 $("#birthDateFields #requiredImage").removeClass();
		 $("#birthPlaceFields #requiredImage").removeClass();
		 $("#citizenshipListFields #requiredImage").removeClass();
		 $("#homeAddress1Fields #requiredImage").removeClass();
		 $("#homeAddress2Fields #requiredImage").removeClass();
		 $("#homeAddress3Fields #requiredImage").removeClass();
		 $("#homeAddress4Fields #requiredImage").removeClass();	
	}
	if ($("#correspondantTypeThree").attr("checked") == "checked") {
		$(".legalRepr_required .warning").show();
		$(".legalRepr_required + .content_field").attr("required","required");
	}
}

function checkCheckBox() {
	if(($("#correspondantType").attr("checked") == "checked")||
	   ($("#correspondantTypeTwo").attr("checked") == "checked")||
	   ($("#correspondantTypeThree").attr("checked") == "checked"))
	{
		$("#typeCorrespondantLabelFields #requiredImage").removeClass().addClass("requiredSucceed");
	}else{
        $("#typeCorrespondantLabelFields #requiredImage").removeClass().addClass("requiredFail");
	}
}

function submitThirdParty(){
	$("#submit_thirdParty").click(function() {
		$("#mainForm").attr("action", "backToThirdParty");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
}

/**************************************************************************************************
 * 
 * 			SECTION FORM3
 * 
 * ***********************************************************************************************/

function initForm3(error){
	if(error == "true"){
		checkRequiredFields("mainForm");
	}
	
	$('#menu-form').addClass("main-menu-selected");
	$("#submit_etape3").addClass("current");
	
	isFirstEmpty();	

	$("#submit_etape3_back").click(function() {
		$("#mainForm").attr("action", "backToThirdParty");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
	
	$("select").each(function(){
		$(this).on("select2-close", function(){
		   	checkSelect($(this).attr("id"), $(this).parent().parent().attr("id"));
		});
		var idTmp = $(this).attr("id");
		var idParentTmp = $(this).parent().parent().attr("id");
		$("#s2id_"+$(this).attr("id")).on("focusout	", function(){
		   	checkSelect(idTmp, idParentTmp);
		});
	});	
	
	$(".requiredText input").each(function(){
		$(this).on("blur", function(){
		   	checkInput($(this).attr("id"), $(this).parent().parent().attr("id"));
		});
	});		
}

//Display or hide each subsidiary.
function displaySub(id){
	if($("#check" + id).is(":checked")){
		$("#subsidiary"+id).show('slow'); 
	} else {
		$("#subsidiary"+id).hide('slow');
		$("#country" + id).removeAttr("value");
		$("#countryDisplay" + id).removeAttr("value");
	}
}

//Init registration country field
function selectRegistrationCountry(id, flag) {
	if (flag) {
		var value = $("#countryData" + id);
		if (value.val() != null) {
			$("#country" + id).val(value.val());
			$("#countryDisplay" + id).val(value.text());
			$("#countrySubAccount"+ id +"Fields #requiredImage").removeClass().addClass("requiredSucceed");
		}
	} else {
		var value = $("#paysOuvertureCompte" + id + " option:selected");
		if (value.val() != null) {
			$("#country" + id).val(value.val());
			$("#countryDisplay" + id).val(value.attr("name"));
			$("#countrySubAccount"+ id +"Fields #requiredImage").removeClass().addClass("requiredSucceed");
		}
	}
}

//Show or hide the subsidiary by user action
function resetSubsidiaryFields(id){
	resetSubsidiaryFields(id, false);
}

function resetSubsidiaryFields(id, flag){
	if(!$("#check" + id).is(":checked")){
		if(confirm($("#deleteSubMessage").val())){			
			displaySub(id);
			//if the user really want to delete this subsidiary, we empty all the sub fields.
			$("#subsidiary" + id +" input").removeAttr("value");
			$("#subsidiary" + id +" #addressSubsidiary1"+ id).val("");
			$("#subsidiary" + id +" #addressSubsidiary2"+ id).val("");
			$("#subsidiary" + id +" #addressSubsidiary3"+ id).val("");
			$("#subsidiary" + id +" #addressSubsidiary4"+ id).val("");
			$("#subsidiary" + id +" #requiredImage").removeClass();
		} else {
			//else we let the checkbox checked
			$("#check" + id).attr("checked","checked");
		}
	} else {
		//if there is no subsidiary yet:
		displaySub(id);
		selectRegistrationCountry(id, flag);
	}
}

//Show newAccount when fields in newAccount are not empty, or when there is no other accountX in the form.
function isFirstEmpty(){
	if(	$("#reference-1").attr('value') != "" || $("#commercialRegister-1").attr('value') != "" || 
		$("#typeCompte-1").attr('value') != "" || $("#deviseCompte-1").attr('value') != "" || 
		$("#paysOuvertureCompte-1").attr('value') != "" || $("#reference0").attr('value') == null )
	{
		$("#accountToCreate").show();
		$("#createNewAccountButton").css("display", "none");

		// Hide the delete button for the new account if there is no other account.
		if ($("#reference0").attr('value') == null) {
			$("#deleteAccount-1").css("display", "none");
		}
	}
}

// Display or hide the new Account
function displayNew(){
	if($("#accountToCreate").css('display') == "none"){
		$("#accountToCreate").show();
		$("#createNewAccountButton").css("display","none");
	} else {
		if(confirm($("#deleteAccount0Message").val())){
			$("#reference-1").attr("value","");
			$("#accountToCreate").hide();
			$("#createNewAccountButton").css("display","block");
		}
	}
}

//Allow to hide or show the subsidiary for each account (loopIndex).
function checkSubsidiaryAccount(loopIndex){
	checkSubsidiaryAccount(loopIndex, false);	
}

function checkSubsidiaryAccount(loopIndex, flag){
	if($("#check" + loopIndex).is(":checked")){
		selectRegistrationCountry(loopIndex, flag);
	}
	if(		($("#addressSubsidiary1" + loopIndex).val() != "")||
			($("#addressSubsidiary2" + loopIndex).val() != "")||
			($("#addressSubsidiary3" + loopIndex).val() != "")||
			($("#addressSubsidiary4" + loopIndex).val() != "")||
			($("#addressSubsidiary5" + loopIndex).val() != "")||
			($("#addressSubsidiary6" + loopIndex).val() != "")||
			($("#addressSubsidiary7" + loopIndex).val() != "")||
			($("#commercialRegister" + loopIndex).val() != "")
		){
			if((loopIndex == -1 && $("#addressSubsidiary1" + loopIndex).val() != "" && $("#addressSubsidiary1" + loopIndex).val() != $("#addressSubsidiary1" + loopIndex).attr("placeholder"))
				|| loopIndex != -1){
				//Check if the new account (referenced by loopIndex = -1) is delete or not)
				$("#check" + loopIndex).attr("checked","checked");
				displaySub(loopIndex);
			}
		
		}
}

//Call the controller to delete an account
function deleteAccount(id) {
	if (id == -1 && $("#paysOuvertureCompte-1").length && $("#paysOuvertureCompte-1").val() == "") {
		$("#accountToCreate").hide();
		$("#createNewAccountButton").css("display", "block");
	} else {
		if (confirm($("#deleteAccount").val())) {
			$("#mainForm").attr("action", "deleteAccount?id=" + id);
			$("#mainForm").submit();
		}
	}
}

//Fill all the paramFunc list in form account Page.
function getAllParamFuncList(idCountryAccount){	
	$("#" + idCountryAccount).on("change", function(e) {		
		$("#mainForm").attr("action", "reloadForm3ChangeCountry");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
}

function checkRequiredFieldsAccountPage(){
	var ret = checkRequiredFields("existingAccounts");
	if($("#accountToCreate").css('display') == "block"){
		ret = checkRequiredFields("accountToCreate");
	}	
	return ret;
}

function submitEtape3() {
	$("#submit_etape3").click(function() {
		$("#mainForm").attr("action", "backToForm3");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
}

/**************************************************************************************************
 * 
 * 			SECTION FORM4
 * 
 * ***********************************************************************************************/

function initForm4(code_joint, code_no_limit, code_power_type, error, dateFormatDisplay){
	if(error == "true"){
		checkRequiredFields("mainForm");
		checkdate("boardResolutionDay", "boardResolutionMonth", "boardResolutionYear", "boardResolutionDateField");
	}
	
	$('#menu-form').addClass("main-menu-selected");
	$("#submit_etape4").addClass("current");
	
	var selectedIndex = $("#countryATP option:selected");
	$('#countryATP option').sort(NASort).appendTo('#countryATP');
	$("#countryATP").val(selectedIndex.val());
	
	$('#datatableATP').dataTable({
		"bLengthChange" : false,
		"bPaginate" : false,
        "ordering": false
	}).rowGrouping({
		bExpandableGrouping : true
	});
	
	$("#submit_etape4_back").click(function() {
 		$("#mainForm").attr("action", "backToForm3");
		$("#mainForm").removeAttr('onsubmit');
 		$("#mainForm").submit();
 	});
	
	$("#submitFormulaire4").click(function() {
 		$("#mainForm").attr("action", "saveFormulaire4");
		$("#mainForm").removeAttr('onsubmit');
 		$("#mainForm").submit();
 	});
	
	$("#submit_etape4_next").click(function() {
		$("#mainForm").attr("action", "form4ToNext");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
 	
	$("#countryATP").on("select2-close", function(){
	   	checkSelect("countryATP", "countryATPField");
	});
	$("#s2id_countryATP").on("focusout	", function(){
	   	checkSelect("countryATP", "countryATPField");
	});
	
	$('#thirdParty').on("select2-close", function(){
		var thirdParty =$("#thirdParty option:selected").val();
		updateThirdPartyList(thirdParty);
		checkSelect("thirdParty", "thirdPartyField");
	});
	$("#s2id_thirdParty").on("focusout	", function(){
	   	checkSelect("thirdParty", "thirdPartyField");
	});
	
	$("#powerType").on("select2-close", function(){
		var powerType = $("#powerType option:selected").val();
		togglePowerType(powerType, code_power_type);
		checkSelect("powerType", "powerTypeField");
	});
	$("#s2id_powerType").on("focusout	", function(){
	   	checkSelect("powerType", "powerTypeField");
	});
	
	if(dateFormatDisplay == "yyyy/mm/dd"){
		$("#boardResolutionDay").on("blur", function(){
			checkdate("boardResolutionDay", "boardResolutionMonth", "boardResolutionYear", "boardResolutionDateField");
		});
	}
	else {
		$("#boardResolutionYear").on("blur", function(){
			checkdate("boardResolutionDay", "boardResolutionMonth", "boardResolutionYear", "boardResolutionDateField");
		});
	}
	
	$('#signatureAuthorizationSelect').on("select2-close", function(){
		var authSign = $("#signatureAuthorizationSelect option:selected").val();
		toggleThirdPartyList(authSign, code_joint);
		checkSelect("signatureAuthorizationSelect", "signatureAuthorizationSelectField");
	});
	$("#s2id_signatureAuthorizationSelect").on("focusout	", function(){
	   	checkSelect("signatureAuthorizationSelect", "signatureAuthorizationSelectField");
	});
	
	$("#statusAmountLimit").on("select2-close", function(){
		var limit = $("#statusAmountLimit option:selected").attr("value");
		toggleAmountLimit(limit, code_no_limit);
		checkSelect("statusAmountLimit", "statusAmountLimitField");
	});
	$("#s2id_statusAmountLimit").on("focusout	", function(){
	   	checkSelect("statusAmountLimit", "statusAmountLimitField");
	});
	$("#deviseAmountLimit").on("select2-close", function(){
		checkSelect("deviseAmountLimit", "amountLimitDevise");
	});
	$("#s2id_deviseAmountLimit").on("focusout	", function(){
	   	checkSelect("statusAmountLimit", "statusAmountLimitField");
	});
	$("#amountLimit").on("blur", function(){
	   	checkInput("amountLimit", "amountLimitField");
	});	
 	
	$("#authorizedThirdPartyList").on("select2-close", function(){
		checkSelect("authorizedThirdPartyList", "authorizedThirdPartyListField");
	});	
	var thirdParty =$("#thirdParty option:selected").val();
	var powerType = $("#powerType option:selected").val();
	var authSign = $("#signatureAuthorizationSelect option:selected").val();
	var limit = $("#statusAmountLimit option:selected").attr("value");
	updateThirdPartyList(thirdParty);
	togglePowerType(powerType, code_power_type);
	toggleThirdPartyList(authSign, code_joint);
	toggleAmountLimit(limit, code_no_limit);
}

function updateThirdPartyList(thirdParty){
	$("#authorizedThirdPartyList option").each(function(){
		$(this).removeAttr("disabled");
	});
	$("#authorizedThirdPartyList option[value='" + thirdParty + "']").attr("disabled", true);
}

function togglePowerType(power, code_power_type){
	if (power < code_power_type) {
		$("#boardResolutionDateFieldSpace").hide();
		$("#boardResolutionDateField").hide();
		$("#boardResolutionDate").removeAttr("value");
		$("#boardResolutionDate").attr("disabled", true);
	} else {
		$("#boardResolutionDateFieldSpace").show();
		$("#boardResolutionDateField").show();
		$("#boardResolutionDate").removeAttr("disabled");
	}
}

function toggleThirdPartyList(authSign, code_joint){	
	if(code_joint == "" || authSign != code_joint){
		$("#authorizedThirdPartyListFieldSpace").hide();
		$("#authorizedThirdPartyListField").hide();
		$("#authorizedThirdPartyList").removeAttr("value");
		$("#authorizedThirdPartyList").attr("disabled", true);
	}else{
		$("#authorizedThirdPartyListFieldSpace").show();
		$("#authorizedThirdPartyListField").show();
		$("#authorizedThirdPartyList").removeAttr("disabled");
	}
}

function toggleAmountLimit(limit, code_no_limit){
	if (limit == code_no_limit) {
		$("#amountLimitFieldSpace").show();
		$("#amountLimitField").show();
		$("#amountLimit").removeAttr("disabled");
		$("#amountLimitDeviseSpace").show();
		$("#amountLimitDevise").show();
		$("#deviseAmountLimit").removeAttr("disabled");
	} else {
		$("#amountLimitFieldSpace").hide();
		$("#amountLimitField").hide();
		$("#amountLimit").removeAttr("value");
		$("#amountLimit").attr("disabled", true);
		$("#amountLimitDeviseSpace").hide();
		$("#amountLimitDevise").hide();
		$("#deviseAmountLimit").removeAttr("value");
		$("#deviseAmountLimit").attr("disabled", true);
	}
}

function checkAccountThirdParty(code_power){
 	//checkRequiredFields("mainForm");
	var ret = true;
	if($("#countryATP").val() == ""){
		ret = false;
	}
	if($("#thirdParty option:selected").val() == ""){
		ret = false;
	}
	if($("#powerType option:selected").val() == ""){
		ret = false;
	}
	if($("#boardResolutionDate").attr("disabled") == null && $("#boardResolutionDate").val() == ""){
		ret = false;
	}
	if($("#powerType option:selected").val() == code_power && $("#publicDeedReference").val() == ""){
		ret = false;
	}
	if($("#signatureAuthorizationSelect option:selected").val() == ""){
		ret = false;
	}
	if($("#statusAmountLimit option:selected").val() == ""){
		ret = false;
	}
	if($("#amountLimit").attr("disabled") == null && $("#amountLimit").val() == ""){
		ret = false;
	}
	if($("#authorizedThirdPartyList").attr("disabled") == null && $("#authorizedThirdPartyList").val() == null){
		ret = false;
	}
	return ret;
}

function submitEtape4(){
	$("#submit_etape4").click(function() {
		$("#mainForm").attr("action", "backToForm4");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
}

/******************************************** DEPRECATED *****************************************/

function checkRequiredFieldsThirds(id){
	var typeCorrespondant = id.substring(id.length -1,id.length);
	if(typeCorrespondant == "3"){
		$("#signatureAuthorization").prop('required', 'required');
		$("#amountLimit").prop('required', 'required');
		$("#amountLimitRequired").css("display","inline");
		$("#signatureAuthorizationRequired").css("display","inline");
		$("#amountFields").addClass("requiredText");
		$("#authorizationFields").addClass("requiredSelect");
	}else{
		$("#signatureAuthorization").prop('required', '');
		$("#amountLimit").prop('required', '');
		$("#amountLimitRequired").css("display","none");
		$("#signatureAuthorizationRequired").css("display","none");
		$("#amountFields").removeClass("requiredText");
		$("#amountFields #requiredImage").removeClass();
		$("#authorizationFields").removeClass("requiredSelect");
		$("#authorizationFields #requiredImage").removeClass();
	}
}

//Delete an Account Third Party
function deleteAccountThirdParty(id){
	if(confirm($("#deleteAccountThirdPartyMessage").val())){
		$("#mainForm").attr("action", "deleteAccountThirdParty?id=" + id);
		$("#form").removeAttr('onsubmit');
		$("#mainForm").submit();
		return false;
	}
}

/**************************************************************************************************
 * 
 * 			SECTION FORM5
 * 
 * ***********************************************************************************************/

function initForm5(error){
	if(error == "true"){
		checkSelect("contact", "blocContainer");
	}
	
	$('#menu-form').addClass("main-menu-selected");
	$("#submit_page5").addClass("current");
	
	submitEtape1();
	submitThirdParty();
	submitEtape3();	
	submitEtape4();	

	$("#submit_page5_back").click(function() {
		$("#mainForm").attr("action", "backToForm4");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
	$("#submit_page5_next").click(function() {
		$("#mainForm").attr("action", "form5ToNext");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
	
	$("#contact").on("select2-close", function(){
		checkSelect("contact", "blocContainer");
	});
	$("#s2id_contact").on("focusout	", function(){
		checkSelect("contact", "blocContainer");
	});
}

function submitEtape5(){
	$("#submit_page5").click(function() {
		$("#mainForm").attr("action", "backToForm5");
		$("#mainForm").removeAttr('onsubmit');
		$("#mainForm").submit();
	});
}

/**************************************************************************************************
 * 
 * 			SECTION SUMMARY
 * 
 * ***********************************************************************************************/

function initSummary(){
	$('#menu-form').addClass("main-menu-selected");
	$("#bc_summary").addClass("current");
	
	submitEtape1();
	submitThirdParty();
	submitEtape3();
	submitEtape4();
	submitEtape5();
	
	$("#submit_summary_back").click(function() {
		$("#mainForm").attr("action", "backToForm5");
		$("#mainForm").submit();
	});
}
