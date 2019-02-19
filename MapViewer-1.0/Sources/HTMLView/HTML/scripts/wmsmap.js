/**************************************************************
*  The list of parameters that are used To create a layer
*  If something is not set, the value in _D is used
*  See example_layers.js for predefined layers 
*****************************************************************/
var _D = {
      URL: 'http://wms.jpl.nasa.gov/wms.cgi?', // url of WMS server
      VERSION: '1.1.1', // 1.1.1 is default, not needed 
      REQUEST: 'GetMap', // GetMap is default, not needed
      LAYERS: ['global_mosaic'],  // an array of the layers to get
      FORMAT: 'image/png', // this is often just 'png' or 'jpg' 
      SRS: 'EPSG:4326',    // 4326 is the default
      STYLES: [],     // A list of styles, see layer def  
      BBOX: [-180,-90,180,90], // [xmin,ymin,xmax,ymax]
      TRANSPARENT: 'TRUE',  
      EXCEPTIONS: 'application/vnd.ogc.se_inimage'
      //on error, return empty image
};

//DEBUG Turn on Debugging
var DEBUG = 0;    
//dbg The html element used in debugging;
var dbg;
var dbg2;
var dbgmouse;
//WMap:: the Class used for all Map stuff (holds WLayer instances) 
var WMap = Class.create();

//:normalizeDegress class method (TODO: fix);
WMap.normalizeDegrees =function(arr){
  // todo: this max should be a parameter
  return arr;
  var max = [360,180];
  if(arr[0]<-max[0]){arr[0]+=max[0];arr[2]+=max[0]}
  if(arr[2]>max[0]){arr[2]-=max[0];arr[0]-=max[0]}
  if(arr[1]<-max[1]){arr[1]+=max[1];arr[3]+=max[1]}
  if(arr[3]>max[1]){arr[3]-=max[1];arr[1]-=max[1]}
  return arr;
}; 

WMap.toggleDEBUG = function(){
  DEBUG=!DEBUG;
  dbg.innerHTML='';dbg2.innerHTML='';dbgmouse.innerHTML="";
};



