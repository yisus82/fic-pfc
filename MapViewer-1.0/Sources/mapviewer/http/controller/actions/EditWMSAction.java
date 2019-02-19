package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.wms.to.WMSTO;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class EditWMSAction extends DefaultAction {

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
	DynaActionForm WMSForm = (DynaActionForm) form;

	/*
	 * If the request is to allow the user to correct errors in the form,
	 * "WMSForm" must not be modified.
	 */
	String action = (String) WMSForm.get("action");

	if (request.getAttribute(Globals.ERROR_KEY) == null) {

	    /*
	     * If the user is updating the WMS, set the rest of attributes.
	     */

	    if ("UPDATE".equals(action)) {

		SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
			.getDelegate();

		Long WMSID = new Long(request.getParameter("WMSID"));

		WMSTO WMSTO;
		try {
		    WMSTO = searchFacadeDelegate.findWMS(WMSID);
		    WMSForm.set("name", WMSTO.getName());
		    WMSForm.set("description", WMSTO.getDescription());
		    WMSForm.set("URL", WMSTO.getURL());
		    WMSForm.set("WMSID", WMSID.toString());
		} catch (InstanceNotFoundException e) {
		    throw new InternalErrorException(e);
		}
	    }

	}

	request.setAttribute("mode", "WMSs");

	/* Return ActionForward. */
	if ("UPDATE".equals(action)) {
	    return mapping.findForward("UpdateWMSForm");
	}
	return mapping.findForward("AddWMSForm");
    }

}
