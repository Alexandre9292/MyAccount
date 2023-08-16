$(document).ready(function() {
	// Trier les listes
	sortLists();
	
	// Resume des signatories
	if ($('#nbSignatories').val() > 0) {
		$('#resumeSignatories').removeAttr('style');
	}
	
	//var isCollege = false;
	
	$('#assignGroup').hide();
	$('#rulesField1').hide();
	$('#rulesField3').hide();
	$('#selectRep').hide();
	
	
	// Définir les submit AJAX des parties
	$('#part1Form').submit(function(e) {
		e.preventDefault();

		var account = $("#part1Form").serialize();
		$.ajax({
			type : "post",
			data : account,
			url : $("#part1Form").attr("action"),
			async : false,
			success: function (response) {
				$("#errorPart1").empty();
                $("#errorPart1").append(response);
                if($.trim($("#errorPart1").text()).length > 0) {
                	if($("#errorPart1").text() == "Please select accounts with same specifications."){
                		$("#errorPart3").show();
                	}
                	else{
                		$("#errorPart1").show();
                	}
                } else {
                	$("#errorPart1").hide();
                	                	
                	// open and close divs
                	$("#blocPart1").addClass('bnpAccount-inscriptionListItem--slidedUp');
                	$("#contentPart1").removeClass('is-open');
                	
                	$("#blocPart2").removeClass('bnpAccount-inscriptionListItem--slidedUp');
                	$("#contentPart2").addClass('is-open');
                	$("#rulesFormContent").addClass('is-open');
                	$("#bnpAccount-legalDocument-description").addClass('is-open');
                }
            },
			error: function () {
                alert('error');
            }
		});
	});
	$('#part2Form').submit(function(e) {
		e.preventDefault();
		var strategy = $("#part2Form").serialize();
		$.ajax({
			type : "post",
			data : strategy,
			url : $("#part2Form").attr("action"),
			async : false,
			success: function (response) {
				$("#errorPart2").empty();
                $("#errorPart2").append(response);
                if($.trim($("#errorPart2").text()).length > 0) {
                	$("#errorPart2").show();
                } else {
                	$("#errorPart2").hide();
                	
                	// open and close divs
                	$("#blocPart2").addClass('bnpAccount-inscriptionListItem--slidedUp');
                	$("#contentPart2").removeClass('is-open');
                	$("#signatoryForm").removeClass('is-open');
                	$("#contentPart2_signatories").removeClass('is-open');
                	$("#rulesFormContent").removeClass('is-open');
                	$("#bnpAccount-legalDocument-description").removeClass('is-open');
                	$("#NextPart2").css('display','none');
                	$("bnpAccount-legalDocument-intro").css('display','none');
                	
                	$("#blocPart3").removeClass('bnpAccount-inscriptionListItem--slidedUp');
                	$("#contentPart3").addClass('is-open');
                }
            },
			error: function () {
                alert('error');
            }
		});
	});
	$('#part3Form').submit(function(e) {
		e.preventDefault();

		var documents = $("#part3Form").serialize();
		$.ajax({
			type : "post",
			//data : documents,
			url : $("#part3Form").attr("action"),
			async : false,
			success: function (data) {
				
				$("#errorPart3").empty();
                $("#errorPart3").append(response);
                if($.trim($("#errorPart3").text()).length > 0) {
                	$("#errorPart3").show();
                } else {
                	$("#errorPart3").hide();
                }
            },
			error: function () {
                alert('error');
            }
		});
	});
	$('#representativeForm').submit(function(e) {
		e.preventDefault();
		
		var documents = $("#representativeForm").serialize();
		$.ajax({
			type : "post",
			data : documents,
			url : $("#representativeForm").attr("action"),
			async : false,
			success: function (response) {
				
//				$("#errorPart3").empty();
//                $("#errorPart3").append(response);
//                if($.trim($("#errorPart3").text()).length > 0) {
//                	$("#errorPart3").show();
//                } else {
//                	$("#errorPart3").hide();
//                }
            },
			error: function () {
                alert('error');
            }
		});
	});
});




/*************************/
/** SIGNATORY AND RULES **/

