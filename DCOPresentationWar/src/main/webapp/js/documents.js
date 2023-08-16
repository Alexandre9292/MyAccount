confirma = false;

$(document).ready(function(){

	if($('#isDocumentsFilterBlockHidden').val() == 'true'){
		$('#hideDocumentsFilterBlock').css("display","block");
		toggleValueButton();
	}

	$('#downloadDocumentsButton').click(function(){
		downloadDocument();
	});
	
	$('#hideDocumentsFilterButton').click(function(){
		slideFilter();
	});

	$('#deleteDocumentsButton').click(function(){
		
		if($("#documentsCheckedForm input[type=checkbox]:checked").size() == 0){
			confirma = true;
		}
		else{
			if(confirm($('#deleteConfirmValue').val())){
				confirma = true;
			}
			else{
				confirma = false;
				$('#documentsCheckedForm').reset();
			}
		}
	});
	
	$('#razDocumentsFilter').click(function(){
		$('#country option:eq(0)').prop("selected",true);
		razHiddenElements();
	});
	// by FTA auto-select legal entity field
	$("#country").on("change", function(){
		selectLegalEntity();
		});
});

function select2width(){
	$("#country").select2({ width: '200px' });
	$("#legalEntity").select2({ width: '200px' });
	$("#language").select2({ width: '200px' });
	$("#documentType").select2({ width: '200px' });
}

function confirmAction(){
	return confirma;
}

function downloadDocument(){
	var dlDocuments='';
	var dlLaunch = false;
	$("#documentsCheckedForm input:checkbox").each(function(){
	    var $this = $(this);
	    if($this.is(":checked")){
	    	dlLaunch=true;
	    	dlDocuments+=$this.attr("value")+';';
	    }
	});
	if (dlLaunch) {
		window.open('downloadDocumentsChecked?id='+dlDocuments);
	}
}

function razHiddenElements(){
	$('#language option:eq(0)').prop("selected",true);
	$('#documentType option:eq(0)').prop("selected",true);
	$('#legalEntity option:eq(0)').prop("selected",true);
	$('#documentTitle').val('');
	select2width();
}

function toggleValueButton(){
	var toggleVal = $('#hideDocumentsFilterButton').val();
	$('#hideDocumentsFilterButton').val($('#toggleValueButton').val());
	$('#toggleValueButton').val(toggleVal);
}

function slideFilter(){
	$('#hideDocumentsFilterBlock').slideToggle(0,function(){
		
		if($('#isDocumentsFilterBlockHidden').val() == 'true'){
			$('#isDocumentsFilterBlockHidden').val('false');
		}
		else{
			$('#isDocumentsFilterBlockHidden').val('true');
		}
		razHiddenElements();
		toggleValueButton();
	});
}

function modifyDocument(id){
	exitMyAccounts = false;
	var controller = "../action/modifyDocument?id=" + id;
	window.location.replace(controller);
}
function fetchLegalEntity(id){
	var ret = $.ajax({
		async:false,
		type: 'GET',
		url: "../action/fetchLegalEntityFromCountry?id=" + id,
		dataType: 'json',
		cache:false,
		success: function(data){
			fillLegalEntitySelect(data);
		}
	});
	return ret;
}
function fetchCountry(id){
	var ret = $.ajax({
		async:false,
		type: 'GET',
		url: "../action/fetchCountryFromLegalEntity?id=" + id,
		dataType: 'json',
		cache:false,
		success: function(data){
			fillCountrySelect(data);
		}
	});
	return ret;
}
function fillLegalEntitySelect(data){
	
	$("#legalEntity").select2("disable");
	
	var idSelected = $("#legalEntity").find('option:selected').val();
	$("#legalEntity").find('option:not(:first)').remove();

	$.each(data, function() {
		if(idSelected == this.id){
			$("#legalEntity").append($("<option selected='selected' onclick='javascript:fetchCountry("+this.id+")'/>").val(this.id).text(this.label));
			
		}else{
			$("#legalEntity").append($("<option onclick='javascript:fetchCountry("+this.id+")'/>").val(this.id).text(this.label));
		}
	});
	
	$("#legalEntity").select2({ width: '200px' });
}
function fillCountrySelect(data){
	
	var idSelected = $("#country").find('option:selected').val();
	$("#country").find('option:not(:first)').remove();

	$.each(data, function() {
		if(idSelected == this.id){
			$("#country").append($("<option  selected='selected' onclick='javascript:fetchLegalEntity("+this.id+")'/>").val(this.id).text(this.label));
		}else{
			$("#country").append($("<option onclick='javascript:fetchLegalEntity("+this.id+")'/>").val(this.id).text(this.label));
		}
	});
}
function selectLegalEntity(){
	var pos = $("#country" + " option:selected").attr("legalEntityId");
	$(legalEntity).select2("val", pos);
}