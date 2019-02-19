package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.model.layer.to.LayerTO;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class ShowLayerDetailsAction extends DefaultAction {

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
	Long layerID = new Long(request.getParameter("layerID"));

	/* Do find layer. */
	return doFindLayer(mapping, layerID, request);
    }

    private ActionForward doFindLayer(ActionMapping mapping, Long layerID,
	    HttpServletRequest request) throws InternalErrorException {
	/* Find layer. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	LayerTO layerTO;
	try {
	    layerTO = searchFacadeDelegate.findLayer(layerID);
	} catch (InstanceNotFoundException e) {
	    throw new InternalErrorException(e);
	}
	request.setAttribute("layer", layerTO);

	request.setAttribute("mode", "maps");

	/* Return ActionForward. */
	return mapping.findForward("ShowLayerDetailsContent");
    }

}