/**********************************************************
All methods available to a WMap object create a WMap 
object via   oMap = new WMap('htmlDiv',[oLayer]);
where oLayer is defined as in example_layers.js
and htmlDiv must be positioned relatively (in stylesheet)
**********************************************************/
WMap.prototype = {
   
  initialize: function(div_id,wms_arr){
/************* WMap parameters   *******************/
// div_id: the id of the div to hold the map 
// wms_arr: an array of wms_specs (see example_layers.js) 
// e.g.  var wmap = new WMap('map_div',[JPL]);
/***************  End WMap ***************************/
      dbg2 = document.createElement('DIV'); 
      document.forms[0].appendChild(dbg2);
      dbgmouse = document.createElement('DIV'); 
      document.forms[0].appendChild(dbgmouse);
      dbg = document.createElement('DIV'); 
      document.forms[0].appendChild(dbg);
    this.div = $(div_id);
    with(this.div.style){
      overflow = 'hidden';
      position = 'relative';
    }
    // The number of layers of tiles surrounding the center tile.
		// To allow map dragging;
    this.tileBuffer = 1; 
    this.LO = 1; // load other images automatically after first?
    this.nT = this.tileBuffer*2+1;
    this.imagePane = []; // div to hold the 2 sets of images
		// an array to hold images TODO: this will break if this.tileBuffer != 1;
    this.img=[[],[],[]];  
    this.originalMapExtents = wms_arr[0].BBOX;
    this.w = parseInt(this.div.style.width) ;
    this.h = parseInt(this.div.style.height) ;
    var tmp = Position.cumulativeOffset(this.div);
    // this.l and t are the offsets of the 'map' div;
    this.l = tmp[0];
    this.t = tmp[1];
    this.layers = []; 

    /******** B:the div that holds stuff **********/
    this.B =  document.createElement('DIV');
    this.B.id = 'B';
    with(this.B.style){
      zIndex = 0;
      // set height and width to NTILES * the div w , h
      width = this.nT * this.w + 'px';
      height = this.nT * this.h + 'px';
      cursor = 'move';
      position = 'relative';
      left = -this.w*this.tileBuffer + 'px'; 
      top = -this.h*this.tileBuffer  + 'px'; 
      border = '1px solid red';
    }
    this.div.appendChild(this.B);
    /*****************  End B ******************/

    /********** contentPane holds useer added overlays *********/
    this.contentPane = document.createElement('DIV');
    with(this.contentPane.style){
      position = 'absolute';
      zIndex = 30;
      left =  '0px';
      top =  '0px';
      border = '1px solid black';
      width = this.nT * this.w +'px';
      height = this.nT * this.h + 'px';
    }
    this.B.appendChild(this.contentPane);

    /******* RegisterEvents **************/
    this.eventMouseUp  = this.mapMouseUp.bindAsEventListener(this);
    this.eventMouseDown  = this.mapMouseDown.bindAsEventListener(this);
    this.eventMouseMove  = this.mapMouseMove.bindAsEventListener(this);
    this.eventMouseOut = this.mapMouseOut.bindAsEventListener(this);
    this.eventImageLoaded = this.loadOtherImages.bindAsEventListener(this);
    this.eventLastImageLoaded = this.unstretchTiles.bindAsEventListener(this);
    this.mapQuery = this.mapQuery.bindAsEventListener(this);
    this.mapQueryCallback = this.mapQueryCallback.bindAsEventListener(this);
    /****************** End RegisterEvents **************/


    /********* Add Layers to Map and render *************/
    for(var i=0;i<wms_arr.length;i++){
       this.addLayer(wms_arr[i]);
    }; this.mapExtents = this.layers[0].BBOX;
    this.render();
    /********* End Layers and Map ********************/

    // call the functions to run after map is rendered;
    this.eventMapInitialized();  
    this.dragger = new
		Draggable('B',{starteffect:function(){},endeffect:function(){}});
  },

	// Add a layer to the map
  addLayer: function(layer){
    this.layers.push(new WLayer(layer,this));
  },

	// prototype Event stuff once map is drawn
  eventMapInitialized: function(){
    Event.observe('B',"click",this.mapQuery);
    Event.observe('B',"mouseup",this.eventMouseUp);
    Event.observe('B',"mousedown",this.eventMouseDown);
    Event.observe('B',"mousemove",this.eventMouseMove);
    Event.observe(this.div.id,"mouseout",this.eventMouseOut);
  },
  doQuery : 0,
  mapQuery : function(e){
      if(!this.doQuery){return false;}
      var pxy = [Event.pointerX(e)-this.l,Event.pointerY(e)-this.t];
      var gxy = this.pixToGeo(pxy[0],pxy[1]);
      var ul = this.pixToGeo(0,0);
      var br = this.pixToGeo(this.w,this.h);
      var qbbox = [ul[0],br[1],br[0],ul[1]];
      var src = this.layers[0].formURL(qbbox);
      var qlayers = src.match(/layers=.+?(?=&)/i);
      src += '&QUERY_' + qlayers;
      src += '&info_format=text/plain'
      src += '&x=' + pxy[0] + '&y=' + pxy[1]
      src = src.replace(/request=.+?(?=&)/i,'request=GetFeatureInfo');
      if(DEBUG){
          dbg2.innerHTML = '<a href=' + src + '>query</a>';
      }
      src = 'remote_get.php?fn=' + encodeURIComponent(src);
      jfetch(src,this.mapQueryCallback);
  },
  
  mapQueryCallback: function(txt){
      // alert(txt)
  },
  mapMouseOut: function(e){
    this.mouseDown = 0;
    var co = this.currentOffset();
		// if there is decent offset and the mouse is outside
		// the div, its a good time to redraw();
    if(Math.abs(co[0])>this.w/2.2 ||
    Math.abs(co[1])> this.h/2.2){
      //TODO: recenter tiles by reshuffling, not redrawing;
      this.redraw();
    }
    this.dragger.finishDrag(e,false);
  },


  mapMouseMove : function(e){
    this.mapMouseX = Event.pointerX(e)-this.l;
    this.mapMouseY = Event.pointerY(e)-this.t;
    if(DEBUG){
      var gxy = this.pixToGeo(this.mapMouseX,this.mapMouseY);
      var pxy = this.geoToPix(gxy[0],gxy[1]);
      gxy[0]=gxy[0].toString().substr(0,10);
      gxy[1]=gxy[1].toString().substr(0,8);
      dbgmouse.innerHTML ='('+ gxy.toString() +') => (' +pxy.toString()+')'
    }
  },

  mapMouseDown : function(e){
     this.mouseDown = 1;
     this.firstX = Event.pointerX(e) - this.l;
     this.firstY = Event.pointerY(e) - this.t;
  },

  mapMouseUp : function(e){
     this.lastX = Event.pointerX(e) - this.l;
     this.lastY = Event.pointerY(e) - this.t;
     this.mouseDown = 0;
     if(Math.abs(this.lastX-this.firstX) > 1 || Math.abs(this.lastY-this.firstY) > 1){
        this.mapMoved();
        if(DEBUG){
          dbg.innerHTML +='<br/>('+ this.firstX + "," + this.firstY +")"
          + " : (" + this.lastX +','+ this.lastY + ')<br/>';
        }
     }


  },
	// See how far div 'B' is from starting pos
  currentOffset: function(){
     var l0 = -this.w*this.tileBuffer;
     var t0 = -this.h*this.tileBuffer;
     var lc = parseInt(this.B.style.left);
     var tc = parseInt(this.B.style.top);
     return([l0-lc,t0-tc]);
  },

  mapMoved: function(){
    if(DEBUG){
      dbg.innerHTML ='';
      this.layers[0].getCapabilities();
    }
    var p0 = this.pixToGeo(this.firstX , this.firstY);
    var p1 = this.pixToGeo(this.lastX , this.lastY);
    var dX = p0[0]-p1[0]; 
    var dY = p0[1]-p1[1]; 
    if(DEBUG){
      dbg2.innerHTML = 'current offset: ' + this.currentOffset();
    }
    // correct the bounding box after drag
    this.layers.each(function(lyr){
      lyr.BBOX[0]+=dX; lyr.BBOX[2]+=dX;
      lyr.BBOX[1]+=dY; lyr.BBOX[3]+=dY;
      lyr.BBOX = WMap.normalizeDegrees(lyr.BBOX);
    });
    this.mapExtents = this.layers[0].BBOX;
    if(DEBUG){
      dbg.innerHTML +='<br/>New BBOX: '+this.layers[0].BBOX + '<br/>';
      dbg.innerHTML +='new Center: ' + this.getCenterGeo() + '<br/>';
    }
    var co = this.currentOffset();
    if(
    Math.abs(co[0]) > this.w/1.5 ||
    Math.abs(co[1]) > this.h/1.5){
      //TODO: instead of redraw() need to shuffle tiles;
      this.redraw();
    }
    
  },
	// TODO: this would allow reuse of tiles, currently unused because
	// i got confused;
  shuffle: function(xDir,yDir){
    // TODO: think about this:
    if(xDir==-1 || yDir==-1){
    for(var k = 0;k<this.layers.length;k++){
      for(var i=0;i<this.nT+xDir;i++){
        for(var j=0;j<this.nT+yDir;j++){
          $(k+'_'+i+'_'+j).src = $(k+'_'+(i-xDir)+'_'+(j-yDir)).src;
        }
      }
    }
		// can combine this into above
    }else if(xDir || yDir){
    for(var k=this.layers.length-1;k>=0;k--){
      var i = this.nT-xDir;
      while(i-->0){
        var j = this.nT-yDir;
        while(j-->0){
          // TODO: this +/- before xDir/yDir might be whack
          $(k+'_'+i+'_'+j).src = $(k+'_'+(i+xDir)+'_'+(j+yDir)).src;
        }
      }
    }
    }
  },
  // TODO: redraw() is alias of render, not needed;
  redraw: function(){
   this.render();
  },

	// Recenter 'B' div after render()
  recenter: function(){
    this.B.style.left = -this.w + 'px';
    this.B.style.top = -this.h  + 'px';
  },
  serialize: function(i){ return this.layers[i].formURL()},

  render: function(){
    for(var lyr =0;lyr<this.layers.length;lyr++){
      if(!this.imagePane[lyr]){
        this.initImagePane(lyr);
      }
      // other layers are drawn when this image loads
      this.img[lyr][1][1].src = this.layers[lyr].formURL();
      if(DEBUG){
        dbg.innerHTML += "<a href="+this.img[lyr][1][1].src+">"+
      this.img[lyr][1][1].src +"</a>";
      }
    }
  },


  // Initialize the div's and images for map layers
  initImagePane: function(lyr){
    this.curLayer =lyr;
    this.imagePane[lyr] = document.createElement('DIV');
    with(this.imagePane[lyr].style){
      position = 'absolute';
      zIndex =  (lyr+2);
      left =  '0px';
      top =  '0px';;
      width = this.nT * this.w +'px';
      height = this.nT * this.h + 'px';
    }
    //this.img holds the (currently) 9 images 
    // the center one is set here, others below
    this.img[lyr]=[[],[],[]]; 
    var T= this.tileBuffer;
    this.B.appendChild(this.imagePane[lyr]);
    for(var i=-T;i<=T;i++){
      for(var j=-T;j<=T;j++){

         this.img[lyr][i+T][j+T] = document.createElement('img');
         var t=((j+1)*this.h)+'px';
         var l=((i+1)*this.w)+'px';
         with(this.img[lyr][i+T][j+T]){
           style.position = 'absolute';
           style.left= l;
           style.top = t; 
           style.width = this.w + 'px';
           style.height = this.h + 'px';
           id =lyr+'_'+ i+'_'+j;
           src='tpix.gif';
         }
/*           this.img[lyr][i+T][j+T].onload = function(){ this.style.visibility = '' };
*/
           this.img[lyr][i+T][j+T].ol = l; // orignial left, top
           this.img[lyr][i+T][j+T].ot = t; // for unstretching;
         this.imagePane[lyr].appendChild(this.img[lyr][i+T][j+T]);
         if(DEBUG){
           this.img[lyr][i+T][j+T].style.border = '1px solid red';
         }
      }
    }
    // when the first image loads, call the other images;
    Event.observe(this.img[lyr][1][1], "load", this.eventImageLoaded );
//    Event.observe(this.img[lyr][2][2], "load", this.eventLastImageLoaded);
         if(DEBUG){
           this.img[lyr][1][1].style.border = '1px solid blue';
           this.img[lyr][1][1].style.zIndex = 20;
         }
 },

  // return the x, y of the center of the current window;
  getCenterGeo : function (){
    return [(this.mapExtents[0]+this.mapExtents[2])/2,
            (this.mapExtents[1]+this.mapExtents[3])/2,
           ];
  },

	// when the center tile is drawn, load other images
  loadOtherImages : function(){
      // the first image is loaded so we center on that
      if(!this.LO){return false;}
      // then loop through other layers
      for(var lyr=0;lyr<this.layers.length;lyr++){
      var curLayer = this.img[lyr];
      // il : image location in array surrounding center tile;
      var il = this.layers[lyr].BBOX;
      // T : the number of rows aroudn the center (1 => 9 tiles)
      var T = this.tileBuffer;
      var x_range = this.mapExtents[2]-this.mapExtents[0];
      var y_range = this.mapExtents[3]-this.mapExtents[1];
      for(var m=-T;m<=T;m++){
        for(var n=-T;n<=T;n++){
            if((!m) && (!n)){continue;} // already filled 0,0 above
//              curLayer[m+T][n+T] = this.createImg(m,n);
              curLayer[m+T][n+T].BBOX = [
                il[0]+m*x_range, 
                il[1]-n*y_range,
                il[2]+m*x_range,
                il[3]-n*y_range
              ];
              curLayer[m+T][n+T].src = 'tpix.gif';
              /*
              curLayer[m+T][n+T].style.visibility = 'none';
              */
              curLayer[m+T][n+T].src=this.layers[lyr].formURL(curLayer[m+T][n+T].BBOX);
           }
        }
      }
      this.unstretchTiles();
      this.recenter();
      this.redrawOverlays();
    },
	 // convert geographic coordinates to pixel coordinates.
	 // gX - int, the x coordinate in geo units
	 // gY - int, the y coordinate in geo units
    geoToPix : function( gX, gY ) {
        var gx_range = this.mapExtents[2]-this.mapExtents[0];
        var gy_range = this.mapExtents[3]-this.mapExtents[1];
        var pX = (gX-this.mapExtents[0])/gx_range * this.w ;
        var pY = (this.mapExtents[3]-gY)/gy_range * this.h ;
        return [Math.round(pX), Math.round(pY)];
    },

  // convert pixel coordinates to geographic coordinates.
  // pX - int, the x coordinate in pixel units
  // pY - int, the y coordinate in pixel units
    pixToGeo : function( pX, pY ) {
    // TODO: make gx/y_range a property since used often
      var gx_range = this.mapExtents[2]-this.mapExtents[0];
      var gy_range = this.mapExtents[3]-this.mapExtents[1];
      var gX = this.mapExtents[0] + (pX/this.w)*gx_range;
      var gY = this.mapExtents[1] + (1-pY/this.h) * gy_range;
      return [gX, gY];
    },
		// center on gX,gY
    centerOnPointGeo: function(gX,gY){
      var gx_rad = (this.mapExtents[2]-this.mapExtents[0])/2;
      var gy_rad = (this.mapExtents[3]-this.mapExtents[1])/2;
      this.mapExtents[0]=gX-gx_rad; this.mapExtents[1]=gY-gy_rad;
      this.mapExtents[2]=gX+gx_rad; this.mapExtents[3]=gY+gy_rad;
      this.layers.each(function(lyr){
        lyr.requestString = lyr.formURL();
      });
      this.render();
    },
    zoomIn : function(zF){
      zF=(zF)?2*zF:4;
      this.zoom(zF);
    },
		// called by zoomIn, zoomOut
    zoom : function(zF){
      this.stretchTiles(zF);
      // zF is zoomFactor, use negative to zoom out
      var dX = (this.mapExtents[2]-this.mapExtents[0])/zF;
      var dY = (this.mapExtents[3]-this.mapExtents[1])/zF;
      this.mapExtents[0]+=dX; this.mapExtents[1]+=dY;
      this.mapExtents[2]-=dX; this.mapExtents[3]-=dY;
      this.layers.each(function(lyr){
        lyr.requestString = lyr.formURL();
      });
      this.render();
      if(DEBUG){
        dbg2.innerHTML = '<br/> center x, y: ' + this.getCenterGeo().toString();
      }
    },
    stretchTiles : function(zF){
    // TODO: this only works if the currentOffsetX,Y are 0.
      return 1;
      var dX, dY;
      zF=(zF<0)?zF:zF;
      var co = this.currentOffset();
      with(this.B.style){
        dX = parseInt(width)/zF;
        dY = parseInt(height)/zF;
        alert(dX + " | " + dY);
        var mf = (Math.abs(zF)!=zF);
        width = dX/2 + parseInt(width) +'px';
        height = dY/2 + parseInt(height) + 'px';
        left = co[0]-dX + parseInt(left) + 'px';
        top = co[1]-dY + parseInt(top) + 'px';
      }
      var T = this.tileBuffer;
      for(var lyr=0;lyr<this.layers.length;lyr++){
         dX = this.w/zF;
         dY = this.h/zF;
         var newImgWidth = this.w +2* dX;
         var newImgHeight = this.h +2* dY;
         for(var i=0;i<(2*T)+1;i++){
           for(var j=0;j<(2*T)+1;j++){
             with(this.img[lyr][i][j].style){
               width=newImgWidth + 'px';
               height=newImgHeight + 'px';
               left = parseInt(left) +2*dX;
               top = parseInt(top)+2*dY;
             }
          }
        }
      }
    },
		// TODO: fix this, with stretch, speeds zoomIn,Out
    unstretchTiles: function(zF){
      return 1;
      var T = this.tileBuffer;
      with(this.B.style){
        width = this.w*this.nT + 'px'; 
        height =this.h*this.nT+'px';
        // this below gets done by recenter:
//        left =-this.w*T+  'px';
 //       top = -this.h*T+'px';
      }
      for(var lyr=0;lyr<this.layers.length;lyr++){
         for(var i=0;i<(2*T)+1;i++){
           for(var j=0;j<(2*T)+1;j++){
               with(this.img[lyr][i][j].style){
                 // use 'with'
                 width=this.w + 'px';
                 height=this.h + 'px';
                 left=this.img[lyr][i][j].ol;
                 top=this.img[lyr][i][j].ot;
               }
          }
        }
      }


    },

    zoomOut : function(zF){
      // this number 4 controls the zoom factors
      zF=(zF)?zF:2;
      this.zoom(-zF);
    }

}; // End Wmap



