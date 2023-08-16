$(function(){
	var inscriptionList = $('.bnpAccount-inscriptionList');
	if(inscriptionList.size()>0){
		$('#bnpAccount-infoRequest-actionFirst').on('change', function(){
			if($(this)[0].value === "" || $(this)[0].value === "All operations except"){
				$(this).siblings("select, input").hide();
				$('.bnpAccount-infoRequest-freeText').show();
			}else{
				$(this).siblings("select,input").show();
				$('.bnpAccount-infoRequest-freeText').hide();
			}
		});

		$('#bnpAccount-infoRequest-actionLimits').on('change', function(){
			if($(this)[0].value !== "No limit"){
				$(this).next('#bnpAccount-ruleAmount-first').show();
				if($(this)[0].value === "Above") {
					$(this).siblings('#bnpAccount-ruleAmount-second').hide();
					$(this).next('#bnpAccount-ruleAmount-first').show();
				}else if($(this)[0].value === "Below"){
					$(this).siblings('#bnpAccount-ruleAmount-second').show();
					$(this).next('#bnpAccount-ruleAmount-first').hide();
				}else{
					$(this).siblings('#bnpAccount-ruleAmount-second').show();
				}
			}else if($(this)[0].value === "No limit"){
				$(this).siblings().hide();
			}
		});

		$('#bnpAccount-validateSignatory').click(function(){
			$('.bnpAccount-rules').removeAttr('style');
			$('.bnpAccount-rules').addClass('bnpAccount-js-inscriptionList-slideContent');
			$('.bnpAccount-rules').addClass('is-open');	
		});
		
		$('.bnpAccount-infoRequest-btnClose').click(function(){
			$(this).parent().hide();
		});

		var rulesBloc = $('.bnpAccount-rules');
		$('.bnpAccount-rulesList-btnReverse').each(function(){
			$(this).on('click', function(e){
				e.preventDefault();
				$(this).addClass('bnpAccount-highLight');
				$(this).siblings('.bnpAccount-infoRequest:not(#bnpAccount-infoRequest-jointlyPeople)').show();
			})
		});

		var actionBtnClick = function(e){
			e.preventDefault();
			var parentModal = $(this).parents('.bnpAccount-infoRequest');
			var liParent = parentModal.parent();
			if(parentModal.find('#bnpAccountSignatoryActing2').is(':checked')){
				var targetModal = $('#bnpAccount-infoRequest-jointlyPeople');
				targetModal.show();
				targetModal.find('.bnpAccount-btn--action').off('click');
				targetModal.find('.bnpAccount-btn--action').on('click', function(){
					targetModal.hide();
					liParent.next('li').find('.bnpAccount-rulesList-btnReverse').removeAttr('disabled');
					targetModal.find('.bnpAccount-btn--action').off('click');
					$(this).find('.bnpAccount-btn--action').on('click',actionBtnClick);
					parentModal.parent().find('.bnpAccount-rulesList-btnReverse').text(function(){	
						var text0 = targetModal.find(':checked').val();
						var splitSG = text0.split(';');
						if (splitSG[0] == 'S' || splitSG[0] == 'G') {
							return $(this).text() + splitSG[1];
						} else{ 
							return $(this).text() + targetModal.find(':checked').val();
						}
					}).attr('data-target', targetModal.find(':checked').val()).removeClass('bnpAccount-highLight');
				});
			}

			liParent.next('li').find('.bnpAccount-rulesList-btnReverse').removeAttr('disabled').addClass('bnpAccount-highLight');
			liParent.find('.bnpAccount-rulesList-btnReverse').text(function(){
				$(this).text('');
				if(parentModal[0].id === 'bnpAccount-infoRequest-people'){
					/* First People */
					var text = parentModal.find(':checked').val();
					var splitSG = text.split(';');
					if (splitSG[0] == 'S' || splitSG[0] == 'G') {
						return splitSG[1];
					} else{ 
						return text;
					}
				} else if(parentModal[0].id === 'bnpAccount-infoRequest-With'){
					/* Acting */
					return parentModal.find(':checked').val();
				} else if(parentModal[0].id === 'bnpAccount-infoRequest-BankOperation'){
					/* Operation */
					var operation = $('#bnpAccount-infoRequest-actionFirst').find(':selected').val() +' ';
					if($('#bnpAccount-infoRequest-actionFirst').next('input').is(':visible')){
						operation += $('#bnpAccount-infoRequest-actionFirst').next('input').val()+' ';
					}
					$(this).attr('data-action', operation);
					var limit = $('#bnpAccount-infoRequest-actionLimits').find(':selected').val()+' ';
					$('#bnpAccount-infoRequest-actionLimits').siblings('input').each(function(){
						if($(this).is(':visible')){
							if($(this).attr('id') === 'bnpAccount-ruleAmount-second' && $('#bnpAccount-infoRequest-actionLimits').find(':selected').val() === 'between'){
								limit += ' and ' +$(this).val();
							} else {
								limit += $(this).val();
							}
							limit += ' ' + $('#currencyRules').val();						
						}
					});
					$(this).attr('data-limit', limit);
					return operation+limit;
				} else {
					var selectedRep = parentModal.find(':checked');
					var list = '';
					selectedRep.each(function () {
						var part = $(this).val().split(';');
						list += part[1] + ', ';
					});
					return list;
				}
		
			}).removeClass('bnpAccount-highLight');
			parentModal.hide();
		};

		$('.bnpAccount-infoRequest').each(function(){
			$(this).find('.bnpAccount-btn--action').on('click',actionBtnClick);
		});

		inscriptionList.find("> li").each(function(){
			var $thisLi = $(this);
			var slideContent = $thisLi.find('.bnpAccount-js-inscriptionList-slideContent');
			var slideTriggerButton = $thisLi.find('.bnpAccount-js-slideTrigger');

			if($(this).hasClass("bnpAccount-inscriptionList--representative")){
				var legalDocumentRadio = $thisLi.find("input:radio[name='account.strategyDocument']");
				legalDocumentRadio.on("change", function(){				
					if($(this).is(":checked")){
						if($(this)[0].value === "Yes"){
							$thisLi.find(".bnpAccount-signatoryBloc,.bnpAccount-rules, .bnpAccount-signatoriesResume, .bnpAccount-signatories").removeClass("is-open");
							$('#bnpAccount-legalDocument-description').css('display', 'block');
							$('#bnpAccount-legalDocument-intro').removeAttr('style');
							$thisLi.find(".bnpAccount-signatories").removeAttr('style');
						} else {
							$thisLi.find(".bnpAccount-signatoryBloc,.bnpAccount-rules, .bnpAccount-signatoriesResume, .bnpAccount-signatories").addClass("is-open");
							$('#bnpAccount-legalDocument-description').removeAttr('style');
							$('#bnpAccount-legalDocument-intro').css('display', 'block');
							$thisLi.find(".bnpAccount-signatories").css('display', 'block');
						}
						$('#NextPart2').show();
					}
				});
			}

			slideTriggerButton.click(function(){
				var legalDocumentRadio = $thisLi.find("input:radio[name='account.strategyDocument']");
				slideContent = $thisLi.find('.bnpAccount-js-inscriptionList-slideContent');
				if($thisLi.hasClass('bnpAccount-inscriptionListItem--slidedUp')){
					$thisLi.removeClass('bnpAccount-inscriptionListItem--slidedUp');
					slideContent.addClass('is-open');
					if (legalDocumentRadio.is(":checked")) {
						$('signatoryForm').addClass('is-open');
						$('contentPart2_signatories').addClass('is-open');
						if($('#bnpAccounthaveLegalDocument').is(":checked")){
							$thisLi.find(".bnpAccount-signatoryBloc,.bnpAccount-rules, .bnpAccount-signatoriesResume, .bnpAccount-signatories").removeClass("is-open");
							$('#bnpAccount-legalDocument-description').css('display', 'block');
							$('#bnpAccount-legalDocument-intro').removeAttr('style');
							$thisLi.find(".bnpAccount-signatories").removeAttr('style');
						} else {
							$thisLi.find(".bnpAccount-signatoryBloc,.bnpAccount-rules, .bnpAccount-signatoriesResume, .bnpAccount-signatories").addClass("is-open");
							$('#bnpAccount-legalDocument-description').removeAttr('style');
							$('#bnpAccount-legalDocument-intro').css('display', 'block');
							$thisLi.find(".bnpAccount-signatories").css('display', 'block');
						}
						$('#NextPart2').show();
					}
				} else {
					$thisLi.addClass('bnpAccount-inscriptionListItem--slidedUp');
					slideContent.removeClass('is-open');
					$('#NextPart2').hide();
					$('#bnpAccount-legalDocument-intro').hide();
				}
			});
		});
	}
	
	$('#bnpAccount-invite').on('click', function(e){
		e.preventDefault();
		$('.bnpAccount-modalContainer').addClass('is-open');
	});

	$('.bnpAccount-modalContainer').on('click', function(){
		$(this).removeClass('is-open');
	});
	
	/******************************/
	/** Verification des données **/
	$('input[required=true]').each(function(index){
		$(this).on('blur', function(e){
			var indexOfField = $('input[required=true]').index(this);
			if(!e.target.checkValidity() || this.value.length === 0){		
				$(this).parent().addClass("js-fieldError");
				$(this).parent().removeClass("js-fieldCorrect");
			}else{
				$(this).parent().removeClass("js-fieldError");
				$(this).parent().addClass("js-fieldCorrect");
			}
		})
	});
	
	$('select[required=true]').each(function(index){
		$(this).on('blur', function(e){
			var indexOfField = $('input[required=true]').index(this);
			if(!e.target.checkValidity() || this.value.length === 0){		
				$(this).parent().addClass("js-fieldError");
				$(this).parent().removeClass("js-fieldCorrect");
			}else{
				$(this).parent().removeClass("js-fieldError");
				$(this).parent().addClass("js-fieldCorrect");
			}
		})
	});
	
	$('input[type=mail]').each(function(index){
		$(this).on('blur', function(e){
			if(validateEmail($(this).val()) == true){
				$(this).parent().removeClass("js-fieldError");
				$(this).parent().addClass("js-fieldCorrect");
			}else{
				$(this).parent().addClass("js-fieldError");
				$(this).parent().removeClass("js-fieldCorrect");
			}
		})
	});
	
	$('input[type=tel]').each(function(index){
		$(this).on('blur', function(e){
			if(validateTel($(this).val()) == true){
				$(this).parent().removeClass("js-fieldError");
				$(this).parent().addClass("js-fieldCorrect");
			}else{
				$(this).parent().addClass("js-fieldError");
				$(this).parent().removeClass("js-fieldCorrect");
			}
		})
	});
	
	
//	 Bloquer les listes de selection à choix unique
	$('select').each(function(index){
		var count = $(this).find('option').size();
		
		if (count == 2) {
			$(this).find("option[value='']").removeAttr("selected");
			$(this).find("option:eq(1)").attr("selected", "selected");
			$(this).attr("disabled", "disabled");	
			$(this).parent().append('<input type="hidden" name="' + $(this).attr("name")+'" value="' + $(this).find("option:eq(1)").val() + '">');
		}
	});
});




/**************************************************************/
/*** GENERIC ***/

/** verification données */
function validateEmail(email) {
    var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    return re.test(email);
}

function validateTel(tel) {
    var re = /^\+([\+(]?(\d){2,}[)]?[- \.]?(\d){2,}[- \.]?(\d){2,}[- \.]?(\d){2,}[- \.]?(\d){2,})|([\+(]?(\d){2,}[)]?[- \.]?(\d){2,}[- \.]?(\d){2,}[- \.]?(\d){2,})|([\+(]?(\d){2,}[)]?[- \.]?(\d){2,}[- \.]?(\d){2,})/; 
    return re.test(tel);
}

/** Lists sort */
function NASort(a, b) {
    return (a.innerHTML > b.innerHTML) ? 1 : -1;
};

/** Change focus when input filled */
function autotab(original,destination) {
	if (original.getAttribute&&original.value.length==original.getAttribute("maxlength"))
	$('#'+destination).focus()
}