function addSignatoryToList() {
	
	$('#resumeSignatories').addClass('bnpAccount-signatoriesResume bnpAccount-signatories bnpAccount-js-inscriptionList-slideContent is-open');
	$('#resumeSignatories').show();
	var signatoryResume = $('<div class="bnpAccount-infoRequest-signatories-container"></div>');
	$('.bnpAccount-collegeResume-before').before(signatoryResume);
	
	var groupeName = $('#assignGroup').find(':checked').val();
	
	if($("#collegeName").val() == '' && $('#assignGroup').find(':checked').size() == 0){
		// INDIVIDUAL
		
		// Paragraphe dans le résumé
		signatoryResume.append('<div class="bnpAccount-signatoriesResume-content bnpAccount-infoRequest-container"></div>');
		signatoryResume.children("div.bnpAccount-signatoriesResume-content").append('<p></p>');
		signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("p").append('<img src="../img/svg/user.svg" alt="" width="23" height="22">');
		signatoryResume.children("div.bnpAccount-signatoriesResume-content").append('<div class="bnpAccount-resumeDataList-container"></div>');
		signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").append('<dl class="bnpAccount-resumeDataList bnpAccount-resumeDataList--info"></dl>');
		signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").children("dl.bnpAccount-resumeDataList").append('<dt>Name</dt>');
		signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").children("dl.bnpAccount-resumeDataList").append('<dd>' + $("#firstname").val() + ' ' + $("#name").val() +'</dd>');
			
		// Ajout des input hidden pour les données du formulaire
		var nbSignatories = parseInt($('#nbSignatories').val());
		
		// Ajout des boutons d'edition/suppression
		signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").append('<p class="bnpAccount-resumeDataList-actionBtns"></p>');
//		signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").children("p.bnpAccount-resumeDataList-actionBtns").append('<button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn" id="editButton"><img src="../img/svg/create.svg" alt=""></button>');
		signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").children("p.bnpAccount-resumeDataList-actionBtns").append('<button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn deleteButtonSignatory'+nbSignatories+'"><img src="../img/svg/delete.svg" alt=""></button>');							
		
		signatoryResume.append('<div class="newSignatory'+nbSignatories+'"></div>');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].firstname" value="' + $("#firstname").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].name" value="' + $("#name").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].role" value="' + $("#bnpAccountSignatoryRole").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].citizenship" value="' + $("#bnpAccountSignatoryCitizenships").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].birthDay" value="' + $("#bnpAccountSignatoryBirthDay").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].birthMonth" value="' + $("#bnpAccountSignatoryBirthMonth").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].birthYear" value="' + $("#bnpAccountSignatoryBirthYear").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].birthPlace" value="' + $("#bnpAccountSignatoryBirthPlace").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].tel" value="' + $("#bnpAccountSignatoryPhone").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].fax" value="' + $("#bnpAccountSignatoryFax").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].homeAddress.fieldOne" value="' + $("#bnpAccountSignatoryStreet").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].homeAddress.fieldFive" value="' + $("#fieldFive").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].homeAddress.fieldTwo" value="' + $("#bnpAccountSignatoryCity").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].homeAddress.fieldThree" value="' + $("#bnpAccountSignatoryZip").val() + '">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].homeAddress.fieldFour" value="' + $("#homeAddressCountry").val() + '">');
	
		$('#nbSignatories').val(nbSignatories + 1);
	} else {
		
		// Ajout des input hidden pour les données du formulaire
		var nbSignatories = parseInt($('#nbSignatories').val());
		var nbColleges = parseInt($('#nbColleges').val());
		signatoryResume.append('<div class="newSignatory'+nbSignatories+'"></div>');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].firstname" value="' + $("#firstname").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].name" value="' + $("#name").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].role" value="' + $("#bnpAccountSignatoryRole").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].citizenship" value="' + $("#bnpAccountSignatoryCitizenships").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].birthDay" value="' + $("#bnpAccountSignatoryBirthDay").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].birthMonth" value="' + $("#bnpAccountSignatoryBirthMonth").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].birthYear" value="' + $("#bnpAccountSignatoryBirthYear").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].birthPlace" value="' + $("#bnpAccountSignatoryBirthPlace").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].tel" value="' + $("#bnpAccountSignatoryPhone").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].fax" value="' + $("#bnpAccountSignatoryFax").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].homeAddress.fieldOne" value="' + $("#bnpAccountSignatoryStreet").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].homeAddress.fieldFive" value="' + $("#fieldFive").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].homeAddress.fieldTwo" value="' + $("#bnpAccountSignatoryCity").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].homeAddress.fieldThree" value="' + $("#bnpAccountSignatoryZip").val() + '" id="newSignatory'+nbSignatories+'">');
		signatoryResume.children("div.newSignatory"+nbSignatories).append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].homeAddress.fieldFour" value="' + $("#homeAddressCountry").val() + '" id="newSignatory'+nbSignatories+'">');
				
		/* Association aux groupes */
		
		var selectedGroups = $('#assignGroup').find(':checked');
		var indexCollege = 0;
		for(var i = 0; i<selectedGroups.size(); i++){
			var group = selectedGroups[i];
			var splitGRP = group.getAttribute("value").split(';');
			
			signatoryResume.append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].collegeIndexes[' + indexCollege + ']" value="' + splitGRP[0] + '" id="newSignatory'+nbSignatories+'-'+indexCollege+'">');
			indexCollege = indexCollege + 1;
		}
		
//		var splitGRP = groupeName.split('-');
//		var indexCollege = 0;
//		signatoryResume.append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].collegeList[' + indexCollege + '].name" value="' + groupeName + '">');
//		signatoryResume.append('<input type="hidden" name="account.signatoriesList[' + nbSignatories + '].collegeIndexes[' + indexCollege + ']" value="' + splitGRP[0] + '">');
//		indexCollege = indexCollege + 1;
		
		$('#nbSignatories').val(nbSignatories + 1);
	}

	// Suppression d'un customer au clic sur la corbeille
	$('.deleteButtonSignatory'+nbSignatories).click(function(){
		resetErrorSignatory();
		if($('#signatoryUseInRules'+nbSignatories).size()>0 || $('#signatoryUseInRules2'+nbSignatories).size()>0){
			 $('#errorPart2').empty();
			 $('#errorPart2').append('This signatory is in a rule.<br/>')
			$('#errorPart2').show();
		 }else{
			$(this).parent().parent().parent().parent().remove();
			$('#signatoryResumeInRules'+nbSignatories).remove();
			$('#signatoryResumeInRules2'+nbSignatories).remove();
			$('.newSignatory'+nbSignatories).remove();
		 }
	});
}