/****************************************************************
WLayer:: create a layer definition as a javascript object, seee
example_layers.js.  These are added to the map.
can also be used to getCapabilities or serialize (get URL);

****************************************************************/

var WLayer = Class.create();
WLayer.prototype = {
  initialize: function(spec,omap){
    // TODO:this wll jnot work for later layers
    // may hvae broken things here
    for(var k in _D){
      this[k] = spec[k] || _D[k];
    }
    for(var k in spec){
      this[k] = spec[k];
    }

    if(typeof(this.BBOX)=='string'){
      this.BBOX = this.BBOX.split(/,/);
    }
    this.initialExtents = this.BBOX;
    this.parent_map = omap;
    this.requestString = this.formURL();
  },
  formURL: function(BBOX){
    // this is horrible use a class method 
    if(BBOX){
      var tmp = BBOX;
      BBOX = this.BBOX;
      this.BBOX=tmp;
    }
    var url = this.URL;
    for (var par in _D){
      if(par == 'URL'){ continue;}
      var val = this[par] || "";
      url += '&' + par + '=' + val.toString();
    }
    url +='&width=' +this.parent_map.w;
    url +='&height=' +this.parent_map.h;
    if(BBOX){ this.BBOX=BBOX; }
    return url;
  },
  getCapabilities: function(){
    var c = this.CAPABILITIES || this.URL;
    c+= '&VERSION=' +
    this.VERSION + '&REQUEST=GetCapabilities&SERVICE=';
      c +=  this.SERVICE || 'wms';
    dbg.innerHTML += '<a href='+c+'>Get Capabilities</a>';
  }
  
};
/********************************************************************
add a googlemaps style marker to the map

map = new WMap('map',[TERRASERVER]);
var marker = map.addOverlayGeo(-120, 34,'http://www.google.com/mapfiles/marker.png');

// add an onmouseover event
    marker.onmouseover = function(){document.getElementById('extra').innerHTML =
    '<font color=blue> any image can be used as a marker </font>'};
// add an onclick event
    marker.onclick = function(){document.getElementById('extra').innerHTML =
    '<font color=blue> any image can be used as a marker </font>'};


********************************************************************/
WMap.prototype.addOverlay = function(gX,gY,imgsrc){
  var p = this.geoToPix(gX,gY);
  p.push(gX);p.push(gY);
  return this.addOverlayPix(p,imgsrc,arguments[3] || '');
};
// remove an overlay
WMap.prototype.removeOverlay = function(o){
  var c = this.contentPane.childNodes;
  for(var i=0;i<c.length;i++){
    if(o==c[i]){
      this.contentPane.removeChild(c[i]);
    }
  }
};

