/** Cookie disclaimer */
function cookieDisplayDisclaimer() {
	if ($.cookie('dco-cookieDisclaimer') == null) {
		$("#cookie-disclaimer").css("display","block");
	}
}
function cookieAcceptDisclaimer() {
    // Expires in 10 years
    var expDate = new Date();
    expDate.setTime(expDate.getTime() + 315360000000);
	$.cookie('dco-cookieDisclaimer','accepted',{expires:expDate,path:'/'});
	$("#cookie-disclaimer").css("display","none");
}

/** Menu */
var timeout = 500;
var closetimer = 0;
var ddmenuitem = 0;

// open hidden layer
function mopen(element, id) {
    // cancel close timer
    mcancelclosetime();

    // close old layer
    mclose();

    // get new layer and show it
    ddmenuitem = document.getElementById(id);
    $(ddmenuitem).show();

    $(ddmenuitem).position({
        my: "left top",
        at: "left bottom",
        of: $(element),
        collision: "fit"
    });
}

// close showed layer
function mclose() {
    if (ddmenuitem) {
        $(ddmenuitem).hide();
    }
}

// go close timer
function mclosetime() {
    closetimer = window.setTimeout(mclose, timeout);
}

// cancel close timer
function mcancelclosetime() {
    if (closetimer) {
        window.clearTimeout(closetimer);
        closetimer = null;
    }
}

// close layer when click-out
document.onclick = mclose;

// Close session when user leave page (close tab, close browser and change url).
var exitMyAccounts = true;
$(window).on("beforeunload", function() { 
    if(exitMyAccounts){
    	$.ajax({
    		async: false,
    		url: "logout"
    	});
    } 
})

$(window).load(function() {
    var maxHeight = 0;
    var lis = $("#sddm > li.mainMenuItem");

    // get max li height
    lis.each(function() { maxHeight = Math.max(maxHeight, this.offsetHeight); });

    // set max li height to all li elements - to not break the design
    lis.each(function() { $(this).height(maxHeight); });
    
    // Close session : catch submit and click on <a> <input> and <button>
    $('a').on('click', function() {
    	exitMyAccounts = false;
    });
    $('form').on('submit', function() {
    	exitMyAccounts = false;
    });
    $(document).bind('keypress', function(e) {
        if (e.keyCode == 116){
        	exitMyAccounts = false;
        }
      });
    $("input[type=submit]").bind("click", function() {
    	exitMyAccounts = false;
    });
    $("button").bind("click", function() {
    	exitMyAccounts = false;
    });
});

$(document).ready(function() {
    // set the height of the menu div to match the height of the menues
    if ($("#sddm").length) {
        $("#menu").height($("#sddm").offset().height);
    }
    
    selectToselect2();
    
});

$(document).resize(function() {
    // set the height of the menu div to match the height of the menues
    if ($("#sddm").length) {
        $("#menu").height($("#sddm").offset().height);
    }
});

/** Lists sort */
function NASort(a, b) {
    return (a.innerHTML > b.innerHTML) ? 1 : -1;
};

