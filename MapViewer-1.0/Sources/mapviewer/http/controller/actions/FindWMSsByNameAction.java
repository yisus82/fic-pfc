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
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class FindWMSsByNameAction extends DefaultAction {

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
	DynaActionForm findByNameForm = (DynaActionForm) form;

	String name;
	int startIndex;
	int count;

	try {
	    startIndex = new Integer(request.getParameter("startIndex"));
	    count = new Integer(request.getParameter("count"));
	} catch (Exception e) {
	    startIndex = 1;
	    count = 5;
	}

	name = request.getParameter("name");
	if ((name == "") || (name == null))
	    name = (String) findByNameForm.get("name");
	else
	    findByNameForm.set("name", name);

	request.setAttribute("startIndex", startIndex);
	request.setAttribute("count", count);

	/* Do find WMSs by name. */
	return doFindWMSsByName(mapping, name, startIndex, count, request);
    }

    private ActionForward doFindWMSsByName(ActionMapping mapping, String name,
	    int startIndex, int count, HttpServletRequest request)
	    throws InternalErrorException {
	/* Find WMSs by name. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	WMSChunkTO WMSs;

	if ((name == null) || (name.equals("")))
	    WMSs = searchFacadeDelegate.findWMSs(startIndex, count);
	else
	    WMSs = searchFacadeDelegate.findWMSsByName(name, startIndex, count);

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
	return mapping.findForward("FindWMSsByNameContent");
    }

}
