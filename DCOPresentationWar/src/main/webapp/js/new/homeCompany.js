$(function() {
	
	/* Gestion slide des onglets */
	var companyResumeFooter = $('.bnpAccount-companyResume-footer');
	var companyResumeScroll = companyResumeFooter.find('.bnpAccount-companyResume-scroll');
	var companyResumeScrollWidth = companyResumeScroll.width();

	companyResumeFooter.find('#bnpAccount-btn--backScrollResume').on('click', function(){
		var positionLeft = companyResumeScroll.position().left;
		if(positionLeft < 0){
			positionLeft += 200;
			companyResumeScroll.css('transform','translateX('+positionLeft+'px)');
		}else {
			companyResumeScroll.css('transform','translateX(0)');
		}
		
	});
	companyResumeFooter.find('#bnpAccount-btn--forwardScrollResume').on('click', function(){
		console.log("width", companyResumeScrollWidth, "left", companyResumeScroll.position().left)
		var positionLeft = companyResumeScroll.position().left;
		if(positionLeft > (-1*companyResumeScrollWidth)){
			positionLeft -=200;
			companyResumeScroll.css('transform','translateX('+positionLeft+'px)');
		}else {
			companyResumeScroll.css('transform','translateX('+ companyResumeScrollWidth +')');
		}
	});
	
	
	
	
});


/** Gestion des cartes de pays */
function showCountryImage(countryID, svg) {
	svg.append("<use xlink:href=\"#" + countryID + "\">");
}

function markedCountriesWithAccount(countryID) {
	$('#'+countryID+'-map').css('fill', '#198953');
}


/** chargement */
$(document).ready(function() {

	// Indiquer l'onglet selectionné
	var tagId = $("#selectedTagValue").val();
	$("#Company" + tagId ).replaceWith(
			"<h2 class=\"bnpAccount-inscriptionList-mainTitle bnpAccount-companyResume-footer-mainTitle\">" +
			$("#Company" + tagId ).text() + "</h2>" );
	
});

function changeSelectedTagValue(numero) {
	$("#selectedTagValue").val(numero);
}