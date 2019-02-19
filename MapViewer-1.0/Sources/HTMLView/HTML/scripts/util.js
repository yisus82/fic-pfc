String.prototype.camelize = function() {
  var oStringList = this.split('-');
  if(oStringList.length == 1)    
    return oStringList[0];
  var ret = this.indexOf("-") == 0 ? 
    oStringList[0].charAt(0).toUpperCase() + oStringList[0].substring(1) : oStringList[0];
  for(var i = 1, len = oStringList.length; i < len; i++){
    var s = oStringList[i];
    ret += s.charAt(0).toUpperCase() + s.substring(1)
  }
  return ret;
};

Element.getStyle = function(element, style) {
  element = $(element);
  var value = element.style[style.camelize()];
  if(!value)
    if(document.defaultView && document.defaultView.getComputedStyle) {
      var css = document.defaultView.getComputedStyle(element, null);
      value = (css!=null) ? css.getPropertyValue(style) : null;
    } else if(element.currentStyle) {
      value = element.currentStyle[style.camelize()];
    }
  
  // If top, left, bottom, or right values have been queried, return "auto" for consistency resaons 
  // if position is "static", as Opera (and others?) returns the pixel values relative to root element 
  // (or positioning context?)
  if (window.opera && (style == "left" || style == "top" || style == "right" || style == "bottom"))
    if (Element.getStyle(element, "position") == "static") value = "auto";
    
  if(value=='auto') value = null;
  return value;
};

// converts rgb() and #xxx to #xxxxxx format,
// returns self (or first argument) if not convertable
Element.makePositioned = function(element) {
  element = $(element);
  var pos = Element.getStyle(element, 'position');
  if(pos =='static' || !pos) {
    element._madePositioned = true;
    element.style.position = "relative";
    // Opera returns the offset relative to the positioning context, when an element is position relative 
    // but top and left have not been defined
    if (window.opera){
      element.style.top = 0;
      element.style.left = 0;
    }  
  }
};
  
Element.undoPositioned = function(element) {
  element = $(element);
  if(typeof element._madePositioned != "undefined"){
    element._madePositioned = undefined;
    element.style.position = "";
    element.style.top = "";
    element.style.left = "";
    element.style.bottom = "";
    element.style.right = "";	  
  }
};
Element.getOpacity = function(element){
  var opacity;
  if (opacity = Element.getStyle(element, "opacity"))
    return parseFloat(opacity);
  if (opacity = (Element.getStyle(element, "filter") || '').match(/alpha\(opacity=(.*)\)/))
    if(opacity[1]) return parseFloat(opacity[1]) / 100;
  return 1.0;
};

Element.setOpacity = function(element, value){
  element= $(element);
  var els = element.style;
  if (value == 1){
    els.opacity = '0.999999';
    if(/MSIE/.test(navigator.userAgent))
      els.filter = Element.getStyle(element,'filter').replace(/alpha\([^\)]*\)/gi,'');
  } else {
    if(value < 0.00001) value = 0;
    els.opacity = value;
    if(/MSIE/.test(navigator.userAgent))
      els.filter = Element.getStyle(element,'filter').replace(/alpha\([^\)]*\)/gi,'') + 
        "alpha(opacity="+value*100+")";
  }  
};

Element.getInlineOpacity = function(element){
  element= $(element);
  var op;
  op = element.style.opacity;
  if (typeof op != "undefined" && op != "") return op;
  return "";
};

Element.setInlineOpacity = function(element, value){
  element= $(element);
  var els = element.style;
  els.opacity = value;
};

Element.getDimensions = function(element){
  element = $(element);
  // All *Width and *Height properties give 0 on elements with display "none", 
  // so enable the element temporarily
  if (Element.getStyle(element,'display') == "none"){
    var els = element.style;
    var originalVisibility = els.visibility;
    var originalPosition = els.position;
    els.visibility = "hidden";
    els.position = "absolute";
    els.display = "";
    var originalWidth = element.clientWidth;
    var originalHeight = element.clientHeight;
    els.display = "none";
    els.position = originalPosition;
    els.visibility = originalVisibility;
    return {width: originalWidth, height: originalHeight};    
  }
  
  return {width: element.offsetWidth, height: element.offsetHeight};
};


Position.positionedOffset = function(element) {
  var valueT = 0, valueL = 0;
  do {
    valueT += element.offsetTop  || 0;
    valueL += element.offsetLeft || 0;
    element = element.offsetParent;
    if (element) {
      p = Element.getStyle(element,'position');
      if(p == 'relative' || p == 'absolute') break;
    }
  } while (element);
  return [valueL, valueT];
};

// Safari returns margins on body which is incorrect if the child is absolutely positioned.
// for performance reasons, we create a specialized version of Position.cumulativeOffset for
// KHTML/WebKit only

if(/Konqueror|Safari|KHTML/.test(navigator.userAgent)) {
  Position.cumulativeOffset = function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      
      if (element.offsetParent==document.body) 
        if (Element.getStyle(element,'position')=='absolute') break;
        
      element = element.offsetParent;
    } while (element);
    return [valueL, valueT];
  };
}

