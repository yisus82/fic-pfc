package mapviewer.http.controller.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.map.to.MapTO;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.userfacade.delegate.UserFacadeDelegate;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class AddMapAction extends DefaultAction {

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
	DynaActionForm mapForm = (DynaActionForm) form;
	String name = (String) mapForm.get("name");
	String description = (String) mapForm.get("description");
	String minLongitudeString;
	String minLatitudeString;
	String maxLongitudeString;
	String maxLatitudeString;

	minLatitudeString = request.getParameter("minLatitude");
	if ((minLatitudeString == "") || (minLatitudeString == null))
	    minLatitudeString = (String) mapForm.get("minLatitude");
	else
	    mapForm.set("minLatitude", minLatitudeString);
	minLongitudeString = request.getParameter("minLongitude");
	if ((minLongitudeString == "") || (minLongitudeString == null))
	    minLongitudeString = (String) mapForm.get("minLongitude");
	else
	    mapForm.set("minLongitude", minLongitudeString);
	maxLatitudeString = request.getParameter("maxLatitude");
	if ((maxLatitudeString == "") || (maxLatitudeString == null))
	    maxLatitudeString = (String) mapForm.get("maxLatitude");
	else
	    mapForm.set("maxLatitude", maxLatitudeString);
	maxLongitudeString = request.getParameter("maxLongitude");
	if ((maxLongitudeString == "") || (maxLongitudeString == null))
	    maxLongitudeString = (String) mapForm.get("maxLongitude");
	else
	    mapForm.set("maxLongitude", maxLongitudeString);

	Double minLongitude = new Double(minLongitudeString);
	Double minLatitude = new Double(minLatitudeString);
	Double maxLongitude = new Double(maxLongitudeString);
	Double maxLatitude = new Double(maxLatitudeString);

	String tagsString = (String) mapForm.get("tags");

	ActionMessages errors = new ActionMessages();

	if ((minLongitude < -180) || (minLongitude > 180)) {
	    request.setAttribute("mode", "maps");
	    errors.add("minLongitude", new ActionMessage(
		    "ErrorMessages.badLongitude"));
	    saveErrors(request, errors);
	    return new ActionForward(mapping.getInput());
	}

	if ((maxLongitude < -180) || (maxLongitude > 180)) {
	    request.setAttribute("mode", "maps");
	    errors.add("maxLongitude", new ActionMessage(
		    "ErrorMessages.badLongitude"));
	    saveErrors(request, errors);
	    return new ActionForward(mapping.getInput());
	}

	if ((minLatitude < -90) || (minLatitude > 90)) {
	    request.setAttribute("mode", "maps");
	    errors.add("minLatitude", new ActionMessage(
		    "ErrorMessages.badLatitude"));
	    saveErrors(request, errors);
	    return new ActionForward(mapping.getInput());
	}

	if ((maxLatitude < -90) || (maxLatitude > 90)) {
	    request.setAttribute("mode", "maps");
	    errors.add("maxLatitude", new ActionMessage(
		    "ErrorMessages.badLatitude"));
	    saveErrors(request, errors);
	    return new ActionForward(mapping.getInput());
	}

	if (minLongitude > maxLongitude) {
	    request.setAttribute("mode", "maps");
	    errors.add("maxLongitude", new ActionMessage(
		    "ErrorMessages.incorrectLongitudes"));
	    saveErrors(request, errors);
	    return new ActionForward(mapping.getInput());
	}

	if (minLatitude > maxLatitude) {
	    request.setAttribute("mode", "maps");
	    errors.add("maxLatitude", new ActionMessage(
		    "ErrorMessages.incorrectLatitudes"));
	    saveErrors(request, errors);
	    return new ActionForward(mapping.getInput());
	}

	String[] tags = tagsString.split(" ");
	List<TagTO> tagTOs = new ArrayList<TagTO>();
	for (String tag : tags)
	    tagTOs.add(new TagTO(tag.toLowerCase()));

	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);
	String userID = SessionManager.getUserID(request);
	MapTO mapTO = new MapTO(new Long("-1"), name, description, minLatitude,
		minLongitude, maxLatitude, maxLongitude, userID);

	request.setAttribute("mode", "maps");

	/* Add map. */
	mapTO = userFacadeDelegate.addMap(mapTO, tagTOs);
	request.setAttribute("mapID", mapTO.getMapID());
	return mapping.findForward("ShowSelectWMS");
    }

}
