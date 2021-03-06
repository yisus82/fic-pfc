package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.WMSChunkTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class FindWMSsAction extends DefaultAction {

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

	/* Do find WMSs. */
	return doFindWMSs(mapping, startIndex, count, request);
    }

    private ActionForward doFindWMSs(ActionMapping mapping, int startIndex,
	    int count, HttpServletRequest request)
	    throws InternalErrorException {
	/* Find WMSs. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	WMSChunkTO WMSs;
	WMSs = searchFacadeDelegate.findWMSs(startIndex, count);

	if (WMSs.getWMSTOs().size() > 0) {
	    request.setAttribute("WMSs", WMSs.getWMSTOs());

	    if (WMSs.getExistMoreWMSs())
		request.setAttribute("next", startIndex + count);

	    if ((startIndex - count) > 1)
		request.setAttribute("previous", startIndex - count);
	    else if (startIndex > 1) request.setAttribute("previous", 1);
	}

	request.setAttribute("interestingWMSs", SessionManager
		.getInterestingWMSs(request));

	request.setAttribute("mode", "WMSs");

	/* Return ActionForward. */
	return mapping.findForward("FindWMSsContent");
    }

}
