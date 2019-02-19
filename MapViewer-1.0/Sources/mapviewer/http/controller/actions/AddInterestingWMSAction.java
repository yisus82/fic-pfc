package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.userfacade.exceptions.AlreadyOwnedException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.udc.fbellas.j2ee.util.exceptions.DuplicateInstanceException;
import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class AddInterestingWMSAction extends DefaultAction {

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
	Long WMSID = new Long(request.getParameter("WMSID"));

	/* Add interesting WMS. */
	ActionMessages errors = new ActionMessages();

	try {
	    SessionManager.addInterestingWMS(request, WMSID);
	} catch (InstanceNotFoundException e) {
	    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		    "ErrorMessages.WMSID.notFound"));
	} catch (DuplicateInstanceException e) {
	    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		    "ErrorMessages.WMSID.duplicated"));
	} catch (AlreadyOwnedException e) {
	    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		    "ErrorMessages.WMSID.alreadyOwned"));
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
