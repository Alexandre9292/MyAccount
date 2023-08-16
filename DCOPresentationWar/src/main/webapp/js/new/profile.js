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