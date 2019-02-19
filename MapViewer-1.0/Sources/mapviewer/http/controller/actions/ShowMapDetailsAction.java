package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.MapDetailsTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class ShowMapDetailsAction extends DefaultAction {

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
	return doFindMap(mapping, mapID, request);
    }

    private ActionForward doFindMap(ActionMapping mapping, Long mapID,
	    HttpServletRequest request) throws InternalErrorException {
	/* Find map. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	MapDetailsTO mapDetailsTO;
	try {
	    mapDetailsTO = searchFacadeDelegate.findMap(mapID);
	} catch (InstanceNotFoundException e) {
	    throw new InternalErrorException(e);
	}
	request.setAttribute("map", mapDetailsTO);

	request.setAttribute("interestingMaps", SessionManager
		.getInterestingMaps(request));

	request.setAttribute("mode", "maps");

	/* Return ActionForward. */
	return mapping.findForward("ShowMapDetailsContent");
    }

}
