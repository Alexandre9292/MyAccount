function initBankAddingPage(){
	$("#returnButtonAddBankUser").click(function(){
		exitMyAccounts = false;
		var controller = "../action/cancelAction";
		window.location.replace(controller);
	});
}

function noFilter(){
	$('#loginUser').val('');
	$('#legalEntity option:eq(0)').prop("selected",true);
	$("#legalEntity").select2({ width: '200px' });
	$("#filterRole input[type=checkbox]:checked").each(function(){
		$(this).prop("checked", false);
		});
}

function initBankModifyPage(){
	
	tooltipMe("telClient", $("#patternTel").val());
	
	$("#returnButtonModifyBankUser").click(function(){
		if(confirm($("#msgCancel").val())){
			exitMyAccounts = false;
			var controller = "../action/cancelAction";
			window.location.replace(controller);
		}
	});
	$("#deleteButtonModifyBankUser").click(function(){
		if(confirm($("#msgDelete").val())){
			exitMyAccounts = false;
			var controller = "../action/doDeleteBankUser";
			window.location.replace(controller);
		}
	});
}

function initClientManagementPage(){
	
   	$('#managementClientTable').dataTable({
   		"aaSorting": [[ 0, "asc" ]],
         	"oLanguage": {
         	    "sLengthMenu":     $("#tabLengthMenu").val(),
         	    "sSearch":         $("#tabSearch").val(),
         	    "sZeroRecords":    $("#tabZeroRecords").val(),
         	    "oPaginate": {
         	        "sFirst":    $("#tabFirst").val(),
         	        "sLast":     $("#tabLast").val(),
         	        "sNext":     $("#tabNext").val(),
         	        "sPrevious": $("#tabPrevious").val()
         	    }
           },
   		"aoColumns": [
   		              null,
   		              null,
   		              null
   		          ],
         	"sPaginationType": "bootstrap"
   	});
	
	$("#resetClientFilter").click(function(){
		$('#entity option:eq(0)').prop("selected",true);
		$("#entity").select2({ width: '200px' });
		$('#firstName').val('');
		$('#lastName').val('');
		$('#login').val('');
	});
	
}

function initClientModifyPage(){
	$("#resetButtonClient").click(function(){
		exitMyAccounts = false;
		var controller = "../action/clientManagementResetPasswordController";
		window.location.replace(controller);
	});
	$("#lockButtonClient").click(function(){
		exitMyAccounts = false;
		var controller = "../action/clientManagementLockController";
		window.location.replace(controller);
	});
	$("#unlockButtonClient").click(function(){
		exitMyAccounts = false;
		var controller = "../action/clientManagementUnlockController";
		window.location.replace(controller);
	});
	
	$("#cancelButtonClient").click(function(){
		var controller = "../action/clientManagementLoad";
		if (confirm($("#confirmCanceling").val())) {
			exitMyAccounts = false;
			window.location.replace(controller);
		}
	});
}

function initBankManagementPage(){
   	$('#managementRoleTable').dataTable({
     	"oLanguage": {
     	    "sLengthMenu":     $("#tabLengthMenu").val(),
     	    "sSearch":         $("#tabSearch").val(),
     	    "sZeroRecords":    $("#tabZeroRecords").val(),
     	    "oPaginate": {
     	        "sFirst":    $("#tabFirst").val(),
     	        "sLast":     $("#tabLast").val(),
     	        "sNext":     $("#tabNext").val(),
     	        "sPrevious": $("#tabPrevious").val()
     	    }
       },
     	"sPaginationType": "bootstrap",
     	"aoColumnDefs": [{ "bSortable": false, "aTargets": [ 1,2,3,4,5,6 ] }] 
	});
   	
	$("#addBankUser").click(function(){
		exitMyAccounts = false;
		var controller = "../action/addBankUser";
		window.location.replace(controller);
	});
}