function addSignatoryToListRules() {
	
	var indexSignatory = $('#nbSignatories').val() - 1;

	// Paragraphe dans le résumé
	var signatoryResume = $('<div class="bnpAccount-infoRequest-container bnpAccount-signatoriesRules" id="signatoryResumeInRules' + indexSignatory + '"></div>');
	$('.bnpAccount-resumeSagnatoryInRules').before(signatoryResume);
	signatoryResume.append('<p class="bnpAccount-fieldGroup"></p>');
	signatoryResume.children("p.bnpAccount-fieldGroup").append('<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountSignatoryPeople2"></label>');
	signatoryResume.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append('<input type="radio" name="GroupOrSignatory1" value="' + 'S;'+ $("#firstname").val() + ' ' + $("#name").val() + ';' + indexSignatory + '" id="Signatory1">');
	signatoryResume.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append('<img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace">');
	signatoryResume.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append($("#firstname").val() + ' ' + $("#name").val());

	var signatoryResume2 = $('<div class="bnpAccount-infoRequest-container bnpAccount-signatoriesRules2" id="signatoryResumeInRules2"></div>');
	$('.bnpAccount-resumeSagnatoryInRules2').before(signatoryResume2);
	signatoryResume2.append('<p class="bnpAccount-fieldGroup"></p>');
	signatoryResume2.children("p.bnpAccount-fieldGroup").append('<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountSignatoryPeople2' + indexSignatory + '"></label>');
	signatoryResume2.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append('<input type="radio" name="GroupOrSignatory2" value="' + 'S;'+ $("#firstname").val() + ' ' + $("#name").val() + ';' + indexSignatory + '" id="Signatory2">');
	signatoryResume2.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append('<img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace">');
	signatoryResume2.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append($("#firstname").val() + ' ' + $("#name").val());

}

function addGroupToList(groupName, groupIndex, numSignatory) {
	
	var sameGroupDiv = $('#collegeResume-group' + groupIndex);
	
	var groupResume;
	if (sameGroupDiv.size() < 1) {
		// Paragraphe dans le résumé
		groupResume = $('<div class="bnpAccount-collegeResume" id="collegeResume-group' + groupIndex + '"></div>');
		$('.bnpAccount-classbeforeGroup').before(groupResume);
	} else {
		groupResume = sameGroupDiv;
	}
	
	// ajouter le symbole groupe (TO DO : a faire uniquement dans le cas création
	groupResume.append('<div class="bnpAccount-signatoriesResume-content listSignatoryGroupResume'+numSignatory+'"></div>');
	groupResume.children("div.bnpAccount-signatoriesResume-content").last().append('<p class="bnpAccount-actionBtns-container"></p>');
	groupResume.children("div.bnpAccount-signatoriesResume-content").last().children("p.bnpAccount-actionBtns-container").append('<img src="../img/svg/group.svg" alt="" width="29" height="22">');
	groupResume.children("div.bnpAccount-signatoriesResume-content").last().children("p.bnpAccount-actionBtns-container").append('<br>');	
	groupResume.children("div.bnpAccount-signatoriesResume-content").last().children("p.bnpAccount-actionBtns-container").append(groupName);
	groupResume.children("div.bnpAccount-signatoriesResume-content").last().append('<div class="bnpAccount-collegeResume-Signatories"></div>');
	
	// Signatories
	var signatoryResume = groupResume.children("div.bnpAccount-signatoriesResume-content").last().children("div.bnpAccount-collegeResume-Signatories");
	$('.bnpAccount-afterSignatoriesGroup').before(signatoryResume);
	signatoryResume.append('<div class="bnpAccount-signatoriesResume-content bnpAccount-infoRequest-container"></div>');
	signatoryResume.children("div.bnpAccount-signatoriesResume-content").append('<div class="bnpAccount-resumeDataList-container"></div>');
	signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").append('<dl class="bnpAccount-resumeDataList bnpAccount-resumeDataList--info"></dl>');
	signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").children("dl.bnpAccount-resumeDataList").append('<dt>Name</dt>');
	signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").children("dl.bnpAccount-resumeDataList").append('<dd>' + $("#firstname").val() + ' ' + $("#name").val() +'</dd>');
	
	// Ajout des boutons d'edition/suppression
	signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").append('<p class="bnpAccount-resumeDataList-actionBtns"></p>');
//	signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").children("p.bnpAccount-resumeDataList-actionBtns").append('<button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn" id="editButton"><img src="../img/svg/create.svg" alt=""></button>');
	signatoryResume.children("div.bnpAccount-signatoriesResume-content").children("div.bnpAccount-resumeDataList-container").children("p.bnpAccount-resumeDataList-actionBtns").append('<button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn deleteButtonSignatoryGroup'+numSignatory+'"><img src="../img/svg/delete.svg" alt=""></button>');							

	// Ajout des input hidden pour les données du formulaire
//	var nbColleges = parseInt($('#nbColleges').val());
//	groupResume.append('<input type="hidden" name="account.collegeList[' + nbColleges + '].name" value="' + groupName + '">');
//	$('#nbColleges').val(nbColleges + 1);
	
	// Suppression d'un customer au clic sur la corbeille
	$('.deleteButtonSignatoryGroup'+numSignatory).click(function(){
		resetErrorSignatory();
		if($('#signatoryUseInRules'+numSignatory).size()>0 || $('#signatoryUseInRules2'+numSignatory).size()>0){
			 $('#errorPart2').empty();
			 $('#errorPart2').append('This signatory is in a rule.<br/>')
			$('#errorPart2').show();
		 }else{
			 $('.listSignatoryGroupResume'+numSignatory).remove();
				$('.newSignatory'+numSignatory).remove();
				$('#signatoryResumeInRules'+numSignatory).remove();
				$('#signatoryResumeInRules2'+numSignatory).remove();
		 }
	});
	
}

