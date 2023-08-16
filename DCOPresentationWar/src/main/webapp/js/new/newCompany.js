
$(document).ready(function() {
	// Trier les listes
	sortLists();
	
	// hide postal address div
	tooglePostalAddress();
	
	// hide contact 2
	toogleContact2();
	
	// afficher le bon formulaire de status legal
	var country = $('#countryIncorp').val();
	setSelectedLegalStatus(country);
	showCountryLegalStatus();
	
	// Suppression d'un customer au clic sur la corbeille
	$('.deleteButton').click(function(){
		$(this).parent().parent().remove();
	});
});

function showCountryLegalStatus() {
	var country = $('#countryIncorp').val();
	
	$('#legalStatus').hide();
	$('#legalStatusAT').hide();
	$('#legalStatusBE').hide();
	$('#legalStatusBG').hide();
	$('#legalStatusCZ').hide();
	$('#legalStatusDK').hide();
	$('#legalStatusFR').hide();
	$('#legalStatusDE').hide();
	$('#legalStatusHU').hide();
	$('#legalStatusIE').hide();
	$('#legalStatusIT').hide();
	$('#legalStatusLU').hide();
	$('#legalStatusNL').hide();
	$('#legalStatusNO').hide();
	$('#legalStatusPL').hide();
	$('#legalStatusPT').hide();
	$('#legalStatusRO').hide();
	$('#legalStatusES').hide();
	$('#legalStatusSE').hide();
	$('#legalStatusCH').hide();
	$('#legalStatusGB').hide();
	
	$('#legalStatus' + country).show();
	setLegalStatusId("legalStatus" + country);
}

function setLegalStatusId(selectId) {
	$("#legalStatusId").val($('#'+selectId).val());
	toogleLegalFormOther($('#countryIncorp').val());
}

function setSelectedLegalStatus(country) {
	$("#legalStatus" + country).val($("#legalStatusId").val());
}

function companyFormSubmit() {
	addRepresentativeToList();
	$("#companyForm").submit();
}

function toogleLegalFormOther(country){
	var legalStatus = $("#legalStatus" + country + " option:selected").attr("id");
	if(legalStatus == undefined || legalStatus.toUpperCase() != ("Other").toUpperCase()){
		$('#warningOther').empty();
		$('#warningOther').hide();
	}else{
		$('#warningOther').empty();
		$('#warningOther').append('If a required company form is not available in the list, please contact ***** in order to ask for its addition.<br/>')
		$('#warningOther').show();
	}
}

/**************************************************************************************************/
/**** Copie des addresses ****/

function tooglePostalAddress() {
	var flag = true;
	for ( var i = 1; i < 5; i++) {
		if ($("#adressePostal" + i).val() != $("#adresseSiege" + i).val()) {
			flag = false;
		}
	}
	if (flag == true) {
		for ( var i = 1; i < 6; i++) {
			$("#adressePostal" + i).attr("readonly", "true");
		}
		$("#postalAddress").hide();
		$("#postal").attr("checked", "checked");
	} else {
		$("#postalAddress").show();
		$("#postal").removeAttr("checked");
	}
}

function copyPostalAddress() {
	if ($("#postal").is(":checked")) {
		$("#postalAddress").hide();
		for ( var i = 1; i < 6; i++) {
			if (i!= 4) {
				$("#adressePostal" + i).attr("value",$("#adresseSiege" + i).val());
				$("#adressePostal" + i).attr("readonly", "true");
			}
		}
		$("#adressePostal4 option[value='']").removeAttr("selected");
		$("#adressePostal4 option[value="+ $("#adresseSiege4").val() + "]").attr("selected", "selected");
	} else {
		$("#postalAddress").show();
		for ( var i = 1; i < 6; i++) {
			if (i!= 4) {
				$("#adressePostal" + i).val("");
				$("#adressePostal" + i).removeAttr("readonly");
			}
		}
		$("#adressePostal4 option[value="+ $("#adresseSiege4").val() + "]").removeAttr("selected");
		$("#adressePostal4 option[value='']").attr("selected", "selected");
	}
}

function copyAddressField(fieldNumber) {
	if ($("#postal").is(":checked")) {
		$("#adressePostal" + fieldNumber).val($("#adresseSiege" + fieldNumber).val());
	}
}