// remove all overlays
WMap.prototype.removeOverlays = function(){
  var c = this.contentPane.childNodes;
  var ret=[];
  for(var i=0;i<c.length;i++){
    ret = c[i];
    this.contentPane.removeChild(c[i]);
  }
};
// INTERNAL
WMap.prototype.addOverlayPix = function(pt,imgsrc){
    var offset = arguments[3] || 0;
    var o = document.createElement('img');
		o.src = imgsrc;
    o.isrc = imgsrc;
    o.gX = pt[2];
    o.gY = pt[3];
    var w=this.w;var h=this.h;
    with(o.style){
      position='absolute';
      cursor = 'default';
    }
    this.contentPane.appendChild(o);
		return o;
};

// INTERNAL: used after zoom
WMap.prototype.redrawOverlays = function(){
//  var ovls = this.removeOverlays();
  var ovls = this.contentPane.childNodes;
  for(var i=0;i<ovls.length;i++){
    var o = ovls[i];
    var p = this.geoToPix(o.gX,o.gY);
    var l = -o.width/2+this.w+p[0];
    var t = -o.height+this.h+p[1];
    o.style.left = l + 'px';
    o.style.top = t + 'px';
   }
};

// this doen'st really belong here, but its short and simple.
// see: http://128.32.253.220/jslibs/jfetch.js
function jfetch(url,target,context) {
  var req = (typeof XMLHttpRequest!= undefined )?new XMLHttpRequest():new ActiveXObject("Microsoft.XMLHTTP");
  req.open("GET",url,true);
  req.onreadystatechange = function() {
    if(req.readyState == 4){
      var rsp = req.responseText;
      if(target.constructor == Function){
        target.call(context,rsp);
      }else{
        var t = document.getElementById(target);
        if(t.value==undefined){t.innerHTML=rsp;}else{t.value=rsp;}
      }
    }
  };
  req.send(null);
}