function addGroupToListCollege(nbColleges) {
		
	// Paragraphe dans le résumé
	var groupResume = $('<div class="bnpAccount-resumeGroupListGroup"></div>');
	$('.bnpAccount-fieldGroup-Group').before(groupResume);
	groupResume.append('<p class="bnpAccount-fieldGroup bnpAccount-actionBtns-container"></p>');
	groupResume.children("p.bnpAccount-actionBtns-container").append('<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountSignatoryGroupe0"></label>');
	groupResume.children("p.bnpAccount-actionBtns-container").children("label.bnpAccount-fieldGroup--checkbox").append('<input type="checkbox" checked name="newCollege.name" value="' + nbColleges + ';' + $("#collegeName").val() + '" id="bnpAccountSignatoryGroupe"' + nbColleges + '">');
	groupResume.children("p.bnpAccount-actionBtns-container").children("label.bnpAccount-fieldGroup--checkbox").append('<img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace">');
	groupResume.children("p.bnpAccount-actionBtns-container").children("label.bnpAccount-fieldGroup--checkbox").append($("#collegeName").val());

	
	// Ajout des boutons d'edition/suppression
	groupResume.children("p.bnpAccount-actionBtns-container").append('<span class="bnpAccount-SignatoryGroup-actionBtns"></span>');
//	groupResume.children("p.bnpAccount-actionBtns-container").children("span.bnpAccount-SignatoryGroup-actionBtns").append('<button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn" id="editButton"><img src="../img/svg/create.svg" alt=""></button>');
	groupResume.children("p.bnpAccount-actionBtns-container").children("span.bnpAccount-SignatoryGroup-actionBtns").append('<button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn deleteButton'+nbColleges+'"><img src="../img/svg/delete.svg" alt=""></button>');							
	
	// Suppression d'un customer au clic sur la corbeille
	$('.deleteButton'+nbColleges).click(function(){
		resetErrorSignatory();
		if($('#collegeResume-group'+nbColleges).find('.bnpAccount-signatoriesResume-content').size() > 0){
			 $('#errorGroupPart').empty();
			 $('#errorGroupPart').append('The college is not empty.<br/>')
			$('#errorGroupPart').show();
		 } else if($('#collegeUseInRules'+nbColleges).size()>0 || $('#collegeUseInRules2'+nbColleges).size()>0){
			 $('#errorGroupPart').empty();
			 $('#errorGroupPart').append('This college is in a rule.<br/>')
			$('#errorGroupPart').show();
		 }else {
			$(this).parent().parent().remove();
			$('#newCollege'+nbColleges).remove();
			$('#collegeResumeInRules'+parseInt($('#nbColleges').val())).remove();
			$('#collegeResumeInRules2'+parseInt($('#nbColleges').val())).remove();
		 }
	});
	
	$("#collegeName").val('');
	document.getElementById("collegeName").value="";
}

/* Ajouter le nouveau groupe dans les pop up de rules */
function addGroupToListRules(collegeName) {

	var indexCollege = $('#nbColleges').val() -1;
	
	// Paragraphe dans le résumé
	var groupResume = $('<div class="bnpAccount-infoRequest-container bnpAccount-collegesRules" id="collegeResumeInRules' + indexCollege + '"></div>');
	$('.bnpAccount-resumeGroupInRules').before(groupResume);
	
	groupResume.append('<p class="bnpAccount-fieldGroup"></p>');
	groupResume.children("p.bnpAccount-fieldGroup").append('<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountRulesGroupe1"></label>');
	groupResume.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append('<input type="radio" name="GroupOrSignatory1" value="' + 'G;'+ collegeName + ';' + indexCollege + '" id="College1">');
	groupResume.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append('<img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace">');
	groupResume.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append(collegeName);

	var groupResume2 = $('<div class="bnpAccount-infoRequest-container bnpAccount-collegesRules2" id="collegeResumeInRules2' + indexCollege + '"></div>');
	$('.bnpAccount-resumeGroupInRules2').before(groupResume2);
	
	groupResume2.append('<p class="bnpAccount-fieldGroup"></p>');
	groupResume2.children("p.bnpAccount-fieldGroup").append('<label class="bnpAccount-fieldGroup--checkbox" for="bnpAccountRulesGroupe2"></label>');
	groupResume2.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append('<input type="radio" name="GroupOrSignatory2" value="' + 'G;'+ collegeName + ';' + indexCollege + '" id="College2">');
	groupResume2.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append('<img src="../img/svg/fieldCorrect.svg" alt="" class="bnpAccount-infoRequest--uiReplace">');
	groupResume2.children("p.bnpAccount-fieldGroup").children("label.bnpAccount-fieldGroup--checkbox").append(collegeName);

}

function part1Submit() {
	if($('#bnpAccounthaveLegalDocument').is(":checked")){
		$(".bnpAccount-signatoryBloc,.bnpAccount-rules, .bnpAccount-signatoriesResume, .bnpAccount-signatories").removeClass("is-open");
		$('#bnpAccount-legalDocument-description').css('display', 'block');
		$('#bnpAccount-legalDocument-intro').removeAttr('style');
		$('.bnpAccount-signatories').removeAttr('style');
	} else if($('#bnpAccountcantDelegate').is(":checked")){
		$(".bnpAccount-signatoryBloc,.bnpAccount-rules, .bnpAccount-signatoriesResume, .bnpAccount-signatories").addClass("is-open");
		$('#bnpAccount-legalDocument-description').removeAttr('style');
		$('#bnpAccount-legalDocument-intro').css('display', 'block');
		$('.bnpAccount-signatories').css('display', 'block');
	}
	$('#NextPart2').show();
	
	$("#part1Form").submit();
}

function part2Submit() {
	addRulesButton();
	$("#part2Form").submit();
	supprHidden();
}

function part3Submit() {
	$("#part3Form").submit();
}

function repButton2() {
	$("#representativeForm").submit();
	$("#selectRep").hide();
}