Position.page = function(forElement) {
  var valueT = 0, valueL = 0;

  var element = forElement;
  do {
    valueT += element.offsetTop  || 0;
    valueL += element.offsetLeft || 0;

    // Safari fix
    if (element.offsetParent==document.body)
      if (Element.getStyle(element,'position')=='absolute') break;
      
  } while (element = element.offsetParent);

  element = forElement;
  do {
    valueT -= element.scrollTop  || 0;
    valueL -= element.scrollLeft || 0;    
  } while (element = element.parentNode);

  return [valueL, valueT];
};

// elements with display:none don't return an offsetParent, 
// fall back to  manual calculation
Position.offsetParent = function(element) {
  if(element.offsetParent) return element.offsetParent;
  if(element == document.body) return element;
  
  while ((element = element.parentNode) && element != document.body)
    if (Element.getStyle(element,'position')!='static')
      return element;
  
  return document.body;
};

Position.clone = function(source, target) {
  var options = Object.extend({
    setLeft:    true,
    setTop:     true,
    setWidth:   true,
    setHeight:  true,
    offsetTop:  0,
    offsetLeft: 0
  }, arguments[2] || {});
  
  // find page position of source
  source = $(source);
  var p = Position.page(source);

  // find coordinate system to use
  target = $(target);
  var delta = [0, 0];
  var parent = null;
  // delta [0,0] will do fine with position: fixed elements, 
  // position:absolute needs offsetParent deltas
  if (Element.getStyle(target,'position') == 'absolute') {
    parent = Position.offsetParent(target);
    delta = Position.page(parent);
  }
  
  // correct by body offsets (fixes Safari)
  if (parent==document.body) {
    delta[0] -= document.body.offsetLeft;
    delta[1] -= document.body.offsetTop; 
  }

  // set position
  if(options.setLeft)   target.style.left  = (p[0] - delta[0] + options.offsetLeft) + "px";
  if(options.setTop)    target.style.top   = (p[1] - delta[1] + options.offsetTop) + "px";
  if(options.setWidth)  target.style.width = source.offsetWidth + "px";
  if(options.setHeight) target.style.height = source.offsetHeight + "px";
};

Position.absolutize = function(element) {
  element = $(element);
  if(element.style.position=='absolute') return;
  Position.prepare();

  var offsets = Position.positionedOffset(element);
  var top     = offsets[1];
  var left    = offsets[0];
  var width   = element.clientWidth;
  var height  = element.clientHeight;

  element._originalLeft   = left - parseFloat(element.style.left  || 0);
  element._originalTop    = top  - parseFloat(element.style.top || 0);
  element._originalWidth  = element.style.width;
  element._originalHeight = element.style.height;

  element.style.position = 'absolute';
  element.style.top    = top + 'px';
  element.style.left   = left + 'px';
  element.style.width  = width + 'px';
  element.style.height = height + 'px';
};

Position.relativize = function(element) {
  element = $(element);
  if(element.style.position=='relative') return;
  Position.prepare();

  element.style.position = 'relative';
  var top  = parseFloat(element.style.top  || 0) - (element._originalTop || 0);
  var left = parseFloat(element.style.left || 0) - (element._originalLeft || 0);

  element.style.top    = top + 'px';
  element.style.left   = left + 'px';
  element.style.height = element._originalHeight;
  element.style.width  = element._originalWidth;
};


Element.Class = {
    // Element.toggleClass(element, className) toggles the class being on/off
    // Element.toggleClass(element, className1, className2) toggles between both classes,
    //   defaulting to className1 if neither exist
    toggle: function(element, className) {
      if(Element.Class.has(element, className)) {
        Element.Class.remove(element, className);
        if(arguments.length == 3) Element.Class.add(element, arguments[2]);
      } else {
        Element.Class.add(element, className);
        if(arguments.length == 3) Element.Class.remove(element, arguments[2]);
      }
    },

    // gets space-delimited classnames of an element as an array
    get: function(element) {
      return $(element).className.split(' ');
    },

    // functions adapted from original functions by Gavin Kistner
    remove: function(element) {
      element = $(element);
      var removeClasses = arguments;
      $R(1,arguments.length-1).each( function(index) {
        element.className = 
          element.className.split(' ').reject( 
            function(klass) { return (klass == removeClasses[index]) } ).join(' ');
      });
    },

    add: function(element) {
      element = $(element);
      for(var i = 1; i < arguments.length; i++) {
        Element.Class.remove(element, arguments[i]);
        element.className += (element.className.length > 0 ? ' ' : '') + arguments[i];
      }
    },

    // returns true if all given classes exist in said element
    has: function(element) {
      element = $(element);
      if(!element || !element.className) return false;
      var regEx;
      for(var i = 1; i < arguments.length; i++) {
        if((typeof arguments[i] == 'object') && 
          (arguments[i].constructor == Array)) {
          for(var j = 0; j < arguments[i].length; j++) {
            regEx = new RegExp("(^|\\s)" + arguments[i][j] + "(\\s|$)");
            if(!regEx.test(element.className)) return false;
          }
        } else {
          regEx = new RegExp("(^|\\s)" + arguments[i] + "(\\s|$)");
          if(!regEx.test(element.className)) return false;
        }
      }
      return true;
    }
};
