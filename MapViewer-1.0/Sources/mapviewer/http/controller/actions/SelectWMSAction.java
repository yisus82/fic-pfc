package mapviewer.http.controller.actions;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.LayerChunkTO;
import mapviewer.model.searchfacade.to.LayerDetailsTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class SelectWMSAction extends DefaultAction {

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
	DynaActionForm selectWMSForm = (DynaActionForm) form;
	Long mapID = new Long(selectWMSForm.getString("mapID"));
	Long WMSID = new Long(selectWMSForm.getString("WMSID"));
	int startIndex;
	int count;

	try {
	    startIndex = new Integer(request.getParameter("startIndex"));
	    count = new Integer(request.getParameter("count"));
	} catch (Exception e) {
	    startIndex = 1;
	    count = 5;
	}

	request.setAttribute("startIndex", startIndex);
	request.setAttribute("count", count);

	/* Find WMS' layers. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	LayerChunkTO layers;
	layers = searchFacadeDelegate.findLayersByWMS(WMSID, startIndex, count);

	request.setAttribute("mapID", mapID);
	request.setAttribute("WMSID", WMSID);

	if (layers.getLayerTOs().size() > 0) {
	    request.setAttribute("layers", layers.getLayerTOs());

	    if (layers.getExistMoreLayers())
		request.setAttribute("next", startIndex + count);

	    if ((startIndex - count) > 1)
		request.setAttribute("previous", startIndex - count);
	    else if (startIndex > 1) request.setAttribute("previous", 1);
	}

	request.setAttribute("mode", "maps");

	HashSet<Long> selectedLayers = new HashSet<Long>();

	for (LayerDetailsTO layer : searchFacadeDelegate.findLayersByMap(mapID,
		-1, -1).getLayerTOs())
	    selectedLayers.add(layer.getLayerID());

	SessionManager.setSelectedLayers(request, selectedLayers);

	/* Return ActionForward. */
	return mapping.findForward("SelectLayersContent");
    }
}