function sortLists() {
	// country adresse
	var selectedIndexCountryAddress = $("#homeAddressCountry option:selected");
	$('#homeAddressCountry option').sort(NASort).appendTo('#homeAddressCountry');
	$("#homeAddressCountry").val(selectedIndexCountryAddress.val());
	
	// citizenship
	var selectedIndexCitizenship = $("#bnpAccountSignatoryCitizenships option:selected");
	$('#bnpAccountSignatoryCitizenships option').sort(NASort).appendTo('#bnpAccountSignatoryCitizenships');
	$("#bnpAccountSignatoryCitizenships").val(selectedIndexCitizenship.val());
}

function groupeSubmit() {
	var collegeName = $("#collegeName").val();
	var nbColleges = $('#nbColleges').val();
	if (collegeName != '') {
		addGroupToListCollege(nbColleges);
		$("#collegesBloc").append('<input type="hidden" name="account.collegeList[' + nbColleges + '].name" value="' + collegeName + '" id="newCollege'+nbColleges+'">');
		var nbCollegesPlus = parseInt(nbColleges) + 1;
		$('#nbColleges').val(nbCollegesPlus);
		addGroupToListRules(collegeName);
	}
}

function groupButton() {
	if($("#assignGroup").css('display') == "none"){
		$("#assignGroup").show();
	}
	else{
		$("#assignGroup").hide();
	}
		
}

function rulesButtonFields1() {
	if($("#rulesField1").css('display') == "none"){
		$("#rulesField1").show();
	}
	else{
		$("#rulesField1").hide();
	}
		
}

function rulesButtonFields3() {
	if($("#rulesField3").css('display') == "none"){
		$("#rulesField3").show();
	}
	else{
		$("#rulesField3").hide();
	}	
}

function repButton() {
	if($("#selectRep").css('display') == "none"){
		$("#selectRep").show();
	}
	else{
		$("#selectRep").hide();
	}	
}

function addSignatoriesButton() {
	$('#errorPart2-Signatory').empty();
	$('#errorPart2-Signatory').hide();
	
	if (!$('.bnpAccount-unorderedList--Signatories').attr('style')) {				
		if ($('.bnpAccount-unorderedList--Rules').attr('style')) {
			/* cas où la section rules était ouverte */
			if (!checkRules()) {
				$('#errorPart2-Rules').append('Please complete all step to created properly your rule.<br/>')
				$('#errorPart2-Rules').show();
			} else {
				saveRule();
				
				$('.bnpAccount-unorderedList--Rules').removeAttr('style');
				if($('.bnpAccount-unorderedList--Signatories').css('display') == "none"){
					$('.bnpAccount-unorderedList--Signatories').css('display', 'block');
				}
			}
		} else {
			/* cas de la 1ere ouverture */
			$('.bnpAccount-unorderedList--Signatories').css('display', 'block');
		}
	} else {
		/* cas où la section signatories était ouverte */
		if (!checkSignatory()) {
			$('#errorPart2-Signatory').append('Please complete all of the fields marked with a *.<br/>')
			$('#errorPart2-Signatory').show();
		} else if (!checkDate()) {
			$('#errorPart2-Signatory').append('Wrong Date.<br/>')
			$('.bnpAccount-birthDateContainer').removeClass("js-fieldCorrect");
			$('.bnpAccount-birthDateContainer').addClass('js-fieldError');
			$('#errorPart2-Signatory').show();
		} else {
			/* enregistrer le signataire en cours d'ecriture et ouvrir la section rules */
			saveSignatory();
		}
	}
}

/* enregistrer le signataire dans la liste */
function saveSignatory() {
	if ($('#firstname').val() != '') {
		if($("#collegeName").val() == '' && $('#assignGroup').find(':checked').size() == 0){
			/* No group */
			addSignatoryToList();
		} else {
			var nbSignatories = parseInt($('#nbSignatories').val());
			addSignatoryToList();
			if ($('#assignGroup').find(':checked').size() > 0) {
				/* existing group */
				var tmp2 = $('#assignGroup').find(':checked');
				for(var i = 0; i<tmp2.size(); i++){
					var tmp = tmp2[i];
					var splitGRP = tmp.getAttribute("value").split(';');
					addGroupToList(splitGRP[1], splitGRP[0], nbSignatories);
				}
			} 
		}
		addSignatoryToListRules();
		
		
		/* Refresh form */
		document.getElementById("firstname").value="";
		document.getElementById("name").value="";
		document.getElementById("bnpAccountSignatoryCitizenships").value="";
		document.getElementById("bnpAccountSignatoryBirthDay").value="";
		document.getElementById("bnpAccountSignatoryBirthMonth").value="";
		document.getElementById("bnpAccountSignatoryBirthYear").value="";
		document.getElementById("bnpAccountSignatoryBirthPlace").value="";
//		document.getElementById("bnpAccountSignatoryPhone").value="";
		document.getElementById("bnpAccountSignatoryStreet").value="";
		document.getElementById("fieldFive").value="";
		document.getElementById("bnpAccountSignatoryCity").value="";
		document.getElementById("bnpAccountSignatoryZip").value="";
		document.getElementById("homeAddressCountry").value="";
		document.getElementById("bnpAccountSignatoryRole").value="";
//		document.getElementById("bnpAccountSignatoryFax").value="";
		$('#contentPart2_signatories').find('.bnpAccount-fieldGroup').each(function() {
			$(this).removeClass('js-fieldError');
			$(this).removeClass('js-fieldCorrect');
		});
		$('#contentPart2_signatories').find('.bnpAccount-birthDateContainer').each(function() {
			$(this).removeClass('js-fieldError');
			$(this).removeClass('js-fieldCorrect');
		});
		$('#assignGroup').find(':checked').each(function () {
			$(this).removeAttr('checked');
		});
	}
}

