package mapviewer.http.controller.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.POIDetailsTO;
import mapviewer.model.tag.to.TagTO;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class EditPOIAction extends DefaultAction {

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
	/* Fill "form". */
	DynaActionForm POIForm = (DynaActionForm) form;

	/*
	 * If the request is to allow the user to correct errors in the form,
	 * "POIForm" must not be modified.
	 */
	String action = (String) POIForm.get("action");

	if (request.getAttribute(Globals.ERROR_KEY) == null) {

	    /*
	     * If the user is updating the POI, set the rest of attributes.
	     */

	    if ("UPDATE".equals(action)) {

		SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
			.getDelegate();

		Long POIID = new Long(request.getParameter("POIID"));

		POIDetailsTO POIDetailsTO;
		try {
		    POIDetailsTO = searchFacadeDelegate.findPOI(POIID);
		    POIForm.set("name", POIDetailsTO.getName());
		    POIForm.set("description", POIDetailsTO.getDescription());
		    POIForm.set("HTML", POIDetailsTO.getHTML());
		    POIForm.set("latitude", POIDetailsTO.getLatitude()
			    .toString());
		    POIForm.set("longitude", POIDetailsTO.getLongitude()
			    .toString());
		    String tags = "";
		    List<TagTO> tagTOs = POIDetailsTO.getTags();
		    for (TagTO tagTO : tagTOs)
			tags += tagTO.getTag() + " ";
		    /* Remove the last blank space . */
		    tags.substring(0, tags.length());
		    POIForm.set("tags", tags);
		    POIForm.set("POIID", POIID.toString());
		} catch (InstanceNotFoundException e) {
		    throw new InternalErrorException(e);
		}
	    } else if ("MAP".equals(action)) {
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		POIForm.set("latitude", latitude);
		POIForm.set("longitude", longitude);
	    }

	}

	request.setAttribute("mode", "POIs");

	/* Return ActionForward. */
	if ("UPDATE".equals(action)) {
	    return mapping.findForward("UpdatePOIForm");
	}
	return mapping.findForward("AddPOIForm");
    }

}