/** User bank management */
function hasChangeColumn(id){
	$('#hasChangeColumn-' + id).val('true');
}
function modifyUser(id){
	exitMyAccounts = false;
	var controller = "../action/modifyBankUser?id=" + id;
	window.location.assign(controller);
}
function modifyClientLoad(id){
	exitMyAccounts = false;
	var controller = "../action/modifyClientUserLoad?id=" + id;
	window.location.assign(controller);
}
function userBankListToggleSuperAdm(userId, init) {
	if ($('#chkSA-'+userId).attr('checked')) {
		$('#chkMgmtAccnt-'+userId).attr('checked', true);
		$('#chkMgmtAccnt-'+userId).attr('disabled', true);
		$('#chkMgmtDoc-'+userId).attr('checked', true);
		$('#chkMgmtDoc-'+userId).attr('disabled', true);
		$('#chkViewCltData-'+userId).attr('checked', true);
		$('#chkViewCltData-'+userId).attr('disabled', true);
		$('#chkViewStat-'+userId).attr('checked', true);
		$('#chkViewStat-'+userId).attr('disabled', true);
		$('#chkMgmtParam-'+userId).attr('checked', true);
		$('#chkMgmtParam-'+userId).attr('disabled', true);
	} else if (!init) {
		$('#chkMgmtAccnt-'+userId).attr('checked', false);
		$('#chkMgmtAccnt-'+userId).removeAttr('disabled');
		$('#chkMgmtDoc-'+userId).attr('checked', false);
		$('#chkMgmtDoc-'+userId).removeAttr('disabled');
		$('#chkViewCltData-'+userId).attr('checked', false);
		$('#chkViewCltData-'+userId).removeAttr('disabled');
		$('#chkViewStat-'+userId).attr('checked', false);
		$('#chkViewStat-'+userId).removeAttr('disabled');
		$('#chkMgmtParam-'+userId).attr('checked', false);
		$('#chkMgmtParam-'+userId).removeAttr('disabled');
	}
}
function userBankUniqueToggleSuperAdm(init) {
	if ($('#roleSA').attr('checked')) {
		$('#roleMgmtAccnt').attr('checked', true);
		$('#roleMgmtAccnt').attr('disabled', true);
		$('#roleMgmtDoc').attr('checked', true);
		$('#roleMgmtDoc').attr('disabled', true);
		$('#roleViewCltData').attr('checked', true);
		$('#roleViewCltData').attr('disabled', true);
		$('#roleViewStat').attr('checked', true);
		$('#roleViewStat').attr('disabled', true);
		$('#roleMgmtParam').attr('checked', true);
		$('#roleMgmtParam').attr('disabled', true);
	} else if (!init) {
		$('#roleMgmtAccnt').attr('checked', false);
		$('#roleMgmtAccnt').removeAttr('disabled');
		$('#roleMgmtDoc').attr('checked', false);
		$('#roleMgmtDoc').removeAttr('disabled');
		$('#roleViewCltData').attr('checked', false);
		$('#roleViewCltData').removeAttr('disabled');
		$('#roleViewStat').attr('checked', false);
		$('#roleViewStat').removeAttr('disabled');
		$('#roleMgmtParam').attr('checked', false);
		$('#roleMgmtParam').removeAttr('disabled');
	}
}
function userBankMainPageToggleSuperAdm(init) {
	if ($('#role_1').attr('checked')) {
		$('#role_2').attr('checked', true);
		$('#role_2').attr('disabled', true);
		$('#role_3').attr('checked', true);
		$('#role_3').attr('disabled', true);
		$('#role_4').attr('checked', true);
		$('#role_4').attr('disabled', true);
		$('#role_6').attr('checked', true);
		$('#role_6').attr('disabled', true);
		$('#role_5').attr('checked', true);
		$('#role_5').attr('disabled', true);
	} else if (!init) {
		$('#role_2').attr('checked', false);
		$('#role_2').removeAttr('disabled');
		$('#role_3').attr('checked', false);
		$('#role_3').removeAttr('disabled');
		$('#role_4').attr('checked', false);
		$('#role_4').removeAttr('disabled');
		$('#role_6').attr('checked', false);
		$('#role_6').removeAttr('disabled');
		$('#role_5').attr('checked', false);
		$('#role_5').removeAttr('disabled');
	}
}
function tooltipMe(id, pattern){
	$("#" + id).attr("title",pattern);
	$("#" + id).tooltip({
	});
}

function deleteParam(idType, idCountry, entry){
	exitMyAccounts = false;
	var controller = "../action/deleteParam?typeParam=" + idType + "&country=" + idCountry + "&entry=" + entry;
	window.location.replace(controller);
}

function selectToselect2(){
	
	$("select").each(function(){
		$(this).select2({ 
			width: '200px',
			minimumResultsForSearch: 10
		 });
	});
	
}

//Method to validate the login form
function validateForm(form) {
	if (!form.login.value || !form.password.value) {

		if ($("#infoBlock .warningblock").length == 0) {
			$("#infoBlock").append("<div class=warningblock></div>");
		}

		$(".errorblock").remove();

		if (!form.login.value) {
			$(".warningblock").text($("#emptyLoginFieldMessage").val());
			return false;
		} else if (!form.password.value) {
			$(".warningblock").text($("#emptyPasswordFieldMessage").val());
			return false;
		}
	} else {
		return true;
	}
}

function initAddressPreview(){
	$(".entityAddressForm").hide();
	$(".editEntityAddress").click(function() {
		$($(this).attr("data-toggle")).show();
	});
}

function createDeleteDataPopup(isDeleted, confirmButton, cancelButton){
	if(isDeleted){
		$(window).load(function(){
			$( "#dialog-confirm" ).dialog({
		      resizable: false,
		      height:120,
		      width: 500,
		      autoOpen: false,
		      dialogClass: 'dialogWithClose',
		      modal: true
		    });
		});
	} else {
		$(window).load(function(){
			$( "#dialog-confirm" ).dialog({
		      resizable: false,
		      height:150,
		      width: 500,
		      autoOpen: false,
		      dialogClass: 'noTitleDialog',
		      modal: true,
		      buttons: [{
		    	  text: confirmButton,
		    	  click: function() {
		          $( this ).dialog( "close" );
		          exitMyAccounts = false;
				  var controller = "../action/deleteUserData";
				  window.location.replace(controller);
		        }},{
		        text: cancelButton,
		        click: function() {
		          $( this ).dialog( "close" );
		        }
		      }]
		    });
		});
	}
}
