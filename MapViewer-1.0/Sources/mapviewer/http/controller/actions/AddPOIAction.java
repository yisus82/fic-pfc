package mapviewer.http.controller.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.poi.to.POITO;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.userfacade.delegate.UserFacadeDelegate;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class AddPOIAction extends DefaultAction {

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
	DynaActionForm POIForm = (DynaActionForm) form;
	String name = (String) POIForm.get("name");
	String description = (String) POIForm.get("description");
	Double latitude = new Double(POIForm.getString("latitude"));
	Double longitude = new Double(POIForm.getString("longitude"));
	String HTML = (String) POIForm.get("HTML");
	HTML = HTML.replaceAll("\r\n", "<br />").replaceAll("\r", "")
		.replaceAll("\n", "").replaceAll("\b", "").replaceAll("\t", "")
		.replaceAll("\f", "");

	String tagsString = (String) POIForm.get("tags");

	String[] tags = tagsString.split(" ");
	List<TagTO> tagTOs = new ArrayList<TagTO>();
	for (String tag : tags)
	    tagTOs.add(new TagTO(tag.toLowerCase()));

	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);
	String userID = SessionManager.getUserID(request);
	POITO POITO = new POITO(new Long("-1"), name, description, latitude,
		longitude, HTML, userID);

	request.setAttribute("mode", "POIs");

	/* Add POI. */
	userFacadeDelegate.addPOI(POITO, tagTOs);
	return mapping.findForward("MainPage");
    }

}
