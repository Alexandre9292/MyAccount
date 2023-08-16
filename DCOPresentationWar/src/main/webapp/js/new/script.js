(function($){
	// Bouton pour afficher le menu mobile
	$('.menu-toggle').click(function(e){
		e.preventDefault();
		$('.main-nav').toggleClass('open');
	});
	
	$(document).click(function(e){
		if(!$(e.target).closest('.main-nav').length && !$(e.target).closest('.menu-toggle').length)
			$('.main-nav').removeClass('open');
	});
	
	// Boutons de changement de variante du template
	$('#swicth-name').click(function(e){
		e.preventDefault();
		$('.topbar .site-name').hide();
		$('.topbar .main-logo').removeClass('has-sitename');
	});
	
	$('#swicth-noname').click(function(e){
		e.preventDefault();
		$('.topbar .site-name').show();
		$('.topbar .main-logo').addClass('has-sitename');
	});

	
	//

	/* ---- placeholder fallback for IE9 ------*/
	var placeholderFallback = function(){
	    if ( !("placeholder" in document.createElement("input")) ) {
	        $("input[placeholder], textarea[placeholder]").each(function() {
	            var val = $(this).attr("placeholder");
	            if ( this.value == "" ) {
	                this.value = val;
	            }
	            $(this).focus(function() {
	                if ( this.value == val ) {
	                    this.value = "";
	                }
	            }).blur(function() {
	                if ( $.trim(this.value) == "" ) {
	                    this.value = val;
	                }
	            })
	        });

	        // Clear default placeholder values on form submit
	        $('form').submit(function() {
	            $(this).find("input[placeholder], textarea[placeholder]").each(function() {
	                if ( this.value == $(this).attr("placeholder") ) {
	                    this.value = "";
	                }
	            });
	        });
	    }
	}

	/* ---- display password ------*/
	var _btPassword = $('.bt-password')
	,	_inputs = $('input[type="password"]')
	,	passwordDisplayer = false
	,	_passwordInput = $('input#password')
	;
	_btPassword.on('click', function(){
		_btPassword.toggleClass('active');
		passwordDisplayer = !passwordDisplayer;
		if(passwordDisplayer){
			_inputs.attr('type', 'text');
		} else {
			_inputs.attr('type', 'password');
		}
	})

	_passwordInput.keydown(checkBtPWvisibility)
	_passwordInput.keyup(checkBtPWvisibility)

	function checkBtPWvisibility(){
		if(_passwordInput.val().length>0){
			_btPassword.addClass('visible')
		} else {
			_btPassword.removeClass('visible')
		}
	}


	if($('html').hasClass('lt-ie10')) {
		placeholderFallback();
		_btPassword.trigger('click')
	}



	/* ---- scroll down ------*/
	var _btScrollDown = $('.scrolldown');

	_btScrollDown.on('click', function(){
		scrollTo($('.page-header').height() + $('header').height())
	})
	function scrollTo(position, duration){
		var d = duration || 1000;
		$("html, body").animate({ scrollTop: position }, d);
	}



	/* ---- map animation ------*/
	var _map = $('.map')
	,	_window = $(window)
	,	_document = $(document)
	,	scrollPercent = 0
	,	_headerContainer = $('.page-header .container').eq(0)
	,	_video = $('video')
	,	_videoWrapper = _video.parent()
	,	_contentContainer = $('.page-content .container').eq(0)
	;

	function getScrollPercent() {
	  	s = _window.scrollTop(),
	    d = _document.height(),
	    c = _window.height();
	    scrollPercent = (s / (d-c));// * 100;
	    //return scrollPercent;
	}

	$(window).resize(function(){
		animateScrollDown()
	})

	_window.scroll(function(e){
		getScrollPercent();
		animateMap()
		animateHeaderContent()
		animateScrollDown()
		animateVideo()
		animatePageContent()
	})

	function animateMap(){
		var distance = _map.height()/5
		,	posY = distance - distance*scrollPercent
		_map.css('margin-top', posY)
	}
	function animateHeaderContent(){
		var distance = _headerContainer.height()/5
		,	posY = 50+(scrollPercent*20)
		_headerContainer.css('top', posY+'%')
	}
	function animateScrollDown(){
		var videoH = _videoWrapper.height()
		,	videoOFT = _videoWrapper.offset().top
		,	totalH = videoH + videoOFT
		,	 difH = totalH - _window.height()
		,	distance = 20
		difH = difH > 0 ? difH + distance : distance;
		var posY = difH-(scrollPercent*difH)
		_btScrollDown.css('bottom', posY)
	}

	function animateVideo(){
		var distance = (_video.height() - _videoWrapper.height())/2
		,	posY = scrollPercent*distance
		_video.css('margin-top', posY)
	}

	function animatePageContent(){
		var distance = _contentContainer.height()/10
		,	posY = distance - scrollPercent*distance
		_contentContainer.css('margin-top', posY)
	}
	setTimeout(function(){
		animatePageContent()
		animateScrollDown()
	}, 100);

})(jQuery);