/* Verification des données du formulaire des signataires */
function checkSignatory() {
	if ($('#firstname').val() == '' &&
			$('#name').val() == '' &&
			$('#bnpAccountSignatoryCitizenships').val() == '' &&
			$('#bnpAccountSignatoryBirthDay').val() == '' &&
			$('#bnpAccountSignatoryBirthMonth').val() == '' &&
			$('#bnpAccountSignatoryBirthYear').val() == '' &&
			$('#bnpAccountSignatoryBirthPlace').val() == '' &&
//			$('#bnpAccountSignatoryPhone').val() == '' &&
			$('#bnpAccountSignatoryStreet').val() == '' &&
			$('#fieldFive').val() == '' &&
			$('#bnpAccountSignatoryCity').val() == '' &&
			$('#bnpAccountSignatoryZip').val() == '' &&
			$('#homeAddressCountry').val() == '' &&
			$('#bnpAccountSignatoryRole').val() == '' ) {
//			$('#bnpAccountSignatoryFax').val() == '') {
		/* Formulaire vide */
		return true;
	} else {
		if ($('#firstname').val() == '' ||
				$('#name').val() == '' ||
				$('#bnpAccountSignatoryRole').val() == '' ||
				$('#bnpAccountSignatoryCitizenships').val() == '' ||
				$('#bnpAccountSignatoryBirthDay').val() == '' ||
				$('#bnpAccountSignatoryBirthMonth').val() == '' ||
				$('#bnpAccountSignatoryBirthYear').val() == '' ||
				$('#bnpAccountSignatoryBirthPlace').val() == '' ||
				$('#bnpAccountSignatoryStreet').val() == '' ||
				$('#bnpAccountSignatoryCity').val() == '' ||
				$('#bnpAccountSignatoryZip').val() == '' ||
				$('#homeAddressCountry').val() == '') {
			/* donnée obligatoire manquante */
			return false;
		} else {
			/* formulaire complet */
			return true;
		}
	} 
}

/* Verification des données du formulaire des signataires */
function checkDate() {
	if ($('#bnpAccountSignatoryBirthDay').val() == '' && 
		$('#bnpAccountSignatoryBirthMonth').val() == '' && 
		$('#bnpAccountSignatoryBirthYear').val() == '')
    	return true;	// formulaire vide
	
    // Parse the date parts to integers
    var day = parseInt($('#bnpAccountSignatoryBirthDay').val(), 10);
    var month = parseInt($('#bnpAccountSignatoryBirthMonth').val(), 10);
    var year = parseInt($('#bnpAccountSignatoryBirthYear').val(), 10);
    if (isNaN(day) || isNaN(month) || isNaN(year))
    	return false;	// saisie non numerique
    	
    // Check the ranges of month and year
    if(year < 1000 || year > 3000 || month <= 0 || month > 12)
        return false;

    var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

    // Adjust for leap years
    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
        monthLength[1] = 29;

    // Check the range of the day
    return day > 0 && day <= monthLength[month - 1];
}

function addRulesButton() {
	$('#errorPart2-Signatory').empty();
	$('#errorPart2-Signatory').hide();
	$('#errorPart2-Rules').empty();
	$('#errorPart2-Rules').hide();
	
	if (!$('.bnpAccount-unorderedList--Rules').attr('style')) {
		if ($('.bnpAccount-unorderedList--Signatories').attr('style')) {
			/* cas où la section signatories était ouverte */
			if (!checkSignatory()) {
				$('#errorPart2-Signatory').append('Please complete all of the fields marked with a *.<br/>')
				$('#errorPart2-Signatory').show();
			} else if (!checkDate()) {
				$('#errorPart2-Signatory').append('Wrong Date.<br/>')
				$('.bnpAccount-birthDateContainer').removeClass("js-fieldCorrect");
				$('.bnpAccount-birthDateContainer').addClass('js-fieldError');
				$('#errorPart2-Signatory').show();
			} else {
				/* enregistrer le signataire en cours d'ecriture et ouvrir la section rules */
				saveSignatory();

				$('.bnpAccount-unorderedList--Signatories').removeAttr('style');
				if($('.bnpAccount-unorderedList--Rules').css('display') == "none"){
					$('.bnpAccount-unorderedList--Rules').css('display', 'block');
				}
			}
		} else {
			/* cas de la 1ere ouverture */
			$('.bnpAccount-unorderedList--Rules').css('display', 'block');
		}
	} else {
		/* cas où la section rules était ouverte */
		if (!checkRules()) {
			$('#errorPart2-Rules').append('Please complete all step to created properly your rule.<br/>')
			$('#errorPart2-Rules').show();
		} else {
			saveRule();
		}
	}
}

