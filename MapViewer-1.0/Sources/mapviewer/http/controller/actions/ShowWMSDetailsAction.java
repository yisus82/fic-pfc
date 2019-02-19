package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.wms.to.WMSTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class ShowWMSDetailsAction extends DefaultAction {

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
	Long WMSID = new Long(request.getParameter("WMSID"));

	/* Do find WMS. */
	return doFindWMS(mapping, WMSID, request);
    }

    private ActionForward doFindWMS(ActionMapping mapping, Long WMSID,
	    HttpServletRequest request) throws InternalErrorException {
	/* Find WMS. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	WMSTO WMSTO;
	try {
	    WMSTO = searchFacadeDelegate.findWMS(WMSID);
	} catch (InstanceNotFoundException e) {
	    throw new InternalErrorException(e);
	}
	request.setAttribute("WMS", WMSTO);

	request.setAttribute("interestingWMSs", SessionManager
		.getInterestingWMSs(request));

	request.setAttribute("mode", "WMSs");

	/* Return ActionForward. */
	return mapping.findForward("ShowWMSDetailsContent");
    }

}
