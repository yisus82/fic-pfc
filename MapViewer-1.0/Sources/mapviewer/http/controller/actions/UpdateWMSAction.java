package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.userfacade.delegate.UserFacadeDelegate;
import mapviewer.model.userfacade.exceptions.NotAuthorizedException;
import mapviewer.model.wms.to.WMSTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class UpdateWMSAction extends DefaultAction {

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
	/* Get data. */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);
	DynaActionForm WMSForm = (DynaActionForm) form;
	Long WMSID = new Long((String) WMSForm.get("WMSID"));
	String name = (String) WMSForm.get("name");
	String description = (String) WMSForm.get("description");
	String URL = (String) WMSForm.get("URL");
	String userID = SessionManager.getUserID(request);
	WMSTO WMSTO = new WMSTO(WMSID, name, description, URL, userID);

	/* UPdate WMS. */
	ActionMessages errors = new ActionMessages();

	try {
	    userFacadeDelegate.updateWMS(WMSTO);
	} catch (InstanceNotFoundException e) {
	    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		    "ErrorMessages.WMSID.notFound"));
	} catch (NotAuthorizedException e) {
	    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		    "ErrorMessages.WMSID.notAuthorized.delete"));
	}

	request.setAttribute("mode", "WMSs");

	/* Return ActionForward. */
	if (errors.isEmpty()) {
	    return mapping.findForward("MainPage");
	}
	saveErrors(request, errors);
	return new ActionForward(mapping.getInput());
    }

}