/* VEnregistrement de la règle dans la liste des règles */
function saveRule() {
	var nbRules = parseInt($('#nbRules').val());
	
	if ($('#bnpAccount-infoRequest-people').find(':checked').size() != 0) {
		var ruleTableContainer = $('.bnpAccount-tableContainer--rules');
		if (ruleTableContainer.find('table').length == 0) {
			// initialiser l'entête du tableau la 1ere fois
			var ruleTable = $('<table class="bnpAccount-table--rules">'+
					'<thead>'+
						'<tr>'+
							'<th>Signatory / Group</th>'+
							'<th>Signatory / Group</th>'+
							'<th>Acting</th>'+
							'<th>Bank operation</th>'+
							'<th>Amount</th>'+
							'<th>Edition</th>'+
						'</tr>'+
					'</thead><tbody></tbody></table>');
			ruleTableContainer.append(ruleTable);
		}
		
		$('.bnpAccount-table--rules').find('tbody').append(function(){
			var tableRow = $('<tr><td></td><td></td><td></td><td></td><td></td>'+
//				'<td>
//					<button class="bnpAccount-btn bnpAccountresumeDataList-action-btn">'+
//					'<img src="../img/svg/create.svg" alt="">'+
//				'</button>
					'<td><button type="button" class="bnpAccount-btn bnpAccountresumeDataList-action-btn deleteButtonRules'+nbRules+'" id="deleteButton'+';'+nbRules+'">'+
					'<img src="../img/svg/delete.svg" alt="">'+
				'</button></td>'+ 
			'</tr>');
			
			$('.bnpAccount-rulesList .bnpAccount-rulesList-btnReverse').each(function(){
				var ruleTableContainer2 = $('.bnpAccount-tableContainer--rules');
				if($(this).siblings('.bnpAccount-infoRequest').attr('id') === "bnpAccount-infoRequest-people"){
					var text = $('#bnpAccount-infoRequest-people').find(':checked').val();
					var splitSG = text.split(';');
					if (splitSG[0] == 'S') {
						ruleTableContainer2.append('<div id="signatoryUseInRules'+splitSG[2]+'"></div>');
						tableRow.find('td:eq(1)').text(splitSG[1]);
					}else if (splitSG[0] == 'G') {
						ruleTableContainer2.append('<div id="collegeUseInRules'+splitSG[2]+'"}></div>');
						tableRow.find('td:eq(1)').text(splitSG[1]);
					} else{ 
						tableRow.find('td:eq(1)').text(text);
					}
					
				} else if($(this).siblings('.bnpAccount-infoRequest').attr('id') === "bnpAccount-infoRequest-With"){
					var text = $('#bnpAccount-infoRequest-With').find(':checked').val();
					if(text === "Individually"){
						tableRow.find('td:eq(0)').text('N/A');
						tableRow.find('td:eq(2)').text(text);
					} else if (text === "Jointly with ") {
						var splitSG = $('#bnpAccount-infoRequest-jointlyPeople').find(':checked').val().split(';');
						if (splitSG[0] == 'S') {
							ruleTableContainer2.append('<div id="signatoryUseInRules2'+splitSG[2]+'"></div>');
							tableRow.find('td:eq(0)').text(splitSG[1]);
						}else if (splitSG[0] == 'G') {
							ruleTableContainer2.append('<div id="collegeUseInRules2'+splitSG[2]+'"}></div>');
							tableRow.find('td:eq(0)').text(splitSG[1]);
						} else{ 
							tableRow.find('td:eq(0)').text($('#bnpAccount-infoRequest-jointlyPeople').find(':checked').val());
						}
						tableRow.find('td:eq(2)').text(text);
					}
				} else {
					console.log($(this), $(this).attr('data-action'), $(this).attr('data-limit'));
					tableRow.find('td:eq(3)').text($(this).attr('data-action'));
					tableRow.find('td:eq(4)').text($(this).attr('data-limit'));
				}
				
			});
			if(!ruleTableContainer.hasClass('is-open')){
				ruleTableContainer.addClass('is-open');
			}
			
			return tableRow;
		});
		
		/** set data as input hidden */
		/* TO DO ! affecter correctement toutes les valeurs ici ou dans le controller (c'est mieux dans le controller */
		
		ruleTableContainer.append('<div class="newRules' + nbRules + '"></div>');
		
		var signatoryOrGroup1 = $('#bnpAccount-infoRequest-people').find(':checked').val();
		var split1 = signatoryOrGroup1.split(';');
		if (split1[0] == 'S') {
			ruleTableContainer.children("div.newRules"+nbRules).append('<input type="hidden" name="account.rulesList[' + nbRules + '].indexSignatory1" value="' + split1[2] + '">');
		} else if (split1[0] == 'G') { // 'G'
			ruleTableContainer.children("div.newRules"+nbRules).append('<input type="hidden" name="account.rulesList[' + nbRules + '].indexCollege1" value="' + split1[2] + '">');
		}		
		if ($('#bnpAccount-infoRequest-jointlyPeople').find(':checked').size() > 0) {
			var signatoryOrGroup2 = $('#bnpAccount-infoRequest-jointlyPeople').find(':checked').val();
			var split2 = signatoryOrGroup2.split(';');
			if (split2[0] == 'S') {
				ruleTableContainer.children("div.newRules"+nbRules).append('<input type="hidden" name="account.rulesList[' + nbRules + '].indexSignatory2" value="' + split2[2] + '">');
			} else if (split2[0] == 'G') { // 'G'
				ruleTableContainer.children("div.newRules"+nbRules).append('<input type="hidden" name="account.rulesList[' + nbRules + '].indexCollege2" value="' + split2[2] + '">');
			}
		}
		ruleTableContainer.children("div.newRules"+nbRules).append('<input type="hidden" name="account.rulesList[' + nbRules + '].amountMin" value="' + $('#bnpAccount-ruleAmount-first').val() + '">');
		ruleTableContainer.children("div.newRules"+nbRules).append('<input type="hidden" name="account.rulesList[' + nbRules + '].amountMax" value="' + $('#bnpAccount-ruleAmount-second').val() + '">');
		ruleTableContainer.children("div.newRules"+nbRules).append('<input type="hidden" name="account.rulesList[' + nbRules + '].typeOperation" value="' + $('#bnpAccount-infoRequest-actionFirst').val() + '">');
		ruleTableContainer.children("div.newRules"+nbRules).append('<input type="hidden" name="account.rulesList[' + nbRules + '].field" value="' + $('#bnpAccount-infoRequest-operation').val() + '">');
		$('#nbRules').val(nbRules + 1);
		
		
		/* reset buttons */
		$('#rulesSignatoryButton').text('List of signatories/groups');				
		$('#bnpAccount-infoRequest-people').find(':checked').removeAttr("checked");
		$('#rulesActingButton').text('Select');	
		$('#rulesActingButton').attr('disabled','disabled');
		$('#bnpAccount-infoRequest-With').find(':checked').removeAttr("checked");
		$('#bnpAccount-infoRequest-jointlyPeople').find(':checked').removeAttr("checked");
		$('#rulesOperationButton').text('Select');	
		$('#rulesOperationButton').attr('disabled','disabled');
		$('#bnpAccount-ruleAmount-first').val('');
		$('#bnpAccount-ruleAmount-first').hide();
		$('#bnpAccount-ruleAmount-second').val('');
		$('#bnpAccount-ruleAmount-second').hide();
		$('#bnpAccount-infoRequest-operation').val('');
		$('#bnpAccount-infoRequest-operation').hide();
		$('#bnpAccount-infoRequest-actionFirst').val('All operations');
		$('#bnpAccount-infoRequest-actionLimits').val('No limit');
		
		$('.deleteButtonRules'+nbRules).click(function(){
			var numRule = (this.id).split(';');
			$(this).parent().parent().remove();
			ruleTableContainer.children("div.newRules"+nbRules).append('<input type="hidden" name="account.rulesList[' + numRule[1] + '].typeOperation" value="suppr">');
			if ($('.bnpAccount-table--rules').find('tbody').find('tr').length == 0) {
				$('.bnpAccount-table--rules').remove();
			}
			$('.newRules'+numRule[1]).remove();
		});
	}	
}

