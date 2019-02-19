	var wmap;
	var marker
	var handCursor;
	var isIE = /MSIE/.test( navigator.userAgent );
	var POIclicked = false;
	var infodiv;
	var infodivdrag;
	
	if (isIE)
		handCursor = 'hand';
	else
		handCursor = 'pointer';

	function handle(delta) {
        if (delta < 0) 
			wmap.zoomOut();
        else 
			wmap.zoomIn();
	}
	
	function wheel(event){
        var delta = 0;
        if (!event) // IE
        	event = window.event;
        if (event.wheelDelta) { // IE/Opera
            delta = event.wheelDelta/120;
            // In Opera 9, delta differs in sign as compared to IE
            if (window.opera)
            	delta = -delta;
        } else if (event.detail) { // Mozilla
                /** In Mozilla, sign of delta is different than in IE.
                 * Also, delta is multiple of 3.
                 */
                delta = -event.detail/3;
        }
        /** If delta is nonzero, handle it.
         * Basically, delta is now positive if wheel was scrolled up,
         * and negative, if wheel was scrolled down.
         */
        if (delta)
                handle(delta);
				
		if (event.preventDefault)
                event.preventDefault();
		event.returnValue = false;
	}

	if (window.addEventListener) 
		window.addEventListener('DOMMouseScroll', wheel, false);
	
	function clickPOI() {
		POIclicked=!POIclicked;
		if (POIclicked) {
			wmap.B.style.cursor = 'default';
			document.images["POI"].title = 'Quitar Modo Insertar POI';
		}
		else {
			wmap.B.style.cursor = 'move';
			document.images["POI"].title = 'Poner Modo Insertar POI';
		}
	}
	
	function clickMap(e) {
		if (POIclicked) {
			var gxy = wmap.pixToGeo(wmap.mapMouseX,wmap.mapMouseY);
			
			if (confirm('¿Insertar POI en (' + gxy[0] + ', ' + gxy[1] + ')?')) {
				clickPOI();
				location.href= 'http://localhost:8080/MapViewer/EditPOI.do?action=MAP&latitude=' + gxy[1] + '&longitude=' + gxy[0];
			}
		}
	}
	
	function center(e) {
		var gxy = wmap.pixToGeo(wmap.mapMouseX,wmap.mapMouseY);
		
		wmap.centerOnPointGeo(gxy[0], gxy[1]);
		wmap.render();
    }
	
	function move(x, y) {
		var center = wmap.getCenterGeo();
		wmap.centerOnPointGeo(center[0] + x, center[1] + y);
		wmap.render();
    }

	function showInfo(x, y, name, description, text) {
		infodiv = document.getElementById('info');
		var isHidden = (infodiv.style.visibility == 'hidden');
		var pix = wmap.geoToPix(x, y);
		var infoName = document.getElementById('infoName');
		var infoDescription = document.getElementById('infoDescription');
		var infoHTML = document.getElementById('infoHTML');
		
		if (isHidden) {
			infodiv.style.left = pix[0] + 'px';
			infodiv.style.top = pix[1] + 'px';
			infoName.innerHTML = '<a href="#" onclick="hide(); return false;"><img src="img/close.png" style="border: blue 1px solid;" /></a><br/><center>' + name + '</center>';
			infoDescription.innerHTML = '<center>' + description + '</center>';
			infoHTML.innerHTML = text;
			infodiv.style.visibility = 'visible';
		}
		else {
			infoName.innerHTML = '<a href="#" onclick="hide(); return false;"><img src="img/close.png" style="border: blue 1px solid;" /></a><br/><center>' + name + '</center>';
			infoDescription.innerHTML = '<center>' + description + '</center>';	
			infoHTML.innerHTML = text;
		}
	}
	
	function hide() {
		infodiv = document.getElementById('info');
		infodiv.style.visibility = 'hidden';
	}