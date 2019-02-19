package mapviewer.http.controller.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.MapDetailsTO;
import mapviewer.model.tag.to.TagTO;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class EditMapAction extends DefaultAction {

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
	DynaActionForm mapForm = (DynaActionForm) form;

	/*
	 * If the request is to allow the user to correct errors in the form,
	 * "mapForm" must not be modified.
	 */
	String action = (String) mapForm.get("action");

	if (request.getAttribute(Globals.ERROR_KEY) == null) {

	    /*
	     * If the user is updating the map, set the rest of attributes.
	     */

	    if ("UPDATE".equals(action)) {

		SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
			.getDelegate();

		Long mapID = new Long(request.getParameter("mapID"));

		MapDetailsTO mapDetailsTO;
		try {
		    mapDetailsTO = searchFacadeDelegate.findMap(mapID);
		    mapForm.set("name", mapDetailsTO.getName());
		    mapForm.set("description", mapDetailsTO.getDescription());
		    mapForm.set("minLongitude", mapDetailsTO.getMinLongitude()
			    .toString());
		    mapForm.set("minLatitude", mapDetailsTO.getMinLatitude()
			    .toString());
		    mapForm.set("maxLongitude", mapDetailsTO.getMaxLongitude()
			    .toString());
		    mapForm.set("maxLatitude", mapDetailsTO.getMaxLatitude()
			    .toString());
		    request.setAttribute("minLongitude", mapDetailsTO
			    .getMinLongitude().toString());
		    request.setAttribute("minLatitude", mapDetailsTO
			    .getMinLatitude().toString());
		    request.setAttribute("maxLongitude", mapDetailsTO
			    .getMaxLongitude().toString());
		    request.setAttribute("maxLatitude", mapDetailsTO
			    .getMaxLatitude().toString());
		    String tags = "";
		    List<TagTO> tagTOs = mapDetailsTO.getTags();
		    for (TagTO tagTO : tagTOs)
			tags += tagTO.getTag() + " ";
		    /* Remove the last blank space . */
		    tags.substring(0, tags.length());
		    mapForm.set("tags", tags);
		    mapForm.set("mapID", mapID.toString());
		} catch (InstanceNotFoundException e) {
		    throw new InternalErrorException(e);
		}
	    }

	}

	request.setAttribute("mode", "maps");

	/* Return ActionForward. */
	if ("UPDATE".equals(action)) {
	    return mapping.findForward("UpdateMapForm");
	}
	return mapping.findForward("AddMapForm");
    }

}