/*************************************************************************************************/
/**** Gestion des contacts ****/

function addContact2() {
	if ($("#addContact").is(":checked")) {
		$("#contact2").show();
	} else {
		$("#contact2").hide();
		$("#contact2-firstname").attr("value","");
		$("#contact2-name").attr("value","");
		$("#contact2-mail").attr("value","");
		$("#contact2-phone").attr("value","");
	}
}

function toogleContact2() {
	if ($("#contact2-firstname").val() != "") {
		$("#contact2").show();
		$("#addContact").attr("checked", "checked");
	} else {
		$("#contact2").hide();
		$("#addContact").removeAttr("checked");
	}
}

/**************************************************************************************************/
/**** Gestion des representatives ****/

function addRepresentativeToList() {
	
	$('#error').empty();
	$('#error').hide();
	
	if ($("#representativeFirstName").val() != '' && $("#representativeLastName").val() != ''){
		
		// Paragraphe dans le résumé
		var representativeResume = $('<div class="bnpAccount-signatoriesResume-content bnpAccount-infoRequest-container bnpAccount-resumeDataList-container"></div>');
		$('.bnpAccount-infoRequest-signatories-container').append(representativeResume);
		representativeResume.append('<dl class="bnpAccount-resumeDataList bnpAccount-resumeDataList--info"></dl>');
		representativeResume.children("dl").first().append('<dd>'+ $("#representativeFirstName").val() + '</dd>');
		representativeResume.children("dl").first().append('<dd>'+ $("#representativeLastName").val() + '</dd>');
		
		// Ajout des boutons d'edition/suppression
		representativeResume.append('<p class="bnpAccount-resumeDataList-actionBtns"></p>');
//		representativeResume.children("div.bnpAccount-resumeDataList-actionBtns").append('<button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn" id="editButton"><img src="../img/svg/create.svg" alt=""></button>');
		representativeResume.children("p.bnpAccount-resumeDataList-actionBtns").append('<button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn deleteButton"><img src="../img/svg/delete.svg" alt=""></button>');							
		
		// Ajout des input hidden pour les données du formulaire
		var nbRepresentatives = parseInt($('#nbRepresentatives').val());
		representativeResume.append('<input type="hidden" name="newEntity.representativesList[' + nbRepresentatives + '].firstname" value="' + $("#representativeFirstName").val() + '">');
		representativeResume.append('<input type="hidden" name="newEntity.representativesList[' + nbRepresentatives + '].name" value="' + $("#representativeLastName").val() + '">');
		$('#nbRepresentatives').val(nbRepresentatives + 1);
		
		// Reset le formulaire de representative
		$("#representativeFirstName").val('');
		$("#representativeLastName").val('');
		
		// Suppression d'un customer au clic sur la corbeille
		$('.deleteButton').click(function(){
			$(this).parent().parent().remove();
		});
		
	} else if (! ($("#representativeFirstName").val() == '' && $("#representativeLastName").val() == '')){
		$('#error').append('Please complete all of the fields marked with a *.<br/>')
		$('#error').show();
	}
}

/**************************************************************************************************/
/**** Tri des listes ****/

function sortLists() {
	var selectedIndex1 = $("#countryIncorp option:selected");
	$('#countryIncorp option').sort(NASort)
			.appendTo('#countryIncorp');
	$("#countryIncorp").val(selectedIndex1.val());
	
	var selectedIndex2 = $("#registrationCountry option:selected");
	$('#registrationCountry option').sort(NASort)
			.appendTo('#registrationCountry');
	$("#registrationCountry").val(selectedIndex2.val());
	
	var selectedIndex3 = $("#adresseSiege4 option:selected");
	$('#adresseSiege4 option').sort(NASort)
			.appendTo('#adresseSiege4');
	$("#adresseSiege4").val(selectedIndex3.val());
	
	var selectedIndex3 = $("#adressePostal4 option:selected");
	$('#adressePostal4 option').sort(NASort)
			.appendTo('#adressePostal4');
	$("#adressePostal4").val(selectedIndex3.val());
}


/***************************************************************************************/
/*******DELETE**********/

function deleteRepresentative(count) {

	$('.deleteButton').click(function(){
		$(this).parent().parent().remove();
	});
	
	$('#customerRepresentative'+count).val("");
}


