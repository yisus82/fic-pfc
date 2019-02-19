package mapviewer.http.controller.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.LayerChunkTO;
import mapviewer.model.userfacade.delegate.UserFacadeDelegate;
import mapviewer.model.userfacade.exceptions.NotAuthorizedException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class SelectLayersAction extends DefaultAction {

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
	DynaActionForm selectLayersForm = (DynaActionForm) form;
	Long mapID = new Long(selectLayersForm.getString("mapID"));
	Long WMSID = new Long(selectLayersForm.getString("WMSID"));
	String action = request.getParameter("action");

	Integer startIndex;
	Integer count;

	try {
	    startIndex = new Integer(request.getParameter("startIndex"));
	    count = new Integer(request.getParameter("count"));
	} catch (Exception e) {
	    startIndex = 1;
	    count = 5;
	}

	if ((action != null)
		&& ((action.equals("SHOW")) || (action.equals("DELETE")) || (action
			.equals("ADD")))) {
	    /* Find WMS' layers. */
	    SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		    .getDelegate();

	    LayerChunkTO layers;
	    layers = searchFacadeDelegate.findLayersByWMS(WMSID, startIndex,
		    count);

	    request.setAttribute("mapID", mapID);
	    request.setAttribute("WMSID", WMSID);
	    request.setAttribute("action", action);
	    request.setAttribute("startIndex", startIndex);
	    request.setAttribute("count", count);

	    if (layers.getLayerTOs().size() > 0) {
		request.setAttribute("layers", layers.getLayerTOs());

		if (layers.getExistMoreLayers())
		    request.setAttribute("next", startIndex + count);

		if ((startIndex - count) > 1)
		    request.setAttribute("previous", startIndex - count);
		else if (startIndex > 1) request.setAttribute("previous", 1);
	    }

	    request.setAttribute("mode", "maps");

	    if (action.equals("DELETE"))
		SessionManager.deleteSelectedLayer(request, new Long(request
			.getParameter("layerID")));
	    else if (action.equals("ADD"))
		SessionManager.addSelectedLayer(request, new Long(request
			.getParameter("layerID")));

	    /* Return ActionForward. */
	    return mapping.findForward("SelectLayersContent");
	}

	HashSet<Long> selectedLayers = SessionManager
		.getSelectedLayers(request);

	List<Long> layerIDs = new ArrayList<Long>();
	for (Long selectedLayerID : selectedLayers)
	    layerIDs.add(new Long(selectedLayerID));

	/* Add layers to the map */
	ActionMessages errors = new ActionMessages();

	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	try {
	    userFacadeDelegate.updateMapLayers(mapID, layerIDs);
	} catch (InstanceNotFoundException e) {
	    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		    "ErrorMessages.mapID.notFound"));
	    saveErrors(request, errors);
	    return new ActionForward(mapping.getInput());
	} catch (NotAuthorizedException e) {
	    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		    "ErrorMessages.mapID.notAuthorized.update"));
	    saveErrors(request, errors);
	    return new ActionForward(mapping.getInput());
	}

	request.setAttribute("mode", "maps");

	/* Return ActionForward. */
	return mapping.findForward("MainPage");

    }
}