/* Verification des données du formulaire des signataires */
function checkRules() {
	if ($('#bnpAccount-infoRequest-people').find(':checked').size() == 0 &&
			$('#bnpAccount-infoRequest-With').find(':checked').size() == 0 &&
			$('#bnpAccount-infoRequest-jointlyPeople').find(':checked').size() == 0) {
		/* formulaire vide */
		return true;
	} else if ($('#bnpAccount-infoRequest-people').find(':checked').size() == 0 ||
			$('#bnpAccount-infoRequest-With').find(':checked').size() == 0) {
		/* formulaire incomplet */
		return false;
	} else {
		/* formulaire complet */
		return true;
	}
}

 function checkedRep(nb){
	 $('#bnpAccountDocumentsPeople' + nb).attr("checked", "checked");
 }

 /***************************************************************************************/
 /*******DELETE**********/

 function deleteSignatory(count) {
	 debugger;
	 resetErrorSignatory();
	 if($('#signatoryUseInRules'+count).size()>0 || $('#signatoryUseInRules2'+count).size()>0){
		 $('#errorPart2').empty();
		 $('#errorPart2').append('This signatory is in a rule.<br/>')
		$('#errorPart2').show();
	 }else{
		 $('#listSignatoryResume'+count).remove();
	 	$('#signatoryToDelete'+count).val("");
	 	$('#signatoryResumeInRules'+count).remove();
		$('#signatoryResumeInRules2'+count).remove();
	 }
 }
 
 function deleteSignatoryGroup(count) {
	 resetErrorSignatory();
	 if($('#signatoryUseInRules'+count).size()>0 || $('#signatoryUseInRules2'+count).size()>0){
		 $('#errorPart2').empty();
		 $('#errorPart2').append('This signatory is in a rule.<br/>')
		$('#errorPart2').show();
	 }else{
		 $('.listSignatoryGroupResume'+count).remove();
		 $('#signatoryGroupToDelete'+count).val("");
		 $('#signatoryResumeInRules'+count).remove();
		 $('#signatoryResumeInRules2'+count).remove();
	 }
 }
 
 function deleteGroup(count) {
	 resetErrorSignatory();
	 if($('#collegeResume-group'+count).find('.bnpAccount-signatoriesResume-content').size() > 0){
		 $('#errorGroupPart').empty();
		 $('#errorGroupPart').append('The college is not empty.<br/>')
			$('#errorGroupPart').show();
	 } else if($('#collegeUseInRules'+count).size()>0 || $('#collegeUseInRules2'+count).size()>0){
		 $('#errorGroupPart').empty();
		 $('#errorGroupPart').append('This college is in a rule.<br/>')
		$('#errorGroupPart').show();
	 } else {
		 $('#listGroupResume'+count).remove();
		 $('#groupToDelete'+count).val("");
		 $('#collegeResumeInRules'+count).remove();
		$('#collegeResumeInRules2'+count).remove();
	 }
	 
 }
 
 function deleteRules(count) {
	 $('#listRulesResume'+count).remove();
 	$('#ruleToDelete'+count).val("suppr");
 	if ($('.bnpAccount-table--rules').find('tbody').find('tr').length == 0) {
 		$('.bnpAccount-table--rules').find('thead').remove();
	}
 }

 function supprHidden(){
	 for(var i=0; i<parseInt($('#nbSignatories2').val())+1; i++){
		 $('.newSignatory'+i).remove();
		 $('#signatoryToDelete'+i).remove();
		 $('#signatoryGroupToDelete'+i).remove();
	 }
	 for(var i=0; i<parseInt($('#nbColleges').val())+1; i++){
		 $('#groupToDelete'+i).remove();
	 }
	 for(var i=0; i<parseInt($('#nbRules2').val())+1; i++){
		 $('.newRules'+i).remove();
		 $('#ruleToDelete'+i).remove();
	 }
 }
 
 function resetErrorSignatory(){
	 $('#errorPart2-Signatory').empty();
	 $('#errorPart2-Signatory').hide();
	 $('#errorPart2').empty();
	 $('#errorPart2').hide();
	 $('#errorGroupPart').empty();
	 $('#errorGroupPart').hide();
 }


