package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.POIDetailsTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class ShowPOIDetailsAction extends DefaultAction {

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
	Long POIID = new Long(request.getParameter("POIID"));

	/* Do find POI. */
	return doFindPOI(mapping, POIID, request);
    }

    private ActionForward doFindPOI(ActionMapping mapping, Long POIID,
	    HttpServletRequest request) throws InternalErrorException {
	/* Find POI. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	POIDetailsTO POIDetailsTO;
	try {
	    POIDetailsTO = searchFacadeDelegate.findPOI(POIID);
	} catch (InstanceNotFoundException e) {
	    throw new InternalErrorException(e);
	}

	request.setAttribute("POI", POIDetailsTO);

	request.setAttribute("interestingPOIs", SessionManager
		.getInterestingPOIs(request));

	request.setAttribute("mode", "POIs");

	/* Return ActionForward. */
	return mapping.findForward("ShowPOIDetailsContent");
    }

}
