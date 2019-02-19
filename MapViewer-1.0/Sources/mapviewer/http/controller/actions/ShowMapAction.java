package mapviewer.http.controller.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.poi.to.POITO;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.LayerDetailsTO;
import mapviewer.model.searchfacade.to.MapDetailsTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class ShowMapAction extends DefaultAction {

    /*
     * (non-Javadoc)
     * 
     * @see es.udc.fbellas.j2ee.util.struts.action.DefaultAction#doExecute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws InternalErrorException {
	/* Get initial data. */
	Long mapID = new Long(request.getParameter("mapID"));

	/* Do find map. */
	return doFindMap(mapping, mapID, request, response);
    }

    private ActionForward doFindMap(ActionMapping mapping, Long mapID,
	    HttpServletRequest request, HttpServletResponse response)
	    throws InternalErrorException {
	/* Find map. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	ActionMessages errors = new ActionMessages();
	MapDetailsTO mapDetailsTO = null;
	List<LayerDetailsTO> layerDetailsTOs = new ArrayList<LayerDetailsTO>();
	List<POITO> POITOs = new ArrayList<POITO>();
	try {
	    mapDetailsTO = searchFacadeDelegate.findMap(mapID);
	    layerDetailsTOs = searchFacadeDelegate.findLayersByMap(mapID, -1,
		    -1).getLayerTOs();
	    if (layerDetailsTOs.isEmpty())
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"ErrorMessages.mapID.noLayers"));
	    POITOs = searchFacadeDelegate.findPOIsByZone(
		    mapDetailsTO.getMinLatitude(),
		    mapDetailsTO.getMinLongitude(),
		    mapDetailsTO.getMaxLatitude(),
		    mapDetailsTO.getMaxLongitude(), -1, -1).getPOITOs();
	} catch (InstanceNotFoundException e) {
	    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		    "ErrorMessages.mapID.notFound"));
	}

	/* Return ActionForward. */
	if (!errors.isEmpty()) {
	    saveErrors(request, errors);
	    return new ActionForward(mapping.getInput());
	}

	return doShowMap(mapDetailsTO, layerDetailsTOs, POITOs, request,
		response);
    }

    private ActionForward doShowMap(MapDetailsTO mapDetailsTO,
	    List<LayerDetailsTO> layerDetailsTOs, List<POITO> POITOs,
	    HttpServletRequest request, HttpServletResponse response)
	    throws InternalErrorException {
	response.setContentType("text/html; charset=ISO-8859-1");
	PrintWriter writer = null;
	try {
	    writer = response.getWriter();
	} catch (IOException e) {
	    throw new InternalErrorException(e);
	}

	writer
		.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
	writer.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
	writer.println("<head>");
	writer
		.println("<link rel='StyleSheet' href='css/styles.css' type='text/css' media='all' />");
	writer
		.println("<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1' />");
	writer.println("<title>Map Viewer 1.0</title>");
	writer.println("<script type='text/javascript'>");
	writer.println("\tvar DEFAULTS = {");
	writer
		.println("\t\tURL: 'http://http://localhost:8888/geoserver/wms?', // url of WMS server");
	writer.println("\t\tVERSION: '1.1.1', // 1.1.1 is default, not needed");
	writer
		.println("\t\tREQUEST: 'GetMap', // GetMap is default, not needed");
	writer
		.println("\t\tLAYERS: ['tiger:giant_polygon','tiger:poly_landmarks','tiger:tiger_roads'],  // an array of the layers to get");
	writer
		.println("\t\tFORMAT: 'image/png', // this is often just 'png' or 'jpg'");
	writer
		.println("\t\tSRS: 'EPSG:4326',    // Projection: Lat/Lon (4326) is the default");
	writer
		.println("\t\tSTYLES: [],     // A list of styles, see layer def");
	writer.println("\t\tBBOX: [-180,-90,180,90], // [minx,miny,maxx,maxy]");
	writer.println("\t\tTRANSPARENT: 'TRUE',");
	writer
		.println("\t\tEXCEPTIONS: 'application/vnd.ogc.se_inimage' //on error, return empty image");
	writer.println("\t};");
	writer.println();
	for (int i = 0; i < layerDetailsTOs.size(); i++) {
	    LayerDetailsTO layerDetailsTO = layerDetailsTOs.get(i);
	    writer.println("\tvar MYMAP" + i + " = {");
	    writer.println("\t\tURL: '" + layerDetailsTO.getWMS().getURL()
		    + "',");
	    writer.print("\t\tLAYERS: [");
	    writer.print("'" + layerDetailsTO.getName() + "'");
	    writer.println("],");
	    writer.print("\t\tSTYLES: [");
	    if (layerDetailsTO.getStyleName() != null)
		writer.print("'" + layerDetailsTO.getStyleName() + "'");
	    else
		writer.print(" ");
	    writer.println("],");
	    writer.println("\t\tBBOX: [" + mapDetailsTO.getMinLongitude() + ","
		    + mapDetailsTO.getMinLatitude() + ","
		    + mapDetailsTO.getMaxLongitude() + ","
		    + mapDetailsTO.getMaxLatitude() + "]");
	    writer.println("\t};");
	    writer.println();
	}
	writer.println("</script>");
	writer
		.println("<script type='text/javascript' src='scripts/prototype.js'></script>");
	writer
		.println("<script type='text/javascript' src='scripts/dragdrop.js'></script>");
	writer
		.println("<script type='text/javascript' src='scripts/util.js'></script>");
	writer
		.println("<script type='text/javascript' src='scripts/wmsmap.js'></script>");
	writer
		.println("<script type='text/javascript' src='scripts/mapfunctions.js'></script>");
	writer.println();
	writer.println("<script type='text/javascript'>");
	writer.println("\tfunction init(){");
	writer.print("\t\twmap = new WMap('map',[");
	for (int i = 0; i < layerDetailsTOs.size(); i++) {
	    if (i == layerDetailsTOs.size() - 1)
		writer.print("MYMAP" + i);
	    else
		writer.print("MYMAP" + i + ",");
	}
	writer.println("]);");
	for (POITO POITO : POITOs) {
	    writer.println("\t\tmarker = wmap.addOverlay("
		    + POITO.getLongitude() + "," + POITO.getLatitude()
		    + ",'img/info.gif');");
	    writer.println("\t\tmarker.onclick = function(){showInfo("
		    + POITO.getLongitude() + "," + POITO.getLatitude() + ", '"
		    + POITO.getName() + "','" + POITO.getDescription() + "','"
		    + POITO.getHTML() + "'); };");

	    writer.println("\t\tmarker.style.cursor = handCursor;");
	}
	writer.println("\t\twmap.render();");
	writer
		.println("\t\tdocument.images['ZoomIn'].style.cursor = handCursor;");
	writer
		.println("\t\tdocument.images['ZoomOut'].style.cursor = handCursor;");
	writer
		.println("\t\tdocument.images['Left'].style.cursor = handCursor;");
	writer.println("\t\tdocument.images['Up'].style.cursor = handCursor;");
	writer
		.println("\t\tdocument.images['Down'].style.cursor = handCursor;");
	writer
		.println("\t\tdocument.images['Right'].style.cursor = handCursor;");
	writer.println("\t\tdocument.images['POI'].style.cursor = handCursor;");
	writer
		.println("\t\tinfodivdrag = new Draggable('info',{starteffect:function(){},endeffect:function(){}});");
	writer.println("\t}");
	writer.println("</script>");
	writer.println();
	writer.println("</head>");
	writer.println("<body onload='init()'>");
	writer.println("<form>");
	writer.println("\t<div id='window'>");
	writer.println("\t\t<div id='pageTitle'>");
	ResourceBundle messages = ResourceBundle.getBundle(
		"mapviewer.http.view.messages.Messages", SessionManager
			.getLocale(request));
	writer.println("\t\t\t<div id='text'>Map Viewer</div>");
	writer.println("\t\t</div>");
	writer.print("\t\t<div id='menu'><span id='menuWelcome'>");
	String userID = SessionManager.getUserID(request);
	if (userID == null)
	    writer.print(messages.getString("DefaultLayout.welcome"));
	else
	    writer.print(messages.getString("DefaultLayout.hello") + " "
		    + userID);
	writer.println("</span> - <span id='menuExplanation'>"
		+ messages.getString("ShowMapContent.headerSpecific")
		+ "</span>");
	writer.println("\t\t\t<div id='menuButtons'>");
	if (userID != null)
	    writer
		    .println("\t\t\t\t<input type='button' name='userProfile' value='"
			    + messages.getString("Buttons.userProfile")
			    + "' onclick=\"location.href='EditUserProfile.do?action=UPDATE'\" style='border-left: #000 1px solid; border-right: #000 1px solid; border-top: #000 1px solid; border-bottom: #0080ff 3px solid; -moz-border-radius: 0px 0px 0px 0px;' />&nbsp;");
	writer
		.println("\t\t\t\t<input type='button' name='maps' value='"
			+ messages.getString("Buttons.maps")
			+ "' onclick=\"location.href='MainPage.do?mode=maps'\" style='background-color: #4aafe0; border-left: #000 1px solid; border-right: #000 1px solid; border-top: #000 1px solid; border-bottom: #4aafe0 3px solid; -moz-border-radius: 0px 0px 0px 0px;' />&nbsp;");
	writer
		.println("\t\t\t\t<input type='button' name='POIs' value='"
			+ messages.getString("Buttons.POIs")
			+ "' onclick=\"location.href='MainPage.do?mode=POIs'\" style='border-left: #000 1px solid; border-right: #000 1px solid; border-top: #000 1px solid; border-bottom: #0080ff 3px solid; -moz-border-radius: 0px 0px 0px 0px;' />&nbsp;");
	writer
		.println("\t\t\t\t<input type='button' name='WMSs' value='"
			+ messages.getString("Buttons.WMSs")
			+ "' onclick=\"location.href='MainPage.do?mode=WMSs'\" style='border-left: #000 1px solid; border-right: #000 1px solid; border-top: #000 1px solid; border-bottom: #0080ff 3px solid; -moz-border-radius: 0px 0px 0px 0px;' />&nbsp;");
	writer
		.println("\t\t\t\t<input type='button' name='users' value='"
			+ messages.getString("Buttons.users")
			+ "' onclick=\"location.href='MainPage.do?mode=users'\" style='border-left: #000 1px solid; border-right: #000 1px solid; border-top: #000 1px solid; border-bottom: #0080ff 3px solid; -moz-border-radius: 0px 0px 0px 0px;' />&nbsp;");
	if (userID == null)
	    writer
		    .println("\t\t\t\t<input type='button' name='login' value='"
			    + messages.getString("Buttons.login")
			    + "' onclick=\"location.href='ShowAuthentication.do'\" style='border-left: #000 1px solid; border-right: #000 1px solid; border-top: #000 1px solid; border-bottom: #0080ff 3px solid; -moz-border-radius: 0px 0px 0px 0px;' />");
	else
	    writer
		    .println("\t\t\t\t<input type='button' name='logout' value='"
			    + messages.getString("Buttons.logout")
			    + "' onclick=\"location.href='Logout.do'\" style='border-left: #000 1px solid; border-right: #000 1px solid; border-top: #000 1px solid; border-bottom: #0080ff 3px solid; -moz-border-radius: 0px 0px 0px 0px;' />");
	writer.println("\t\t\t</div>");
	writer.println("\t\t</div>");
	writer
		.println("\t<div id='info' onmousedown=\"this.style.cursor='move'\" onmouseup=\"this.style.cursor='default'\" style='overflow: auto; border: blue 3px solid; background: #0080ff; visibility:hidden; position:absolute; left:750px; top:5px; z-index:10; min-height: 200 px; min-width: 400px' >");
	writer
		.println("\t\t<div id='infoName' style='background: #8181e1; border-bottom: blue 2px solid; min-width: 400px'>");
	writer.println("\t\t</div>");
	writer
		.println("\t\t<div id='infoDescription' style='background: #4aafe0; border-bottom: blue 2px solid; min-width: 400px'>");
	writer.println("\t\t</div>");
	writer.println("\t\t<div id='infoHTML' style='min-width: 400px'>");
	writer.println("\t\t</div>");
	writer.println("\t</div>");
	writer
		.println("\t<img id='ZoomIn' title='"
			+ messages.getString("ImageButton.zoomIn")
			+ "' alt='"
			+ messages.getString("ImageButton.zoomIn")
			+ "' src='img/zoomIn.gif' onclick='wmap.zoomIn()' style='z-index: 5; position: absolute; top: 450px; left: 250px;' />");
	writer
		.println("\t<img id='ZoomOut' title='"
			+ messages.getString("ImageButton.zoomOut")
			+ "' alt='"
			+ messages.getString("ImageButton.zoomOut")
			+ "' src='img/zoomOut.gif' onclick='wmap.zoomOut()' style='z-index: 5; position: absolute; top: 450px; left: 350px;' />");
	writer
		.println("\t<img id='Left' title='"
			+ messages.getString("ImageButton.Left")
			+ "' alt='"
			+ messages.getString("ImageButton.Left")
			+ "' src='img/left.gif' onclick='move(-0.01, 0)' style='z-index: 5; position: absolute; top: 450px; left: 450px;' />");
	writer
		.println("\t<img id='Up' title='"
			+ messages.getString("ImageButton.Up")
			+ "' alt='"
			+ messages.getString("ImageButton.Up")
			+ "' src='img/up.gif' onclick='move(0, 0.01)' style='z-index: 5; position: absolute; top: 450px; left: 550px;' />");
	writer
		.println("\t<img id='Down' title='"
			+ messages.getString("ImageButton.Down")
			+ "' alt='"
			+ messages.getString("ImageButton.Down")
			+ "' src='img/down.gif' onclick='move(0, -0.01)' style='z-index: 5; position: absolute; top: 450px; left: 650px;' />");
	writer
		.println("\t<img id='Right' title='"
			+ messages.getString("ImageButton.Right")
			+ "' alt='"
			+ messages.getString("ImageButton.Right")
			+ "' src='img/right.gif' onclick='move(0.01, 0)' style='z-index: 5; position: absolute; top: 450px; left: 750px;' />");
	writer
		.println("\t<img id='POI' title='"
			+ messages.getString("ImageButton.POI.On")
			+ "' alt='"
			+ messages.getString("ImageButton.POI.On")
			+ "' src='img/poi.jpg' onclick='clickPOI();' style='z-index: 5; position: absolute; top: 450px; left: 850px;' />");
	writer
		.println("\t<div id='map' style='width: 1245px; height: 350px' onclick='clickMap(event);' ondblclick='center(event)' onmousewheel='wheel(event);'>");
	writer.println("\t</div>");
	writer
		.println("\t<div id='footer' style='position:absolute; left:14px; top:439px; height: 105px;'>");
	writer.println("\t<br />");
	writer.println("\t<br />");
	writer.println("\t<br />");
	writer.println("\t<br />");
	writer.println("\t<br />");
	writer
		.println(messages.getString("DefaultFooter.softwareBy")
			+ " <a href='http://jesus-angel-perezroca-fernandez.neurona.com/'>Jesus Angel Perez-Roca Fernandez</a> (<a href='mailto:djalma_fd@yahoo.es'>djalma_fd@yahoo.es</a>)");
	writer.println("\t<br />");
	writer.println("\t\t<div id='text'> &copy; "
		+ messages.getString("DefaultFooter.copyright") + "</div>");
	writer.println("\t</div>");
	writer.println("</form>");
	writer.println("</body>");
	writer.println("</html>");

	writer.flush();
	writer.close();

	return null;
    }
}
