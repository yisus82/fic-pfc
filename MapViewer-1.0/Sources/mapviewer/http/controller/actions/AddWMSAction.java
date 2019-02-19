package mapviewer.http.controller.actions;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.layer.to.LayerTO;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.userfacade.delegate.UserFacadeDelegate;
import mapviewer.model.wms.to.WMSTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;
import org.xml.sax.SAXException;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class AddWMSAction extends DefaultAction {

    private List<LayerTO> getLayers(Document xmlDocument) {
	List<LayerTO> layerTOs = new ArrayList<LayerTO>();

	Element root = xmlDocument.getRootElement();

	for (Iterator<Element> iterator = root
		.getDescendants(new ElementFilter("Layer")); iterator.hasNext();) {
	    Element layerNode = iterator.next();
	    String name = layerNode.getChildText("Name");
	    if (name != null) {
		String title = layerNode.getChildText("Title");

		Double minLatitude = null;
		Double maxLatitude = null;
		Double minLongitude = null;
		Double maxLongitude = null;

		Element EX_GeographicBoundingBox = layerNode
			.getChild("EX_GeographicBoundingBox");
		Element LatLonBoundingBox = layerNode
			.getChild("LatLonBoundingBox");
		Element BoundingBox = layerNode.getChild("BoundingBox");

		if (EX_GeographicBoundingBox != null) {
		    minLatitude = new Double(EX_GeographicBoundingBox
			    .getChildText("southBoundLatitude"));
		    maxLatitude = new Double(EX_GeographicBoundingBox
			    .getChildText("northBoundLatitude"));
		    minLongitude = new Double(EX_GeographicBoundingBox
			    .getChildText("westBoundLongitude"));
		    maxLongitude = new Double(EX_GeographicBoundingBox
			    .getChildText("eastBoundLongitude"));
		} else if (LatLonBoundingBox != null) {
		    minLatitude = new Double(LatLonBoundingBox
			    .getAttributeValue("miny"));
		    maxLatitude = new Double(LatLonBoundingBox
			    .getAttributeValue("maxy"));
		    minLongitude = new Double(LatLonBoundingBox
			    .getAttributeValue("minx"));
		    maxLongitude = new Double(LatLonBoundingBox
			    .getAttributeValue("maxx"));
		} else if (BoundingBox != null) {
		    minLatitude = new Double(BoundingBox
			    .getAttributeValue("miny"));
		    maxLatitude = new Double(BoundingBox
			    .getAttributeValue("maxy"));
		    minLongitude = new Double(BoundingBox
			    .getAttributeValue("minx"));
		    maxLongitude = new Double(BoundingBox
			    .getAttributeValue("maxx"));
		}
		List<Element> parentStyles = layerNode.getParentElement()
			.getChildren("Style");
		List<Element> styles = layerNode.getChildren("Style");
		for (Element parentStyle : parentStyles)
		    layerTOs.add(new LayerTO(new Long("-1"), title, name,
			    parentStyle.getChildText("Title"), parentStyle
				    .getChildText("Name"), minLatitude,
			    minLongitude, maxLatitude, maxLongitude, new Long(
				    "-1")));
		for (Element style : styles)
		    layerTOs.add(new LayerTO(new Long("-1"), title, name, style
			    .getChildText("Title"), style.getChildText("Name"),
			    minLatitude, minLongitude, maxLatitude,
			    maxLongitude, new Long("-1")));
		if ((parentStyles.isEmpty()) && (styles.isEmpty()))
		    layerTOs.add(new LayerTO(new Long("-1"), title, name, null,
			    null, minLatitude, minLongitude, maxLatitude,
			    maxLongitude, new Long("-1")));
	    }
	}

	return layerTOs;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.udc.fbellas.j2ee.util.struts.action.DefaultAction#doExecute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected ActionForward doExecute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws InternalErrorException {
	/* Get data. */
	DynaActionForm WMSForm = (DynaActionForm) form;
	String name = (String) WMSForm.get("name");
	String description = (String) WMSForm.get("description");
	String URL = (String) WMSForm.get("URL");
	String userID = SessionManager.getUserID(request);
	List<LayerTO> layerTOs = new ArrayList<LayerTO>();

	ActionMessages errors = new ActionMessages();

	/* Check if there is a WMS with the same URL in the database. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();
	WMSTO WMSTO = null;
	try {
	    WMSTO = searchFacadeDelegate.findWMSByURL(URL);
	} catch (InstanceNotFoundException e) {
	    try {
		URLConnection connection = new URL(URL
			+ "SERVICE=WMS&VERSION=1.1.1&REQUEST=GetCapabilities")
			.openConnection();
		Reader XML = new InputStreamReader(connection.getInputStream());
		Source source = new StreamSource(XML);
		SchemaFactory factory = SchemaFactory
			.newInstance("http://www.w3.org/2001/XMLSchema");
		URL schemaLocation = new URL(
			"http://schemas.opengis.net/wms/1.3.0/capabilities_1_3_0.xsd");
		Schema schema = factory.newSchema(schemaLocation);
		Validator validator = schema.newValidator();
		validator.validate(source);
		connection = new URL(URL
			+ "SERVICE=WMS&VERSION=1.1.1&REQUEST=GetCapabilities")
			.openConnection();
		SAXBuilder builder = new SAXBuilder();
		Document xmlDocument = builder.build(connection
			.getInputStream());
		layerTOs = getLayers(xmlDocument);
	    } catch (SAXException e2) {
		request.setAttribute("mode", "WMSs");
		errors
			.add("URL", new ActionMessage(
				"ErrorMessages.URL.notWMS"));
		saveErrors(request, errors);
		return new ActionForward(mapping.getInput());
	    } catch (Exception e2) {
		throw new InternalErrorException(e2);
	    }

	    UserFacadeDelegate userFacadeDelegate = SessionManager
		    .getUserFacadeDelegate(request);
	    WMSTO = new WMSTO(new Long("-1"), name, description, URL, userID);

	    request.setAttribute("mode", "WMSs");

	    /* Add WMS. */
	    userFacadeDelegate.addWMS(WMSTO, layerTOs);
	    return mapping.findForward("MainPage");
	}

	request.setAttribute("mode", "WMSs");

	errors.add("URL", new ActionMessage("ErrorMessages.URL.alreadyExists"));
	saveErrors(request, errors);
	return new ActionForward(mapping.getInput());
    }
}
