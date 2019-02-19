package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class DeleteInterestingMapAction extends DefaultAction {

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
	Long mapID = new Long(request.getParameter("mapID"));

	/* Delete interesting map. */
	ActionMessages errors = new ActionMessages();

	try {
	    SessionManager.deleteInterestingMap(request, mapID);
	} catch (InstanceNotFoundException e) {
	    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		    "ErrorMessages.mapID.notFound"));
	}

	request.setAttribute("mode", "maps");

	/* Return ActionForward. */
	if (errors.isEmpty()) {
	    return mapping.findForward("MainPage");
	}
	saveErrors(request, errors);
	return new ActionForward(mapping.getInput());
    }

}
